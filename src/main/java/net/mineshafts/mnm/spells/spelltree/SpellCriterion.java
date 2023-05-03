package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpellCriterion {
    @Nullable
    private final CriterionConditions conditions;

    public SpellCriterion(@Nullable CriterionConditions conditions) {
        this.conditions = conditions;
    }

    public SpellCriterion() {
        this.conditions = null;
    }

    public void toPacket(PacketByteBuf buf) {
    }

    public static SpellCriterion fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        Identifier identifier = new Identifier(JsonHelper.getString(obj, "trigger"));
        Criterion<CriterionConditions> criterion = Criteria.getById(identifier);
        if (criterion == null) {
            throw new JsonSyntaxException("Invalid criterion trigger: " + identifier);
        }
        CriterionConditions criterionConditions = criterion.conditionsFromJson(JsonHelper.getObject(obj, "conditions", new JsonObject()), predicateDeserializer);
        return new SpellCriterion(criterionConditions);
    }

    public static SpellCriterion fromPacket(PacketByteBuf buf) {
        return new SpellCriterion();
    }

    public static Map<String, SpellCriterion> criteriaFromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        HashMap<String, SpellCriterion> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            map.put(entry.getKey(), SpellCriterion.fromJson(JsonHelper.asObject(entry.getValue(), "criterion"), predicateDeserializer));
        }
        return map;
    }

    public static Map<String, SpellCriterion> criteriaFromPacket(PacketByteBuf buf) {
        return buf.readMap(PacketByteBuf::readString, SpellCriterion::fromPacket);
    }

    public static void criteriaToPacket(Map<String, SpellCriterion> criteria, PacketByteBuf buf2) {
        buf2.writeMap(criteria, PacketByteBuf::writeString, (buf, criterion) -> criterion.toPacket(buf));
    }

    @Nullable
    public CriterionConditions getConditions() {
        return this.conditions;
    }

    public JsonElement toJson() {
        if (this.conditions == null) {
            throw new JsonSyntaxException("Missing trigger");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trigger", this.conditions.getId().toString());
        JsonObject jsonObject2 = this.conditions.toJson(AdvancementEntityPredicateSerializer.INSTANCE);
        if (jsonObject2.size() != 0) {
            jsonObject.add("conditions", jsonObject2);
        }
        return jsonObject;
    }
}
