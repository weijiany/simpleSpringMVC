package myFrame.frame.core.process;

import java.util.HashMap;
import java.util.Map;

public class MapService implements BeanFactory {

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

    @Override
    public void addBean(Class<?> aClass, String qualifiedName) {
        String key = firstCharToLow(aClass.getSimpleName());
        serviceContainer.put(key, qualifiedName);
    }

    private String firstCharToLow(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
