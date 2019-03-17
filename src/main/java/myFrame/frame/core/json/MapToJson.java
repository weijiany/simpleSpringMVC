package myFrame.frame.core.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class MapToJson implements JsonConvert {
    @Override
    public String toJson(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Class<?> aClass = obj.getClass();
        try {
            Method keySetMethod = aClass.getMethod("keySet");
            Method getMethod = aClass.getMethod("get", Object.class);
            Set set = (Set) keySetMethod.invoke(obj);
            for (Object key : set) {
                sb.append(keyName(key.toString()));
                sb.append(":");

                sb.append(nextToStep(getMethod.invoke(obj, key)));
            }

            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
