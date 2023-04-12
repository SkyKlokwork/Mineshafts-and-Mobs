package net.mineshafts.mnm.keybinding;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mineshafts.mnm.gui.CharacterCreationMenu;
import net.mineshafts.mnm.networking.ModMessages;
import org.lwjgl.glfw.GLFW;

@Environment(value= EnvType.CLIENT)
public class MnMKeybinding {
    public static final String MNM_CATEGORY = "key.category.mnm";

    public static void register() {
        KeyBinding openASMenu = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.mnm.menu.abilityscores", InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_M, MNM_CATEGORY));


        KeyBinding nextSpell = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.mnm.spells.nextspell", InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_BRACKET, MNM_CATEGORY));

        KeyBinding prevSpell = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.mnm.spelss.prevspell", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET, MNM_CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openASMenu.wasPressed()) {
                client.setScreen(new CharacterCreationMenu());
            }
        });

    }
}
