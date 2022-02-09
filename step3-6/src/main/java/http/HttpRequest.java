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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String,String> map = new ConcurrentHashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        exportHttpMessage(br);
    }

    private Map<String,String> exportHttpMessage(BufferedReader br) throws IOException {
        String[] firstHeaderLine = splitFirstLine(br);
        setMethod(firstHeaderLine);
        setHeader(br);
        return setHttpMessage(br, firstHeaderLine);
    }

    public String getMethod(){
        return map.get("method");
    }
    private void setMethod(String[] firstHeaderLine) {
        map.put("method", firstHeaderLine[0]);
    }
    public String getPath(){
        return map.get("path");
    }
    public String getHeader(String name){
        return map.get(name);
    }
    private void setHeader(BufferedReader br) throws IOException {
        String read = null;
        while(!"".equals(read)){
            read = br.readLine();
            log.debug("read .. {}", read);
            String[] splits = read.split(": ");
            if(splits.length == 2){         //empty line이 아닌경우만
                map.put(splits[0], splits[1]);
            }
        }
    }
    public String getParameter(String name){
        return map.get(name);
    }
    private void setGetParameter(String[] firstHeaderLine) {
        String[] pathParam = firstHeaderLine[1].split("\\?");
        map.put("path",pathParam[0]);
        if(pathParam.length == 2){
            map.putAll(HttpRequestUtils.parseQueryString(pathParam[1]));
        }
    }
    private void setPostParameter(BufferedReader br, String[] firstHeaderLine) throws IOException {
        String parameters = IOUtils.readData(br, Integer.parseInt(map.get("Content-Length")));
        map.putAll(HttpRequestUtils.parseQueryString(parameters));
        map.put("path", firstHeaderLine[1]);
    }

    private Map<String, String> setHttpMessage(BufferedReader br, String[] firstHeaderLine) throws IOException {
        if(firstHeaderLine[0].equals("GET")) {
            setGetParameter(firstHeaderLine);
        }else{
            setPostParameter(br, firstHeaderLine);
        }
        return map;
    }

    private String[] splitFirstLine(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        String[] path = firstLine.split(" ");
        return path;
    }

}
