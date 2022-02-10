package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class LoginController extends AbstractController{

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User loginUser = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"),request.getParameter("email"));
        User findUser = DataBase.findUserById(loginUser.getUserId());
        if (findUser != null && loginUser.getPassword().equals(findUser.getPassword())) {
            response.addHeader("Set-Cookie","logined=true");
            response.sendRedirect("/index.html");
            log.debug("success login");
        } else {
            response.addHeader("Set-Cookie","logined=false");
            response.sendRedirect("/index.html");
            log.debug("fail login");
        }
    }
}
