package study;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {
    private StringCalculator cal;
    @Before
    public void setUp() {
        cal = new StringCalculator();
    }

    @Test
    public void add_null_또는_빈문자() {
        Assert.assertEquals(0,cal.sum(null));
        Assert.assertEquals(0,cal.sum(""));
    }
    @Test
    public void add_숫자하나() {
        Assert.assertEquals(1,cal.sum("1"));
    }
    @Test
    public void add_쉼표구분자() {
        Assert.assertEquals(3,cal.sum("1,2"));
    }
    @Test
    public void add_쉼표_또는_콜론_구분자() {
        Assert.assertEquals(6,cal.sum("1,2:3"));
    }
    @Test
    public void add_custom_구분자() {
        Assert.assertEquals(6,cal.sum("//;\n1;2;3"));
    }
    @Test(expected = RuntimeException.class)
    public void add_negative() {
        cal.sum("-1,2,3");
    }

/*    @Test
    public void sum() {
        Assert.assertEquals(6,cal.sum("1:2,3"));
        Assert.assertEquals(6,cal.sum("//;\n1;2;3"));
    }

    @Test
    public void subtract() {
        Assert.assertEquals(2,cal.subtract("5:1,2"));
        Assert.assertEquals(2,cal.subtract("//;\n5;1;2"));
    }

    @Test
    public void multiply() {
        Assert.assertEquals(24,cal.multiply("3:2,4"));
        Assert.assertEquals(24,cal.multiply("//;\n3;2;4"));
    }

    @Test
    public void divide() {
        Assert.assertEquals(1,cal.divide("12:4,3"));
        Assert.assertEquals(1,cal.divide("//;\n12;4;3"));
    }*/

}