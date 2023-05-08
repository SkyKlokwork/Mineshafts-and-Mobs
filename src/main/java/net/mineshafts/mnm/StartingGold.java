package net.mineshafts.mnm;

import net.minecraft.util.math.random.Random;

public class StartingGold extends Gold{

    private int dice, sides, multiplier;
    public StartingGold(int dice, int sides, int multiplier){
        this.dice = dice;
        this.sides = sides;
        this.multiplier = multiplier;
    }
    public StartingGold(float value){
        setValue(value);
    }
    public int roll(){
        int roll = 0;
        for (int i=0;i<dice;i++)
            roll += Random.createLocal().nextInt(sides) + 1;
        roll *= multiplier;
        setValue(roll);
        return roll;
    }
}
