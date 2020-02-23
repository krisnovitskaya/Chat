package les01;

public class Cat implements Participant {
    private final String name; //имя кота
    private float maxJumpHeight;
    private int maxRunDistance;
    private boolean ableToTry = true;



    //public Cat(String name) {        this.name = name;    }

    public Cat(String name, float maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = (maxJumpHeight > 0.0f) && (maxJumpHeight < 2.0f) ? maxJumpHeight : 1.0f;
        this.maxRunDistance = (maxRunDistance > 0) && (maxRunDistance < 1000) ? maxRunDistance : 500;
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
