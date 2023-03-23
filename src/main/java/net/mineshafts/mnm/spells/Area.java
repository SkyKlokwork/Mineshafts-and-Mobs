package net.mineshafts.mnm.spells;

public abstract class Area {
    protected float[] dimensions;

    public float[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(float[] dimensions) {
        this.dimensions = dimensions;
    }

    public Area(float...dimensions){
        this.dimensions = dimensions;
    }
}
