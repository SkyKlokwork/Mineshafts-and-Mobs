package net.mineshafts.mnm.gui.statgenscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.StatType;
import net.mineshafts.mnm.gui.CharCreationScreen;
import net.mineshafts.mnm.gui.widgets.MutableGridWidget;
import net.mineshafts.mnm.playerdata.PlayerAbilityScores;

import java.util.Arrays;

public class StatSelectionScreen extends CharCreationScreen {
    protected StatSelectionScreen(Text title, Screen parent) {
        super(title, parent);
    }

    protected int[] standardArray = new int[]{15, 14, 13, 12, 10,8};
    protected ButtonWidget[] dropdownElements = new ButtonWidget[0];
    protected TextWidget[] textRow0 = new TextWidget[0];
    protected TextWidget[] textRow1 = new TextWidget[0];
    protected TextWidget[] textRow2 = new TextWidget[0];
    protected TextWidget[] textRow3 = new TextWidget[0];
    protected int[] asi = new int[6];
    protected int[] results = new int[6];
    protected boolean[] resultsFilled = new boolean[6];
    protected int displayY = 0;
    public void setScores(int[] scores){
        this.standardArray = scores;
    }
    protected ButtonWidget getDropdown(int index, Text text, int width, Tooltip tooltip, MutableGridWidget gridWidget){
        return ButtonWidget.builder(text, button->createDropdownElements(button, index, gridWidget)).width(width).tooltip(tooltip).build();
    }
    protected void createDropdownElements(ButtonWidget dropdown, int index, MutableGridWidget gridWidget){
        int x = dropdown.getX();
        int y = dropdown.getY();
        int w = dropdown.getWidth();
        int h = dropdown.getHeight();
        clearDropdowns();
        dropdownElements = new ButtonWidget[standardArray.length+1];
        dropdownElements[0] = ButtonWidget.builder(Text.translatable("mnm.menu.placeholder"),button -> {
            Text value = dropdown.getMessage();
            int val;
            try {
                val = Integer.parseInt(value.getString());
                addToSA(val);
            } catch (NumberFormatException ignored) {}
            dropdown.setMessage(Text.translatable("mnm.menu.placeholder"));
            clearDropdowns();
            setCalculations(0, index);
        }).dimensions(x,y+h, w, h).build();
        this.addDrawableChild(dropdownElements[0]);
        for (int i=1;i<standardArray.length+1;i++){
            dropdownElements[i] = ButtonWidget.builder(Text.of(String.valueOf(standardArray[i-1])),button->setDropdownValue(dropdown,button,index,gridWidget)).dimensions(x,y+(i+1)*h, w, h).build();
            this.addDrawableChild(dropdownElements[i]);
        }
    }
    protected void setDropdownValue(ButtonWidget root, ButtonWidget element, int i, MutableGridWidget gridWidget){
        Integer currentValue;
        int newValue = Integer.parseInt(element.getMessage().getString());
        try {
            currentValue = Integer.parseInt(root.getMessage().getString());
        } catch (NumberFormatException numberFormatException){
            currentValue = null;
        }
        int length = standardArray.length;
        int[] newScores;
        boolean taken = false;
        int index = 0;
        if(currentValue!=null){
            newScores = new int[length];
            for (int score: standardArray){
                if (score == newValue&!taken){
                    taken = true;
                    newScores[index] = currentValue;
                }
                else
                    newScores[index] = score;
                index++;
            }
        }
        else {
            newScores = new int[length-1];
            for (int score: standardArray){
                if (score == newValue&!taken){
                    taken = true;
                    continue;
                }
                newScores[index] = score;
                index++;
            }
        }
        standardArray = newScores;
        sortSA();
        root.setMessage(element.getMessage());
        clearDropdowns();
        setCalculations(newValue, i);
    }
    protected void setCalculations(int newValue, int index){
        int l = textRow0.length;
        MutableGridWidget newGrid = new MutableGridWidget();
        newGrid.getMainPositioner().margin(4,4,4,0);
        MutableGridWidget.Adder newAdder = newGrid.createAdder(3);

        if(l == textRow1.length & l == textRow2.length) {
            int total = newValue + asi[index];
            String sign;
            results[index] = total;
            resultsFilled[index] = true;
            for (int i=0;i<3;i++)
                newAdder.add(new TextWidget(Text.translatable(StatType.values()[i].getTranslationKey()),this.textRenderer));
            for (int i=0;i<3;i++){
                if (i==index)
                    textRow0[i].setMessage(Text.translatable("mnm.score.total",total));
                newAdder.add(textRow0[i]);
            }
            total = total>0?total:10;
            sign = total/2-5<0?"":"+";
            for (int i=0;i<3;i++){
                if (i==index)
                    textRow1[i].setMessage(Text.translatable("mnm.score.modifier",sign,total/2-5));
                newAdder.add(textRow1[i]);
            }
            for (int i=0;i<3;i++) {
                if (i==index)
                    textRow2[i].setMessage(Text.translatable("mnm.score.base",newValue));
                newAdder.add(textRow2[i]);
            }
            sign = asi[index]<0?"":"+";
            for (int i=0;i<3;i++){
                if (i==index)
                    textRow3[i].setMessage(Text.translatable("mnm.score.racial",sign,asi[i]));
                newAdder.add(textRow3[i]);
            }
            for (int i=0;i<3;i++){
                newAdder.add(new TextWidget(Text.of(""),textRenderer));
            }
            total = newValue + asi[index];
            for (int i=3;i<6;i++)
                newAdder.add(new TextWidget(Text.translatable(StatType.values()[i].getTranslationKey()),this.textRenderer));
            for (int i=3;i<6;i++){
                if (i==index)
                    textRow0[i].setMessage(Text.translatable("mnm.score.total",total));
                newAdder.add(textRow0[i]);
            }
            total = total>0?total:10;
            sign = total/2-5<0?"":"+";
            for (int i=3;i<6;i++){
                if (i==index)
                    textRow1[i].setMessage(Text.translatable("mnm.score.modifier",sign,total/2-5));
                newAdder.add(textRow1[i]);
            }
            for (int i=3;i<6;i++) {
                if (i==index)
                    textRow2[i].setMessage(Text.translatable("mnm.score.base",newValue));
                newAdder.add(textRow2[i]);
            }
            sign = asi[index]<0?"":"+";
            for (int i=3;i<6;i++){
                if (i==index)
                    textRow3[i].setMessage(Text.translatable("mnm.score.racial",sign,asi[i]));
                newAdder.add(textRow3[i]);
            }
        }
        for (int i=0;i<this.children().size();i++)
            if (this.children().get(i) instanceof MutableGridWidget g)
                this.remove(g);
        newGrid.recalculateDimensions();
        SimplePositioningWidget.setPos(newGrid, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(newGrid);
    }
    protected void clearDropdowns(){
        for (ButtonWidget element: dropdownElements){
            this.remove(element);
        }
        dropdownElements = new ButtonWidget[0];
    }
    protected void addToSA(int value){
        int[] newScores = new int[standardArray.length+1];
        newScores[0] = value;
        System.arraycopy(standardArray, 0, newScores, 1, standardArray.length);
        standardArray = newScores;
        sortSA();
    }
    protected void sortSA(){
        Arrays.sort(standardArray);
        int[] newArray = new int[standardArray.length];
        for (int i=0;i<standardArray.length;i++){
            newArray[i] = standardArray[standardArray.length-i-1];
        }
        standardArray = newArray;
    }
    protected void setScores(){
        for (boolean isFilled: resultsFilled)
            if (isFilled)
                return;
        PlayerAbilityScores.setScores(results);
    }
}
