package nl.hu.cisq1.lingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LingoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(LingoApplication.class, args);
    }
}
