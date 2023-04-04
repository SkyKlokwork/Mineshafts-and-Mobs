package net.mineshafts.mnm.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound persistentData();
}
