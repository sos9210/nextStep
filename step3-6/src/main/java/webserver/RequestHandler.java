package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();

            String path = line.split(" ")[1];
            log.debug("path ... {}",path);
            if(line == null){
                return;
            }

            Map<String,String> httpMessage = HttpRequestUtils.getHttpMessage(br, line);    //http메시지 데이터를 map에 저장해서 가져온다.

            DataOutputStream dos = new DataOutputStream(out);
            byte[] bytes;
            if(path.equals("/user/create")) {
                String parameters = IOUtils.readData(br, Integer.parseInt(httpMessage.get("Content-Length")));   //http 메시지 본문 데이터를 가져온다
                log.debug("parameters...  {}", parameters);
                User user = HttpRequestUtils.requestParams(parameters);
                DataBase.addUser(user);
                response302Header(dos);
            }else if(path.equals("/user/login")){
                String parameters = IOUtils.readData(br, Integer.parseInt(httpMessage.get("Content-Length")));
                User loginUser = HttpRequestUtils.requestParams(parameters);
                User findUser = DataBase.findUserById(loginUser.getUserId());
                if(findUser != null && loginUser.getPassword().equals(findUser.getPassword())){
                    responseLoginHeader(dos,true);
                    log.debug("success login");
                }else{
                    responseLoginHeader(dos,false);
                    log.debug("fail login");
                }
                bytes = Files.readAllBytes(Paths.get("./webapp/index.html"));
                responseBody(dos, bytes);
            }else{
                bytes = Files.readAllBytes(Paths.get("./webapp"+path));
                response200Header(dos, bytes.length);
                responseBody(dos, bytes);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private void responseLoginHeader(DataOutputStream dos, boolean loginYn) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: /index.html" + "\r\n");
            dos.writeBytes("Set-Cookie: logined="+loginYn+"\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error("header200 error ... {}",e.getMessage());
            e.printStackTrace();
            log.error("header200 error ... {}",e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error("header200 error ... {}",e.getMessage());
            e.printStackTrace();
            log.error("header200 error ... {}",e.getMessage());
        }
    }
    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: /index.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error("header302 error ... {}",e.getMessage());
            e.printStackTrace();
            log.error("header302 error ... {}",e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error("body error ... {}",e.getMessage());
            e.printStackTrace();
            log.error("body error ... {}",e.getMessage());
        }
    }
}
