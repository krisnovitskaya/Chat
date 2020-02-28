package les03.phonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Phonebook {
    private Map<Integer, String> data;

    public Phonebook() {
        this.data = new HashMap<Integer, String>();
    }

    void add(int phoneNumber, String surname ){
        this.data.put(phoneNumber, surname);
    }

    ArrayList<Integer> get(String surname){
        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : this.data.entrySet()) {
                if(surname.equals(entry.getValue())){
                    list.add(entry.getKey());
                }
        }

        System.out.printf("%s phone number are %s%n", surname, list);
        return list;
    }
}
