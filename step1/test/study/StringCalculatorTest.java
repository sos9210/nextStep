package study;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import study.StringCalculator;

public class StringCalculatorTest {
    private StringCalculator cal;
    @Before
    public void setUp() {
        cal = new StringCalculator();
    }

    @Test
    public void add() {
        Assert.assertEquals(6,cal.add("1:2,3"));
        Assert.assertEquals(6,cal.add("//;\\n1;2;3"));
    }

    @Test
    public void subtract() {
        Assert.assertEquals(2,cal.subtract("5:1,2"));
        Assert.assertEquals(2,cal.subtract("//;\\n5;1;2"));
    }

    @Test
    public void multiply() {
        Assert.assertEquals(24,cal.multiply("3:2,4"));
        Assert.assertEquals(24,cal.multiply("//;\\n3;2;4"));
    }

    @Test
    public void divide() {
        Assert.assertEquals(1,cal.divide("12:4,3"));
        Assert.assertEquals(1,cal.divide("//;\\n12;4;3"));
    }
}