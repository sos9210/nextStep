package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        userDao.insert(user);
        String view = "redirect:/";
        return jspView(view);
    }
}
