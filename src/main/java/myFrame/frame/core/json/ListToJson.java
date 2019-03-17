package myFrame.frame.core.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class ListToJson implements JsonConvert {

    @Override
    public String toJson(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Class<?> aClass = obj.getClass();
        try {
            Method iteratorMethod = aClass.getMethod("iterator");
            Iterator iterator = (Iterator) iteratorMethod.invoke(obj);
            while (iterator.hasNext()) {
                sb.append(nextToStep(iterator.next()));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
