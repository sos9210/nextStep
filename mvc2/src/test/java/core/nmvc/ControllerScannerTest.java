package core.nmvc;

import core.annotation.ControllerScanner;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerScannerTest {
    private final Logger logger =  LoggerFactory.getLogger(getClass());

    private ControllerScanner cf;
    @Before
    public void setup() {
        cf = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception{
        Map<Class<?>, Object> controllers = cf.getControllers();
        for(Class<?> controller : controllers.keySet()){
            logger.debug("controller : {}", controller);
        }

    }
}
