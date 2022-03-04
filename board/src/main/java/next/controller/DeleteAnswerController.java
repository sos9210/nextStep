package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDAO;
import next.dao.BoardDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Optional;

public class DeleteAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer();
        setAnswer(req, answer);

        AnswerDAO ad = new AnswerDAO();
        int adCnt = ad.delete(answer);

        BoardDao bd = new BoardDao();
        bd.answerCntUpdt(answer.getQuestionId(), -1);
        if(adCnt > 0){
            return jsonView().addObject("status",true).addObject("message","");
        }
        return jsonView().addObject("status",false).addObject("message","error message");
    }

    private void setAnswer(HttpServletRequest req, Answer answer) {
        String answerId = Optional.ofNullable(req.getParameter("answerId")).orElse("0");
        String questionId = Optional.ofNullable(req.getParameter("questionId")).orElse("0");
        answer.setAnswerId(Integer.parseInt(answerId));
        answer.setQuestionId(Integer.parseInt(questionId));
    }
}
