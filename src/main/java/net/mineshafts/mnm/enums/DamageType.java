package net.mineshafts.mnm.enums;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value= EnvType.CLIENT)
public enum DamageType {
    ACID(0, "Acid"),
    BLUDGEONING(1, "Bludgeoning"),
    COLD(2, "Cold"),
    FIRE(3, "Fire"),
    FORCE(4, "Force"),
    LIGHTNING(5, "Lightning"),
    NECROTIC(6,"Necrotic"),
    PIERCING(7,"Piercing"),
    POISON(8,"Poison"),
    PSYCHIC(9,"Psychic"),
    RADIANT(10,"Radiant"),
    SLASHING(11,"Slashing"),
    THUNDER(12,"Thunder");
    private final int statId;
    private final String id;

    DamageType(int statId, String id) {
        this.statId = statId;
        this.id = id;
    }
    public String getId() {
        return this.id;
    }
}
