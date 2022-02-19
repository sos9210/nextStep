package http;

import util.HttpRequestUtils;

import java.util.Map;

public class HttpCookie {
    //          쿠키name 쿠키value
    private Map<String,String> cookies;
    //쿠키정보들을 생성자의 인자로 받는다
    HttpCookie(String cookieValue){
        cookies = HttpRequestUtils.parseCookies(cookieValue);
    }

    public String getCookie(String name){
        return cookies.get(name);
    }
}
