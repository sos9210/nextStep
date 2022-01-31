import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringCalculator {
    private void minusException(int num){
        if(num < 0){
            throw new RuntimeException();
        }
    }
    private int[] separatorConvertor(String num){
        String regexp = "\\/{2}\\D\\\\n.[\\S]+$";
        if(num.matches(regexp)){
            int lt = num.lastIndexOf("/");
            int rt = num.indexOf("\\n");
            String separator = num.substring(lt+1,rt);
            return Arrays.stream(num.substring(rt+2).split(separator)).mapToInt(Integer::parseInt).peek(v -> minusException(v)).toArray();
        }else{
            return Stream.of(num.split(":|,")).mapToInt(Integer::parseInt).peek(v -> minusException(v)).toArray();
        }
    }
    public void add(String num){
        System.out.println(Arrays.stream(separatorConvertor(num)).sum());
    }
    public void subtract(String num){
        System.out.println(Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x - y).getAsInt());
    }
    public void multiply(String num){
        System.out.println(Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x * y).getAsInt());
    }
    public void divide(String num){
        System.out.println(Arrays.stream(separatorConvertor(num)).reduce((x, y) -> x / y).getAsInt());
    }

    public static void main(String[] args) {
        StringCalculator cal = new StringCalculator();
        cal.add("1:2,3");
        cal.add("//;\\n1;2;3");
        cal.subtract("1:2,3");
        cal.subtract("//;\\n1;2;3");
        cal.multiply("1:2,3");
        cal.multiply("//;\\n1;2;3");
        cal.divide("1:2,3");
        cal.divide("//;\\n1;2;3");
        cal.divide("1:2,-3");
        cal.divide("//;\\n1;2;-3");
    }
}
