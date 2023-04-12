package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.*;

import java.util.ArrayList;
import java.util.List;

import static net.mineshafts.mnm.networking.ModMessages.SET_PROFICIENCIES;

public class Proficiencies {
    private static final List<StatType> savingThrows = new ArrayList<>();
    private static final List<ArmorType> armor = new ArrayList<>();
    private static final List<SkillsEnum> skills = new ArrayList<>();
    private static final List<WeaponCategory> weaponCategories = new ArrayList<>();
    private static final List<WeaponType> weapons = new ArrayList<>();
    private static final List<Language> languages = new ArrayList<>();

    public static void clear(){
        savingThrows.clear();
        armor.clear();
        skills.clear();
        weaponCategories.clear();
        languages.clear();
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
            if (proficiency instanceof WeaponType weaponType)
                weapons.add(weaponType);
            if (proficiency instanceof Language language)
                languages.add(language);
        }
    }
    public static void getProficiencies(NbtCompound proficiencies) {
        clear();
        NbtList savingThrows = proficiencies.getList("mnm.savingthrows", NbtElement.STRING_TYPE);
        NbtList armor = proficiencies.getList("mnm.armor", NbtElement.STRING_TYPE);
        NbtList skills = proficiencies.getList("mnm.skills",NbtElement.STRING_TYPE);
        NbtList weaponCategories = proficiencies.getList("mnm.weaponCategories",NbtElement.STRING_TYPE);
        NbtList weapons = proficiencies.getList("mnm.weapons",NbtElement.STRING_TYPE);
        NbtList languages = proficiencies.getList("mnm.languages",NbtElement.STRING_TYPE);
        for (int i=0;i<savingThrows.size();i++){
            StatType statType = (StatType) StatType.SgetEnum(savingThrows.getString(i));
            Proficiencies.savingThrows.add(statType);
        }
        for (int i=0;i<armor.size();i++){
            ArmorType armorType = (ArmorType) ArmorType.SgetEnum(armor.getString(i));
            Proficiencies.armor.add(armorType);
        }
        for (int i=0;i<skills.size();i++){
            SkillsEnum skillsEnum = (SkillsEnum) SkillsEnum.SgetEnum(savingThrows.getString(i));
            Proficiencies.skills.add(skillsEnum);
        }
        for (int i=0;i<weaponCategories.size();i++){
            WeaponCategory weaponCategory = (WeaponCategory) WeaponCategory.SgetEnum(savingThrows.getString(i));
            Proficiencies.weaponCategories.add(weaponCategory);
        }
        for (int i=0;i<weapons.size();i++){
            WeaponType weaponType = (WeaponType) WeaponType.SgetEnum(savingThrows.getString(i));
            Proficiencies.weapons.add(weaponType);
        }
        for (int i=0;i<languages.size();i++){
            Language language = (Language) Language.SgetEnum(savingThrows.getString(i));
            Proficiencies.languages.add(language);
        }
    }
    public static void setProficiencies(){
        PacketByteBuf packet = PacketByteBufs.create();
        NbtCompound nbt = new NbtCompound();
        setProficiencyType(nbt, savingThrows, "mnm.savingthrows");
        setProficiencyType(nbt, armor, "mnm.armor");
        setProficiencyType(nbt, skills, "mnm.skills");
        setProficiencyType(nbt, weaponCategories, "mnm.weaponCategories");
        setProficiencyType(nbt, weapons, "mnm.weapons");
        setProficiencyType(nbt, languages, "mnm.languages");
        packet.writeNbt(nbt);
        ClientPlayNetworking.send(SET_PROFICIENCIES, packet);
    }
    private static <T extends CharStatEnum> void setProficiencyType(NbtCompound nbt, List<T> proficiencyList, String key){
        NbtList nbtList = new NbtList();
        for (T prof: proficiencyList)
            nbtList.add(NbtString.of(prof.getTranslationKey()));
        nbt.put(key, nbtList);
    }
}
