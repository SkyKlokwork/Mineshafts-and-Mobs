package net.mineshafts.mnm.playerdata;

import org.jetbrains.annotations.Nullable;

public class DefensiveStats {
    private static int armorClass;
    private static int maxHP;
    private static int tempHP;
    private static float walkingSpeed;
    @Nullable
    private static Float climbingSpeed = null;
    @Nullable
    private static Float swimmingSpeed = null;
    @Nullable
    private static Float flyingSpeed = null;

    public static int getArmorClass() {
        return armorClass;
    }

    public static void setArmorClass(int armorClass) {
        DefensiveStats.armorClass = armorClass;
    }

    public static int getMaxHP() {
        return maxHP;
    }

    public static void setMaxHP(int maxHP) {
        DefensiveStats.maxHP = maxHP;
    }

    public static int getTempHP() {
        return tempHP;
    }

    public static void setTempHP(int tempHP) {
        DefensiveStats.tempHP = tempHP;
    }

    public static @Nullable Float getClimbingSpeed() {
        return climbingSpeed;
    }

    public static void setClimbingSpeed(@Nullable Float climbingSpeed) {
        DefensiveStats.climbingSpeed = climbingSpeed;
    }

    public static @Nullable Float getSwimmingSpeed() {
        return swimmingSpeed;
    }

    public static void setSwimmingSpeed(@Nullable Float swimmingSpeed) {
        DefensiveStats.swimmingSpeed = swimmingSpeed;
    }

    public static @Nullable Float getFlyingSpeed() {
        return flyingSpeed;
    }

    public static void setFlyingSpeed(@Nullable Float flyingSpeed) {
        DefensiveStats.flyingSpeed = flyingSpeed;
    }

    public static float getWalkingSpeed() {
        return walkingSpeed;
    }

    public static void setWalkingSpeed(float walkingSpeed) {
        DefensiveStats.walkingSpeed = walkingSpeed;
    }
}
