package net.mineshafts.mnm.gui.statgenscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.GenerationType;
import net.mineshafts.mnm.gui.CharCreationScreen;

public class ScoreGenSelection extends CharCreationScreen {
    public ScoreGenSelection(Screen parent) {
        super(Text.translatable("mnm.menu.scoregenselection"), parent);
    }
    @Override
    public void init() {
        super.init();
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(1);
        for (GenerationType value: GenerationType.values()){
            adder.add(screenChangeButton(Text.translatable(value.getTranslationKey()), ()->value.genScreen(this)));
        }
        adder.add(backButton());

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
}
