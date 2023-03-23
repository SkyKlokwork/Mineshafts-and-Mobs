package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.mineshafts.mnm.gui.widgets.ScrollableMenu;

import java.util.Arrays;

public class InfoScreen extends MenuScreen {
    protected InfoScreen(Text title, Screen parent) {
        super(title, parent);
    }
    protected String alignColumns(String text) {
        String[] lines = text.split("\n");
        int maxColumns = Arrays.stream(lines)
                .map(line -> line.split("\t"))
                .mapToInt(cols -> cols.length)
                .max().orElse(0);
        int[] columnWidths = new int[maxColumns];

        for (String line : lines) {
            String[] columns = line.split("\t");
            for (int i = 0; i < columns.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], textRenderer.getWidth(columns[i].trim()));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String[] columns = lines[i].split("\t");
            for (int j = 0; j < columns.length; j++) {
                sb.append(columns[j]).append(" ".repeat((columnWidths[j] - textRenderer.getWidth(columns[j].trim()))/textRenderer.getWidth(" ")));
            }
            if (i < lines.length - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
    protected void createInfoScreen(ClickableWidget[] buttons, String[][] description){
        ScrollableMenu scrollableMenu = new ScrollableMenu(this.textRenderer, 5, 15, this.width-5, this.height-100, Text.of(""));
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 10, 4, 10);
        GridWidget.Adder adder = gridWidget.createAdder(buttons.length);
        StringBuilder contents = new StringBuilder();
        StringBuilder table = new StringBuilder();
        for (String[] descrip: description){
            for(String desc:descrip) {
                String trans = Text.translatable(desc).getString();
                if (trans.contains("\t")) {
                    table.append('\n').append(trans);
                } else {
                    if (!table.isEmpty()) {
                        contents.append(alignColumns(table.toString()));
                        table = new StringBuilder();
                    }
                    contents.append('\n').append(trans);
                }
            }
        }
        if (!table.isEmpty())
            contents.append(alignColumns(table.toString()));
        scrollableMenu.setMessage(Text.of(contents.toString()));
        adder.add(scrollableMenu,buttons.length);
        for(ClickableWidget button:buttons)
            adder.add(button);
        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
}
