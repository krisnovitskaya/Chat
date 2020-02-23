package les01;

public interface Participant {
    void run();
    void jump();

    boolean isAbleToTry();
    void setAbleToTry(boolean ableToTry);

    float getMaxJumpHeight();
    int getMaxRunDistance();


}
