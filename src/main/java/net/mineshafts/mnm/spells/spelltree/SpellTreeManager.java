package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;

public class SpellTreeManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Map<Identifier, Spell> spells = Maps.newHashMap();
    private final Set<Spell> roots = Sets.newLinkedHashSet();
    private final Set<Spell> dependents = Sets.newLinkedHashSet();
    @Nullable
    private SpellTreeManager.Listener listener;

    private void remove(Spell spell) {
        for (Spell spell1 : spell.getChildren()) {
            this.remove(spell1);
        }
        LOGGER.info("Forgot about spell {}", spell.getId());
        this.spells.remove(spell.getId());
        if (spell.getParent() == null) {
            this.roots.remove(spell);
            if (this.listener != null) {
                this.listener.onRootRemoved(spell);
            }
        } else {
            this.dependents.remove(spell);
            if (this.listener != null) {
                this.listener.onDependentRemoved(spell);
            }
        }
    }

    public void removeAll(Set<Identifier> spells) {
        for (Identifier identifier : spells) {
            Spell spell = this.spells.get(identifier);
            if (spell == null) {
                LOGGER.warn("Told to remove spells {} but I don't know what that is", (Object)identifier);
                continue;
            }
            this.remove(spell);
        }
    }

    public void load(Map<Identifier, Spell.Builder> spells) {
        HashMap<Identifier, Spell.Builder> map = Maps.newHashMap(spells);
        while (!map.isEmpty()) {
            boolean bl = false;
            Iterator<Map.Entry<Identifier, Spell.Builder>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Identifier, Spell.Builder> entry = iterator.next();
                Identifier identifier = entry.getKey();
                Spell.Builder builder = entry.getValue();
                if (!builder.findParent(this.spells::get)) continue;
                Spell spell = builder.build(identifier);
                this.spells.put(identifier, spell);
                bl = true;
                iterator.remove();
                if (spell.getParent() == null) {
                    this.roots.add(spell);
                    if (this.listener == null) continue;
                    this.listener.onRootAdded(spell);
                    continue;
                }
                this.dependents.add(spell);
                if (this.listener == null) continue;
                this.listener.onDependentAdded(spell);
            }
            if (bl) continue;
            for (Map.Entry entry : map.entrySet()) {
                LOGGER.error("Couldn't load spell {}: {}", entry.getKey(), entry.getValue());
            }
        }
        LOGGER.info("Loaded {} spells", this.spells.size());
    }

    public void clear() {
        this.spells.clear();
        this.roots.clear();
        this.dependents.clear();
        if (this.listener != null) {
            this.listener.onClear();
        }
    }

    public Iterable<Spell> getRoots() {
        return this.roots;
    }

    public Collection<Spell> getSpells() {
        return this.spells.values();
    }

    @Nullable
    public Spell get(Identifier id) {
        return this.spells.get(id);
    }

    public void setListener(@Nullable SpellTreeManager.Listener listener) {
        this.listener = listener;
        if (listener != null) {
            for (Spell spell : this.roots) {
                listener.onRootAdded(spell);
            }
            for (Spell spell : this.dependents) {
                listener.onDependentAdded(spell);
            }
        }
    }

    public static interface Listener {
        public void onRootAdded(Spell var1);

        public void onRootRemoved(Spell var1);

        public void onDependentAdded(Spell var1);

        public void onDependentRemoved(Spell var1);

        public void onClear();
    }
}
