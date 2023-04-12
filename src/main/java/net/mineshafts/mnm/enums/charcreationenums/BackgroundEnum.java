package net.mineshafts.mnm.enums.charcreationenums;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.mineshafts.mnm.ItemChoice;
import net.mineshafts.mnm.StartingGold;
import net.mineshafts.mnm.gui.BackgroundScreen;
import net.mineshafts.mnm.playerdata.Proficiencies;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static net.mineshafts.mnm.enums.StandardEquipment.*;
import static net.mineshafts.mnm.enums.charcreationenums.SkillsEnum.*;
import static net.mineshafts.mnm.gui.CharCreationScreen.EQUIPMENT;

@Environment(value= EnvType.CLIENT)
public enum BackgroundEnum implements CharacterCreationEnum {
    // id, key, Array of proficiencies (armor, weapons, tools, saving throws, skills)<leave last n null for n choices>
    ACOLYTE(0,"acolyte", new CharStatEnum[]{INSIGHT,RELIGION,null,null},
            new ItemChoice[][]{
                    {new ItemChoice("a holy symbol (a gift to you when you entered the priesthood)",A_HOLY_SYMBOL)},
                    {new ItemChoice("a prayer book",PRAYER_BOOK.getStack(1)),new ItemChoice("a prayer wheel",PRAYER_BOOK.getStack(1))},
                    {new ItemChoice("vestments",VESTMENTS.getStack(1))},
                    {new ItemChoice("a pouch containing 15 gp",MONEY_POUCH.getStack(15))},
                    {new ItemChoice("5 sticks of incense",INCENSE_STICK.getStack(1))},
    }){
        @Override
        public CharStatEnum[][] getOptionsLists() {
            CharStatEnum[] list = Language.values();
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
        public int[][] getAbilityCounts() {
            return new int[][]{{2,2}};
        }
    },
    CELEB_ADVENTURE_SCION(0,"celeb_adventure_scion", new CharStatEnum[]{PERCEPTION,PERFORMANCE,null,null},null){
        // + Disguise kit proficiency
        @Override
        public CharStatEnum[][] getOptionsLists() {
            CharStatEnum[] list = Language.values();
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
        public int[][] getAbilityCounts() {
            return new int[][]{{3,1}};
        }
    };

    //TODO: add more backgrounds
    private final int classId;
    private final String id;
    protected final CharStatEnum[] proficiencies;
    private final ItemChoice[][] startingEquipment;

    BackgroundEnum(int classId, String id, CharStatEnum[] proficiencies, ItemChoice[][] startingEquipment) {
        this.classId = classId;
        this.id = id;
        this.proficiencies = proficiencies;
        this.startingEquipment = startingEquipment;
    }

    @Override
    public Consumer<CharStatEnum[]> resultsSaver() {
        return results->Proficiencies.addProficiencies(this.getProficiencies());
    }
    @Override
    public CharStatEnum[] getProficiencies() {
        return proficiencies;
    }

    public ItemChoice[][] getStartingEquipment(){
        return startingEquipment;
    }
    public StartingGold getStartingGold(){
        return null;
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
    public int getId(CharStatEnum statEnum) {
        return BackgroundEnum.getID(statEnum);
    }
    public static int getID(CharStatEnum statEnum){
        return ((BackgroundEnum)statEnum).classId;
    }
    public BackgroundEnum getEnum(int id){
        return BackgroundEnum.SgetEnum(id);
    }
    public static BackgroundEnum SgetEnum(int id) {
        for(BackgroundEnum backgroundEnum: BackgroundEnum.values()){
            if(backgroundEnum.classId==id)
                return backgroundEnum;
        }
        return null;
    }
    public BackgroundEnum getEnum(String name){
        return SgetEnum(name);
    }
    public static BackgroundEnum SgetEnum(String name){
        for(BackgroundEnum backgroundEnum: BackgroundEnum.values()){
            if(Objects.equals(backgroundEnum.getTranslationKey(), name))
                return backgroundEnum;
        }
        return null;
    }
    @Override
    public CharacterCreationEnum getDefault() {
        return null;
    }
    public Screen infoScreen(Screen parent) {
        BackgroundScreen info = new BackgroundScreen(this,parent);
        info.setNextScreen(()->EQUIPMENT);
        info.setResultsSaver(this.resultsSaver());
        return info;
    }
    public Screen manageScreen(Screen parent) {
        return null;
    }
}
