package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    public int insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1,answer.getWriter());
                pstmt.setString(2,answer.getContent());
                pstmt.setInt(3,answer.getQuestionId());
            }
        };
        String sql = "INSERT INTO ANSWERS VALUES(NULL,?,?,SYSDATE,?)";
        return jdbcTemplate.insert(sql,pss);
    }

    public Answer findByAnswer(int answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<Answer> rowMapper = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                Answer answer = new Answer();
                answer.setAnswerId(rs.getInt("ANSWERID"));
                answer.setWriter(rs.getString("WRITER"));
                answer.setContent(rs.getString("CONTENTS"));
                answer.setCreateDate(rs.getDate("CREATEDDATE"));
                answer.setQuestionId(rs.getInt("QUESTIONID"));
                return answer;
            }
        };
        String sql = "SELECT * FROM ANSWERS WHERE ANSWERID = ?";
        return jdbcTemplate.queryForObject(sql,rowMapper,answerId);
    }
    public List<Answer> findAllAnswer(int questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<Answer> rowMapper = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                Answer answer = new Answer();
                answer.setAnswerId(rs.getInt("ANSWERID"));
                answer.setWriter(rs.getString("WRITER"));
                answer.setContent(rs.getString("CONTENTS"));
                answer.setCreateDate(rs.getDate("CREATEDDATE"));
                answer.setQuestionId(rs.getInt("QUESTIONID"));
                return answer;
            }
        };
        String sql = "SELECT * FROM ANSWERS WHERE QUESTIONID = ?";
        return jdbcTemplate.query(sql,rowMapper,questionId);
    }

    public int delete(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM ANSWERS WHERE ANSWERID = ? AND QUESTIONID = ?";
        return jdbcTemplate.update(sql,answer.getAnswerId(),answer.getQuestionId());
    }
}
