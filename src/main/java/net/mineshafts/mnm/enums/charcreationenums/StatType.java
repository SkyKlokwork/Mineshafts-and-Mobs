package net.mineshafts.mnm.enums.charcreationenums;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value= EnvType.CLIENT)
public enum StatType implements CharStatEnum {
    STRENGTH(0, "strength"),
    DEXTERITY(1, "dexterity"),
    CONSTITUTION(2,"constitution"),
    INTELLIGENCE(3,"intelligence"),
    WISDOM(4,"wisdom"),
    CHARISMA(5,"charisma");
    private final int statId;
    private final String id;

    StatType(int statId, String id) {
        this.statId = statId;
        this.id = id;
    }
    public int getId() {
        return this.statId;
    }

    @Override
    public String getTranslationKey() {
        return "mnm."+id;
    }
    @Override
    public int getId(CharStatEnum Enum) {
        return SgetId(Enum);
    }
    public static int SgetId(CharStatEnum Enum) {
        return Enum.getId();
    }

    @Override
    public CharStatEnum getEnum(int id) {
        return SgetEnum(id);
    }
    public static CharStatEnum SgetEnum(int id) {
        for (StatType type: StatType.values()){
            if (type.statId==id)
                return type;
        }
        return null;
    }
}
