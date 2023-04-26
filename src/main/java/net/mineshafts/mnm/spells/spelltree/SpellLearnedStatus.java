package net.mineshafts.mnm.spells.spelltree;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value= EnvType.CLIENT)
public enum SpellLearnedStatus {
    LEARNED(0),
    UNLEARNED(1);

    private final int spriteIndex;

    SpellLearnedStatus(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    public int getSpriteIndex() {
        return this.spriteIndex;
    }
}
