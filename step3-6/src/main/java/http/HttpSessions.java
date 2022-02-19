package http;

import java.util.HashMap;
import java.util.Map;

//서버에서 모든 클라이언트들의 세션정보를 담는다
public class HttpSessions {
    private static Map<String,HttpSession> sessions = new HashMap<>();
    public static HttpSession getSession(String id){    //
        HttpSession session = sessions.get(id);
        if(session == null){
            session = new HttpSession(id);
            sessions.put(id,session);
        }
        return session;
    }

    public static void remove(String id){
        sessions.remove(id);
    }
}
