package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;

import controller.*;
import http.HttpResponse;
import http.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import util.HttpRequestUtils;

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

            //요청 클라이언트의 쿠키에 세션ID가 NULL이면 새로 생성한다.
            if(request.getCookies().getCookie("JSESSIONID") == null){
                response.addHeader("Set-Cookie", "JSESSIONID="+ UUID.randomUUID());
            }

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
    private String getSessionId(String cookievaluye){
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookievaluye);
        return cookies.get("JSESSIONID");

    }
}
