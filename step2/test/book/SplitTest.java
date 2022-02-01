package book;

import org.junit.Assert;
import org.junit.Test;

public class SplitTest {

    @Test
    public void split(){
        String[] values = "1".split(",");
        Assert.assertArrayEquals(new String[] {"1"}, values);

        values = "1,2".split(",");
        Assert.assertArrayEquals(new String[] {"1","2"}, values);
    }
}
