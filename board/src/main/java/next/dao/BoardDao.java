package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Board;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    public int insert(Board board) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, board.getWriter());
                pstmt.setString(2, board.getTitle());
                pstmt.setString(3, board.getContents());
                pstmt.setInt(4, 0);
            }
        };
        String sql = "INSERT INTO QUESTIONS VALUES(NULL,?,?,?,SYSDATE,?)";
        return jdbcTemplate.insert(sql, pss);
    }

    public Board select(int questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<Board> rm = new RowMapper<Board>() {
            @Override
            public Board mapRow(ResultSet rs) throws SQLException {
                return new Board(
                        rs.getInt("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getDate("createdDate"),
                        rs.getInt("countOfAnswer")
                );
            }
        };

        String sql = "SELECT * FROM QUESTIONS WHERE QUESTIONID = ?";
        return jdbcTemplate.queryForObject(sql,rm,questionId);
    }

    public List<Board> list(Board board){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<Board> rowMapper = new RowMapper<Board>() {
            @Override
            public Board mapRow(ResultSet rs) throws SQLException {
                return new Board(
                        rs.getInt("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getDate("createdDate"),
                        rs.getInt("countOfAnswer")
                );
            }
        };
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setInt(1,(board.getPaging().getCurrentPage()-1) * board.getPaging().getPageSize()+1);
                pstmt.setInt(2,board.getPaging().getCurrentPage() * board.getPaging().getPageSize());
//                pstmt.setInt(3,board.getPaging().getEndPage());
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM( SELECT ROWNUM RNUM,* FROM (" +
                "SELECT * FROM QUESTIONS ORDER BY QUESTIONID DESC" +
                "))WHERE RNUM BETWEEN ? AND ?");
        System.out.println("BOARD :: "+sb.toString());
        System.out.println("param1 :: "+((board.getPaging().getCurrentPage()-1) * board.getPaging().getPageSize()));
        System.out.println("param2 :: "+(board.getPaging().getCurrentPage() * board.getPaging().getPageSize()));
        return jdbcTemplate.query(sb.toString(),rowMapper,pss);
    }

    public int boardTotalCnt() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<Integer> rm = new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs) throws SQLException {
                return rs.getInt("CNT");
            }
        };
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                return;
            }
        };
        return jdbcTemplate.queryForObject("SELECT COUNT(QUESTIONID) CNT FROM QUESTIONS",rm,pss);
    }

    public int answerCntAdd(int questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET COUNTOFANSWER = " +
                "(SELECT COUNTOFANSWER+1 FROM QUESTIONS WHERE QUESTIONID = ?) " +
                "WHERE QUESTIONID = ?";
        return jdbcTemplate.update(sql,questionId,questionId);
    }
}
