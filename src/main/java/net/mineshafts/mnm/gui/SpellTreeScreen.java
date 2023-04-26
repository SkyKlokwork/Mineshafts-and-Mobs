package net.mineshafts.mnm.gui;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.AdvancementTabC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.keybinding.MnMKeybinding;
import net.mineshafts.mnm.spells.spelltree.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class SpellTreeScreen
        extends Screen
        implements ClientSpellTreeManager.Listener {
    private static final Identifier WINDOW_TEXTURE = new Identifier("textures/gui/advancements/window.png");
    private static final Identifier TABS_TEXTURE = new Identifier("textures/gui/advancements/tabs.png");
    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 140;
    private static final int PAGE_OFFSET_X = 9;
    private static final int PAGE_OFFSET_Y = 18;
    public static final int PAGE_WIDTH = 234;
    public static final int PAGE_HEIGHT = 113;
    private static final int TITLE_OFFSET_X = 8;
    private static final int TITLE_OFFSET_Y = 6;
    public static final int field_32302 = 16;
    public static final int field_32303 = 16;
    public static final int field_32304 = 14;
    public static final int field_32305 = 7;
    private static final Text SAD_LABEL_TEXT = Text.translatable("advancements.sad_label");
    private static final Text EMPTY_TEXT = Text.translatable("advancements.empty");
    private static final Text SPELL_TREE_TEXT = Text.translatable("mnm.gui.spelltree");
    private final ClientSpellTreeManager spellTreeHandler;
    private final Map<Spell, SpellTab> tabs = Maps.newLinkedHashMap();
    @Nullable
    private SpellTab selectedTab;
    private boolean movingTab;

    public SpellTreeScreen(ClientSpellTreeManager spellTreeHandler) {
        super(NarratorManager.EMPTY);
        this.spellTreeHandler = spellTreeHandler;
    }

    @Override
    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;
        this.spellTreeHandler.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.spellTreeHandler.selectTab(this.tabs.values().iterator().next().getRoot(), true);
        } else {
            this.spellTreeHandler.selectTab(this.selectedTab == null ? null : this.selectedTab.getRoot(), true);
        }
    }

    @Override
    public void removed() {
        this.spellTreeHandler.setListener(null);
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            clientPlayNetworkHandler.sendPacket(AdvancementTabC2SPacket.close());
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int i = (this.width - 252) / 2;
            int j = (this.height - 140) / 2;
            for (SpellTab spellTab : this.tabs.values()) {
                if (!spellTab.isClickOnTab(i, j, mouseX, mouseY)) continue;
                this.spellTreeHandler.selectTab(spellTab.getRoot(), true);
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (MnMKeybinding.openSpellTree.matchesKey(keyCode, scanCode)) {
            this.client.setScreen(null);
            this.client.mouse.lockCursor();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        this.renderBackground(matrices);
        this.drawSpellTree(matrices, mouseX, mouseY, i, j);
        this.drawWindow(matrices, i, j);
        this.drawWidgetTooltip(matrices, mouseX, mouseY, i, j);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            this.movingTab = false;
            return false;
        }
        if (!this.movingTab) {
            this.movingTab = true;
        } else if (this.selectedTab != null) {
            this.selectedTab.move(deltaX, deltaY);
        }
        return true;
    }

    private void drawSpellTree(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        SpellTab spellTab = this.selectedTab;
        if (spellTab == null) {
            AdvancementsScreen.fill(matrices, x + 9, y + 18, x + 9 + 234, y + 18 + 113, -16777216);
            int i = x + 9 + 117;
            AdvancementsScreen.drawCenteredText(matrices, this.textRenderer, EMPTY_TEXT, i, y + 18 + 56 - this.textRenderer.fontHeight / 2, -1);
            AdvancementsScreen.drawCenteredText(matrices, this.textRenderer, SAD_LABEL_TEXT, i, y + 18 + 113 - this.textRenderer.fontHeight, -1);
            return;
        }
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x + 9, y + 18, 0.0f);
        RenderSystem.applyModelViewMatrix();
        spellTab.render(matrices);
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    public void drawWindow(MatrixStack matrices, int x, int y) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, WINDOW_TEXTURE);
        this.drawTexture(matrices, x, y, 0, 0, 252, 140);
        if (this.tabs.size() > 1) {
            RenderSystem.setShaderTexture(0, TABS_TEXTURE);
            for (SpellTab spellTab : this.tabs.values()) {
                spellTab.drawBackground(matrices, x, y, spellTab == this.selectedTab);
            }
            RenderSystem.defaultBlendFunc();
            for (SpellTab spellTab : this.tabs.values()) {
                spellTab.drawIcon(x, y, this.itemRenderer);
            }
            RenderSystem.disableBlend();
        }
        this.textRenderer.draw(matrices, SPELL_TREE_TEXT, (float)(x + 8), (float)(y + 6), 0x404040);
    }

    private void drawWidgetTooltip(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.selectedTab != null) {
            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.push();
            matrixStack.translate(x + 9, y + 18, 400.0f);
            RenderSystem.applyModelViewMatrix();
            RenderSystem.enableDepthTest();
            this.selectedTab.drawWidgetTooltip(matrices, mouseX - x - 9, mouseY - y - 18, x, y);
            RenderSystem.disableDepthTest();
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        }
        if (this.tabs.size() > 1) {
            for (SpellTab spellTab : this.tabs.values()) {
                if (!spellTab.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.renderTooltip(matrices, spellTab.getTitle(), mouseX, mouseY);
            }
        }
    }

    @Override
    public void onRootAdded(Spell root) {
        SpellTab spellTab = SpellTab.create(this.client, this, this.tabs.size(), root);
        if (spellTab == null) {
            return;
        }
        this.tabs.put(root, spellTab);
    }

    @Override
    public void onRootRemoved(Spell root) {
    }

    @Override
    public void onDependentAdded(Spell dependent) {
        SpellTab spellTab = this.getTab(dependent);
        if (spellTab != null) {
            spellTab.addSpell(dependent);
        }
    }

    @Override
    public void onDependentRemoved(Spell dependent) {
    }

    @Override
    public void setProgress(Spell advancement, SpellProgress progress) {
        SpellWidget spellWidget = this.getSpellWidget(advancement);
        if (spellWidget != null) {
            spellWidget.setProgress(progress);
        }
    }

    @Override
    public void selectTab(@Nullable Spell spell) {
        this.selectedTab = this.tabs.get(spell);
    }

    @Override
    public void onClear() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    @Nullable
    public SpellWidget getSpellWidget(Spell spell) {
        SpellTab spellTab = this.getTab(spell);
        return spellTab == null ? null : spellTab.getWidget(spell);
    }

    @Nullable
    private SpellTab getTab(Spell spell) {
        while (spell.getParent() != null) {
            spell = spell.getParent();
        }
        return this.tabs.get(spell);
    }
}
