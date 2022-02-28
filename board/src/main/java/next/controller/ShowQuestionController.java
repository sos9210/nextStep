package next.controller;

import core.mvc.Controller;
import next.dao.BoardDao;
import next.model.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardDao bd = new BoardDao();
        Board board = bd.select(Integer.parseInt(request.getParameter("questionId")));
        request.setAttribute("board",board);
        return "/qna/show.jsp";
    }
}
