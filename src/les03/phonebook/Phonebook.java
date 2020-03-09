package les03.phonebook;

import java.util.*;

public class Phonebook {
    private final Map<String, Set<Integer>> data = new HashMap<>();



    public void add(String surname, Integer phoneNumber){
        Set<Integer> number = getNumbers(surname);
        number.add(phoneNumber);
    }
    private Set<Integer> getNumbers(String surname){
        Set<Integer> numbers = data.getOrDefault(surname, new HashSet<>());
        if(!data.containsKey(surname)){
            data.put(surname, numbers);
        }
        return numbers;
    }

//    public ArrayList<Integer> get(String surname){
//        ArrayList<Integer> list = new ArrayList<>();
//        for (Map.Entry<Integer, String> entry : this.data.entrySet()) {
//                if(surname.equals(entry.getValue())){
//                    list.add(entry.getKey());
//                }
//        }
//        System.out.printf("%s phone number are %s%n", surname, list);
//        return list;
//    }

    public Set<Integer> get(String surname){

        Set<Integer> numbers = data.get(surname);
        System.out.printf("%s phone number are %s%n", surname, numbers);

        return numbers;

    }
}
