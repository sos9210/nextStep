package study;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//리팩토링 3가지 원칙
// 메소드가 한가지 책임만 구현하도록한다. o
// 인덴트 깊이를 1단계로 유지한다. o
// if문에서 else를 지양한다. o
public class StringCalculator {
    private void minusException(int num){  // 0보다 작은 값이 입력될 경우 예외발생시키는 책임을 가진다
        if(num < 0){
            throw new RuntimeException();
        }
    }
    private int[] separatorConvertor(String num){ //문자열을 정수형배열로 변환하는 책임을 가진다
        if(num.matches("\\/{2}\\D\\n.[\\S]+$")){
            int lt = num.lastIndexOf("/");
            int rt = num.indexOf("\n");
            String separator = num.substring(lt+1,rt);
            return Arrays.stream(num.substring(rt+1).split(separator)).mapToInt(Integer::parseInt).peek(v -> minusException(v)).toArray();
        }
        return Stream.of(num.split(":|,")).mapToInt(Integer::parseInt).peek(v -> minusException(v)).toArray();
    }
    public int sum(String num){     //덧셈 책임을 가진다
        int asInt = Arrays.stream(separatorConvertor(num)).sum();
        System.out.println(asInt);
        return asInt;
    }
    public int subtract(String num){ //뺄셈 책임을 가진다
        int asInt = Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x - y).getAsInt();
        System.out.println(asInt);
        return asInt;
    }
    public int multiply(String num){ //곱셈 책임을 가진다
        int asInt = Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x * y).getAsInt();
        System.out.println(asInt);
        return asInt;
    }
    public int divide(String num){  //나눗셈 책임을 가진다
        int asInt = Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x / y).getAsInt();
        System.out.println(asInt);
        return asInt;
    }
}
