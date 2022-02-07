package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String,String> map = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        map = exportHttpMessage(br);
    }

    public String getMethod(){
        return map.get("method");
    }
    public String getPath(){
        return map.get("path");
    }
    public String getHeader(String name){
        return map.get(name);
    }
    public String getParameter(String name){
        return map.get(name);
    }

    private Map<String,String> exportHttpMessage(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        String[] path = firstLine.split(" ");
        map.put("method",path[0]);
        String read = null;
        while(!"".equals(read)){
            read = br.readLine();
            log.debug("read .. {}", read);
            String[] splits = read.split(": ");
            if(splits.length == 2){         //empty line이 아닌경우만
                map.put(splits[0], splits[1]);
            }
        }
        if(path[0].equals("GET")){
            String[] pathParam = path[1].split("\\?");
            map.put("path",pathParam[0]);
            if(pathParam.length == 2){
                map.putAll(HttpRequestUtils.parseQueryString(pathParam[1]));
            }
        }else{
            String parameters = IOUtils.readData(br, Integer.parseInt(map.get("Content-Length")));
            map.putAll(HttpRequestUtils.parseQueryString(parameters));
            map.put("path",path[1]);
        }
        return map;
    }

}
