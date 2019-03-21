package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Autowired;
import myFrame.frame.annotaion.web.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapController implements BeanFactory {

    private Map<String, Class<?>> controllerContainer;

    private Map<String, String> routeContainer;

    public MapController() {
        controllerContainer = new HashMap<>();
        routeContainer = new HashMap<>();
    }

    @Override
    public <T> T getBean(String name, Class<T> resultClass) {
        return null;
    }

    @Override
    public void addBean(Class<?> aClass, String qualifiedName) {
        String basePath = aClass.getAnnotation(RequestMapping.class).value();

        Method[] methods = aClass.getMethods();
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    String path = method.getAnnotation(RequestMapping.class).value();
                    String url = basePath + path;
                    controllerContainer.put(url, aClass);
                    routeContainer.put(url, method.getName());
                });
    }

    public Object getController(String url) {
        Class<?> contollerClass = controllerContainer.get(url);

        try {
            Object obj = contollerClass.newInstance();
            Arrays.stream(contollerClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            BeanFactory mapBean = BeanCoreFactory.getBeanContainer();
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

    public String getMthodName(String url) {
        return routeContainer.get(url);
    }
}
