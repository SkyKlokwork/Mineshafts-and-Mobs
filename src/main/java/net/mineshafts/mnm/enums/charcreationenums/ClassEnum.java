package net.mineshafts.mnm.enums.charcreationenums;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.mineshafts.mnm.ItemChoice;
import net.mineshafts.mnm.StartingGold;
import net.mineshafts.mnm.gui.ManageScreen;
import net.mineshafts.mnm.gui.statgenscreens.ScoreGenSelection;
import net.mineshafts.mnm.playerdata.PlayerClass;
import net.mineshafts.mnm.playerdata.Proficiencies;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static net.mineshafts.mnm.enums.StandardEquipment.*;
import static net.mineshafts.mnm.gui.CharCreationScreen.*;

@Environment(value= EnvType.CLIENT)
public enum ClassEnum implements CharacterCreationEnum {
    // id, key, hitDie, Array of proficiencies (armor, weapons, tools, saving throws, skills)<leave last n null for n choices>, starting gold [I,J,K]->(<I>d<J> * <K>gp), Array of ItemChoices
    BARBARIAN(0, "barbarian", 12,
            new CharStatEnum[]{StatType.STRENGTH, StatType.CONSTITUTION,ArmorType.LIGHT,ArmorType.MEDIUM,ArmorType.SHIELD,WeaponCategory.SIMPLE,WeaponCategory.MARTIAL,null,null},
            new StartingGold(2,4,10),
            new ItemChoice[][]{
                    {new ItemChoice("a greataxe",GREATAXE.getStack(1)),new ItemChoice("any martial melee weapon",MARTIAL_MELEE)},
                    {new ItemChoice("two handaxes",HANDAXE.getStack(2)),new ItemChoice("any simple weapon",SIMPLE)},
                    {new ItemChoice("an explorer's pack",EXPLORERS_PACK.getStack(1))},{new ItemChoice("four javelins",JAVELIN.getStack(4))}
            }){
        @Override
        public CharStatEnum[][] getOptionsLists() {
            CharStatEnum[] list = new CharStatEnum[]{SkillsEnum.ANIMAL_HANDLING, SkillsEnum.ATHLETICS, SkillsEnum.INTIMIDATION, SkillsEnum.NATURE, SkillsEnum.PERCEPTION, SkillsEnum.SURVIVAL};
            return new CharStatEnum[][]{list, list};
        }

        @Override
        public CharacterCreationEnum getSubEnum() {
            return null;
        }

        @Override
        public int[] getButtonCounts() {
            int[] counts = new int[20];
            Arrays.fill(counts, 2);
            return counts;
        }

        @Override
        public void setResult(CharStatEnum Enum, int index) {
            proficiencies[proficiencies.length-2+index] = Enum;
        }

        @Override
        public int getAC(int dexMod, int conMod) {
            return 10+dexMod+conMod;
        }

        // Each array is one level, each int is one ability of that level, with N rows of text
        @Override
        public int[][] getAbilityCounts() {
            return new int[][]{{3,5,8,1}, {1,2}, {1}, {2}, {1,1}, {}, {2}, {2}, {2},
                    {}, {2}, {2}, {2}, {}, {1}, {2}, {1}, {1}, {2}, {1}};
        }

    },
    BARD(1, "bard", 8,null,null,null){

        @Override
        public CharacterCreationEnum[][] getOptionsLists() {
            return new CharacterCreationEnum[0][];
        }

        @Override
        public CharacterCreationEnum getSubEnum() {
            return null;
        }

        @Override
        public int[] getButtonCounts() {
            int[] counts = new int[20];
            Arrays.fill(counts, 0);
            return counts;
        }

        @Override
        public void setResult(CharStatEnum Enum, int index) {

        }

        @Override
        public int getAC(int dexMod, int conMod) {
            return 10;
        }

        @Override
        public int[][] getAbilityCounts() {
            return new int[0][];
        }
    };

    //TODO: add more classes
    private final int classId;
    private final String id;
    private final int hitDie;
    protected final CharStatEnum[] proficiencies;
    private final StartingGold startingGold;
    private final ItemChoice[][] startingEquipment;

    ClassEnum(int classId, String id, int hitDie, CharStatEnum[] proficiencies, StartingGold startingGold, ItemChoice[][] startingEquipment) {
        this.classId = classId;
        this.id = id;
        this.hitDie = hitDie;
        this.proficiencies = proficiencies;
        this.startingGold = startingGold;
        this.startingEquipment = startingEquipment;
    }

    public ItemChoice[][] getStartingEquipment(){
        return startingEquipment;
    }
    public StartingGold getStartingGold(){
        return startingGold;
    }

    @Override
    public Consumer<CharStatEnum[]> resultsSaver() {
        return results->{
            PlayerClass.setClasses();
            Proficiencies.addProficiencies(this.getProficiencies());
        };
    }
    @Override
    public CharStatEnum[] getProficiencies() {
        return proficiencies;
    }

    public int getId(){
        return this.classId;
    }
    public String getTranslationKey(){
        return "mnm."+this.id;
    }
    public Text getText(){
        return Text.translatable(this.getTranslationKey());
    }
    public int getId(CharStatEnum classEnum) {
        return ClassEnum.getID(classEnum);
    }
    public static int getID(CharStatEnum classEnum){
        return ((ClassEnum)classEnum).classId;
    }
    public ClassEnum getEnum(int id){
        return ClassEnum.SgetEnum(id);
    }
    public static ClassEnum SgetEnum(int id) {
        for(ClassEnum classEnum: ClassEnum.values()){
            if(classEnum.classId==id)
                return classEnum;
        }
        return null;
    }
    public ClassEnum getEnum(String name){
        return SgetEnum(name);
    }
    public static ClassEnum SgetEnum(String name){
        for(ClassEnum classEnum: ClassEnum.values()){
            if(Objects.equals(classEnum.getTranslationKey(), name))
                return classEnum;
        }
        return null;
    }
    @Override
    public CharacterCreationEnum getDefault() {
        return null;
    }
    public Screen infoScreen(Screen parent) {
        ManageScreen info = new ManageScreen(this,"info",parent);
        info.setNextScreen(()->this.manageScreen(info));
        return info;
    }
    public Screen manageScreen(Screen parent) {
        PlayerClass.addPlayerClass(this);
        ManageScreen manage = new ManageScreen(this,"manage",1,parent);
        manage.setNextScreen(()->ABILITIES);
        manage.setResultsSaver(this.resultsSaver());
        return manage;
    }

    public abstract int getAC(int dexMod, int conMod);

    public int getHitDie() {
        return hitDie;
    }
}
