package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Board;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
}
