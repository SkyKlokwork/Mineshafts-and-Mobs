package net.mineshafts.mnm.spells.spelltree;

import net.minecraft.advancement.Advancement;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.AdvancementTabC2SPacket;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SpellTabC2SPacket
        implements Packet<ServerPlayPacketListener> {
    private final SpellTabC2SPacket.Action action;
    @Nullable
    private final Identifier tabToOpen;

    public SpellTabC2SPacket(SpellTabC2SPacket.Action action, @Nullable Identifier tab) {
        this.action = action;
        this.tabToOpen = tab;
    }

    public static SpellTabC2SPacket open(Spell spell) {
        return new SpellTabC2SPacket(SpellTabC2SPacket.Action.OPENED_TAB, spell.getId());
    }

    public static SpellTabC2SPacket close() {
        return new SpellTabC2SPacket(SpellTabC2SPacket.Action.CLOSED_SCREEN, null);
    }

    public SpellTabC2SPacket(PacketByteBuf buf) {
        this.action = buf.readEnumConstant(SpellTabC2SPacket.Action.class);
        this.tabToOpen = this.action == SpellTabC2SPacket.Action.OPENED_TAB ? buf.readIdentifier() : null;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(this.action);
        if (this.action == SpellTabC2SPacket.Action.OPENED_TAB) {
            buf.writeIdentifier(this.tabToOpen);
        }
    }

    @Override
    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
        // TODO: write .onSpellTab(this) handler
//        serverPlayPacketListener.onAdvancementTab(this);
    }

    public SpellTabC2SPacket.Action getAction() {
        return this.action;
    }

    @Nullable
    public Identifier getTabToOpen() {
        return this.tabToOpen;
    }

    public static enum Action {
        OPENED_TAB,
        CLOSED_SCREEN;

    }
}
