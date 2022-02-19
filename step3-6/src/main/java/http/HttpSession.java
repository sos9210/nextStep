package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    Map<String,Object> map = new HashMap<>();
    private String id;
    public HttpSession (String id){
        this.id = id;
    }
    //세션ID 반환
    public String getId(){
        return id;
    }
    //세션정보 입력
    public void setAttribute(String name, Object value){
        map.put(name, value);
    }
    //세션정보 반환
    public Object getAttribute(String name) {
        return map.get(name);
    }
    //세션정보 삭제
    public void removeAttribute(String name){
        map.remove(name);
    }
    //모든세션정보 무효화
    public void invalidate(){
        HttpSessions.remove(id);
    }
}
