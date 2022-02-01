package book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 실무에서 적용하기 어렵겠지만 학습 목적이므로 극단적인 단계까지 리팩토링하고 책임을 분할시켜본다.
public class StringCalculator {
    public int add(String text){
        if(isBlank(text)){
            return 0;
        }
        return sum(toInts(split(text)));
    }

    private boolean isBlank(String text) {          //"" 이거나 null 여부를 확인한다
        return text == null || text.isEmpty();
    }

    private String[] split(String text) {           //구분자로 split하여 문자배열로 반환한다.
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if(m.find()){
            String customDelimiter = m.group(1);    //(.) 1번그룹 , (.*) 2번그룹
            return m.group(2).split(customDelimiter);
        }
        return text.split(",|:");
    }

    private int[] toInts(String[] values){          //문자배열을 정수로 변환하여 반환한다.
        int[] numbers = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            numbers[i] = toPositive(values[i]);
        }
        return numbers;
    }

    private int toPositive(String value) {
        int number = Integer.parseInt(value);
        if(number < 0){
            throw new RuntimeException();
        }
        return number;
    }

    private int sum(int[] numbers) {                //정수배열을 더한다.
        int sum = 0;
        for (int number: numbers) {
            sum += number;
        }
        return sum;
    }
}
