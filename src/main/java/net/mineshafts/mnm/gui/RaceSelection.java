package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.RaceEnum;

public class RaceSelection extends CharCreationScreen {
    public RaceSelection(Screen parent) {
        super(Text.translatable("mnm.menu.raceselection"), parent);
    }
    @Override
    public void init() {
        super.init();
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(1);
        for (RaceEnum value: RaceEnum.values()){
            adder.add(screenChangeButton(Text.translatable(value.getTranslationKey()), ()->value.infoScreen(this)));
        }
        adder.add(backButton());

        int y = this.children().stream().mapToInt(element->{
            if(element instanceof ClickableWidget c)
                return c.getY();
            return 0;
        }).max().getAsInt();

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, y, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
}
