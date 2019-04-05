package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Autowired;
import myFrame.frame.annotaion.web.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

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
        Class<?> controllerClass = controllerContainer.get(url);

        try {
            Object obj = controllerClass.newInstance();
            setAutoWiredField.accept(Arrays.stream(controllerClass.getDeclaredFields()), obj);
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    private BiConsumer<Stream<Field>, Object> setAutoWiredField = (stream, bean) ->
            stream.filter(field -> field.isAnnotationPresent(Autowired.class))
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            Object o = autoWiredField(fieldName, field.getType());

                            o = BeanCoreFactory.getServiceContainer().proxyObject(fieldName, o);
                            field.set(bean, o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

    private Object autoWiredField(String name, Class<?> type) {
        Object bean = BeanCoreFactory.getBeansContainer()
                .stream()
                .map(container -> container.getBean(name, type))
                .filter(Objects::nonNull)
                .findFirst().orElseThrow(RuntimeException::new);
        setAutoWiredField.accept(Arrays.stream(bean.getClass().getDeclaredFields()), bean);
        return bean;
    }

    public String getMethodName(String url) {
        return routeContainer.get(url);
    }
}
