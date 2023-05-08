package net.mineshafts.mnm.gui.statgenscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.StatType;
import net.mineshafts.mnm.gui.widgets.MutableGridWidget;
import net.mineshafts.mnm.playerdata.PlayerRace;

public class StandardArrayScreen extends StatSelectionScreen {
    public StandardArrayScreen(Screen parent) {
        super(Text.translatable("mnm.standard_array"), parent);
        if(PlayerRace.getRace()!=null)
            this.asi = PlayerRace.getRace().getAsi().getScores();
    }
    @Override
    public void init() {
        super.init();
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(6);
        MutableGridWidget scoreDescription = new MutableGridWidget();
        scoreDescription.getMainPositioner().margin(4,4,4,0);
        MutableGridWidget.Adder scoreAdder = scoreDescription.createAdder(3);
        for (StatType type:StatType.values()){
            adder.add(new TextWidget(Text.translatable(type.getTranslationKey()),this.textRenderer));
        }
        for (int i=0;i<6;i++){
            adder.add(getDropdown(i,Text.translatable("mnm.menu.placeholder"),98,Tooltip.of(Text.translatable(StatType.values()[i].getTranslationKey())), scoreDescription));
        }
        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height/4, 0.5f, 0.15f);
        this.addDrawableChild(gridWidget);
        for (int i=0;i<3;i++)
            scoreAdder.add(new TextWidget(Text.translatable(StatType.values()[i].getTranslationKey()),this.textRenderer));
        textRow0 = new TextWidget[6];
        textRow1 = new TextWidget[6];
        textRow2 = new TextWidget[6];
        textRow3 = new TextWidget[6];
        for (int i=0;i<3;i++){
            textRow0[i] = new TextWidget(Text.translatable("mnm.score.total",0),this.textRenderer);
            scoreAdder.add(textRow0[i]);
        }
        for (int i=0;i<3;i++){
            textRow1[i] = new TextWidget(Text.translatable("mnm.score.modifier","+",0),this.textRenderer);
            scoreAdder.add(textRow1[i]);
        }
        for (int i=0;i<3;i++) {
            textRow2[i] = new TextWidget(Text.translatable("mnm.score.base",0),this.textRenderer);
            scoreAdder.add(textRow2[i]);
        }
        for (int i=0;i<3;i++){
            textRow3[i] = new TextWidget(Text.translatable("mnm.score.racial","+",asi[i]),this.textRenderer);
            scoreAdder.add(textRow3[i]);
        }
        for (int i=0;i<3;i++){
            scoreAdder.add(new TextWidget(Text.of(""),textRenderer));
        }
        for (int i=3;i<6;i++)
            scoreAdder.add(new TextWidget(Text.translatable(StatType.values()[i].getTranslationKey()),this.textRenderer));
        for (int i=3;i<6;i++) {
            textRow0[i] = new TextWidget(Text.translatable("mnm.score.total",0),this.textRenderer);
            scoreAdder.add(textRow0[i]);
        }
        for (int i=3;i<6;i++) {
            textRow1[i] = new TextWidget(Text.translatable("mnm.score.modifier","+",0),this.textRenderer);
            scoreAdder.add(textRow1[i]);
        }
        for (int i=3;i<6;i++) {
            textRow2[i] = new TextWidget(Text.translatable("mnm.score.base",0),this.textRenderer);
            scoreAdder.add(textRow2[i]);
        }
        for (int i=3;i<6;i++){
            textRow3[i] = new TextWidget(Text.translatable("mnm.score.racial","+",asi[i]),this.textRenderer);
            scoreAdder.add(textRow3[i]);
        }
        scoreDescription.recalculateDimensions();
        displayY = gridWidget.getY()+gridWidget.getHeight();
        SimplePositioningWidget.setPos(scoreDescription, 0, this.height/10, this.width, scoreDescription.getHeight()+displayY, 0.5f, 0.25f);
        this.addDrawableChild(scoreDescription);

        GridWidget nav = new GridWidget();
        nav.getMainPositioner().margin(4, 1, 4, 0);
        GridWidget.Adder navAdder = nav.createAdder(2);
        navAdder.add(backButton());
        navAdder.add(screenChangeButton(Text.translatable("mnm.next"),()->DESCRIPTION,()->setScores()));
        nav.recalculateDimensions();
        SimplePositioningWidget.setPos(nav, 0, this.height/10, this.width, this.height, 0.5f, 0.5f);
        this.addDrawableChild(nav);
    }

}
