package les01;

public class RunningTrack implements Hurdle {
    private final String name;
    private int distance;


    public RunningTrack(String name) {
        this.name = name;
    }

    public RunningTrack(String name, int distance) {
        this.name = name;
        this.distance = (distance > 0) ? distance : 0;
    }

    @Override
    public void tryIt(Participant member) {
        if(member.isAbleToTry()){
            member.run();
            if(member.getMaxRunDistance() >= this.getDistance()){
                System.out.println(member + " преодолел " + this.toString() + " успешно.");
            } else {
                member.setAbleToTry(false);
                System.out.println(member + " не смог преодолеть " + this.toString() + " и сошел с дистанции.");
            }
        }

        /* старое до задания со звездочкой
        member.run();
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

    private int getDistance() {
        return distance;
    }
}
