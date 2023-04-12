package net.mineshafts.mnm.enums.charcreationenums;

public enum WeaponProperties implements CharStatEnum{
    MELEE(0,"weapons.melee"),
    RANGED(1,"weapons.ranged"),
    VERSATILE(2, "weapons.versatile");
    private final int id;
    private final String key;
    WeaponProperties(int id, String key){
        this.id = id;
        this.key = key;
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
        return null;
    }

    @Override
    public CharStatEnum getEnum(String name) {
        return null;
    }

    public static CharStatEnum SgetEnum(int id){
        for (WeaponProperties category: WeaponProperties.values()){
            if(category.id==id)
                return category;
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTranslationKey() {
        return "mnm."+key;
    }
}
