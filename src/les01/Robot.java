package les01;

public class Robot implements Team {

    private final String name; //имя робота
    private float maxJumpHeight;
    private int maxRunDistance;
    private boolean ableToTry = true;



    //public Robot(String name) {        this.name = name;    }

    public Robot(String name, float maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = (maxJumpHeight > 0.0f) && (maxJumpHeight < 1.0f) ? maxJumpHeight : 0.5f;
        this.maxRunDistance = (maxRunDistance > 0) && (maxRunDistance < 2000) ? maxRunDistance : 1000;
    }

    @Override
    public void jump() {
        System.out.println(this.toString() + " пытается прыгнуть");
    }

    @Override
    public void run() {
        System.out.println(this.toString() + " начинает бежать");
    }

    @Override
    public String toString() {
        return this.name;
    }

    public float getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public int getMaxRunDistance() {
        return maxRunDistance;
    }

    public boolean isAbleToTry() {
        return ableToTry;
    }

    public void setAbleToTry(boolean ableToTry) {
        this.ableToTry = ableToTry;
    }
}
