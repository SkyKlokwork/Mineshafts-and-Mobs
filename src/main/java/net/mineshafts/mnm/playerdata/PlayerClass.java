package net.mineshafts.mnm.playerdata;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.ClassEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.mineshafts.mnm.networking.ModMessages.SET_CLASS;

public class PlayerClass {
    private static final Map<ClassEnum, CharStatEnum> playerClass = new HashMap<>();

    public static Set<ClassEnum> getPlayerClasses() {
        return PlayerClass.playerClass.keySet();
    }
    public static void addPlayerClass(ClassEnum playerClass) {
        PlayerClass.playerClass.putIfAbsent(playerClass,null);
    }
    public static CharStatEnum getSubClass(ClassEnum playerClass) {
        return PlayerClass.playerClass.get(playerClass);
    }
    public static void setSubClass(ClassEnum playerClass, CharStatEnum subClass) {
        if(PlayerClass.playerClass.containsKey(playerClass))
            PlayerClass.playerClass.put(playerClass,subClass);
    }
    public static void getClasses(NbtCompound nbt){
        for (String key: nbt.getKeys()){
            ClassEnum classEnum = ClassEnum.SgetEnum(key);
            if (classEnum==null)
                break;

            // TODO: implement subclasses
            CharStatEnum subClassEnum = classEnum.getSubEnum().getEnum(nbt.getString(key));
            playerClass.put(classEnum, subClassEnum);
        }
    }
    public static void setClasses(){
        PacketByteBuf packet = PacketByteBufs.create();
        NbtCompound nbt = new NbtCompound();
        for (Map.Entry<ClassEnum, CharStatEnum> entry: playerClass.entrySet()){
            String subclass = entry.getValue()!=null?entry.getValue().getTranslationKey():"none";
            nbt.putString(entry.getKey().getTranslationKey(),subclass);
        }
        packet.writeNbt(nbt);
        ClientPlayNetworking.send(SET_CLASS, packet);
    }
}
