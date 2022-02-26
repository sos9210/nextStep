package next.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void insert() throws Exception{
        UserDao userDao = new UserDao();
        User expected = new User("userId", "password", "name", "javajigi@email.com");

        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }
    @Test
    public void update() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User("userId", "password", "name", "javajigi@email.com");
        userDao.insert(user);

        User expected = new User("userId", "password2", "name2", "sanjigi@email.com");
        userDao.update(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void delete() throws Exception{
        UserDao userDao = new UserDao();
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        userDao.insert(expected);

        userDao.delete(expected.getUserId());

        User actual = userDao.findByUserId(expected.getUserId());
        assertTrue(actual == null);
    }

    @Test
    public void findAll() throws Exception {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
    }
}