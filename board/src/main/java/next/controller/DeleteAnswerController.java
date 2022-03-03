package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDAO;
import next.dao.BoardDao;
import next.model.Answer;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Optional;

public class DeleteAnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer();
        setAnswer(req, answer);

        AnswerDAO ad = new AnswerDAO();
        int adCnt = ad.delete(answer);

        BoardDao bd = new BoardDao();
        bd.answerCntUpdt(answer.getQuestionId(), -1);

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        if(adCnt > 0){
            out.print(mapper.writeValueAsString(Result.ok()));
        }else{
            out.print(mapper.writeValueAsString(Result.fail("error message")));
        }
        return null;
    }

    private void setAnswer(HttpServletRequest req, Answer answer) {
        String answerId = Optional.ofNullable(req.getParameter("answerId")).orElse("0");
        String questionId = Optional.ofNullable(req.getParameter("questionId")).orElse("0");
        answer.setAnswerId(Integer.parseInt(answerId));
        answer.setQuestionId(Integer.parseInt(questionId));
    }
}
