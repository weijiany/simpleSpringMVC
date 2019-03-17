package myFrame.frame.core.json;

import java.lang.reflect.Field;

public class ObjectToJson implements JsonConvert {

    @Override
    public String toJson(Object obj) {
        StringBuilder sb = new StringBuilder("{");

        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            sb.append(keyName(field.getName()));
            sb.append(":");

            field.setAccessible(true);
            try {
                sb.append(nextToStep(field.get(obj)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
