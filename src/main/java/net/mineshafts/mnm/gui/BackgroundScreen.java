package net.mineshafts.mnm.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.CharacterCreationEnum;
import net.mineshafts.mnm.gui.util.ButtonAdder;
import net.mineshafts.mnm.playerdata.PlayerBackground;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BackgroundScreen extends InfoScreen{
    protected int[][] leveledAbilities;
    protected CharacterCreationEnum name;
    protected String[] leveledDescriptions;
    protected int index;
    protected CharStatEnum[][] optionsLists;
    protected int[] buttonCounts;
    protected Supplier<Screen> nextScreen;
    protected Consumer<CharStatEnum[]> resultsSaver;
    public BackgroundScreen(CharacterCreationEnum name, Screen parent) {
        super(Text.translatable(name.getTranslationKey()+".screen"), parent);
        this.name = name;
        this.optionsLists = name.getOptionsLists();
        this.buttonCounts = name.getButtonCounts();
        this.results = new CharStatEnum[optionsLists.length];
        PlayerBackground.setBackground((BackgroundEnum) name);
    }
    public void setNextScreen(Supplier<Screen> nextScreen){
        this.nextScreen = nextScreen;
    }
    public void setResultsSaver(Consumer<CharStatEnum[]> resultsSaver){
        this.resultsSaver = resultsSaver;
    }

    @Override
    public void init() {
        // Debug code
        MinecraftClient.getInstance().player.sendMessage(this.title);

        super.init();

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
        description[0] = leveledAbilities(name.getAbilityCounts());
        ClickableWidget[] buttons = new ClickableWidget[buttonCounts[0]+2];
        ButtonAdder buttonAdder = new ButtonAdder();
        for (int i = 0; i < optionsLists.length; i++) {
            int I = i;
            buttonAdder.addButton(buttons, i + 1, value -> results[I] = value, optionsLists[i]);
        }
        buttons[0] = backButton();
        buttons[buttons.length-1] = screenChangeButton(Text.translatable("mnm.next"), nextScreen, resultsSaver);
        createInfoScreen(buttons, description);
    }

    protected String[] leveledAbilities(int[][] descriptionCounts){
        this.leveledAbilities = descriptionCounts;
        int total = 5;
        for(int ability: leveledAbilities[0])
            total+=ability+2;
        leveledDescriptions = new String[total];
        index = 0;
        add("bold");add("description");add("traits0");add("traits1");add();

        int abilCount = 0;
        if (!Arrays.equals(leveledAbilities[0], new int[]{}))
            for (int j=0;j<leveledAbilities[0].length;j++){
                add("ability"+abilCount);
                abilCount++;
                for(int k=0;k<leveledAbilities[0][j];k++){
                    add("ability"+j+".description"+k);
                }
                add();
            }

        return leveledDescriptions;
    }

    protected void add(String toAdd){
        leveledDescriptions[index] = name.getTranslationKey()+"."+toAdd;
        index++;
    }
    protected void add(){
        leveledDescriptions[index] = "";
        index++;
    }
}
