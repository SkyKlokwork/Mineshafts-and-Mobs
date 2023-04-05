package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuScreen extends Screen {
    public Screen parent;
    protected CharStatEnum[] results;
    protected MenuScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    protected ButtonWidget screenChangeButton(Text text, Supplier<Screen> screenSupplier) {
        return ButtonWidget.builder(text, button -> this.client.setScreen((Screen)screenSupplier.get())).width(98).build();
    }
    protected ButtonWidget screenChangeButton(Text text, Supplier<Screen> screenSupplier, Runnable onClick) {
        return ButtonWidget.builder(text, button -> {this.client.setScreen((Screen)screenSupplier.get());onClick.run();}).width(98).build();
    }
    protected ButtonWidget screenChangeButton(Text text, Supplier<Screen> screenSupplier, Consumer<CharStatEnum[]> onClick) {
        return ButtonWidget.builder(text, button -> {this.client.setScreen((Screen)screenSupplier.get());if(onClick!=null)onClick.accept(results);}).width(98).build();
    }
    protected ButtonWidget createButton(Text text, ButtonWidget.PressAction onPress, Text tooltip){
        return ButtonWidget.builder(text, onPress).width(98).tooltip(Tooltip.of(tooltip)).build();
    }
    protected ButtonWidget createButton(Text text, ButtonWidget.PressAction onPress){
        return createButton(text, onPress, text);
    }
    protected TextWidget createText(Text text, int x, int y, int width){
        return new TextWidget(x, y, width, this.textRenderer.fontHeight, text, this.textRenderer);
    }
    protected ButtonWidget backButton(){
        return screenChangeButton(Text.translatable("mnm.back"), ()->parent);
    }
}
