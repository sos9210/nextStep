package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import controller.*;
import db.DataBase;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String,Controller> controllerMap = new HashMap<>();
    private Socket connection;
    private Controller controller;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    private void init() {
        controllerMap.put("/user/create", new CreateUserController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list.html", new ListUserController());
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            init();
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            log.debug("method ..... {}",request.getMethod());
            Controller controller = controllerMap.get(request.getPath());
            if(controller == null){
                response.forward(request.getPath());
            }
            controller.service(request,response);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
