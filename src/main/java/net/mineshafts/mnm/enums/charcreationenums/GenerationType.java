package net.mineshafts.mnm.enums.charcreationenums;

import net.minecraft.client.gui.screen.Screen;
import net.mineshafts.mnm.gui.statgenscreens.RollScreen;
import net.mineshafts.mnm.gui.statgenscreens.RolledScreen;
import net.mineshafts.mnm.gui.statgenscreens.StandardArrayScreen;

public enum GenerationType implements CharStatEnum{
    STANDARD_ARRAY(0,"standard_array"){
        @Override
        public Screen genScreen(Screen parent) {
            return new StandardArrayScreen(parent);
        }
    },
    MANUAL(1,"manual") {
        @Override
        public Screen genScreen(Screen parent) {
            return null;
        }
    },
    ROLLED(2,"rolled") {
        @Override
        public Screen genScreen(Screen parent) {
            return new RollScreen(parent);
        }
    },
    POINT_BUY(3,"point_buy") {
        @Override
        public Screen genScreen(Screen parent) {
            return null;
        }
    };
    private final int id;
    private final String key;
    GenerationType(int id, String key){
        this.id = id;
        this.key = key;
    }

    @Override
    public int getId(CharStatEnum Enum) {
        return SgetId(Enum);
    }
    public static int SgetId(CharStatEnum Enum) {
        return Enum.getId();
    }
    @Override
    public CharStatEnum getEnum(int id) {
        return SgetEnum(id);
    }
    public static CharStatEnum SgetEnum(int id){
        for (GenerationType type: GenerationType.values()){
            if (type.id==id)
                return type;
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTranslationKey() {
        return "mnm."+key;
    }

    public abstract Screen genScreen(Screen parent);
}
