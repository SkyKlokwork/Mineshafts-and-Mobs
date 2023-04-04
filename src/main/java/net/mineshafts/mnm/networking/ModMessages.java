package net.mineshafts.mnm.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.networking.packets.ExampleC2SPacket;

public class ModMessages {
    public static final Identifier NEXT_SPELL_ID = new Identifier(MnM.ModId, "next_spell");
    public static final Identifier PREV_SPELL_ID = new Identifier(MnM.ModId, "prev_spell");
    public static final Identifier LEARN_SPELL_ID = new Identifier(MnM.ModId, "learn_spell");
    public static final Identifier CURRENT_SPELL_ID = new Identifier(MnM.ModId, "retrieve_spells");
    public static final Identifier EXAMPLE_ID = new Identifier(MnM.ModId, "example");

    public static void registerC2SPacket(){
        //packets sent from the client to the server
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);

    }
    public static void registerS2CPacket(){
        //packets sent from the server to the client
    }
}
