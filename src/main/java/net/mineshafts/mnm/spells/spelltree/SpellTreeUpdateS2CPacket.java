package net.mineshafts.mnm.spells.spelltree;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SpellTreeUpdateS2CPacket
        implements Packet<ClientPlayPacketListener> {
    private final boolean clearCurrent;
    private final Map<Identifier, Spell.Builder> toEarn;
    private final Set<Identifier> toRemove;
    private final Map<Identifier, SpellProgress> toSetProgress;

    public SpellTreeUpdateS2CPacket(boolean clearCurrent, Collection<Spell> toEarn, Set<Identifier> toRemove, Map<Identifier, SpellProgress> toSetProgress) {
        this.clearCurrent = clearCurrent;
        ImmutableMap.Builder<Identifier, Spell.Builder> builder = ImmutableMap.builder();
        for (Spell spell : toEarn) {
            builder.put(spell.getId(), spell.createTask());
        }
        this.toEarn = builder.build();
        this.toRemove = ImmutableSet.copyOf(toRemove);
        this.toSetProgress = ImmutableMap.copyOf(toSetProgress);
    }

    public SpellTreeUpdateS2CPacket(PacketByteBuf buf) {
        this.clearCurrent = buf.readBoolean();
        this.toEarn = buf.readMap(PacketByteBuf::readIdentifier, Spell.Builder::fromPacket);
        this.toRemove = buf.readCollection(Sets::newLinkedHashSetWithExpectedSize, PacketByteBuf::readIdentifier);
        this.toSetProgress = buf.readMap(PacketByteBuf::readIdentifier, SpellProgress::fromPacket);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.clearCurrent);
        buf.writeMap(this.toEarn, PacketByteBuf::writeIdentifier, (buf2, task) -> task.toPacket(buf2));
        buf.writeCollection(this.toRemove, PacketByteBuf::writeIdentifier);
        buf.writeMap(this.toSetProgress, PacketByteBuf::writeIdentifier, (buf2, progress) -> progress.toPacket(buf2));
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        // TODO: add .onSpells(this) handler
//        clientPlayPacketListener.onAdvancements(this);
    }

    public Map<Identifier, Spell.Builder> getSpellsToEarn() {
        return this.toEarn;
    }

    public Set<Identifier> getSpellIdsToRemove() {
        return this.toRemove;
    }

    public Map<Identifier, SpellProgress> getSpellsToProgress() {
        return this.toSetProgress;
    }

    public boolean shouldClearCurrent() {
        return this.clearCurrent;
    }
}
