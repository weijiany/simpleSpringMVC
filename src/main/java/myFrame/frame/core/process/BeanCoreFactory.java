package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Configuration;
import myFrame.frame.annotaion.web.Controller;

import java.util.HashMap;
import java.util.Map;

public class BeanCoreFactory {

    private static Map<Class<?>, BeanFactory> classMap = new HashMap<>();

    static {
        classMap.put(Configuration.class, new MapBean());
        classMap.put(Controller.class, new MapController());
    }

    public static BeanFactory createBeanFactory(Class<?> aClass) {
        return classMap.get(aClass);
    }

    public static MapBean getBeanContainer() {
        return (MapBean) classMap.get(Configuration.class);
    }

    public static MapController getControllerContainer() {
        return (MapController) classMap.get(Controller.class);
    }
}
