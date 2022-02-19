package next.front;

import javassist.NotFoundException;
import next.controller.Controller;
import next.controller.CreateUserGetController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    Map<String, Controller> controllerMap = new HashMap<>();

    public void init(){
        controllerMap.put("/users/create",new CreateUserGetController());
        controllerMap.put("/users/form",new CreateUserGetController());
    }

    public Controller getController(String url){
        return controllerMap.get(url);
    }
}
