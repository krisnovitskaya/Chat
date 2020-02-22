package les01;

public interface Team {
    void run();
    void jump();

    boolean isAbleToTry();
    void setAbleToTry(boolean ableToTry);

    float getMaxJumpHeight();
    int getMaxRunDistance();


}
