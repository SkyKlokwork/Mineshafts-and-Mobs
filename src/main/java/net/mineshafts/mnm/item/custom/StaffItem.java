package net.mineshafts.mnm.item.custom;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StaffItem extends Item {
    public StaffItem(Settings settings){super(settings);}

    int i = 0;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 5);
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            SpellDecider(i=3,user,world);
        }

        return super.use(world, user, hand);
    }
    private void SpellDecider(int spellID, PlayerEntity user, World world) {
        switch (spellID){
            case 1:
                user.sendMessage(Text.literal("you are stupid!"));
                break;
            case 3:
                if (!world.isClient) {
                    ItemStack itemStack = new ItemStack(Items.ARROW);
                    ArrowItem arrowItem = (ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
                    PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, user);
                    persistentProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F,  3.0F, 1.0F);
                    persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    world.spawnEntity(persistentProjectileEntity);

                }
            case 7:
                world.playSound( null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.MASTER, 1, 1);
                break;
            default:
                break;
        }
    }
}
