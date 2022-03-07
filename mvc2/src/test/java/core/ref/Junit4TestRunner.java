package core.ref;

import org.junit.Test;
import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getMethods();
        for (Method method:methods) {
            if(method.isAnnotationPresent(MyTest.class)){
                method.invoke(clazz.newInstance());
            }
        }
    }
}
