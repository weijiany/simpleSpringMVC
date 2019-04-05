package myFrame.project.service;

import myFrame.frame.annotaion.bean.Autowired;
import myFrame.frame.annotaion.bean.Service;
import myFrame.project.models.Person;
import myFrame.project.models.Sex;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private Person person;

    @Autowired
    private Sex sex;

    public Person getPerson() {
        return person;
    }

    public Sex getSex() {
        return sex;
    }
}
