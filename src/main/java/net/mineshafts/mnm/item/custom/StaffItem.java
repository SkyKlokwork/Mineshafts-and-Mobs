package net.mineshafts.mnm.item.custom;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.mineshafts.mnm.networking.ModMessages.SET_SPELLS;

public class StaffItem extends Item {
    private final int COOLDOWN;
    private final int RANGE;
    private final float POWER;
    private final int MANA_USAGE;

    public StaffItem(Settings settings, int cooldown, int range, float power, int mana_usage){
        super(settings);
        this.COOLDOWN = cooldown;
        this.RANGE = range;
        this.POWER = power;
        this.MANA_USAGE = mana_usage;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        PacketByteBuf buf = PacketByteBufs.create();
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            user.getItemCooldownManager().set(this, 5);
            if(user.isSneaking())
                buf.writeIntArray(new int[]{1,13,12,9});
            else
                buf.writeInt(13);
            ClientPlayNetworking.send(SET_SPELLS, buf);
        }


        return super.use(world, user, hand);
    }
}
