package net.mineshafts.mnm.spells;

public class Line extends Area{
    public Line(float width, float length){
        super(width, length);
    }

    public float getWidth(){
        return dimensions[0];
    }
    public float getLength(){
        return dimensions[1];
    }
}
