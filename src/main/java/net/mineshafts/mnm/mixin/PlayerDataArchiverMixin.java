package net.mineshafts.mnm.mixin;

import net.minecraft.nbt.NbtCompound;
import net.mineshafts.mnm.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;


import javax.swing.text.html.parser.Entity;

@Mixin(Entity.class)
public abstract class PlayerDataArchiverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
    return persistentData;
    }
}
