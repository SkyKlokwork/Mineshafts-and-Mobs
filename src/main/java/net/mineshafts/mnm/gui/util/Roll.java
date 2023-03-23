package net.mineshafts.mnm.gui.util;

import net.minecraft.util.math.random.Random;

import java.util.Arrays;

public class Roll {
    private int total = 0;
    private int[] rolls;
    private final int numOfDice;
    private final int numOfSides;
    private final int dropLowestN;
    public Roll(int numOfDice, int numOfSides, int dropLowestN){
        this.numOfDice = numOfDice;
        this.numOfSides = numOfSides;
        this.dropLowestN = dropLowestN;
        if(dropLowestN<0)
            dropLowestN = 0;
        if(numOfDice-dropLowestN<=0)
            throw new RuntimeException("Must keep at least 1 die");
        this.roll();
    }
    public void roll(){
        rolls = new int[numOfDice];
        for (int i=0;i<rolls.length;i++)
            rolls[i]=Random.createLocal().nextInt(numOfSides)+1;
        Arrays.sort(rolls);
        for (int i=dropLowestN;i<rolls.length;i++)
            total += rolls[i];
    }

    public String getRolls(int padding) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(padding));
        for (int i=rolls.length-1;i>dropLowestN-1;i--)
            sb.append("§1").append(rolls[i]).append("§r, ");
        for (int i=dropLowestN-1;i>=0;i--)
            sb.append("§7").append(rolls[i]).append("§r, ");
        int end = sb.lastIndexOf("§r, ");
        sb.delete(end, sb.length());
        return sb.toString();
    }
    public int getTotal() {
        return total;
    }
}
