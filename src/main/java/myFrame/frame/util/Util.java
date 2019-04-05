package myFrame.frame.util;

public class Util {

    public static String firstCharToLow(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
