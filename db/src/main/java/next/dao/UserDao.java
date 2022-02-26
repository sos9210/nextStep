package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {

    public void insert(User user) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = pstmt -> {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
        };
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pss);
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss =  pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };

        jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?", pss);
//        jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?", user.getPassword(),user.getName(),user.getEmail(),user.getUserId());
    }

    public void delete(String userId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, userId);
        };
        jdbcTemplate.delete("DELETE FROM USERS WHERE USERID = ?", pss);
    }

    public List findAll() {
        JdbcTemplate select = new JdbcTemplate();

        RowMapper<List> rowMapper = rs ->  {
            List users = new ArrayList<>();
            if(rs.next()){
                users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email")));
            }
            return users;
        };
        PreparedStatementSetter pss =pstmt -> {};

        return select.query("SELECT * FROM USERS",rowMapper,pss);
    }

    public User findByUserId(String userId) {
        JdbcTemplate select = new JdbcTemplate();

            RowMapper<User> rowMapper = rs ->{
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"),
                            rs.getString("name"), rs.getString("email"));
                }
                return user;
            };
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, userId);
        };

        return select.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rowMapper,pss);
//        return select.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",rowMapper,userId);
    }
}
