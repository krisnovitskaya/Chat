package les03;
/*
Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
Посчитать сколько раз встречается каждое слово.
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartOne {

    public static void main(String[] args) {
        String[] array = initStringArray();

        Set uniqueString = makeSet(array);
        System.out.println(uniqueString);

        Map<String, Integer> mapCountUniqueString = countUniqueString(array, uniqueString);
        System.out.println(mapCountUniqueString);
    }

    public static Map<String, Integer> countUniqueString(String[] arr, Set<String> stringSet){
        Map<String, Integer> map = new HashMap<>();

//        for (String s : stringSet) {
//            int count = 0;
//            for (int i = 0; i < arr.length ; i++) {
//                if(s.equals(arr[i])){
//                    count++;
//                }
//            }
//            map.put(s, count);
//        }
       // for (String word: arr) {
       //     map.merge(word, 1, Integer::sum);
       // }
        for (String word: arr){
            Integer count = map.getOrDefault(word, 0);
            map.put(word, count + 1);
        }



        return map;
    }


    public static Set<String> makeSet(String[] array){
        Set stringSet = new HashSet();
        for (String s : array) {
            stringSet.add(s);
        }
        return stringSet;
    }


    public static String[] initStringArray(){

        String[] arr = {
                "cat", "dog", "flower", "sun", "rain",
                "moon", "dog", "dog", "rain", "tree",
                "cat", "flower", "dog", "sun", "fly",
                "sun", "rain", "dog", "rain", "cat"
        };
        return arr;
    }

}
