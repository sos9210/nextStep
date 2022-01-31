import study.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CalculatorTest {
    private Calculator cal;

    @Before
    public void setup(){
        cal = new Calculator();     // Calculator 인스턴스를 생성하는 중복코드를 제거할 수 있게 도와준다
        System.out.println("before");
    }

    @Test
    public void add(){
//        Calculator cal = new Calculator();
//        System.out.println(cal.add(6,3));     //main메서드 에서는 출력함으로써 직접 수동으로 확인해야한다
        Assert.assertEquals(9,cal.add(6,3)); //assertEquals(기대값,실제값) .. 성공
//        Assert.assertEquals(8,cal.add(6,3)); //assertEquals(기대값,실제값) .. 실패
        System.out.println("add");
    }

    @Test
    public void subtract(){
//        Calculator cal = new Calculator();
//        System.out.println(cal.subtract(6,3));
        System.out.println("subtract");
    }

    @After
    public void teardown(){
        System.out.println("teardown");
    }
}