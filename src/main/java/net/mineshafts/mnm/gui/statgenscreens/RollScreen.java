package net.mineshafts.mnm.gui.statgenscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.gui.CharCreationScreen;
import net.mineshafts.mnm.gui.util.Roll;

public class RollScreen extends CharCreationScreen {
    protected int[] scores = new int[]{0,0,0,0,0,0};
    protected TextWidget[] row0 = new TextWidget[6];
    protected TextWidget[] row1 = new TextWidget[6];
    protected ButtonWidget[] row2 = new ButtonWidget[6];
    public RollScreen(Screen parent) {
        super(Text.translatable("mnm.roll_screen"), parent);
    }
    @Override
    public void init(){
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(6);

        for (int i=0;i<6;i++) {
            row0[i] = adder.add(new TextWidget(Text.translatable("mnm.placeholder"), this.textRenderer));
        }
        for (int i=0;i<6;i++) {
            row1[i] = adder.add(new TextWidget(Text.translatable("mnm.placeholder"), this.textRenderer));
        }
        for (int i=0;i<6;i++) {
            int finalI = i;
            row2[i] = adder.add(ButtonWidget.builder(Text.translatable("mnm.roll"), button->rollButton(button, finalI, gridWidget))
                    .tooltip(Tooltip.of(Text.translatable("mnm.roll"))).build());
        }

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height/4, 0.5f, 0.15f);
        this.addDrawableChild(gridWidget);

        GridWidget nav = new GridWidget();
        nav.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder navAdder = nav.createAdder(2);
        navAdder.add(backButton());
        StandardArrayScreen next = new StandardArrayScreen(this);
        navAdder.add(screenChangeButton(Text.translatable("mnm.next"),()->next,()->next.setScores(scores)));
        nav.recalculateDimensions();
        SimplePositioningWidget.setPos(nav, 0, this.height/10, this.width, this.height, 0.5f, 0.5f);
        this.addDrawableChild(nav);
    }
    protected void rollButton(ButtonWidget button, int i, GridWidget gridWidget){
        Roll roll = new Roll(4,6,1);
        for (int j=0;j<6;j++){
            if(j==i) {
                row0[i].setMessage(Text.of(String.valueOf(roll.getTotal())));
                row1[i].setMessage(Text.of(roll.getRolls(5)));
                row2[i].setMessage(Text.translatable("mnm.reroll"));
                scores[i] = roll.getTotal();
            }
        }
        gridWidget.recalculateDimensions();
    }
}
