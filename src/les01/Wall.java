package les01;

public class Wall implements Hurdle {
    private final String name;
    private float height;

    //public Wall(String name) {        this.name = name;    }

    public Wall(String name, float height) {
        this.name = name;
        this.height = (height > 0.0f) ? height : 0.0f;
    }

    @Override
    public void tryIt(Team member) {
        if(member.isAbleToTry()){
            member.jump();
            if(member.getMaxJumpHeight() >= this.getHeight()){
                System.out.println(member + " преодолел " + this.toString() + " успешно.");
            } else {
                member.setAbleToTry(false);
                System.out.println(member + " не смог преодолеть " + this.toString() + " и сошел с дистанции.");
            }
        }

        /* старое до задания 4*
        member.jump();
        int a = (int)(Math.random() * 2); // 0 ил 1
        if(a == 0){
            System.out.println(member + " преодолел " + this.toString() + " успешно");
        }else {
            System.out.println(member + " не смог преодолеть " + this.toString());
        }

         */
    }

    @Override
    public String toString() {
        return this.name;
    }

    private float getHeight() {
        return height;
    }
}
