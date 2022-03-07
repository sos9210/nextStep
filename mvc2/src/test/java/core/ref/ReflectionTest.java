package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        Field[] fields = clazz.getFields();
        for (Object obj:fields) {
            logger.debug("field {}", obj.toString());
        }
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Object obj:constructors) {
            logger.debug("constructor {}", obj.toString());
        }
        Method[] methods = clazz.getMethods();
        for (Object obj:methods) {
            logger.debug("method {}",obj.toString());
        }
        logger.debug(clazz.getName());
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws Exception {
        Class<User> clazz = User.class;
        Constructor<?>[] userConstructor = clazz.getDeclaredConstructors();
        for (Constructor constructor:userConstructor) {
            User o = (User) constructor.newInstance("id", "1234", "name", "email@emial.com");
            logger.debug("user :: {}",o.toString());
        }
        logger.debug(clazz.getName());

    }
    
    @Test
    public void privateFieldAccess() throws Exception  {
        Class<Student> clazz = Student.class;
        Student student = new Student();
        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");
        name.setAccessible(true);
        name.set(student,"길동");
        age.setAccessible(true);
        age.set(student,19);

        logger.debug("name : {}, age : {}",student.getName(),student.getAge());
        logger.debug(clazz.getName());
    }
}
