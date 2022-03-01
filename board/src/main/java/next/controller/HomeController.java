package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.BoardDao;
import next.dao.UserDao;
import next.model.Board;
import next.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class HomeController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        BoardDao boardDao = new BoardDao();
        req.setAttribute("users", userDao.findAll());
        int startPage = Integer.parseInt(Optional.ofNullable(req.getParameter("startPage")).orElse("0"));
        int endPage = Integer.parseInt(Optional.ofNullable(req.getParameter("endPage")).orElse("5"));
        int currentPage = Integer.parseInt(Optional.ofNullable(req.getParameter("currentPage")).orElse("1"));

        Board board = new Board();
        Paging paging = new Paging(startPage,endPage,currentPage);
        int total = boardDao.boardTotalCnt();

        paging.setTotalPost(total);
        paging.setTotalPage(( (total - 1) / paging.getPageSize()) + 1);
        paging.setStartPage((currentPage-1)/paging.getPageSize() * paging.getPageSize() + 1);
        paging.setEndPage(((currentPage-1)/paging.getPageSize()+1) * paging.getPageSize());
        if(((paging.getTotalPost() - 1) / paging.getPageNum()) + 1 < paging.getEndPage()){
            paging.setEndPage(((paging.getTotalPost() - 1) / paging.getPageNum()) + 1);
        }
        logger.debug("startPage  :  {} ",paging.getStartPage());
        logger.debug("endPage  :  {} ",paging.getEndPage());

        board.setPaging(paging);
        List<Board> list = boardDao.list(board);
        logger.debug("paging :: {}", paging);
        req.setAttribute("board", list);
        req.setAttribute("paging", paging);
        return "home.jsp";
    }
}
