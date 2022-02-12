package http;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RequestLineTest {

    @Test
    public void get_method() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./src/test/resources/RequestLine_GET.txt"));
        RequestLine line = new RequestLine(br);
        HttpMethod httpMethod = HttpMethod.valueOf(line.getMethod());

        Assert.assertTrue(httpMethod.isGet());
        Assert.assertEquals("/index.html",line.getPath());
    }
    @Test
    public void get_params_method() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./src/test/resources/Http_GET.txt"));
        RequestLine line = new RequestLine(br);
        Map<String,String> map = line.getParamMap();
        HttpMethod httpMethod = HttpMethod.valueOf(line.getMethod());

        Assert.assertTrue(httpMethod.isGet());
        Assert.assertEquals("/user/create",line.getPath());
        Assert.assertEquals("asdf1234",map.get("userId"));
        Assert.assertEquals("1234",map.get("password"));
        Assert.assertEquals("sungs",map.get("name"));
    }

    @Test
    public void post_params_method() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./src/test/resources/Http_POST.txt"));
        RequestLine line = new RequestLine(br);
        Map<String,String> map = line.getParamMap();
        Assert.assertEquals("POST",line.getMethod());
        Assert.assertEquals("/user/create",line.getPath());
    }
}
