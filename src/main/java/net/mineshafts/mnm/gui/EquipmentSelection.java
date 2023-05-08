package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;
import net.mineshafts.mnm.gui.equipmentscreens.StartingEquipment;

public class EquipmentSelection extends CharCreationScreen {
    public EquipmentSelection(Screen parent) {
        super(Text.translatable("mnm.menu.equipmentselection"), parent);
    }
    @Override
    public void init() {
        super.init();
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(1);
        adder.add(screenChangeButton(Text.translatable("mnm.equipment.equipment"),()->new StartingEquipment(this)));
        adder.add(screenChangeButton(Text.translatable("mnm.equipment.gold"),()->{
            // starting gold screen
            return null;
        }));
        adder.add(backButton());

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
}
