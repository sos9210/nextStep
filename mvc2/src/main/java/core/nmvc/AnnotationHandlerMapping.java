package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.ControllerScanner;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.HandlerMapping;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    //패키지 경로를 인자로 받는 생성자
    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }
    //초기화 작업
    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);

        //@Controller 설정된 클래스로 매핑된 Map을 가져온다.
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        //controllers의 Key값을 인자로 @RequestMappingMethod로 설정된 메서드 정보들을 가져온다
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());

        for (Method method:methods) {
            //@RequestMapping 설정 정보
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            logger.debug("url : {}, httpMethod : {} ,method : {}",rm.value(), rm.method() ,method);

            //RequestMapping의 value(url정보)와 method(Httpmethod정보)를 담는 HandlerKey를 key값으로 사용
            handlerExecutions.put(createHandlerKey(rm),
                    //@Controller 설정된 클래스와 method를 인자를 생성자로 생성한 객체를 value값으로 사용
                    new HandlerExecution(controllers.get(method.getDeclaringClass()),method));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers){
        Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz:controllers){           //clazz: @Controller로 설정된 클래스
                                                    // clazz의 안에있는 @RequestMapping 설정된 메서드정보를담는다.
            requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz,
                    ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethods;

    }

    //handlerExecutions(RequestMapping 정보)에 사용할 key값을 반환한다
    private HandlerKey createHandlerKey(RequestMapping rm) {
                //@RequestMapping의 url정보와 HttpMethod정보를 인자로 생성한 객체를 반환한다.
        return new HandlerKey(rm.value(), rm.method());
    }

}
