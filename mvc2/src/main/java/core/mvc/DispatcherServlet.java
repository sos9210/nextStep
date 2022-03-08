package core.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMapping = Lists.newArrayList();
    @Override
    public void init() throws ServletException {
        LegacyHandlerMapping lhm = new LegacyHandlerMapping();
        lhm.initMapping();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();

        handlerMapping.add(lhm);
        handlerMapping.add(ahm);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object controller = getHandler(req);
        if(controller == null){ //요청 URL을 처리할 수 있는 핸들러가 존재하지 않음.
            new IllegalArgumentException("404 NOT FOUND .. 찾을 수 없는 주소입니다.");
        }
        try {
            ModelAndView mav = excute(controller,req,resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView excute(Object controller, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(controller instanceof Controller){
            return ((Controller)controller).execute(request,response);  //기존 컨트롤러
        }else{
            return ((HandlerExecution)controller).handle(request,response); //새롭게 추가된 컨트롤러
        }
    }

    private Object getHandler(HttpServletRequest request) {
        //요청URL 처리가 가능한 핸들러를 조회하고 없으면 null반환
        for (HandlerMapping hm: handlerMapping) {
            if(hm.getHandler(request) != null){
                return hm.getHandler(request);
            }
        }
        return null;
    }
}
