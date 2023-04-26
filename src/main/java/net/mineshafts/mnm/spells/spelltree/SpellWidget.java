package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementObtainedStatus;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class SpellWidget
extends DrawableHelper {
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/advancements/widgets.png");
    private static final int field_32286 = 26;
    private static final int field_32287 = 0;
    private static final int field_32288 = 200;
    private static final int field_32289 = 26;
    private static final int ICON_OFFSET_X = 8;
    private static final int ICON_OFFSET_Y = 5;
    private static final int ICON_SIZE = 26;
    private static final int field_32293 = 3;
    private static final int field_32294 = 5;
    private static final int TITLE_OFFSET_X = 32;
    private static final int TITLE_OFFSET_Y = 9;
    private static final int TITLE_MAX_WIDTH = 163;
    private static final int[] SPLIT_OFFSET_CANDIDATES = new int[]{0, 10, -10, 25, -25};
    private final SpellTab tab;
    private final Spell spell;
    private final SpellDisplay display;
    private final OrderedText title;
    private final int width;
    private final List<OrderedText> description;
    private final MinecraftClient client;
    @Nullable
    private SpellWidget parent;
    private final List<SpellWidget> children = Lists.newArrayList();
    @Nullable
    private SpellProgress progress;
    private final int x;
    private final int y;

    public SpellWidget(SpellTab tab, MinecraftClient client, Spell spell, SpellDisplay display) {
        this.tab = tab;
        this.spell = spell;
        this.display = display;
        this.client = client;
        this.title = Language.getInstance().reorder(client.textRenderer.trimToWidth(display.getTitle(), 163));
        this.x = MathHelper.floor(display.getX() * 28.0f);
        this.y = MathHelper.floor(display.getY() * 27.0f);
        int i = spell.getRequirementCount();
        int j = String.valueOf(i).length();
        int k = i > 1 ? client.textRenderer.getWidth("  ") + client.textRenderer.getWidth("0") * j * 2 + client.textRenderer.getWidth("/") : 0;
        int l = 29 + client.textRenderer.getWidth(this.title) + k;
        this.description = Language.getInstance().reorder(this.wrapDescription(Texts.setStyleIfAbsent(display.getDescription().copy(), Style.EMPTY.withColor(display.getFrame().getTitleFormat())), l));
        for (OrderedText orderedText : this.description) {
            l = Math.max(l, client.textRenderer.getWidth(orderedText));
        }
        this.width = l + 3 + 5;
    }

    private static float getMaxWidth(TextHandler textHandler, List<StringVisitable> lines) {
        return (float)lines.stream().mapToDouble(textHandler::getWidth).max().orElse(0.0);
    }

    private List<StringVisitable> wrapDescription(Text text, int width) {
        TextHandler textHandler = this.client.textRenderer.getTextHandler();
        List<StringVisitable> list = null;
        float f = Float.MAX_VALUE;
        for (int i : SPLIT_OFFSET_CANDIDATES) {
            List<StringVisitable> list2 = textHandler.wrapLines(text, width - i, Style.EMPTY);
            float g = Math.abs(SpellWidget.getMaxWidth(textHandler, list2) - (float)width);
            if (g <= 10.0f) {
                return list2;
            }
            if (!(g < f)) continue;
            f = g;
            list = list2;
        }
        return list;
    }

    @Nullable
    private SpellWidget getParent(Spell spell) {
        while ((spell = spell.getParent()) != null && spell.getDisplay() == null) {
        }
        if (spell == null || spell.getDisplay() == null) {
            return null;
        }
        return this.tab.getWidget(spell);
    }

    public void renderLines(MatrixStack matrices, int x, int y, boolean border) {
        if (this.parent != null) {
            int n;
            int i = x + this.parent.x + 13;
            int j = x + this.parent.x + 26 + 4;
            int k = y + this.parent.y + 13;
            int l = x + this.x + 13;
            int m = y + this.y + 13;
            int n2 = n = border ? -16777216 : -1;
            if (border) {
                this.drawHorizontalLine(matrices, j, i, k - 1, n);
                this.drawHorizontalLine(matrices, j + 1, i, k, n);
                this.drawHorizontalLine(matrices, j, i, k + 1, n);
                this.drawHorizontalLine(matrices, l, j - 1, m - 1, n);
                this.drawHorizontalLine(matrices, l, j - 1, m, n);
                this.drawHorizontalLine(matrices, l, j - 1, m + 1, n);
                this.drawVerticalLine(matrices, j - 1, m, k, n);
                this.drawVerticalLine(matrices, j + 1, m, k, n);
            } else {
                this.drawHorizontalLine(matrices, j, i, k, n);
                this.drawHorizontalLine(matrices, l, j, m, n);
                this.drawVerticalLine(matrices, j, m, k, n);
            }
        }
        for (SpellWidget spellWidget : this.children) {
            spellWidget.renderLines(matrices, x, y, border);
        }
    }

    public void renderWidgets(MatrixStack matrices, int x, int y) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            float f = this.progress == null ? 0.0f : this.progress.getProgressBarPercentage();
            SpellLearnedStatus spellLearnedStatus = f >= 1.0f ? SpellLearnedStatus.LEARNED : SpellLearnedStatus.UNLEARNED;
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
            this.drawTexture(matrices, x + this.x + 3, y + this.y, this.display.getFrame().getTextureV(), 128 + spellLearnedStatus.getSpriteIndex() * 26, 26, 26);
            this.client.getItemRenderer().renderInGui(this.display.getIcon(), x + this.x + 8, y + this.y + 5);
        }
        for (SpellWidget spellWidget : this.children) {
            spellWidget.renderWidgets(matrices, x, y);
        }
    }

    public int getWidth() {
        return this.width;
    }

    // TODO: SpellProgress class
    public void setProgress(@Nullable SpellProgress progress) {
        this.progress = progress;
    }

    public void addChild(SpellWidget widget) {
        this.children.add(widget);
    }

    public void drawTooltip(MatrixStack matrices, int originX, int originY, float alpha, int x, int y) {
        SpellLearnedStatus spellLearnedStatus3;
        SpellLearnedStatus spellLearnedStatus2;
        SpellLearnedStatus spellLearnedStatus;
        boolean bl = x + originX + this.x + this.width + 26 >= this.tab.getScreen().width;
        String string = this.progress == null ? null : this.progress.getProgressBarFraction();
        int i = string == null ? 0 : this.client.textRenderer.getWidth(string);
        boolean bl2 = 113 - originY - this.y - 26 <= 6 + this.description.size() * this.client.textRenderer.fontHeight;
        float f = this.progress == null ? 0.0f : this.progress.getProgressBarPercentage();
        int j = MathHelper.floor(f * (float)this.width);
        if (f >= 1.0f) {
            j = this.width / 2;
            spellLearnedStatus = SpellLearnedStatus.LEARNED;
            spellLearnedStatus2 = SpellLearnedStatus.LEARNED;
            spellLearnedStatus3 = SpellLearnedStatus.LEARNED;
        } else if (j < 2) {
            j = this.width / 2;
            spellLearnedStatus = SpellLearnedStatus.UNLEARNED;
            spellLearnedStatus2 = SpellLearnedStatus.UNLEARNED;
            spellLearnedStatus3 = SpellLearnedStatus.UNLEARNED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            spellLearnedStatus = SpellLearnedStatus.LEARNED;
            spellLearnedStatus2 = SpellLearnedStatus.LEARNED;
            spellLearnedStatus3 = SpellLearnedStatus.UNLEARNED;
        } else {
            spellLearnedStatus = SpellLearnedStatus.LEARNED;
            spellLearnedStatus2 = SpellLearnedStatus.UNLEARNED;
            spellLearnedStatus3 = SpellLearnedStatus.UNLEARNED;
        }
        int k = this.width - j;
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        int l = originY + this.y;
        int m = bl ? originX + this.x - this.width + 26 + 6 : originX + this.x;
        int n = 32 + this.description.size() * this.client.textRenderer.fontHeight;
        if (!this.description.isEmpty()) {
            if (bl2) {
                this.renderDescriptionBackground(matrices, m, l + 26 - n, this.width, n, 10, 200, 26, 0, 52);
            } else {
                this.renderDescriptionBackground(matrices, m, l, this.width, n, 10, 200, 26, 0, 52);
            }
        }
        this.drawTexture(matrices, m, l, 0, spellLearnedStatus.getSpriteIndex() * 26, j, 26);
        this.drawTexture(matrices, m + j, l, 200 - k, spellLearnedStatus2.getSpriteIndex() * 26, k, 26);
        this.drawTexture(matrices, originX + this.x + 3, originY + this.y, this.display.getFrame().getTextureV(), 128 + spellLearnedStatus3.getSpriteIndex() * 26, 26, 26);
        if (bl) {
            this.client.textRenderer.drawWithShadow(matrices, this.title, (float)(m + 5), (float)(originY + this.y + 9), -1);
            if (string != null) {
                this.client.textRenderer.drawWithShadow(matrices, string, (float)(originX + this.x - i), (float)(originY + this.y + 9), -1);
            }
        } else {
            this.client.textRenderer.drawWithShadow(matrices, this.title, (float)(originX + this.x + 32), (float)(originY + this.y + 9), -1);
            if (string != null) {
                this.client.textRenderer.drawWithShadow(matrices, string, (float)(originX + this.x + this.width - i - 5), (float)(originY + this.y + 9), -1);
            }
        }
        if (bl2) {
            for (int o = 0; o < this.description.size(); ++o) {
                this.client.textRenderer.draw(matrices, this.description.get(o), (float)(m + 5), (float)(l + 26 - n + 7 + o * this.client.textRenderer.fontHeight), -5592406);
            }
        } else {
            for (int o = 0; o < this.description.size(); ++o) {
                this.client.textRenderer.draw(matrices, this.description.get(o), (float)(m + 5), (float)(originY + this.y + 9 + 17 + o * this.client.textRenderer.fontHeight), -5592406);
            }
        }
        this.client.getItemRenderer().renderInGui(this.display.getIcon(), originX + this.x + 8, originY + this.y + 5);
    }

    /**
     * Renders the description background.
     *
     * @implNote This splits the area into 9 parts (4 corners, 4 edges and 1
     * central box) and draws each of them.
     */
    protected void renderDescriptionBackground(MatrixStack matrices, int x, int y, int width, int height, int cornerSize, int textureWidth, int textureHeight, int u, int v) {
        this.drawTexture(matrices, x, y, u, v, cornerSize, cornerSize);
        this.drawTextureRepeatedly(matrices, x + cornerSize, y, width - cornerSize - cornerSize, cornerSize, u + cornerSize, v, textureWidth - cornerSize - cornerSize, textureHeight);
        this.drawTexture(matrices, x + width - cornerSize, y, u + textureWidth - cornerSize, v, cornerSize, cornerSize);
        this.drawTexture(matrices, x, y + height - cornerSize, u, v + textureHeight - cornerSize, cornerSize, cornerSize);
        this.drawTextureRepeatedly(matrices, x + cornerSize, y + height - cornerSize, width - cornerSize - cornerSize, cornerSize, u + cornerSize, v + textureHeight - cornerSize, textureWidth - cornerSize - cornerSize, textureHeight);
        this.drawTexture(matrices, x + width - cornerSize, y + height - cornerSize, u + textureWidth - cornerSize, v + textureHeight - cornerSize, cornerSize, cornerSize);
        this.drawTextureRepeatedly(matrices, x, y + cornerSize, cornerSize, height - cornerSize - cornerSize, u, v + cornerSize, textureWidth, textureHeight - cornerSize - cornerSize);
        this.drawTextureRepeatedly(matrices, x + cornerSize, y + cornerSize, width - cornerSize - cornerSize, height - cornerSize - cornerSize, u + cornerSize, v + cornerSize, textureWidth - cornerSize - cornerSize, textureHeight - cornerSize - cornerSize);
        this.drawTextureRepeatedly(matrices, x + width - cornerSize, y + cornerSize, cornerSize, height - cornerSize - cornerSize, u + textureWidth - cornerSize, v + cornerSize, textureWidth, textureHeight - cornerSize - cornerSize);
    }

    /**
     * Draws a textured rectangle repeatedly to cover the area of {@code
     * width} and {@code height}. The last texture is clipped to fit the area.
     */
    protected void drawTextureRepeatedly(MatrixStack matrices, int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight) {
        for (int i = 0; i < width; i += textureWidth) {
            int j = x + i;
            int k = Math.min(textureWidth, width - i);
            for (int l = 0; l < height; l += textureHeight) {
                int m = y + l;
                int n = Math.min(textureHeight, height - l);
                this.drawTexture(matrices, j, m, u, v, k, n);
            }
        }
    }

    public boolean shouldRender(int originX, int originY, int mouseX, int mouseY) {
        if (this.display.isHidden() && (this.progress == null || !this.progress.isDone())) {
            return false;
        }
        int i = originX + this.x;
        int j = i + 26;
        int k = originY + this.y;
        int l = k + 26;
        return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
    }

    public void addToTree() {
        if (this.parent == null && this.spell.getParent() != null) {
            this.parent = this.getParent(this.spell);
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}
