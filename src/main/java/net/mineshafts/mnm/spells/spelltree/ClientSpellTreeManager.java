package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ClientSpellTreeManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final MinecraftClient client;
    private final SpellTreeManager manager = new SpellTreeManager();
    private final Map<Spell, SpellProgress> spellProgress = Maps.newHashMap();
    @Nullable
    private ClientSpellTreeManager.Listener listener;
    @Nullable
    private Spell selectedTab;

    public ClientSpellTreeManager(MinecraftClient client) {
        this.client = client;
    }

    public void onSpellTree(SpellTreeUpdateS2CPacket packet) {
        if (packet.shouldClearCurrent()) {
            this.manager.clear();
            this.spellProgress.clear();
        }
        this.manager.removeAll(packet.getSpellIdsToRemove());
        this.manager.load(packet.getSpellsToEarn());
        for (Map.Entry<Identifier, SpellProgress> entry : packet.getSpellsToProgress().entrySet()) {
            Spell spell = this.manager.get(entry.getKey());
            if (spell != null) {
                SpellProgress spellProgress = entry.getValue();
                spellProgress.init(spell.getCriteria(), spell.getRequirements());
                this.spellProgress.put(spell, spellProgress);
                if (this.listener != null) {
                    this.listener.setProgress(spell, spellProgress);
                }
                if (packet.shouldClearCurrent() || !spellProgress.isDone() || spell.getDisplay() == null || !spell.getDisplay().shouldShowToast()) continue;
                this.client.getToastManager().add(new SpellToast(spell));
                continue;
            }
            LOGGER.warn("Server informed client about progress for unknown spell {}", entry.getKey());
        }
    }

    public SpellTreeManager getManager() {
        return this.manager;
    }

    public void selectTab(@Nullable Spell tab, boolean local) {
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
        if (clientPlayNetworkHandler != null && tab != null && local) {
            clientPlayNetworkHandler.sendPacket(SpellTabC2SPacket.open(tab));
        }
        if (this.selectedTab != tab) {
            this.selectedTab = tab;
            if (this.listener != null) {
                this.listener.selectTab(tab);
            }
        }
    }

    public void setListener(@Nullable ClientSpellTreeManager.Listener listener) {
        this.listener = listener;
        this.manager.setListener(listener);
        if (listener != null) {
            for (Map.Entry<Spell, SpellProgress> entry : this.spellProgress.entrySet()) {
                listener.setProgress(entry.getKey(), entry.getValue());
            }
            listener.selectTab(this.selectedTab);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static interface Listener
            extends SpellTreeManager.Listener {
        public void setProgress(Spell var1, SpellProgress var2);

        public void selectTab(@Nullable Spell var1);
    }
}
