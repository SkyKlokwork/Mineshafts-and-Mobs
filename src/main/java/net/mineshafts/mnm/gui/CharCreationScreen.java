package net.mineshafts.mnm.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;
import net.mineshafts.mnm.gui.statgenscreens.ScoreGenSelection;

public class CharCreationScreen extends MenuScreen {
    public static final Screen CHAR_CREATION = new CharacterCreationMenu();
    public static final MenuScreen RACE_SELECTION = new RaceSelection(CHAR_CREATION);
    public static final MenuScreen CLASS_SELECTION = new ClassSelection(RACE_SELECTION);
    public static final MenuScreen ABILITIES = new ScoreGenSelection(CLASS_SELECTION);
    public static final MenuScreen DESCRIPTION = new BackgroundSelection(ABILITIES);
    public static final MenuScreen EQUIPMENT = new EquipmentSelection(DESCRIPTION);
    public static Screen[] tabList = new Screen[]{CHAR_CREATION, RACE_SELECTION, CLASS_SELECTION, ABILITIES, DESCRIPTION, EQUIPMENT};
    protected CharCreationScreen(Text title, Screen parent) {
        super(title, parent);
    }
    protected void createTabNav(){
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().margin(0, 0, 0, 0);
        GridWidget.Adder adder = gridWidget.createAdder(tabList.length);
        for (Screen tab: tabList){
            if (tab == null) {
                adder.add(new ButtonWidget.Builder(Text.of("Placeholder"),(button)->{}).width(98).build());
                continue;
            }
            adder.add(screenChangeButton(tab.getTitle(),()->tab));
        }
        gridWidget.recalculateDimensions();
        SimplePositioningWidget.setPos(gridWidget, 0, 0, this.width, this.height, 0.5f, 0f);
        this.addDrawableChild(gridWidget);
    }
    @Override
    public void init(){
        createTabNav();
    }
}
