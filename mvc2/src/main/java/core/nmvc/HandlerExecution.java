package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Object declaredObject;
    private Method method;
                       //@Controller 설정된 클래스와 그 클래스 안에있는 메서드정보를 인자로 생성
    public HandlerExecution(Object declaredObject, Method method){
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response){
        try {
            //요청에 맞는 메서드를 호출한다.
            return (ModelAndView) method.invoke(declaredObject,request,response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.debug("{} invoke method error.. message {}",method,e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
