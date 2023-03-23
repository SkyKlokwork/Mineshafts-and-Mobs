package net.mineshafts.mnm.enums.charcreationenums;

import net.minecraft.util.TranslatableOption;

public interface CharStatEnum extends TranslatableOption {
    int getId(CharStatEnum Enum);
    CharStatEnum getEnum(int id);
}
