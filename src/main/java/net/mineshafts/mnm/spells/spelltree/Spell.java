package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Spell {
    @Nullable
    private final Spell parent;
    @Nullable
    private final SpellDisplay display;
//    private final AdvancementRewards rewards;
    private final Identifier id;
    private final Map<String, SpellCriterion> criteria;
    private final String[][] requirements;
    private final Set<Spell> children = Sets.newLinkedHashSet();
    private final Text text;

    public Spell(Identifier id, @Nullable Spell parent, @Nullable SpellDisplay display, Map<String, SpellCriterion> criteria, String[][] requirements) {
        this.id = id;
        this.display = display;
        this.criteria = ImmutableMap.copyOf(criteria);
        this.parent = parent;
        this.requirements = requirements;
        if (parent != null) {
            parent.addChild(this);
        }
        if (display == null) {
            this.text = Text.literal(id.toString());
        } else {
            Text text = display.getTitle();
            Formatting formatting = display.getFrame().getTitleFormat();
            MutableText text2 = Texts.setStyleIfAbsent(text.copy(), Style.EMPTY.withColor(formatting)).append("\n").append(display.getDescription());
            MutableText text3 = text.copy().styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text2)));
            this.text = Texts.bracketed(text3).formatted(formatting);
        }
    }

    public Spell.Builder createTask() {
        return new Spell.Builder(this.parent == null ? null : this.parent.getId(), this.display, this.criteria, this.requirements);
    }

    @Nullable
    public Spell getParent() {
        return this.parent;
    }

    @Nullable
    public SpellDisplay getDisplay() {
        return this.display;
    }

