package net.mineshafts.mnm.util;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class OriginGetter {
    private static final Identifier RACE = new Identifier("mnm", "race");
    private static final Identifier RACE_OPTIONS = new Identifier("mnm", "raceoptions");
    private static final Identifier SUB_RACE = new Identifier("mnm", "subrace");
    private static final Identifier CLASS = new Identifier("mnm", "class");
    public static String getOrigin(Identifier layer) {
        OriginComponent originComponent = ModComponents.ORIGIN.get(MinecraftClient.getInstance().player);
        OriginLayer originLayer = OriginLayers.getLayer(layer);
        Origin origin = originComponent.getOrigin(originLayer);
        return origin.toString();
    }
    public static String get_Race(){
        return getOrigin(RACE);
    }
    public static String get_Subrace(){
        return getOrigin(SUB_RACE);
    }
    public static String get_RaceOptions(){
        return getOrigin(RACE_OPTIONS);
    }
    public static String get_Class(){
        return getOrigin(CLASS);
    }
}
