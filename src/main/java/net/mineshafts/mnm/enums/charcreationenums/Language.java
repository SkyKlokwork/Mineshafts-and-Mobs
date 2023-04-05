package net.mineshafts.mnm.enums.charcreationenums;

public enum Language implements CharStatEnum{
    ABANASINIAN(0, "lang.abanasinian"),
    ABYSSAL(1,"lang.abyssal"),
    CELESTIAL(2,"lang.celestial");
    private final int id;
    private final String key;
    Language(int id, String key){
        this.id = id;
        this.key = key;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTranslationKey() {
        return "mnm."+key;
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
    public static CharStatEnum SgetEnum(int id){
        for (Language type: Language.values()){
            if(type.id==id)
                return type;
        }
        return null;
    }
}
