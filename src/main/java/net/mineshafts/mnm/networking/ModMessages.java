package net.mineshafts.mnm.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mineshafts.mnm.MnM;
import net.mineshafts.mnm.playerdata.PlayerAbilityScores;
import net.mineshafts.mnm.networking.packet.AbilityScoresC2SPacket;

public class ModMessages {
    public static final Identifier STRENGTH_ADD = new Identifier(MnM.ModId, "strength_add");
    public static final Identifier STRENGTH_SET = new Identifier(MnM.ModId, "strength_set");
    public static final Identifier DEXTERITY_SET = new Identifier(MnM.ModId, "dexterity_set");
    public static final Identifier STRENGTH_REQUEST = new Identifier(MnM.ModId, "strength_request");
    public static final Identifier STRENGTH_GET = new Identifier(MnM.ModId, "strength_get");
    public static final Identifier SEND_MESSAGE = new Identifier(MnM.ModId, "send_message");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(STRENGTH_ADD, AbilityScoresC2SPacket::addStrength);
        ServerPlayNetworking.registerGlobalReceiver(STRENGTH_SET, AbilityScoresC2SPacket::setStrength);
        ServerPlayNetworking.registerGlobalReceiver(DEXTERITY_SET, AbilityScoresC2SPacket::setDexterity);
        ServerPlayNetworking.registerGlobalReceiver(STRENGTH_REQUEST, AbilityScoresC2SPacket::sendStrength);
    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(STRENGTH_GET, (client, handler, buf, responseSender) -> {
            int str = buf.readInt();
            client.execute(() -> {
                PlayerAbilityScores.setScore("strength", str);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(SEND_MESSAGE, (client, handler, buf, responseSender) -> {
            String msg = buf.readString();
            client.player.sendMessage(Text.of(msg));
        });
    }
}
