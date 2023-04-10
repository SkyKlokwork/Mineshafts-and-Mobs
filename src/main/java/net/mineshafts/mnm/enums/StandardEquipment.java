package net.mineshafts.mnm.enums;

import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.mineshafts.mnm.item.ModItems;

public enum StandardEquipment {
    GREATAXE(Items.IRON_AXE),
    HANDAXE(Items.IRON_AXE),
    JAVELIN(Items.IRON_BARS),
    BACKPACK(Items.CHEST),
    BEDROLL(Items.RED_BED),
    MESS_KIT(Items.BOWL),
    TINDER_BOX(Items.FLINT_AND_STEEL),
    TORCH(Items.TORCH),
    RATION(Items.COOKED_BEEF),
    WATERSKIN(Items.WATER_BUCKET),
    ROPE_50ft(Items.LEAD),
    PLATINUM_COIN(ModItems.PLAT_COIN),
    GOLD_COIN(ModItems.GOLD_COIN),
    SILVER_COIN(ModItems.SILVER_COIN),
    COPPER_COIN(ModItems.COPPER_COIN),
    COMMON_CLOTHES(Items.LEATHER_CHESTPLATE),
    INCENSE_STICK(Items.CANDLE),
    VESTMENTS(Items.LEATHER_HELMET),
    PRAYER_BOOK(Items.BOOK),
    PRAYER_WHEEL(Items.BOWL),
    AMULET(Items.EMERALD),
    EMBLEM(Items.BLACK_BANNER),
    RELIQUARY(Items.GLASS_BOTTLE),
    HOLY_SYMBOL(Items.SUNFLOWER),
    GLAIVE(Items.IRON_SWORD),
    TRIDENT(Items.TRIDENT),
    CROSSBOW_LIGHT(Items.CROSSBOW),
    SICKLE(Items.IRON_HOE),
    MONEY_POUCH(Items.BUNDLE){
        @Override
        public ItemStack getStack(float value){
            ItemStack[] coins = new ItemStack[4];
            if (value/10>=1) {
                coins[0] = PLATINUM_COIN.getStack((int) (value/10));
                value %= 10;
            }
            if (value>=1) {
                coins[1] = GOLD_COIN.getStack((int)value);
                value %= 1;
            }
            if (value/0.1>=1) {
                coins[2] = SILVER_COIN.getStack((int) (value/0.1));
                value %= 0.1;
            }
            if (value/0.01>=1) {
                coins[3] = COPPER_COIN.getStack((int) (value/0.01));
            }
            MONEY_POUCH.contents = coins;
            return super.getStack(1);
        }
    },
    EXPLORERS_PACK(Items.BUNDLE,
            BACKPACK.getStack(1),BEDROLL.getStack(1),MESS_KIT.getStack(1),TINDER_BOX.getStack(1),
            TORCH.getStack(10),RATION.getStack(10),WATERSKIN.getStack(1),ROPE_50ft.getStack(1));
    public static final ItemStack[] A_HOLY_SYMBOL = new ItemStack[]{AMULET.getStack(1),EMBLEM.getStack(1),RELIQUARY.getStack(1),HOLY_SYMBOL.getStack(1)};
    public static final ItemStack[] MARTIAL_MELEE = new ItemStack[]{GLAIVE.getStack(1),TRIDENT.getStack(1)};
    public static final ItemStack[] SIMPLE = new ItemStack[]{CROSSBOW_LIGHT.getStack(1),SICKLE.getStack(1)};
    private static final String ITEMS_KEY = "Items";
    private final Item item;
    private ItemStack[] contents;
    StandardEquipment(Item item){
        this.item = item;
    }
    StandardEquipment(Item item, ItemStack... contents){
        this.item = item;
        this.contents = contents;
    }

    public ItemStack getStack(float count){
        ItemStack itemStack = new ItemStack(item, (int)count);
        if (contents==null)
            return itemStack;
        NbtCompound nbtCompound = itemStack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            nbtCompound.put(ITEMS_KEY, new NbtList());
        }
        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
        for (ItemStack stack: contents){
            if (stack==null)
                continue;
            NbtCompound nbtCompound2 = new NbtCompound();
            stack.writeNbt(nbtCompound2);
            nbtList.add(0, nbtCompound2);
        }
        itemStack.setNbt(nbtCompound);
        return itemStack;
    }
}
