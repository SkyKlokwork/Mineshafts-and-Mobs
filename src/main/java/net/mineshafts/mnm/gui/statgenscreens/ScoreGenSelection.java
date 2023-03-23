package net.mineshafts.mnm.gui.statgenscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.ClassEnum;
import net.mineshafts.mnm.enums.charcreationenums.GenerationType;
import net.mineshafts.mnm.gui.MenuScreen;

public class ScoreGenSelection extends MenuScreen {
    public ScoreGenSelection(Screen parent) {
        super(Text.translatable("mnm.menu.raceselection"), parent);
    }
    @Override
    protected void init() {
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
