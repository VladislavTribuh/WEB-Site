import by.bsuir.cs.dao.DataAccessObject;
import by.bsuir.cs.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * Created by Maksim Danilov on 17.12.2016.
 * WEB-site
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:config/test_dao.xml")
public class DataAccessTest {

    @Autowired
    private DataAccessObject dataAccessObject;

    private final String LOGIN_FORMAT = "login%1$d";
    private final String EMAIL_FORMAT = "mail%1$d";
    private final String PASSWORD = "123456";

//    @Test
    public void testSignUp() {
        int numOfRows = 100;
        for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
            dataAccessObject.signUp(
                    String.format(LOGIN_FORMAT, rowNum), PASSWORD, String.format(EMAIL_FORMAT, rowNum));
        }
    }

    @Test
    public void testSingIn() {
        int numOfRows = 100;
        for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
            User user = dataAccessObject.signIn(
                    String.format(LOGIN_FORMAT, rowNum), PASSWORD);
            System.out.println(user.toString());
        }
    }
}
