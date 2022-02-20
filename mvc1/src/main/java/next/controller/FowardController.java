package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//단순히 페이지 이동만하는 URL일 경우 사용하는 컨트롤러
public class FowardController implements Controller{
    private String fowardUrl;
    public FowardController(String fowardUrl){
        this.fowardUrl = fowardUrl;
        if(fowardUrl == null){
            throw new NullPointerException("fowardUrl is null.이동할 URL을 입력하세요");
        }
    }
    @Override
    public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return fowardUrl;
    }
}
