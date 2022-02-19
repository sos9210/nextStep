package next.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserGetController implements Controller{
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserGetController.class);

    @Override
    public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/user/form.jsp";
    }
}
