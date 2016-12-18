import by.bsuir.cs.dao.DataAccessObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Maksim Danilov on 17.12.2016.
 * WEB-site
 */
public class Example {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"config/dao.xml"});
        DataAccessObject dataAccessObject = (DataAccessObject) context.getBean("dataAccessObject");
    }
}
