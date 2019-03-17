package myFrame.frame.core.process;

import lombok.Getter;
import myFrame.frame.annotaion.Autowired;
import myFrame.frame.annotaion.Controller;
import myFrame.frame.annotaion.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapRouter extends MyAbstractMap {

    @Getter
    private Map<String, Method> handleMethods;

    private MapBean mapBean;

    public MapRouter(String fileName, MapBean mapBean) {
        super(fileName);
        this.mapBean = mapBean;
        handleMethods = new HashMap<>();
    }

    @Override
    public Map<String, Object> getBeanMapClass(String fileName) {
        try {
            Class<?> aClass = Class.forName(fileName);
            if (!aClass.isAnnotationPresent(Controller.class)) return null;

            Object obj = getBean(aClass);

            String basePath = aClass.getAnnotation(RequestMapping.class).value();

            Map<String, Object> result = new HashMap<>();
            Arrays.stream(aClass.getMethods()).forEach(method -> {
                if (!method.isAnnotationPresent(RequestMapping.class)) return;

                String path = method.getAnnotation(RequestMapping.class).value();
                String url = basePath + path;
                result.put(url, obj);
                handleMethods.put(url, method);
            });
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private Object getBean(Class<?> aClass) {
        try {
            Object obj = aClass.newInstance();
            Arrays.stream(aClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(obj, mapBean.getBean(field.getName(), field.getType()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private String getUpperFirstCharName(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
