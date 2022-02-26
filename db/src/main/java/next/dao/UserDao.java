package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValue(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            };
        };
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pss);
    }

/*    String createQueryForInsert() {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        return sql;
    }

    void setValueForInsert(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
    }*/

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
/*        JdbcTemplate jdbcTemplate = new JdbcTemplate(){
            public void setValue(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

        };*/
//        jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?",user);
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValue(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            };
        };

//        jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?", pss);
        jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?", user.getPassword(),user.getName(),user.getEmail(),user.getUserId());
    }

/*    void setValueForUpdate(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
    }

    String createQueryForUpdate() {
        String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
        return sql;
    }*/

    public List findAll() throws SQLException {

        JdbcTemplate select = new JdbcTemplate(); /*{

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                List objects = new ArrayList<>();
                if(rs.next()){
                    objects.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email")));
                }
                return objects;
            }
        };*/
        RowMapper<List> rowMapper = new RowMapper<List>() {
            @Override
            public List mapRow(ResultSet rs) throws SQLException {
                List users = new ArrayList<>();
                if(rs.next()){
                    users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email")));
                }
                return users;
            }
        };
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValue(PreparedStatement pstmt) throws SQLException {
            }
        };

        return select.query("SELECT * FROM USERS",rowMapper,pss);
/*        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        con = ConnectionManager.getConnection();
        String sql = "SELECT * FROM USERS";
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        List<User> users = new ArrayList<>();
        if(rs.next()){
            users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email")));
        }
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (con != null) {
            con.close();
        }

        return users;*/
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate select = new JdbcTemplate();

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"),
                            rs.getString("name"), rs.getString("email"));
                }
                return user;
            }
        };
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValue(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

//        return select.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rowMapper,pss);
        return select.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",rowMapper,userId);

/*        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }*/
    }
}
