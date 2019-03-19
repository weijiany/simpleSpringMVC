package myFrame.project.config;

import myFrame.frame.annotaion.bean.Bean;
import myFrame.frame.annotaion.bean.Configuration;
import myFrame.project.models.Person;
import myFrame.project.models.Sex;

@Configuration
public class Beans {

    @Bean
    public Person person() {
        return new Person("Lucy", sex());
    }

    @Bean
    public Sex sex() {
        return new Sex("male");
    }
}
