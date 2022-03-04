package next.controller;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.AnswerDAO;
import next.dao.BoardDao;
import next.model.Answer;
import next.model.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardDao bd = new BoardDao();
        AnswerDAO ad = new AnswerDAO();
        Board board = bd.select(Integer.parseInt(request.getParameter("questionId")));
        List<Answer> listAnswer = ad.findAllAnswer(board.getQuestionId());
        request.setAttribute("answers",listAnswer);
        request.setAttribute("board",board);
        return jspView("/qna/show.jsp");
    }
}
