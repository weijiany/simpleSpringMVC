package myFrame.frame.core.json;

import java.lang.reflect.Array;

public class ArrayToJson implements JsonConvert {

    @Override
    public String toJson(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int len = Array.getLength(obj);
        for (int i = 0; i < len; i ++) {
            sb.append(nextToStep(Array.get(obj, i)));
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
