package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class MenuScreenMain extends Screen {
    protected MenuScreenMain(Text title) {
        super(title);
    }

    protected ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier) {
        return ButtonWidget.builder(text, button -> this.client.setScreen((Screen)screenSupplier.get())).width(98).build();
    }
    protected TextWidget createText(Text text, int x, int y, int width){
        return new TextWidget(x, y, width, this.textRenderer.fontHeight, text, this.textRenderer);
    }
}
