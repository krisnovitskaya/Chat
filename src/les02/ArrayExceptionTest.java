package les02;
/*
1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то элементе массива преобразование не удалось
(например, в ячейке лежит символ или текст вместо числа), должно быть брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.
3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException, и вывести результат расчета.
 */

public class ArrayExceptionTest {

    public static void main(String[] args) {
        String[][] array = initArray(4);
        randomDamageArray(array);

        try{
            arrayConvertAndAmount(array);
        }catch (MySizeArrayException e){
            System.out.println(e.getMessage());
            System.out.println("Двумерный строковый массив имеет размер [" + array.length +"][" + array.length + "].");
        }catch (MyArrayDataException e){
            System.out.println("В ячейке [" + e.getI() +"][" + e.getJ() + "] неверные данные");
        }
    }

    public static void arrayConvertAndAmount(String[][] arr) throws MyArrayDataException, MySizeArrayException{
        if(arr.length != 4){
            throw new MySizeArrayException("Размер массива не соответствует 4х4");
        }
        int sum = 0;
        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                }catch (NumberFormatException e){
                    throw new MyArrayDataException(i, j);
                }

            }
        }
        System.out.println("Сумма чисел в массиве равна " + sum);
    }

    /**
     * Автозаполнение двумерного массива String целыми числами
     * @param arraySize - max length of array
     * @return String[arraySize][arraySize] by random int +/-
     */
    public static String[][] initArray(int arraySize){
        String[][] arr = new String[arraySize][arraySize];
        for(int i = 0; i < arraySize; i++){
            for(int j = 0; j < arraySize; j++){

                int a = (int)(Math.random() * arraySize);
                if(Math.random() > 0.5){
                    a = -a;
                }
                String number = String.valueOf(a);
                arr[i][j] = number;
            }
        }
        return arr;
     }


     public static void randomDamageArray(String[][] arr){
         if(Math.random() > 0.5){
            int i = (int)(Math.random() * arr.length);
            int j = (int)(Math.random() * arr.length);
            arr[i][j] += "s";
         }
    }
}
