package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Bean;
import myFrame.frame.annotaion.bean.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MapBean extends AbstractBeanFactory {

    public MapBean(String fileName) {
        super(fileName);
    }

    @Override
    public Map<String, String> getBeanMapClass(String fileName) {
        try {
            Class aClass = Class.forName(fileName);
            if (!aClass.isAnnotationPresent(Configuration.class)) return null;

            Method[] methods = aClass.getMethods();
            return Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(Bean.class))
                    .collect(Collectors.toMap(Method::getName, method -> fileName + "#" + method.getName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public <T> T getBean(String name, Class<T> resultClass) {
        try {
            // map 中以 class {全限定名}#{方法名}
            String[] info = map.get(name).split("#");
            Object o = Class.forName(info[0]).newInstance();
            Method method = o.getClass().getMethod(info[1]);
            return resultClass.cast(method.invoke(o));
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }
}
