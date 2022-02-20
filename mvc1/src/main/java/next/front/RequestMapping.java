package next.front;

import next.controller.*;
import org.h2.command.dml.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private final Logger log = LoggerFactory.getLogger(getClass());
    Map<String, Controller> controllerMap = new HashMap<>();

    public void init(){
        controllerMap.put("/",new HomeController());
        controllerMap.put("/users/form",new FowardController("/user/form.jsp"));
        controllerMap.put("/users/loginForm",new FowardController("/user/login.jsp"));
        controllerMap.put("/users", new ListUserController());
        controllerMap.put("/users/login", new LoginController());
        controllerMap.put("/users/profile", new ProfileController());
        controllerMap.put("/users/logout", new LogoutController());
        controllerMap.put("/users/create",new CreateUserController());
        controllerMap.put("/users/updateForm", new UpdateFromUserController());
        controllerMap.put("/users/update", new UpdateUserController());

        log.debug("Initialized Request Mapping");
    }

    public Controller findController(String url){
        return controllerMap.get(url);
    }

    void put(String url, Controller controller){
        controllerMap.put(url,controller);
    }

    public Controller getController(String url){
        return controllerMap.get(url);
    }
}
