package net.mineshafts.mnm.spells.spelltree;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum SpellFrame {
    TASK("task", 0, Formatting.GREEN),
    CHALLENGE("challenge", 26, Formatting.DARK_PURPLE),
    GOAL("goal", 52, Formatting.GREEN);

    private final String id;
    private final int textureV;
    private final Formatting titleFormat;
    private final Text toastText;

    private SpellFrame(String id, int texV, Formatting titleFormat) {
        this.id = id;
        this.textureV = texV;
        this.titleFormat = titleFormat;
        this.toastText = Text.translatable("advancements.toast." + id);
    }

    public String getId() {
        return this.id;
    }

    public int getTextureV() {
        return this.textureV;
    }

    public static SpellFrame forName(String name) {
        for (SpellFrame spellFrame : SpellFrame.values()) {
            if (!spellFrame.id.equals(name)) continue;
            return spellFrame;
        }
        throw new IllegalArgumentException("Unknown frame type '" + name + "'");
    }

    public Formatting getTitleFormat() {
        return this.titleFormat;
    }

    public Text getToastText() {
        return this.toastText;
    }
}
