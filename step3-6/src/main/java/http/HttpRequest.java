package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String,String> headerMap = new HashMap<>();
    private Map<String,String> paramMap = new HashMap<>();
    private RequestLine requestLine;
    private HttpMethod httpMethod;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        requestLine = new RequestLine(br);
        setHeader(br);
        httpMethod = HttpMethod.valueOf(getMethod());
        if(httpMethod.isPost()){
            String body = IOUtils.readData(br,Integer.parseInt(headerMap.get("Content-Length")));
            paramMap = HttpRequestUtils.parseQueryString(body);
        }else{
            paramMap = requestLine.getParamMap();
        }
    }
    //서버에 저장된 세션ID정보들중에 쿠키값으로 넘어온 세션ID와 일치하는 세션을 반환한다.
    public HttpSession getSessions(){
        return HttpSessions.getSession(getCookies().getCookie("JSESSIONID"));
    }

    // 클라이언트의 쿠키정보가 모두 담긴다.
    public HttpCookie getCookies(){
        return new HttpCookie(getHeader("Cookie"));
    }
    public String getCookie(String name){
        return HttpRequestUtils.parseCookies(getHeader("Cookie")).get(name);
    }
    public String getMethod(){
        return requestLine.getMethod();
    }
    public String getPath(){
        return requestLine.getPath();
    }
    public String getHeader(String name){
        return headerMap.get(name);
    }
    private void setHeader(BufferedReader br) throws IOException {
        String read = null;
        while(!"".equals(read)){
            read = br.readLine();
            log.debug("read .. {}", read);
            String[] splits = read.split(": ");
            if(splits.length == 2){         //empty line이 아닌경우만
                headerMap.put(splits[0], splits[1]);
            }
        }
    }
    public String getParameter(String name){
        return paramMap.get(name);
    }

}
