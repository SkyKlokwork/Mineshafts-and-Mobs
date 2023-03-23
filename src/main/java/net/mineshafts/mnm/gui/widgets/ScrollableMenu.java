package net.mineshafts.mnm.gui.widgets;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public class ScrollableMenu extends ScrollableWidget {
    private final TextRenderer textRenderer;
    private final List<Substring> lines = Lists.newArrayList();
    private String text;
    public ScrollableMenu(TextRenderer textRenderer, int x, int y, int width, int height, Text message){
        super(x, y, width, height, message);
        this.textRenderer = textRenderer;
    }

    @Override
    protected int getContentsHeight() {
        return this.textRenderer.fontHeight*lines.size();
    }

    @Override
    protected boolean overflows() {
        return false;
    }

    @Override
    protected double getDeltaYPerScroll() {
        return this.textRenderer.fontHeight;
    }

    @Override
    protected void renderContents(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.text = this.getMessage().getString();
        rewrap();
        int j = 0;
        int k = 0;
        int l = this.getY() + this.getPadding();
        for (Substring substring: this.getLines()){
            boolean bl3 = this.isVisible(l, l + this.textRenderer.fontHeight);
            if(bl3)
                j = this.textRenderer.drawWithShadow(matrices, this.text.substring(substring.beginIndex(), substring.endIndex()), (float)(this.getX() + this.getPadding()), (float)l, -2039584) - 1;
            k = l;
            l += this.textRenderer.fontHeight;
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
    private void rewrap() {
        this.lines.clear();
        if (this.text.isEmpty()) {
            this.lines.add(Substring.EMPTY);
            return;
        }
        this.textRenderer.getTextHandler().wrapLines(this.text, this.width, Style.EMPTY, false, (style, start, end) -> this.lines.add(new Substring(start, end)));
        if (this.text.charAt(this.text.length() - 1) == '\n') {
            this.lines.add(new Substring(this.text.length(), this.text.length()));
        }
    }
    public Iterable<Substring> getLines() {
        return this.lines;
    }

    @Environment(value= EnvType.CLIENT)
    protected record Substring(int beginIndex, int endIndex) {
        static final Substring EMPTY = new Substring(0, 0);
    }
}
