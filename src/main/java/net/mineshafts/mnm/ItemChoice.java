package net.mineshafts.mnm;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Arrays;

public class ItemChoice {
    private final ItemStack[] choices;
    private final boolean isChoice;
    private String[] names;
    private final String description;
    public ItemChoice(String description, ItemStack[] choices){
        this.description = description;
        this.choices = choices;
        this.isChoice = true;
    }
    public ItemChoice(String description, ItemStack choice){
        this.description = description;
        choices = new ItemStack[1];
        choices[0] = choice;
        this.isChoice = false;
    }

    // this constructor is only for when the item of the choice is a placeholder
    public ItemChoice(String... placeholders){
        this.description = placeholders[0];
        isChoice = placeholders.length>1;
        this.choices = null;
        this.names = placeholders;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public ItemStack[] getChoices() {
        return choices;
    }
    public Text[] getChoiceNames(){
        if (names==null&choices!=null)
            return Arrays.stream(choices).map(ItemStack::getName).toList().toArray(new Text[0]);
        else if (names!=null)
            return (Text[]) Arrays.stream(names).map(Text::literal).toArray();
        else
            return null;
    }
    public int choiceCount(){
        if (names==null&choices!=null)
            return choices.length;
        else if (names!=null)
            return names.length;
        else
            return 0;
    }
    public String getDescription(){
        return description;
    }
}
