package myFrame.frame.core.process;

import lombok.Getter;
import myFrame.frame.core.AspectHandler;
import myFrame.frame.util.Util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapService implements BeanFactory {

    @Getter
    private Map<String, String> serviceContainer;

    public MapService() {
        this.serviceContainer = new HashMap<>();
    }

    @Override
    public <T> T getBean(String name, Class<T> resultClass) {
        try {
            if (!serviceContainer.containsKey(name)) return null;
            Class<?> aClass = Class.forName(serviceContainer.get(name));
            Object obj = aClass.newInstance();
            return resultClass.cast(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public <T> T proxyObject(String name, T target) {
        try {
            MapAspect aspectContainer = BeanCoreFactory.getAspectContainer();
            if (aspectContainer.containKey(name)) {
                String targetMethodName = aspectContainer.getTargetMethodName(name);
                AspectHandler handler = aspectContainer.getAspectHandler(name);

                Method targetMethod;
                targetMethod = target.getClass().getMethod(targetMethodName);
                handler.registerMethod(targetMethod);
                target = (T) handler.bind(target);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return target;
    }

    @Override
    public void addBean(Class<?> aClass, String qualifiedName) {
        Class<?> aInterface = aClass.getInterfaces()[0];
        String key = Util.firstCharToLow(aInterface.getSimpleName());
        if (!serviceContainer.containsKey(key))
            serviceContainer.put(key, qualifiedName);
    }

}
