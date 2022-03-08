package core.annotation;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
//@Controller가 설정된 클래스를 스캔한다.
public class ControllerScanner {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Reflections reflections;
    //패키지 경로를 인자로 받는 생성자
    public ControllerScanner(Object... basePackage){
        reflections = new Reflections(basePackage);
    }

    //@Controller 설정된 클래스로 매핑된 Map을 반환한다.
    public Map<Class<?>,Object> getControllers()  {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(annotated);
    }

    //@Controller 설정된 클래스를 매핑한다.
    public Map<Class<?>,Object> instantiateControllers(Set<Class<?>> controllers) {
        Map<Class<?>,Object> controllerMap = new HashMap<>();
        for (Class clazz:controllers) {
            try {
                controllerMap.put(clazz,clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.getMessage();
            }
        }
        return controllerMap;
    }

}
