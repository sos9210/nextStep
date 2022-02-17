package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User(request.getParameter("userId"),request.getParameter("password"),
                request.getParameter("name"),request.getParameter("email"));
        DataBase.addUser(user);
        response.sendRedirect("/user/list");
    }
}
