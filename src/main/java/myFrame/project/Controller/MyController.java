package myFrame.project.Controller;

import myFrame.frame.annotaion.bean.Autowired;
import myFrame.frame.annotaion.web.Controller;
import myFrame.frame.annotaion.web.RequestMapping;
import myFrame.frame.annotaion.web.ResponseBody;
import myFrame.project.models.Person;
import myFrame.project.models.Sex;
import myFrame.project.service.MyService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    private MyService myService;

    @ResponseBody
    @RequestMapping("person")
    public Person person() {
        return myService.getPerson();
    }

    @ResponseBody
    @RequestMapping("name")
    public Sex sex() {
        return myService.getSex();
    }

    @ResponseBody
    @RequestMapping("persons")
    public List<Person> persons() {
        Person anotherPerson = new Person("Bob", new Sex("Female"));
        ArrayList<Person> people = new ArrayList<>();
        people.add(myService.getPerson());
        people.add(anotherPerson);
        return people;
    }
}