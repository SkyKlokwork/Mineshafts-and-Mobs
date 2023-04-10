package net.mineshafts.mnm.gui.equipmentscreens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.mineshafts.mnm.ItemChoice;
import net.mineshafts.mnm.enums.charcreationenums.BackgroundEnum;
import net.mineshafts.mnm.enums.charcreationenums.ClassEnum;
import net.mineshafts.mnm.gui.CharCreationScreen;
import net.mineshafts.mnm.playerdata.PlayerBackground;
import net.mineshafts.mnm.playerdata.PlayerClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.mineshafts.mnm.networking.ModMessages.*;

public class StartingEquipment extends CharCreationScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Set<ClassEnum> classEnums;
    private final BackgroundEnum background;
    private final List<ItemChoice[]> choices = new ArrayList<>();
    private ItemStack[] results;
    protected ButtonWidget[] dropdownElements = new ButtonWidget[0];
    public StartingEquipment(Screen parent) {
        super(Text.translatable("mnm.menu.startingequipment"), parent);
        this.background = PlayerBackground.getBackground();
        this.classEnums = PlayerClass.getPlayerClasses();
        if (classEnums.size()!=0)
            this.choices.addAll(classEnums.stream().map(classEnum -> Arrays.stream(classEnum.getStartingEquipment()).toList()).reduce((a,c)->{
                a.addAll(c);
                return a;
            }).get());
        if (background!=null)
            this.choices.addAll(Arrays.stream(background.getStartingEquipment()).toList());
    }
    @Override
    public void init() {
        super.init();
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(4, 4, 4, 0);
        GridWidget.Adder adder = gridWidget.createAdder(2);

        addOptions(adder, 2);

        adder.add(backButton());
        adder.add(screenChangeButton(Text.translatable("mnm.next"),()->{
                giveResults();
                return null;
        }));

        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0.25f);
        this.addDrawableChild(gridWidget);
    }
    private void giveResults(){
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(results.length);
        for (ItemStack item: results)
            packet.writeItemStack(item);
        LOGGER.debug("getting "+results.length+" items");
        ClientPlayNetworking.send(GET_ITEM,packet);
    }

    private void addOptions(GridWidget.Adder adder, int occupiedColumns){
        results = new ItemStack[choices.size()];
        for (int i=0;i<choices.size();i++){
            ItemChoice[] choice = choices.get(i);
            for (int j=0;j<choice.length;j++){
                if (!choice[j].isChoice()) {
                    int finalI = i;
                    int finalJ = j;
                    adder.add(ButtonWidget.builder(Text.translatable(choice[j].getDescription()), button->setResult(finalI,choice[finalJ].getChoices()[0])).build(),occupiedColumns);
                }
                else
                    adder.add(getDropdown(i,Text.translatable(choice[j].getDescription()),choice[j]),occupiedColumns);
                if (j+1<choice.length){
                    adder.add(new TextWidget(Text.translatable("mnm.or"),this.textRenderer),occupiedColumns);
                }
            }
        }
    }
    private void setResult(int index, ItemStack item){
        results[index] = item;
    }
    protected ButtonWidget getDropdown(int index, Text text, ItemChoice choice){
        return ButtonWidget.builder(text, button->createDropdownElements(button, choice, index)).build();
    }
    protected void createDropdownElements(ButtonWidget dropdown, ItemChoice choice, int index){
        int x = dropdown.getX();
        int y = dropdown.getY();
        int w = dropdown.getWidth();
        int h = dropdown.getHeight();
        clearDropdowns();
        dropdownElements = new ButtonWidget[choice.choiceCount()+1];
        dropdownElements[0] = ButtonWidget.builder(Text.translatable(choice.getDescription()),button -> {
            dropdown.setMessage(Text.translatable(choice.getDescription()));
            clearDropdowns();
        }).dimensions(x,y+h, w, h).build();
        this.addDrawableChild(dropdownElements[0]);
        for (int i=1;i<choice.choiceCount()+1;i++){
            int finalI = i-1;
            dropdownElements[i] = ButtonWidget.builder(choice.getChoiceNames()[i-1], button->setDropdownValue(dropdown, choice.getChoices()[finalI],button,index)).dimensions(x,y+(i+1)*h, w, h).build();
            this.addDrawableChild(dropdownElements[i]);
        }
    }

    protected void setDropdownValue(ButtonWidget root, ItemStack item, ButtonWidget element, int index){
        results[index] = item;
        root.setMessage(element.getMessage());
        clearDropdowns();
    }
    protected void clearDropdowns(){
        for (ButtonWidget element: dropdownElements){
            this.remove(element);
        }
        dropdownElements = new ButtonWidget[0];
    }
}
