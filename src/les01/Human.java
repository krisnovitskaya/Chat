package les01;

public class Human implements Participant {
    private final String name; //имя человека
    private float maxJumpHeight;
    private int maxRunDistance;
    private boolean ableToTry = true;


   // public Human(String name) {        this.name = name;    }

    public Human(String name, float maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = (maxJumpHeight > 0.0f) && (maxJumpHeight < 3.0f) ? maxJumpHeight : 2.0f;
        this.maxRunDistance = (maxRunDistance > 0) && (maxRunDistance < 1500) ? maxRunDistance : 700;
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
