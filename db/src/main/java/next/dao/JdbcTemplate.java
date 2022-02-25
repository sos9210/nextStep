package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate {
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws SQLException {
        List row = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();){
            pss.setValue(pstmt);
            row = (List) rowMapper.mapRow(rs);
        }
        return row;
    }
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... objects) throws SQLException {
        List row = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();){
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i+1,objects[i]);
            }
            row = (List) rowMapper.mapRow(rs);
        }
        return (List) row;
    }
    public <T> T queryForObject(String sql,RowMapper<T> rowMapper,PreparedStatementSetter pss) throws SQLException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();) {
            pss.setValue(pstmt);
            T object = rowMapper.mapRow(rs);
            return object;
        }
    }
    public <T> T queryForObject(String sql,RowMapper<T> rowMapper,Object... objects) throws SQLException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();) {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i+1,objects[i]);
            }
            T object = rowMapper.mapRow(rs);
            return object;
        }
    }
    public void update(String sql, Object... objects) throws SQLException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i+1,objects[i]);
            }
            pstmt.executeUpdate();
        }
    }
    public void update(String sql, PreparedStatementSetter pss) throws SQLException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setValue(pstmt);
            pstmt.executeUpdate();
        }
    }
    public PreparedStatementSetter createPreparedStatementSetter(Object... objects){
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValue(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < objects.length; i++) {
                    pstmt.setObject(i+1, objects[i]);
                }
            }
        };
        return pss;
    }
}
