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
        phonebook.add(12345, "Orlov");
        phonebook.add(14578, "Kotov");
        phonebook.add(14584, "Metlova");
        phonebook.add(14785, "Arbuzov");
        phonebook.add(14577, "Orlov");
        phonebook.add(36987, "Kotov");
        phonebook.add(68974, "Orlov");


        phonebook.get("Orlov");
        phonebook.get("Arbuzov");
    }
}
