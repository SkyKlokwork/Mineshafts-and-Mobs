package net.mineshafts.mnm.playerdata;

public class SpellList {
    private int[] learnedSpells = new int[27];
    private int currentSpell = 0 ;

    public SpellList() {
        this.learnedSpells = learnedSpells;
    }



    public void nextSpell() {
        if(currentSpell >= learnedSpells.length){

        }
        currentSpell++;
    }
}
