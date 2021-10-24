package geek.java.course.springbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean("student102")
    public Student getStudent() {
        return new Student();
    }
}
