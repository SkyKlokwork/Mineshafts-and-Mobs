package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.*;

public class SpellProgress implements Comparable<SpellProgress>{
    final Map<String, CriterionProgress> criteriaProgresses;
    private String[][] requirements = new String[0][];

    private SpellProgress(Map<String, CriterionProgress> criteriaProgresses) {
        this.criteriaProgresses = criteriaProgresses;
    }

    public SpellProgress() {
        this.criteriaProgresses = Maps.newHashMap();
    }

    public void init(Map<String, SpellCriterion> criteria, String[][] requirements) {
        Set<String> set = criteria.keySet();
        this.criteriaProgresses.entrySet().removeIf(progress -> !set.contains(progress.getKey()));
        for (String string : set) {
            if (this.criteriaProgresses.containsKey(string)) continue;
            this.criteriaProgresses.put(string, new CriterionProgress());
        }
        this.requirements = requirements;
    }

    public boolean isDone() {
        if (this.requirements.length == 0) {
            return false;
        }
        for (String[] strings : this.requirements) {
            boolean bl = false;
            for (String string : strings) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            return false;
        }
        return true;
    }

    public boolean isAnyObtained() {
        for (CriterionProgress criterionProgress : this.criteriaProgresses.values()) {
            if (!criterionProgress.isObtained()) continue;
            return true;
        }
        return false;
    }

    public boolean obtain(String name) {
        CriterionProgress criterionProgress = this.criteriaProgresses.get(name);
        if (criterionProgress != null && !criterionProgress.isObtained()) {
            criterionProgress.obtain();
            return true;
        }
        return false;
    }

    public boolean reset(String name) {
        CriterionProgress criterionProgress = this.criteriaProgresses.get(name);
        if (criterionProgress != null && criterionProgress.isObtained()) {
            criterionProgress.reset();
            return true;
        }
        return false;
    }

    public String toString() {
        return "SpellProgress{criteria=" + this.criteriaProgresses + ", requirements=" + Arrays.deepToString(this.requirements) + "}";
    }

    public void toPacket(PacketByteBuf buf2) {
        buf2.writeMap(this.criteriaProgresses, PacketByteBuf::writeString, (buf, progresses) -> progresses.toPacket((PacketByteBuf)buf));
    }

    public static SpellProgress fromPacket(PacketByteBuf buf) {
        Map<String, CriterionProgress> map = buf.readMap(PacketByteBuf::readString, CriterionProgress::fromPacket);
        return new SpellProgress(map);
    }

    @Nullable
    public CriterionProgress getCriterionProgress(String name) {
        return this.criteriaProgresses.get(name);
    }

    public float getProgressBarPercentage() {
        if (this.criteriaProgresses.isEmpty()) {
            return 0.0f;
        }
        float f = this.requirements.length;
        float g = this.countObtainedRequirements();
        return g / f;
    }

    @Nullable
    public String getProgressBarFraction() {
        if (this.criteriaProgresses.isEmpty()) {
            return null;
        }
        int i = this.requirements.length;
        if (i <= 1) {
            return null;
        }
        int j = this.countObtainedRequirements();
        return j + "/" + i;
    }

    private int countObtainedRequirements() {
        int i = 0;
        for (String[] strings : this.requirements) {
            boolean bl = false;
            for (String string : strings) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            ++i;
        }
        return i;
    }

    public Iterable<String> getUnobtainedCriteria() {
        ArrayList<String> list = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
            if (entry.getValue().isObtained()) continue;
            list.add(entry.getKey());
        }
        return list;
    }

    public Iterable<String> getObtainedCriteria() {
        ArrayList<String> list = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
            if (!entry.getValue().isObtained()) continue;
            list.add(entry.getKey());
        }
        return list;
    }

    @Nullable
    public Date getEarliestProgressObtainDate() {
        Date date = null;
        for (CriterionProgress criterionProgress : this.criteriaProgresses.values()) {
            if (!criterionProgress.isObtained() || date != null && !criterionProgress.getObtainedDate().before(date)) continue;
            date = criterionProgress.getObtainedDate();
        }
        return date;
    }

    @Override
    public int compareTo(SpellProgress spellProgress) {
        Date date = this.getEarliestProgressObtainDate();
        Date date2 = spellProgress.getEarliestProgressObtainDate();
        if (date == null && date2 != null) {
            return 1;
        }
        if (date != null && date2 == null) {
            return -1;
        }
        if (date == null && date2 == null) {
            return 0;
        }
        return date.compareTo(date2);
    }

//    @Override
//    public /* synthetic */ int compareTo(Object other) {
//        return this.compareTo((AdvancementProgress)other);
//    }

    public static class Serializer
            implements JsonDeserializer<SpellProgress>,
            JsonSerializer<SpellProgress> {
        @Override
        public JsonElement serialize(SpellProgress spellProgress, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<String, CriterionProgress> entry : spellProgress.criteriaProgresses.entrySet()) {
                CriterionProgress criterionProgress = entry.getValue();
                if (!criterionProgress.isObtained()) continue;
                jsonObject2.add(entry.getKey(), criterionProgress.toJson());
            }
            if (!jsonObject2.entrySet().isEmpty()) {
                jsonObject.add("criteria", jsonObject2);
            }
            jsonObject.addProperty("done", spellProgress.isDone());
            return jsonObject;
        }

        @Override
        public SpellProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "advancement");
            JsonObject jsonObject2 = JsonHelper.getObject(jsonObject, "criteria", new JsonObject());
            SpellProgress spellProgress = new SpellProgress();
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                String string = entry.getKey();
                spellProgress.criteriaProgresses.put(string, CriterionProgress.obtainedAt(JsonHelper.asString(entry.getValue(), string)));
            }
            return spellProgress;
        }

//        @Override
//        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
//            return this.deserialize(functionJson, unused, context);
//        }

//        @Override
//        public /* synthetic */ JsonElement serialize(Object entry, Type unused, JsonSerializationContext context) {
//            return this.serialize((SpellProgress)entry, unused, context);
//        }
    }
}
