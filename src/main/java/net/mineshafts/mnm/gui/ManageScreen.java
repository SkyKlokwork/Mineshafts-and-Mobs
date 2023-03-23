package net.mineshafts.mnm.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.CharacterCreationEnum;
import net.mineshafts.mnm.gui.util.ButtonAdder;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ManageScreen extends InfoScreen{
    protected int level = 0;
    protected int[][] leveledAbilities;
    protected CharacterCreationEnum name;
    protected String[] leveledDescriptions;
    protected int index;
    protected CharStatEnum[][] optionsLists;
    protected Consumer<CharacterCreationEnum>[] consumers;
    protected int[] buttonCounts;
    protected Supplier<Screen> nextScreen;
    protected Consumer<CharStatEnum[]> resultsSaver;
    protected String type;
    public ManageScreen(CharacterCreationEnum name, String type, Screen parent) {
        super(Text.translatable(name.getTranslationKey()+"."+type), parent);
        this.name = name;
        this.level = 20;
        this.optionsLists = name.getOptionsLists();
        this.buttonCounts = name.getButtonCounts();
        this.results = new CharacterCreationEnum[optionsLists.length];
        this.type = type;
    }
    public ManageScreen(CharacterCreationEnum name, String type, int level, Screen parent){
        this(name, type, parent);
        this.level = level;
    }
    public void setNextScreen(Supplier<Screen> nextScreen){
        this.nextScreen = nextScreen;
    }
    public void setResultsSaver(Consumer<CharStatEnum[]> resultsSaver){
        this.resultsSaver = resultsSaver;
    }

    @Override
    protected void init() {
        // Debug code
        MinecraftClient.getInstance().player.sendMessage(this.title);

        String[][] description = new String[1][];
        if(buttonCounts.length<20){
            int[] temp = new int[20];
            for (int i=0;i<20;i++){
                if(i<buttonCounts.length)
                    temp[i] = buttonCounts[i];
                else
                    temp[i] = 0;
            }
            buttonCounts = temp;
        }
        description[0] = leveledAbilities(name.getAbilityCounts(), level);
        ClickableWidget[] buttons;
        if (!Objects.equals(this.type, "info")) {
            buttons = new ClickableWidget[buttonCounts[level-1]+2];
            ButtonAdder buttonAdder = new ButtonAdder();
            for (int i = 0; i < optionsLists.length; i++) {
                int I = i;
                buttonAdder.addButton(buttons, i + 1, value -> name.setResult(value,I), optionsLists[i]);
            }
        } else {
            buttons = new ClickableWidget[2];
        }
        buttons[0] = backButton();
        buttons[buttons.length-1] = screenChangeButton(Text.translatable("mnm.next"), nextScreen, resultsSaver);
        createInfoScreen(buttons, description);
    }

    protected String[] leveledAbilities(int[][] descriptionCounts,int level){
        this.leveledAbilities = descriptionCounts;
        int total = 5;
        for(int i=0;i<leveledAbilities.length&i<level;i++)
            for(int ability: leveledAbilities[i])
                total+=ability+3;
        leveledDescriptions = new String[total];
        index = 0;
        add("bold");add("description");add("traits0");add("traits1");add();
        for(int i=0;i<leveledAbilities.length&i<level;i++){
            int abilCount = 0;
            if (!Arrays.equals(leveledAbilities[i], new int[]{}))
                for (int j=0;j<leveledAbilities[i].length;j++){
                    add("ability"+abilCount);add(i);
                    abilCount++;
                    for(int k=0;k<leveledAbilities[i][j];k++){
                        add("ability"+j+".description"+k);
                    }
                    add();
                }
        }

        return leveledDescriptions;
    }

    protected void add(String toAdd){
        leveledDescriptions[index] = name.getTranslationKey()+"."+toAdd;
        index++;
    }
    protected void add(int num){
        leveledDescriptions[index] = "mnm.level."+num;
        index++;
    }
    protected void add(){
        leveledDescriptions[index] = "";
        index++;
    }
}
