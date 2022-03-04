package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.UserDao;
import next.model.User;

public class ProfileController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return jspView("/user/profile.jsp");
    }
}
