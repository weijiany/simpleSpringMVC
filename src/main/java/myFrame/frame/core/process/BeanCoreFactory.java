package myFrame.frame.core.process;

import myFrame.frame.annotaion.bean.Configuration;
import myFrame.frame.annotaion.bean.Service;
import myFrame.frame.annotaion.web.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanCoreFactory {

    private static Map<Class<?>, BeanFactory> classMap = new HashMap<>();

    static {
        classMap.put(Configuration.class, new MapBean());
        classMap.put(Controller.class, new MapController());
        classMap.put(Service.class, new MapService());
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

    public static MapService getServiceContainer() {
        return (MapService) classMap.get(Service.class);
    }

    public static List<BeanFactory> getBeansContainer() {
        List<BeanFactory> list = new ArrayList<>(2);
        list.add(getBeanContainer());
        list.add(getServiceContainer());
        return list;
    }
}
