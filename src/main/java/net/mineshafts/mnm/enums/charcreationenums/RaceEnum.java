package net.mineshafts.mnm.enums.charcreationenums;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.mineshafts.mnm.ItemChoice;
import net.mineshafts.mnm.Lifespan;
import net.mineshafts.mnm.Speed;
import net.mineshafts.mnm.StartingGold;
import net.mineshafts.mnm.enums.SizeEnum;
import net.mineshafts.mnm.gui.ClassSelection;
import net.mineshafts.mnm.gui.ManageScreen;
import net.mineshafts.mnm.playerdata.AbilityScoreIncrease;
import net.mineshafts.mnm.playerdata.PlayerRace;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.mineshafts.mnm.gui.CharCreationScreen.*;

@Environment(value= EnvType.CLIENT)
public enum RaceEnum implements CharacterCreationEnum {
    AARAKOCRA_LEGACY(0, "aarakocra_legacy",null,null,null,null){
        @Override
        public int[][] getAbilityCounts() {
            return new int[0][];
        }

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
            int[] buttons = new int[20];
            Arrays.fill(buttons, 2);
            return buttons;
        }

        @Override
        public void setResult(CharStatEnum Enum, int index) {

        }
        @Override
        public Consumer<CharStatEnum[]> resultsSaver(){
            return null;
        }
    },
    DRAGONBORN(1, "dragonborn", new AbilityScoreIncrease(2,0,0,0,0,1),new Lifespan(15,80), SizeEnum.MEDIUM,new Speed(30)){

        @Override
        public int[][] getAbilityCounts() {
            return new int[][]{{13,1,1}};
        }

        @Override
        public CharStatEnum[][] getOptionsLists() {
            return new CharStatEnum[][]{DragonbornColor.values()};
        }

        @Override
        public CharStatEnum getSubEnum() {
            return DragonbornColor.CHOOSE;
        }

        @Override
        public int[] getButtonCounts() {
            int[] buttons = new int[20];
            Arrays.fill(buttons, 1);
            return buttons;
        }

        @Override
        public void setResult(CharStatEnum Enum, int index) {

        }
        @Override
        public Consumer<CharStatEnum[]> resultsSaver(){
            return results->PlayerRace.setSubRace(results[0]);
        }
    };

    //TODO: add more races
    private final int raceId;
    private final String id;
    private final AbilityScoreIncrease asi;
    private final Lifespan lifespan;
    private final SizeEnum size;
    private final Speed speed;

    RaceEnum(int raceId, String id, AbilityScoreIncrease asi, Lifespan lifespan, SizeEnum size, Speed speed) {
        this.raceId = raceId;
        this.id = id;
        this.asi = asi;
        this.lifespan = lifespan;
        this.size = size;
        this.speed = speed;
    }
    public CharacterCreationEnum[] getProficiencies(){
        return new CharacterCreationEnum[0];
    }
    public CharacterCreationEnum getDefault(){
        return null;
    }
    public int getId(){
        return this.raceId;
    }
    public int getId(CharStatEnum creationEnum){
        return RaceEnum.SgetId(creationEnum);
    }
    public String getTranslationKey(){
        return "mnm."+this.id;
    }
    public Text getText(){
        return Text.translatable(this.getTranslationKey());
    }
    public static int SgetId(CharStatEnum creationEnum){
        return ((RaceEnum)creationEnum).raceId;
    }

    @Override
    public CharacterCreationEnum getEnum(int id) {
        return RaceEnum.SgetEnum(id);
    }
    public static CharacterCreationEnum SgetEnum(int id){
        for (RaceEnum race: RaceEnum.values()){
            if(race.raceId==id)
                return race;
        }
        return null;
    }

    public Screen infoScreen(Screen parent) {
        ManageScreen info = new ManageScreen(this,"info",parent);
        info.setNextScreen(()->this.manageScreen(info));
        return info;
    }
    public Screen manageScreen(Screen parent) {
        PlayerRace.setRace(this);
        ManageScreen manage = new ManageScreen(this,"manage",1,parent);
        manage.setNextScreen(()->CLASS_SELECTION);
        manage.setResultsSaver(this.resultsSaver());
        return manage;
    }

    public ItemChoice[][] getStartingEquipment(){
        return null;
    }
    public StartingGold getStartingGold(){
        return null;
    }

    public AbilityScoreIncrease getAsi() {
        return asi;
    }

    public Lifespan getLifespan() {
        return lifespan;
    }

    public SizeEnum getSize() {
        return size;
    }

    public Speed getSpeed() {
        return speed;
    }
}
