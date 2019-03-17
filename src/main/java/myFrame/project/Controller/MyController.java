package myFrame.project.Controller;

import myFrame.frame.annotaion.Autowired;
import myFrame.frame.annotaion.Controller;
import myFrame.frame.annotaion.RequestMapping;
import myFrame.frame.annotaion.ResponseBody;
import myFrame.project.models.Person;
import myFrame.project.models.Sex;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    private Person person;

    @Autowired
    private Sex sex;

    @ResponseBody
    @RequestMapping("person")
    public Person person() {
        return person;
    }

    @ResponseBody
    @RequestMapping("name")
    public Sex sex() {
        return sex;
    }

    @ResponseBody
    @RequestMapping("persons")
    public List<Person> persons() {
        Person anotherPerson = new Person("Bob", new Sex("Female"));
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        people.add(anotherPerson);
        return people;
    }
}