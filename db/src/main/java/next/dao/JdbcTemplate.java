package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate {
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();){
            pss.setValue(pstmt);
            List row = (List) rowMapper.mapRow(rs);
            return row;
        }catch (SQLException e){
            throw new DataAccessException();
        }
    }
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... objects) throws DataAccessException {
        List row = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();){
            PreparedStatementSetter pss = createPreparedStatementSetter(objects);
            pss.setValue(pstmt);
            row = (List) rowMapper.mapRow(rs);
        }catch (SQLException e){
            throw new DataAccessException();
        }
        return (List) row;
    }
    public <T> T queryForObject(String sql,RowMapper<T> rowMapper,PreparedStatementSetter pss) throws DataAccessException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ) {
            pss.setValue(pstmt);
            ResultSet rs = pstmt.executeQuery();
            T object = rowMapper.mapRow(rs);
            return object;
        }catch (SQLException e){
            throw new DataAccessException();
        }
    }
    public <T> T queryForObject(String sql,RowMapper<T> rowMapper,Object... objects) throws DataAccessException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ) {
            PreparedStatementSetter pss = createPreparedStatementSetter(objects);
            pss.setValue(pstmt);
            ResultSet rs = pstmt.executeQuery();
            T object = rowMapper.mapRow(rs);
            return object;
        }catch (SQLException e){
            throw new DataAccessException();
        }
    }
    public void update(String sql, Object... objects) throws SQLException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {
            PreparedStatementSetter pss = createPreparedStatementSetter(objects);
            pss.setValue(pstmt);
            pstmt.executeUpdate();
        }
    }
    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setValue(pstmt);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException();
        }
    }

    public void delete(String sql, PreparedStatementSetter pss) throws DataAccessException{
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setValue(pstmt);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException();
        }
    }

    public PreparedStatementSetter createPreparedStatementSetter(Object... objects){
        return pstmt -> {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i+1, objects[i]);
            }
        };
    }
}
