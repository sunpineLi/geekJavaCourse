package geek.java.course.springbean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component("student100")
public class Student implements BeanNameAware {

    private int id;

    private String name;

    String beanName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("id=" + id + ", name=" + name);
    }

    @Override
    public void setBeanName(String s) {
        beanName = s;
    }
}
