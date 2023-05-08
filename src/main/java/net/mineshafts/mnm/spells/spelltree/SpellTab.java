package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mineshafts.mnm.gui.SpellTreeScreen;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

@Environment(value= EnvType.CLIENT)
public class SpellTab
extends DrawableHelper {
    private final MinecraftClient client;
    private final SpellTreeScreen screen;
    private final SpellTabType type;
    private final int index;
    private final Spell root;
    private final SpellDisplay display;
    private final ItemStack icon;
    private final Text title;
    private final SpellWidget rootWidget;
    private final Map<Spell, SpellWidget> widgets = Maps.newLinkedHashMap();
    private double originX;
    private double originY;
    private int minPanX = Integer.MAX_VALUE;
    private int minPanY = Integer.MAX_VALUE;
    private int maxPanX = Integer.MIN_VALUE;
    private int maxPanY = Integer.MIN_VALUE;
    private float alpha;
    private boolean initialized;

    public SpellTab(MinecraftClient client, SpellTreeScreen screen, SpellTabType type, int index, Spell root, SpellDisplay display) {
        this.client = client;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.root = root;
        this.display = display;
        this.icon = display.getIcon();
        this.title = display.getTitle();
        this.rootWidget = new SpellWidget(this, client, root, display);
        this.addWidget(this.rootWidget, root);
    }

    public SpellTabType getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public Spell getRoot() {
        return this.root;
    }

    public Text getTitle() {
        return this.title;
    }

    public SpellDisplay getDisplay() {
        return this.display;
    }

    public void drawBackground(MatrixStack matrices, int x, int y, boolean selected) {
        this.type.drawBackground(matrices, this, x, y, selected, this.index);
    }

    public void drawIcon(int x, int y, ItemRenderer itemRenderer) {
        this.type.drawIcon(x, y, this.index, itemRenderer, this.icon);
    }

    public void render(MatrixStack matrices) {
        if (!this.initialized) {
            this.originX = 117 - (this.maxPanX + this.minPanX) / 2d;
            this.originY = 56 - (this.maxPanY + this.minPanY) / 2d;
            this.initialized = true;
        }
        matrices.push();
        matrices.translate(0.0f, 0.0f, 950.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTab.fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0.0f, 0.0f, -950.0f);
        RenderSystem.depthFunc(518);
        AdvancementTab.fill(matrices, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        Identifier identifier = this.display.getBackground();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, Objects.requireNonNullElse(identifier, TextureManager.MISSING_IDENTIFIER));
        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        int k = i % 16;
        int l = j % 16;
        for (int m = -1; m <= 15; ++m) {
            for (int n = -1; n <= 8; ++n) {
                AdvancementTab.drawTexture(matrices, k + 16 * m, l + 16 * n, 0.0f, 0.0f, 16, 16, 16, 16);
            }
        }
        this.rootWidget.renderLines(matrices, i, j, true);
        this.rootWidget.renderLines(matrices, i, j, false);
        this.rootWidget.renderWidgets(matrices, i, j);
        RenderSystem.depthFunc(518);
        matrices.translate(0.0f, 0.0f, -950.0f);
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTab.fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthFunc(515);
        matrices.pop();
    }

    public void drawWidgetTooltip(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        matrices.push();
        matrices.translate(0.0f, 0.0f, -200.0f);
        AdvancementTab.fill(matrices, 0, 0, 234, 113, MathHelper.floor(this.alpha * 255.0f) << 24);
        boolean bl = false;
        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        if (mouseX > 0 && mouseX < 234 && mouseY > 0 && mouseY < 113) {
            for (SpellWidget spellWidget : this.widgets.values()) {
                if (!spellWidget.shouldRender(i, j, mouseX, mouseY)) continue;
                bl = true;
                spellWidget.drawTooltip(matrices, i, j, this.alpha, x, y);
                break;
            }
        }
        matrices.pop();
        this.alpha = bl ? MathHelper.clamp(this.alpha + 0.02f, 0.0f, 0.3f) : MathHelper.clamp(this.alpha - 0.04f, 0.0f, 1.0f);
    }

    public boolean isClickOnTab(int screenX, int screenY, double mouseX, double mouseY) {
        return this.type.isClickOnTab(screenX, screenY, this.index, mouseX, mouseY);
    }

    @Nullable
    public static SpellTab create(MinecraftClient client, SpellTreeScreen screen, int index, Spell root) {
        if (root.getDisplay() == null) {
            return null;
        }
        for (SpellTabType spellTabType : SpellTabType.values()) {
            if (index >= spellTabType.getTabCount()) {
                index -= spellTabType.getTabCount();
                continue;
            }
            return new SpellTab(client, screen, spellTabType, index, root, root.getDisplay());
        }
        return null;
    }

    public void move(double offsetX, double offsetY) {
        if (this.maxPanX - this.minPanX > 234) {
            this.originX = MathHelper.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0);
        }
        if (this.maxPanY - this.minPanY > 113) {
            this.originY = MathHelper.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0);
        }
    }

    public void addSpell(Spell spell) {
        if (spell.getDisplay() == null) {
            return;
        }
        SpellWidget spellWidget = new SpellWidget(this, this.client, spell, spell.getDisplay());
        this.addWidget(spellWidget, spell);
    }

    private void addWidget(SpellWidget widget, Spell spell) {
        this.widgets.put(spell, widget);
        int i = widget.getX();
        int j = i + 28;
        int k = widget.getY();
        int l = k + 27;
        this.minPanX = Math.min(this.minPanX, i);
        this.maxPanX = Math.max(this.maxPanX, j);
        this.minPanY = Math.min(this.minPanY, k);
        this.maxPanY = Math.max(this.maxPanY, l);
        for (SpellWidget spellWidget : this.widgets.values()) {
            spellWidget.addToTree();
        }
    }

    @Nullable
    public SpellWidget getWidget(Spell spell) {
        return this.widgets.get(spell);
    }

    public SpellTreeScreen getScreen() {
        return this.screen;
    }
}
