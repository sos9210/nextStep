package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update.jsp")
public class UpdateUserFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User findUser = DataBase.findUserById(request.getParameter("userId"));
        request.setAttribute("user",findUser);
        RequestDispatcher rd = request.getRequestDispatcher("/user/update.jsp");
        rd.forward(request,response);
    }
}
