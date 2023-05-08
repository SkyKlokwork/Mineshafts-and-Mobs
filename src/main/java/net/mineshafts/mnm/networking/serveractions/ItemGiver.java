package net.mineshafts.mnm.networking.serveractions;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.mineshafts.mnm.util.IEntityDataSaver;

public class ItemGiver {
    public static void giveItem(ServerPlayerEntity player, ItemStack item) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        boolean hasGotten = nbt.contains("hasStartingEquipment");
        if (hasGotten)
            hasGotten = nbt.getBoolean("hasStartingEquipment");
        if (hasGotten)
            return;
        player.giveItemStack(item);
    }

    public static void setGotten(ServerPlayerEntity player, String key, boolean val){
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putBoolean(key, val);
    }
}
