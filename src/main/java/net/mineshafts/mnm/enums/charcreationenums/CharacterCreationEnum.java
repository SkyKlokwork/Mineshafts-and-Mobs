package net.mineshafts.mnm.enums.charcreationenums;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.TranslatableOption;

import java.util.function.Consumer;

public interface CharacterCreationEnum extends CharStatEnum {
    int[][] getAbilityCounts();
    CharStatEnum getDefault();
    Screen infoScreen(Screen parent);
    Screen manageScreen(Screen parent);
    CharStatEnum[][] getOptionsLists();
    CharStatEnum getSubEnum();
    int[] getButtonCounts();
    CharStatEnum[] getProficiencies();
    void setResult(CharStatEnum Enum, int index);
    Consumer<CharStatEnum[]> resultsSaver();
}
