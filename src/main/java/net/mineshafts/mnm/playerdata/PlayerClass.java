package net.mineshafts.mnm.playerdata;

import net.mineshafts.mnm.enums.charcreationenums.ClassEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerClass {
    private static final Map<ClassEnum, Enum<?>> playerClass = new HashMap<>();

    public static Set<ClassEnum> getPlayerClasses() {
        return PlayerClass.playerClass.keySet();
    }
    public static void addPlayerClass(ClassEnum playerClass) {
        PlayerClass.playerClass.putIfAbsent(playerClass,null);
    }
    public static Enum<?> getSubClass(ClassEnum playerClass) {
        return PlayerClass.playerClass.get(playerClass);
    }
    public static void setSubClass(ClassEnum playerClass, Enum<?> subClass) {
        if(PlayerClass.playerClass.containsKey(playerClass))
            PlayerClass.playerClass.put(playerClass,subClass);
    }
}
