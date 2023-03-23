package net.mineshafts.mnm.enums.charcreationenums;

import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.DamageType;
import net.mineshafts.mnm.spells.Area;
import net.mineshafts.mnm.spells.Cone;
import net.mineshafts.mnm.spells.Line;

public enum DragonbornColor implements CharStatEnum {
    CHOOSE(-1,"mnm.menu.choose_option", null, null, null),
    BLACK(0, "black", DamageType.ACID, new Line(5,30), StatType.DEXTERITY),
    BLUE(1,"blue", DamageType.LIGHTNING,  new Line(5,30), StatType.DEXTERITY),
    BRASS(2, "brass", DamageType.FIRE, new Line(5,30), StatType.DEXTERITY),
    BRONZE(3,"bronze", DamageType.LIGHTNING, new Line(5,30),StatType.DEXTERITY),
    COPPER(4,"copper", DamageType.ACID, new Line(5,30),StatType.DEXTERITY),
    GOLD(5, "gold", DamageType.FIRE, new Cone(15),StatType.DEXTERITY),
    GREEN(6,"green",DamageType.POISON, new Cone(15), StatType.CONSTITUTION),
    RED(7,"red",DamageType.FIRE, new Cone(15), StatType.DEXTERITY),
    SILVER(8,"silver",DamageType.COLD, new Cone(15), StatType.CONSTITUTION),
    WHITE(9,"white",DamageType.COLD, new Cone(15), StatType.CONSTITUTION);
    private final int colorId;
    private final String id;
    private final DamageType damageType;
    private final Area breathWeaponShape;
    private final StatType breathWeaponSave;
    DragonbornColor(int colorId, String id, DamageType damageType, Area breathWeaponShape, StatType breathWeaponSave){
        this.colorId = colorId;
        this.id = id;
        this.damageType = damageType;
        this.breathWeaponShape = breathWeaponShape;
        this.breathWeaponSave = breathWeaponSave;
    }
    public String getTranslationKey(){
        if(this.colorId==-1)
            return this.id;
        else
            return "mnm.dragonborn."+this.id;
    }

    @Override
    public Text getText() {
        return Text.translatable(this.getTranslationKey());
    }

    public int getId(){
        return this.colorId;
    }
    public DamageType getDamageType(){
        return this.damageType;
    }
    public Area getBreathWeaponShape() {
        return breathWeaponShape;
    }
    public StatType getBreathWeaponSave() {
        return breathWeaponSave;
    }

    @Override
    public int getId(CharStatEnum Enum) {
        return DragonbornColor.SgetId(Enum);
    }
    public static int SgetId(CharStatEnum Enum) {
        return ((DragonbornColor)Enum).colorId;
    }

    @Override
    public CharStatEnum getEnum(int id) {
        return DragonbornColor.SgetEnum(id);
    }
    public static CharStatEnum SgetEnum(int id){
        for(DragonbornColor color:DragonbornColor.values()){
            if(color.colorId==id)
                return color;
        }
        return null;
    }
}
