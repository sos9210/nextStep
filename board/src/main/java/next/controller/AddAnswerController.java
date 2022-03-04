package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDAO;
import next.dao.BoardDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AddAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"),req.getParameter("content")
                                    ,Integer.parseInt(req.getParameter("questionId")));

        AnswerDAO answerDAO = new AnswerDAO();
        BoardDao bd = new BoardDao();
        int result = answerDAO.insert(answer);
        Answer findByAnswer = answerDAO.findByAnswer(result);
        bd.answerCntUpdt(findByAnswer.getQuestionId(),1);
        req.setAttribute("answer",findByAnswer);
        return jsonView().addObject("answer",findByAnswer);
    }
}
