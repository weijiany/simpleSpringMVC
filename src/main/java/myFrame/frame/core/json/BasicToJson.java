package myFrame.frame.core.json;

public class BasicToJson implements JsonConvert {

    @Override
    public String toJson(Object obj) {
        return "\"" +
                obj.toString() +
                "\"";
    }
}
