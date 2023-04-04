package net.mineshafts.mnm.event;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.mineshafts.mnm.networking.ModMessages;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    private static final String KEY_CATEGORY_MINESHAFTS = "key.category.mineshafts.keybindings";
    private static final String KEY_MINESHAFTS_NEXT_SPELL = "key.mineshafts.next_spell";
    private static final String KEY_MINESHAFTS_PREV_SPELL = "key.mineshafts.prev_spell";
    private static final String KEY_TEST_MINESHAFTS = "key.mineshafts.test_key";
    private static KeyBinding next_spell;
    private static KeyBinding prev_spell;
    private static KeyBinding test_key;

//    ---------------------------------------------------------
//    example of how to send a packet to the server
    public static void exampleKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (test_key.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.EXAMPLE_ID, PacketByteBufs.create());
            }
        });
    }
//  -----------------------------------------------------------

    public static void nextSpellKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(next_spell.wasPressed()) {
               return;
            }
        });
    }
    public static void prevSpellKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(prev_spell.wasPressed()) {
             return;
            }
        });
    }

    public static void register() {
        prev_spell = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MINESHAFTS_PREV_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                KEY_CATEGORY_MINESHAFTS
        ));
        next_spell = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MINESHAFTS_NEXT_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                KEY_CATEGORY_MINESHAFTS
        ));
        test_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TEST_MINESHAFTS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_MINESHAFTS
        ));
        exampleKey();
    }

}
