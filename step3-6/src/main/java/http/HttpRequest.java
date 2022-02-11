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
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String,String> headerMap = new HashMap<>();
    private Map<String,String> paramMap = new HashMap<>();
    private RequestLine requestLine;
    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        requestLine = new RequestLine(br);
        setHeader(br);
        if("POST".equals(getMethod())){
            String body = IOUtils.readData(br,Integer.parseInt(headerMap.get("Content-Length")));
            paramMap = HttpRequestUtils.parseQueryString(body);
        }else{
            paramMap = requestLine.getParamMap();
        }
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
