package net.mineshafts.mnm.playerdata;

import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;

public class PlayerBackground {
    public static boolean existingBackground = false;
    private static BackgroundEnum background;

    public static BackgroundEnum getBackground() {
        return background;
    }
    public static void setBackground(BackgroundEnum background) {
        PlayerBackground.background = background;
        existingBackground = true;
    }
}
