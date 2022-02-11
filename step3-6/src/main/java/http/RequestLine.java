package http;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private Map<String,String> paramMap = new HashMap<>();
    private String method;
    private String path;
    public RequestLine(BufferedReader br) throws IOException {
        String headerLine = br.readLine();
        String[] headerFirstLine = headerLine.split(" ");
        method = headerFirstLine[0];
        if(headerFirstLine[0].equals("GET")) {
            String[] pathParam = headerFirstLine[1].split("\\?");
            path = pathParam[0];
            if(pathParam.length == 2){
                paramMap.putAll(HttpRequestUtils.parseQueryString(pathParam[1]));
            }
        }else{
            path = headerFirstLine[1];
        }
    }
    public Map<String,String> getParamMap(){
        return paramMap;
    }
    public String getPath(){
        return path;
    }
    public String getMethod(){
        return method;
    }
}
