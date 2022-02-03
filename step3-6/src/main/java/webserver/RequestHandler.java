package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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

            Map<String,String> headers = HttpRequestUtils.getHeaders(br, line);    //http메시지 데이터를 map에 저장해서 가져온다.

            if(path.contains("/user/create")){
                String parameters = IOUtils.readData(br,Integer.parseInt(headers.get("Content-Length")));   //http 메시지 본문 데이터를 가져온다
                log.debug("parameters...  {}",parameters);
                HttpRequestUtils.requestParams(parameters);
                path = "/index.html";
            }
            byte[] body = Files.readAllBytes(Paths.get("./webapp"+path));

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error("header error ... {}",e.getMessage());
            e.printStackTrace();
            log.error("header error ... {}",e.getMessage());
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
