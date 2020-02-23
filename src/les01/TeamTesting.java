package les01;
/*
1. Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса. Эти классы должны уметь бегать и прыгать (методы просто выводят информацию о действии в консоль).
2. Создайте два класса: беговая дорожка и стена, при прохождении через которые, участники должны выполнять соответствующие действия (бежать или прыгать),
 результат выполнения печатаем в консоль (успешно пробежал, не смог пробежать и т.д.).
3. Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор препятствий.
4.* У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и прыжки. Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.
 */
public class TeamTesting {
    public static void main(String[] args) {
        //Team member1 = new Robot("Amigo");
        //Hurdle hurdle1 = new Wall("Стена");
        //hurdle1.tryIt(member1);

        Participant[] dreamTeam = new Participant[6];             //создание массива икроков
        dreamTeam[0] = new Robot("Adios", 2.0f,200);
        dreamTeam[1] = new Cat("Murka", 3.0f, 700);
        dreamTeam[2] = new Cat("Murzik", 1.0f, 800);
        dreamTeam[3] = new Human("Alex", 1.0f, 1500);
        dreamTeam[4] = new Human("Kate", 2.0f, 1200);
        dreamTeam[5] = new Robot("Amigo", 0.5f, 500);

        Hurdle[] hurdles = new Hurdle[4];           //создание массива препятсвий
        hurdles[0] = new Wall("низкая стена", 1.5f);
        hurdles[1] = new RunningTrack("длинная дорога", 1000);
        hurdles[2] = new Wall("высокая стена", 2.5f);
        hurdles[3] = new RunningTrack("дорога", 500);

        //прохождение препятсвий
        for(int i = 0; i < hurdles.length; i++){
            for(int j = 0; j < dreamTeam.length; j++){
                hurdles[i].tryIt(dreamTeam[j]);
            }
        }
    }
}
