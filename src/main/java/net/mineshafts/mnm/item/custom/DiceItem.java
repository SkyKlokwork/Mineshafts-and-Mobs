package net.mineshafts.mnm.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class DiceItem extends Item {
    private final int SIDES;
    public DiceItem(Settings settings, int sides) {
        super(settings);
        this.SIDES = sides;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            outputRandomNumber(user);

            user.getItemCooldownManager().set(this, 5);
        }

        return super.use(world, user, hand);
    }

    private void outputRandomNumber (PlayerEntity player) {
        int[] rolledNums = new int[5];
        int itemCount = player.getMainHandStack().getCount();
        for (int i = 0; i < itemCount; i++){
            rolledNums[i] = getRandomNumber();
        }
        if (itemCount == 1){
            player.sendMessage(Text.literal(getStringColor(rolledNums[0]) + "Rolled a " + rolledNums[0]));
        }
        else {
            player.sendMessage(Text.literal("Your " + itemCount + " dice rolled the following:"));
            for(int i = 0; i < itemCount; i++){
                player.sendMessage(Text.literal(getStringColor(rolledNums[i]) + "Rolled a " + rolledNums[i]));
            }
        }
    }

    private int getRandomNumber() {
        return Random.createLocal().nextInt(SIDES) + 1;
    }

    private String getStringColor(int result) {
        if (result == 1)
            return "§4§l";
        if (result == SIDES)
            return "§2§l";
        return "";
    }
}
