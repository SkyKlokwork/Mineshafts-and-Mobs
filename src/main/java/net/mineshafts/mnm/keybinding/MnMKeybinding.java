package net.mineshafts.mnm.keybinding;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mineshafts.mnm.gui.CharacterCreationMenu;
import net.mineshafts.mnm.playerdata.SpellCycle;
import org.lwjgl.glfw.GLFW;

@Environment(value= EnvType.CLIENT)
public class MnMKeybinding {
    public static KeyBinding openASMenu = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mnm.menu.abilityscores", InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_M, "key.category.mnm"));
    public static KeyBinding openSpellTree = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mnm.menu.spelltree", InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_O, "key.category.mnm"));
    public static KeyBinding nextSpell = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mnm.spell.next_spell",InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_BRACKET,"key.category.mnm"));
    public static KeyBinding prevSpell = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mnm.spell.prev_spell",InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_LEFT_BRACKET,"key.category.mnm"));
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openASMenu.wasPressed()) {
                client.setScreen(new CharacterCreationMenu());
            }
        });

        ClientTickEvents.END_WORLD_TICK.register(client -> {
            if(nextSpell.wasPressed())
            {
                SpellCycle.next_spell();
            }
        });
        ClientTickEvents.END_WORLD_TICK.register(client -> {
            if(prevSpell.wasPressed())
            {
                SpellCycle.prev_spell();
            }
        });
    }
}
