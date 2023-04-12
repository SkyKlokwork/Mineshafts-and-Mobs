package net.mineshafts.mnm.enums.charcreationenums;

public enum ArmorType implements CharStatEnum{
    LIGHT(0, "armor.light"),
    MEDIUM(1,"armor.medium"),
    HEAVY(2,"armor.heavy"),
    SHIELD(3,"armor.shield");
    private final int id;
    private final String key;
    ArmorType(int id, String key){
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

    @Override
    public CharStatEnum getEnum(String name) {
        return SgetEnum(name);
    }
    public static CharStatEnum SgetEnum(String name){
        for (ArmorType type: ArmorType.values()){
            if(type.getTranslationKey().equals(name))
                return type;
        }
        return null;
    }

    public static CharStatEnum SgetEnum(int id){
        for (ArmorType type: ArmorType.values()){
            if(type.id==id)
                return type;
        }
        return null;
    }
}
