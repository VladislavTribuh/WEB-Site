package by.bsuir.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Maksim Danilov on 17.12.2016.
 * WEB-site
 */
@SpringBootApplication
@ImportResource("config/dao.xml")
public class Example {

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }
}
