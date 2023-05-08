package net.mineshafts.mnm.enums.charcreationenums;

public enum WeaponCategory implements CharStatEnum{
    SIMPLE(0,"weapons.simple"),
    MARTIAL(1,"weapons.martial");
    private final int id;
    private final String key;
    WeaponCategory(int id, String key){
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
        return SgetEnum(name);
    }
    public static CharStatEnum SgetEnum(String name) {
        for (WeaponCategory category: WeaponCategory.values()){
            if(category.getTranslationKey().equals(name))
                return category;
        }
        return null;
    }

    public static CharStatEnum SgetEnum(int id){
        for (WeaponCategory category: WeaponCategory.values()){
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
