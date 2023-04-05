package net.mineshafts.mnm.spells;

public class Cone extends Area{
    public Cone(float diameter){
        super(diameter);
    }

    public float getDiameter(){
        return dimensions[0];
    }
    public float getLength(){
        return dimensions[0];
    }
}
