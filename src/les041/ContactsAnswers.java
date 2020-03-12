package les041;

public class ContactsAnswers {


    private static String[] answers = {"Da", "Net", "povtori esche raz", "privet", "xD", "(("};



    public static String getAnswer(){
        String answer = answers[(int)(Math.random() * answers.length)];
        return answer;
    }


}
