package net.mineshafts.mnm.enums.charcreationenums;

public enum SkillsEnum implements CharStatEnum {
    CHOOSE(-1,"choose.skill.barbarian",null),
    ACROBATICS(0,"acrobatics", StatType.DEXTERITY),
    ANIMAL_HANDLING(1,"animal_handling",StatType.WISDOM),
    ARCANA(2,"arcana",StatType.INTELLIGENCE),
    ATHLETICS(3,"athletics",StatType.STRENGTH),
    DECEPTION(4,"deception",StatType.CHARISMA),
    INTIMIDATION(5,"intimidation",StatType.CHARISMA),
    INVESTIGATION(6,"investigation",StatType.INTELLIGENCE),
    MEDICINE(7,"medicine",StatType.WISDOM),
    NATURE(8,"nature",StatType.WISDOM),
    PERCEPTION(9,"perception",StatType.WISDOM),
    PERFORMANCE(10,"performance",StatType.CHARISMA),
    PERSUASION(11,"persuasion",StatType.CHARISMA),
    RELIGION(12,"religion",StatType.INTELLIGENCE),
    SLEIGHT_OF_HAND(13,"sleight_of_hand",StatType.DEXTERITY),
    STEALTH(14,"stealth",StatType.DEXTERITY),
    SURVIVAL(15,"survival",StatType.WISDOM);

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
