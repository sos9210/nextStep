package next.front;

import javassist.NotFoundException;
import next.controller.Controller;
import next.controller.CreateUserGetController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private RequestMapping mapping;

    public DispatcherServlet(){
        mapping = new RequestMapping();
        mapping.init();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Controller controller = mapping.getController(request.getRequestURI());
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            String view = controller.excute(request, response);

            if(view.startsWith("redirect:")){
                response.sendRedirect(view);
            }else{
                RequestDispatcher rd = request.getRequestDispatcher(view);
                rd.forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
