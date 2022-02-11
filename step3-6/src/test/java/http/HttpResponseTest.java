package http;

import http.HttpResponse;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {

    @Test
    public void responseForward() throws Exception {
        //index.html과 같은내용의 txt파일생성
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }
    @Test
    public void responseDirect() throws Exception {
        //302응답 Location: /index.html인 파일생성
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception{
        // 응답헤더에 Set-Cookie: logined=true인 파일생성
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie","logined=true");
        response.sendRedirect("/index.html");
    }
    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File("./src/test/resources/"+filename));
    }
}
