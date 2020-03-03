package les03.phonebook;
/*
2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать
номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
тогда при запросе такой фамилии должны выводиться все телефоны.
 */
public class PhonebookTesting {
    public static void main(String[] args) {

        Phonebook phonebook = new Phonebook();
        phonebook.add("Orlov", 15425);


        phonebook.add("Orlov", 12345);
        phonebook.add("Kotov", 14578);
        phonebook.add("Metlova", 14578);
        phonebook.add( "Arbuzov", 14578);
        phonebook.add("Orlov",14578);
        phonebook.add( "Kotov", 36987);
        phonebook.add( "Orlov",68974);


        phonebook.get("Orlov");
        phonebook.get("Arbuzov");
    }
}
