package next.controller;

import core.mvc.Controller;
import next.dao.BoardDao;
import next.model.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class CreateQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Board board = new Board();
        board.setWriter(request.getParameter("writer"));
        board.setTitle(request.getParameter("title"));
        board.setContents(request.getParameter("contents"));
        BoardDao bd = new BoardDao();
        int result = bd.insert(board);
        return "redirect:/qna/show?questionId=" + result;
    }
}