//    public AdvancementRewards getRewards() {
//        return this.rewards;
//    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + "}";
    }

    public Iterable<Spell> getChildren() {
        return this.children;
    }

    public Map<String, SpellCriterion> getCriteria() {
        return this.criteria;
    }

    public int getRequirementCount() {
        return this.requirements.length;
    }

    public void addChild(Spell child) {
        this.children.add(child);
    }

    public Identifier getId() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spell spell)) {
            return false;
        }
        return this.id.equals(spell.id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String[][] getRequirements() {
        return this.requirements;
    }

    public Text toHoverableText() {
        return this.text;
    }

    public static class Builder {
        @Nullable
        private Identifier parentId;
        @Nullable
        private Spell parentObj;
        @Nullable
        private SpellDisplay display;
//        private AdvancementRewards rewards = AdvancementRewards.NONE;
        private Map<String, SpellCriterion> criteria = Maps.newLinkedHashMap();
        @Nullable
        private String[][] requirements;
        private CriterionMerger merger = CriterionMerger.AND;

        Builder(@Nullable Identifier parentId, @Nullable SpellDisplay display, Map<String, SpellCriterion> criteria, String[][] requirements) {
            this.parentId = parentId;
            this.display = display;
            this.criteria = criteria;
            this.requirements = requirements;
        }

        private Builder() {
        }

        public static Spell.Builder create() {
            return new Spell.Builder();
        }

        public Spell.Builder parent(Spell parent) {
            this.parentObj = parent;
            return this;
        }

        public Spell.Builder parent(Identifier parentId) {
            this.parentId = parentId;
            return this;
        }

        public Spell.Builder display(ItemStack icon, Text title, Text description, @Nullable Identifier background, SpellFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return this.display(new SpellDisplay(icon, title, description, background, frame, showToast, announceToChat, hidden));
        }

        public Spell.Builder display(ItemConvertible icon, Text title, Text description, @Nullable Identifier background, SpellFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return this.display(new SpellDisplay(new ItemStack(icon.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
        }

        public Spell.Builder display(SpellDisplay display) {
            this.display = display;
            return this;
        }

//        public Advancement.Builder rewards(AdvancementRewards.Builder builder) {
//            return this.rewards(builder.build());
//        }

//        public Advancement.Builder rewards(AdvancementRewards rewards) {
//            this.rewards = rewards;
//            return this;
//        }

        public Spell.Builder criterion(String name, CriterionConditions conditions) {
            return this.criterion(name, new SpellCriterion(conditions));
        }

        public Spell.Builder criterion(String name, SpellCriterion criterion) {
            if (this.criteria.containsKey(name)) {
                throw new IllegalArgumentException("Duplicate criterion " + name);
            }
            this.criteria.put(name, criterion);
            return this;
        }

        public Spell.Builder criteriaMerger(CriterionMerger merger) {
            this.merger = merger;
            return this;
        }

        public Spell.Builder requirements(String[][] requirements) {
            this.requirements = requirements;
            return this;
        }

        public boolean findParent(Function<Identifier, Spell> parentProvider) {
            if (this.parentId == null) {
                return true;
            }
            if (this.parentObj == null) {
                this.parentObj = parentProvider.apply(this.parentId);
            }
            return this.parentObj != null;
        }

        public Spell build(Identifier id2) {
            if (!this.findParent(id -> null)) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            }
            if (this.requirements == null) {
                this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }
            return new Spell(id2, this.parentObj, this.display, this.criteria, this.requirements);
        }

        public Spell build(Consumer<Spell> exporter, String id) {
            Spell spell = this.build(new Identifier(id));
            exporter.accept(spell);
            return spell;
        }

        public JsonObject toJson() {
            if (this.requirements == null) {
                this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }
            JsonObject jsonObject = new JsonObject();
            if (this.parentObj != null) {
                jsonObject.addProperty("parent", this.parentObj.getId().toString());
            } else if (this.parentId != null) {
                jsonObject.addProperty("parent", this.parentId.toString());
            }
            if (this.display != null) {
                jsonObject.add("display", this.display.toJson());
            }
//            jsonObject.add("rewards", this.rewards.toJson());
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<String, SpellCriterion> entry : this.criteria.entrySet()) {
                jsonObject2.add(entry.getKey(), entry.getValue().toJson());
            }
            jsonObject.add("criteria", jsonObject2);
            JsonArray jsonArray = new JsonArray();
            for (String[] strings : this.requirements) {
                JsonArray jsonArray2 = new JsonArray();
                for (String string : strings) {
                    jsonArray2.add(string);
                }
                jsonArray.add(jsonArray2);
            }
            jsonObject.add("requirements", jsonArray);
            return jsonObject;
        }

        public void toPacket(PacketByteBuf buf) {
            if (this.requirements == null) {
                this.requirements = this.merger.createRequirements(this.criteria.keySet());
            }
            buf.writeNullable(this.parentId, PacketByteBuf::writeIdentifier);
            buf.writeNullable(this.display, (buf2, display) -> display.toPacket((PacketByteBuf)buf2));
            SpellCriterion.criteriaToPacket(this.criteria, buf);
            buf.writeVarInt(this.requirements.length);
            for (String[] strings : this.requirements) {
                buf.writeVarInt(strings.length);
                for (String string : strings) {
                    buf.writeString(string);
                }
            }
        }

        public String toString() {
            return "Task Advancement{parentId=" + this.parentId + ", display=" + this.display + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + "}";
        }

        public static Spell.Builder fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
            int i;
            Identifier identifier = obj.has("parent") ? new Identifier(JsonHelper.getString(obj, "parent")) : null;
            SpellDisplay spellDisplay = obj.has("display") ? SpellDisplay.fromJson(JsonHelper.getObject(obj, "display")) : null;
//            AdvancementRewards advancementRewards = obj.has("rewards") ? AdvancementRewards.fromJson(JsonHelper.getObject(obj, "rewards")) : AdvancementRewards.NONE;
            Map<String, SpellCriterion> map = SpellCriterion.criteriaFromJson(JsonHelper.getObject(obj, "criteria"), predicateDeserializer);
            if (map.isEmpty()) {
                throw new JsonSyntaxException("Advancement criteria cannot be empty");
            }
            JsonArray jsonArray = JsonHelper.getArray(obj, "requirements", new JsonArray());
            String[][] strings = new String[jsonArray.size()][];
            for (i = 0; i < jsonArray.size(); ++i) {
                JsonArray jsonArray2 = JsonHelper.asArray(jsonArray.get(i), "requirements[" + i + "]");
                strings[i] = new String[jsonArray2.size()];
                for (int j = 0; j < jsonArray2.size(); ++j) {
                    strings[i][j] = JsonHelper.asString(jsonArray2.get(j), "requirements[" + i + "][" + j + "]");
                }
            }
            if (strings.length == 0) {
                strings = new String[map.size()][];
                for (String string : map.keySet()) {
                    strings[i++] = new String[]{string};
                }
            }
            for (String[] strings2 : strings) {
                if (strings2.length == 0 && map.isEmpty()) {
                    throw new JsonSyntaxException("Requirement entry cannot be empty");
                }
                for (String string2 : strings2) {
                    if (map.containsKey(string2)) continue;
                    throw new JsonSyntaxException("Unknown required criterion '" + string2 + "'");
                }
            }
            for (String string3 : map.keySet()) {
                boolean bl = false;
                for (Object[] objectArray : strings) {
                    if (!ArrayUtils.contains(objectArray, string3)) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
                throw new JsonSyntaxException("Criterion '" + string3 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
            }
            return new Spell.Builder(identifier, spellDisplay, map, strings);
        }

        public static Spell.Builder fromPacket(PacketByteBuf buf) {
            Identifier identifier = buf.readNullable(PacketByteBuf::readIdentifier);
            SpellDisplay spellDisplay = buf.readNullable(SpellDisplay::fromPacket);
            Map<String, SpellCriterion> map = SpellCriterion.criteriaFromPacket(buf);
            String[][] strings = new String[buf.readVarInt()][];
            for (int i = 0; i < strings.length; ++i) {
                strings[i] = new String[buf.readVarInt()];
                for (int j = 0; j < strings[i].length; ++j) {
                    strings[i][j] = buf.readString();
                }
            }
            return new Spell.Builder(identifier, spellDisplay, map, strings);
        }

        public Map<String, SpellCriterion> getCriteria() {
            return this.criteria;
        }
    }
}
