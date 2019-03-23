package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapBean implements BeanFactory {

    private Map<String, String> beanContainer;

    public MapBean() {
        this.beanContainer = new HashMap<>();
    }


    @Override
    public <T> T getBean(String name, Class<T> resultClass) {
        try {
            if (!beanContainer.containsKey(name)) return null;
            String[] info = beanContainer.get(name).split("#");
            Object o = Class.forName(info[0]).newInstance();
            Method method = o.getClass().getMethod(info[1]);
            return resultClass.cast(method.invoke(o));
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    @Override
    public void addBean(Class<?> aClass, String qualifiedName) {
        Method[] methods = aClass.getMethods();

        beanContainer.putAll(
                Arrays.stream(methods)
                        .filter(method -> method.isAnnotationPresent(Bean.class))
                        .collect(Collectors.toMap(Method::getName, method -> qualifiedName + "#" + method.getName())));
    }
}
