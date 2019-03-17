package myFrame.frame.core.json;

import java.util.List;
import java.util.Map;
import java.util.Set;

final class JsonConvertFactory {

    static JsonConvert getJsonConvert(Object obj) {
        if (isArray(obj))
            return new ArrayToJson();
        else if (isListType(obj))
            return new ListToJson();
        else if (isBasicType(obj))
            return new BasicToJson();
        else if (isMapType(obj))
            return new MapToJson();
        else
            return new ObjectToJson();
    }

    private static boolean isListType(Object obj) {
        return obj instanceof List ||
                obj instanceof Set;
    }

    private static boolean isMapType(Object obj) {
        return obj instanceof Map;
    }

    private static boolean isBasicType(Object obj) {
        return obj instanceof Byte ||
                obj instanceof Short ||
                obj instanceof Integer ||
                obj instanceof Long ||
                obj instanceof Float ||
                obj instanceof Double ||
                obj instanceof Character ||
                obj instanceof String ||
                obj instanceof Boolean;
    }

    private static boolean isArray(Object obj) {
        return obj.getClass().getComponentType() != null;
    }
}
