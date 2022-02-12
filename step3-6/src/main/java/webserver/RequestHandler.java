package webserver;

import java.io.*;
import java.net.Socket;

import controller.*;
import http.HttpResponse;
import http.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private Controller controller;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            log.debug("method ..... {}",request.getMethod());
            Controller controller = RequestMapping.getController(request.getPath());
            if(controller == null){
                String path = getOrDefault(request.getPath());
                response.forward(path);
            }
            controller.service(request,response);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private String getOrDefault(String path) {
        if(path.equals("/")){
            return "/index.html";
        }
        return path;
    }
}
