package net.mineshafts.mnm.playerdata;

import net.mineshafts.mnm.enums.charcreationenums.CharStatEnum;
import net.mineshafts.mnm.enums.charcreationenums.RaceEnum;

public class PlayerRace {
    public static boolean existingRace = false;
    public static boolean existingSubRace = false;
    private static RaceEnum race;
    private static CharStatEnum subRace;

    public static RaceEnum getRace() {
        return race;
    }
    public static void setRace(RaceEnum race) {
        PlayerRace.race = race;
        existingRace = true;
    }

    public static CharStatEnum getSubRace() {
        return subRace;
    }
    public static void setSubRace(CharStatEnum subRace) {
        PlayerRace.subRace = subRace;
        existingSubRace = true;
    }
}
