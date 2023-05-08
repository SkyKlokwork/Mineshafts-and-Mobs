package net.mineshafts.mnm.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import static net.mineshafts.mnm.networking.ModMessages.BROADCAST_NBT;
import static net.mineshafts.mnm.networking.ModMessages.RESET_NBT;

public class CharacterCreationMenu extends MenuScreenMain {

    public CharacterCreationMenu() {
        super(Text.translatable("mnm.menu.charactercreator"));
    }
    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(1);

        adder.add(createButton(Text.translatable("mnm.menu.raceselection"),button-> client.setScreen(new RaceSelection(this))));
        adder.add(createButton(Text.translatable("mnm.menu.broadcastnbt"),button -> ClientPlayNetworking.send(BROADCAST_NBT,PacketByteBufs.create())));
        adder.add(createButton(Text.translatable("mnm.menu.resetnbt"),button -> ClientPlayNetworking.send(RESET_NBT, PacketByteBufs.create())));

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
}
