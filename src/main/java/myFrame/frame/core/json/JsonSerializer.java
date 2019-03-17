package myFrame.frame.core.json;

public class JsonSerializer {

    public String toJson(Object obj) {
        return JsonConvertFactory.getJsonConvert(obj).toJson(obj);
    }
}
