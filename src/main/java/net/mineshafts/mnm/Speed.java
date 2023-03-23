package net.mineshafts.mnm;

public class Speed {
    public float walkingSpeed;
    public float flyingSpeed;
    public float swimmingSpeed;
    public float climbingSpeed;
    public Speed(float walkingSpeed){
        new Speed(walkingSpeed, 0, 0, 0);
    }
    public Speed(float walkingSpeed, float swimmingSpeed){
        new Speed(walkingSpeed, swimmingSpeed, 0, 0);
    }
    public Speed(float walkingSpeed, float swimmingSpeed, float climbingSpeed){
        new Speed(walkingSpeed,swimmingSpeed,climbingSpeed,0);
    }
    public Speed(float walkingSpeed, float swimmingSpeed, float climbingSpeed, float flyingSpeed){
        this.walkingSpeed = walkingSpeed;
        this.swimmingSpeed = swimmingSpeed;
        this.climbingSpeed = climbingSpeed;
        this.flyingSpeed = flyingSpeed;
    }
}
