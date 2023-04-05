package net.mineshafts.mnm.enums.charcreationenums;

public enum SkillsEnum implements CharStatEnum {
    CHOOSE(-1,"choose.skill.barbarian",null),
    ACROBATICS(0,"acrobatics", StatType.DEXTERITY),
    ANIMAL_HANDLING(1,"animal_handling",StatType.WISDOM),
    ARCANA(2,"arcana",StatType.INTELLIGENCE),
    ATHLETICS(3,"athletics",StatType.STRENGTH),
    DECEPTION(4,"deception",StatType.CHARISMA),
    INSIGHT(5,"insight",StatType.WISDOM),
    INTIMIDATION(6,"intimidation",StatType.CHARISMA),
    INVESTIGATION(7,"investigation",StatType.INTELLIGENCE),
    MEDICINE(8,"medicine",StatType.WISDOM),
    NATURE(9,"nature",StatType.WISDOM),
    PERCEPTION(10,"perception",StatType.WISDOM),
    PERFORMANCE(11,"performance",StatType.CHARISMA),
    PERSUASION(12,"persuasion",StatType.CHARISMA),
    RELIGION(13,"religion",StatType.INTELLIGENCE),
    SLEIGHT_OF_HAND(14,"sleight_of_hand",StatType.DEXTERITY),
    STEALTH(15,"stealth",StatType.DEXTERITY),
    SURVIVAL(16,"survival",StatType.WISDOM);

    private final int skillID;
    private final String id;
    private final StatType stat;
    SkillsEnum(int skillID, String id, StatType stat){
        this.skillID = skillID;
        this.id = id;
        this.stat = stat;
    }
    public int getId() {
        return skillID;
    }
    @Override
    public String getTranslationKey() {
        if(this.skillID==-1)
            return this.id;
        return "mnm."+this.id;
    }

    @Override
    public int getId(CharStatEnum Enum) {
        return SgetId(Enum);
    }
    public static int SgetId(CharStatEnum Enum){
        return Enum.getId();
    }

    @Override
    public CharStatEnum getEnum(int id) {
        return SgetEnum(id);
    }
    public static CharStatEnum SgetEnum(int id){
        for (SkillsEnum skill: SkillsEnum.values()){
            if (skill.skillID==id)
                return skill;
        }
        return null;
    }
}
