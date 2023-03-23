package net.mineshafts.mnm.playerdata;

import net.mineshafts.mnm.enums.charcreationenums.*;

import java.util.ArrayList;

public class Proficiencies {
    private static ArrayList<StatType> savingThrows = new ArrayList<>();
    private static ArrayList<ArmorType> armor = new ArrayList<>();
    private static ArrayList<SkillsEnum> skills = new ArrayList<>();
    private static ArrayList<WeaponCategory> weaponCategories = new ArrayList<>();

    public static void clear(){
        savingThrows.clear();
        armor.clear();
        skills.clear();
        weaponCategories.clear();
    }

    public static void addProficiencies(CharStatEnum[] proficiencies){
        for (CharStatEnum proficiency: proficiencies){
            if (proficiency instanceof StatType stat)
                savingThrows.add(stat);
            if (proficiency instanceof ArmorType armorType)
                armor.add(armorType);
            if (proficiency instanceof SkillsEnum skill)
                skills.add(skill);
            if (proficiency instanceof WeaponCategory weaponCategory)
                weaponCategories.add(weaponCategory);

        }
    }
}
