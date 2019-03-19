package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Autowired;
import myFrame.frame.annotaion.web.Controller;
import myFrame.frame.annotaion.web.RequestMapping;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapRouter extends AbstractBeanFactory {

    private Map<String, Class<?>> controllerClass;

    private MapBean mapBean;

    public MapRouter(String fileName, MapBean mapBean) {
        super(fileName);
        this.mapBean = mapBean;
        controllerClass = new HashMap<>();
    }

    @Override
    public Map<String, String> getBeanMapClass(String fileName) {
        Map<String, String> routeMap = new HashMap<>();
        try {
            Class<?> aClass = Class.forName(fileName);
            if (!aClass.isAnnotationPresent(Controller.class)) return null;

            String basePath = aClass.getAnnotation(RequestMapping.class).value();

            Arrays.stream(aClass.getMethods()).forEach(method -> {
                if (!method.isAnnotationPresent(RequestMapping.class)) return;

                String path = method.getAnnotation(RequestMapping.class).value();
                String url = basePath + path;
                controllerClass.put(url, aClass);
                routeMap.put(url, method.getName());
            });
            return routeMap;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private Object newControllerInstanceAndDependencyInjection(Class<?> aClass) {
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

    public Object getController(String url) {
        return newControllerInstanceAndDependencyInjection(controllerClass.get(url));
    }

    @Override
    public <T> T getBean(String name, Class<T> aClass) {
        throw new NotImplementedException();
    }
}
