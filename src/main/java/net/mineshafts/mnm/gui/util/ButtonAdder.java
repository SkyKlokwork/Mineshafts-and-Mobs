package net.mineshafts.mnm.gui.util;

import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.CharacterCreationEnum;
import net.mineshafts.mnm.gui.widgets.SimpleChoice;

import java.util.Arrays;
import java.util.function.Consumer;

public class ButtonAdder {
    public void addButton(ClickableWidget[] buttons, int index, Consumer<CharStatEnum> consumer, CharStatEnum[] optionList) {
        SimpleChoice<CharStatEnum> optionCycler = new SimpleChoice<>(value -> Tooltip.of(Text.translatable(value.getTranslationKey())),
                SimpleChoice.enumValueText(),
                new SimpleChoice.PotentialValuesBasedCallbacks<CharStatEnum>(Arrays.asList(optionList), Codec.INT.xmap(optionList[0]::getEnum,value->optionList[0].getId(value))),
                optionList[0],
                consumer);
        buttons[index] = optionCycler.createButton(MinecraftClient.getInstance().options, 0, 0, 98, consumer);
    }
}
