package net.mineshafts.mnm.spells.spelltree;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class SpellToast
implements Toast {
    private final Spell spell;
    private boolean soundPlayed;

    public SpellToast(Spell spell) {
        this.spell = spell;
    }

    @Override
    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        SpellDisplay spellDisplay = this.spell.getDisplay();
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        if (spellDisplay != null) {
            int i;
            List<OrderedText> list = manager.getClient().textRenderer.wrapLines(spellDisplay.getTitle(), 125);
            int n = i = spellDisplay.getFrame() == SpellFrame.CHALLENGE ? 0xFF88FF : 0xFFFF00;
            if (list.size() == 1) {
                manager.getClient().textRenderer.draw(matrices, spellDisplay.getFrame().getToastText(), 30.0f, 7.0f, i | 0xFF000000);
                manager.getClient().textRenderer.draw(matrices, list.get(0), 30.0f, 18.0f, -1);
            } else {
                int j = 1500;
                float f = 300.0f;
                if (startTime < 1500L) {
                    int k = MathHelper.floor(MathHelper.clamp((float)(1500L - startTime) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    manager.getClient().textRenderer.draw(matrices, spellDisplay.getFrame().getToastText(), 30.0f, 11.0f, i | k);
                } else {
                    int k = MathHelper.floor(MathHelper.clamp((float)(startTime - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int l = this.getHeight() / 2 - list.size() * manager.getClient().textRenderer.fontHeight / 2;
                    for (OrderedText orderedText : list) {
                        manager.getClient().textRenderer.draw(matrices, orderedText, 30.0f, (float)l, 0xFFFFFF | k);
                        l += manager.getClient().textRenderer.fontHeight;
                    }
                }
            }
            if (!this.soundPlayed && startTime > 0L) {
                this.soundPlayed = true;
                if (spellDisplay.getFrame() == SpellFrame.CHALLENGE) {
                    manager.getClient().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f));
                }
            }
            manager.getClient().getItemRenderer().renderInGui(spellDisplay.getIcon(), 8, 8);
            return startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}
