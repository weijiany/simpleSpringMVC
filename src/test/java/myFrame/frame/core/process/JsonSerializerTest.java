package myFrame.frame.core.process;

import myFrame.frame.core.json.JsonSerializer;
import myFrame.project.models.Person;
import myFrame.project.models.Sex;
import myFrame.project.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class JsonSerializerTest {

    private JsonSerializer jsonSerializer;
    private User user;
    private Person person;

    @Before
    public void before() {
        jsonSerializer = new JsonSerializer();
        user = new User("abc");
        person = Person.builder()
                .name("abc")
                .sex(Sex.builder().name("male").build())
                .build();
    }

    @Test
    public void arrayToJson() {
        String[] arr = new String[]{"a", "b"};

        String s = jsonSerializer.toJson(arr);
        String expected = "[\"a\",\"b\"]";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void simpleObjToJson() {
        String s = jsonSerializer.toJson(user);
        String expected = "{\"name\":\"abc\"}";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void complicatedObjToJson() {
        String s = jsonSerializer.toJson(person);
        String expected = "{\"name\":\"abc\",\"sex\":{\"name\":\"male\"}}";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void stringListToJson() {
        List<String> list = new ArrayList<>(2);
        list.add("a");
        list.add("b");
        String s = jsonSerializer.toJson(list);
        String expected = "[\"a\",\"b\"]";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void stringSetToJson() {
        Set<String> set = new HashSet<>(2);
        set.add("a");
        set.add("b");
        String s = jsonSerializer.toJson(set);
        String expected = "[\"a\",\"b\"]";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void simpleObjListToJson() {
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user);
        String s = jsonSerializer.toJson(list);
        String expected = "[{\"name\":\"abc\"},{\"name\":\"abc\"}]";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void complicatedListToJson() {
        List<Person> list = new ArrayList<>(2);
        list.add(person);
        list.add(person);
        String s = jsonSerializer.toJson(list);
        String expected = "[{\"name\":\"abc\",\"sex\":{\"name\":\"male\"}},{\"name\":\"abc\",\"sex\":{\"name\":\"male\"}}]";

        Assert.assertEquals(expected, s);
    }

    @Test
    public void mapToJson() {
        Map<String, Person> map = new HashMap<>();
        map.put("ppap", person);
        String s = jsonSerializer.toJson(map);
        String expected = "{\"ppap\":{\"name\":\"abc\",\"sex\":{\"name\":\"male\"}}}";

        Assert.assertEquals(expected, s
        );
    }
}