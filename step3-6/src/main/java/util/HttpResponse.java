package util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    DataOutputStream dos;
    private Map<String,String> headerMap = new HashMap<>();

    public HttpResponse(OutputStream out){
        dos = new DataOutputStream(out);
    }

    public void addHeader(String key,String val){
        headerMap.put(key,val);
    }

    public void forward(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("./webapp"+path));
        addHeader("Content-Length",String.valueOf(bytes.length));
        if(path.contains(".css")){
            addHeader("Content-Type","text/css");
        }else if(path.contains(".js")){
            addHeader("Content-Type","application/javascript");
        }else{
            addHeader("Content-Type","text/html");
        }
        response200Header(dos);
        responseBody(dos,bytes);
    }

    public void forwardBody(String body) {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        addHeader("Content-Type","text/html;charset=utf-8");
        addHeader("Content-Length: ",String.valueOf(bytes.length));
        response200Header(dos);
        responseBody(dos,bytes);
    }

    public void sendRedirect(String redirect){
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: "+ redirect + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: "+headerMap.get("Content-Type")+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + Integer.parseInt(headerMap.get("Content-Length")) + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
