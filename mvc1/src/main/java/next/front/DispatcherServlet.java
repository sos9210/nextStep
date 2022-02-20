package next.front;

import next.controller.Controller;
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
    private final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private RequestMapping mapping;

    @Override
    public void init(){
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
            String viewName = controller.excute(request, response);

            move(request, response, viewName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void move(HttpServletRequest request, HttpServletResponse response, String viewName) throws IOException, ServletException {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher(viewName);
        rd.forward(request, response);
    }
}
