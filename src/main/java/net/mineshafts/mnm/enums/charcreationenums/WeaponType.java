package net.mineshafts.mnm.enums.charcreationenums;

import net.mineshafts.mnm.Damage;

import static net.mineshafts.mnm.enums.charcreationenums.WeaponCategory.*;

public enum WeaponType implements CharStatEnum{
    BATTLEAXE(0,"weapon.battleaxe",10,new Damage(1,8),new Damage(1,10),MARTIAL,WeaponProperties.VERSATILE);
    private final int id;
    private final String key;
    private final WeaponCategory category;
    private final float baseCost;
    private final Damage baseDamage;
    private final Damage versatileDamage;
    private final WeaponProperties[] properties;
    WeaponType(int id, String key, float baseCost, Damage baseDamage, WeaponCategory category, WeaponProperties... properties){
        this.id = id;
        this.key = key;
        this.category = category;
        this.baseCost = baseCost;
        this.baseDamage = baseDamage;
        this.properties = properties;
        this.versatileDamage = baseDamage;
    }
    WeaponType(int id, String key, float baseCost, Damage baseDamage, Damage versatileDamage, WeaponCategory category, WeaponProperties... properties){
        this.id = id;
        this.key = key;
        this.category = category;
        this.baseCost = baseCost;
        this.baseDamage = baseDamage;
        this.properties = properties;
        this.versatileDamage = versatileDamage;
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

    @Override
    public CharStatEnum getEnum(String name) {
        return SgetEnum(name);
    }
    public static CharStatEnum SgetEnum(String name){
        for (WeaponType category: WeaponType.values()){
            if(category.getTranslationKey().equals(name))
                return category;
        }
        return null;
    }

    public static CharStatEnum SgetEnum(int id){
        for (WeaponType category: WeaponType.values()){
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
