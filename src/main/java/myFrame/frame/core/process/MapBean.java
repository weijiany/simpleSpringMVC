package myFrame.frame.core.process;

import myFrame.frame.annotaion.Bean;
import myFrame.frame.annotaion.Configuration;

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
    public Map<String, Object> getBeanMapClass(String fileName) {
        try {
            Class aClass = Class.forName(fileName);
            if (!aClass.isAnnotationPresent(Configuration.class)) return null;

            Object obj = aClass.newInstance();
            Method[] methods = aClass.getMethods();
            return Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(Bean.class))
                    .collect(Collectors.toMap(Method::getName, method -> {
                        try {
                            return method.invoke(obj);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        throw new RuntimeException();
                    }));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public <T> T getBean(String name, Class<T> aClass) {
        return aClass.cast(map.get(name));
    }
}
