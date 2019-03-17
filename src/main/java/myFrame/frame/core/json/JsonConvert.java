package myFrame.frame.core.json;

@FunctionalInterface
public interface JsonConvert {

    String toJson(Object obj);

    default String nextToStep(Object obj) {
        return JsonConvertFactory.getJsonConvert(obj).toJson(obj) + ",";
    }

    default String keyName(String name) {
        return "\"" +
                name +
                "\"";
    }
}
