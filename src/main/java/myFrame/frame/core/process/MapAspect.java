package myFrame.frame.core.process;

import myFrame.frame.annotaion.aspect.Before;
import myFrame.frame.core.AspectHandler;
import myFrame.frame.util.Util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapAspect implements BeanFactory {

    // 存储目标方法
    private Map<String, String> targetMethod;

    // 使用 class + '#' + 方法名的形式
    private Map<String, String> aopObjectAndMethod;

    public MapAspect() {
        targetMethod = new HashMap<>();
        aopObjectAndMethod = new HashMap<>();
    }

    @Override
    public <T> T getBean(String name, Class<T> resultClass) {
        return null;
    }

    @Override
    public void addBean(Class<?> aClass, String qualifiedName) {
        try {
            Class<?> aopObjClass = Class.forName(qualifiedName);
            Method aopMethod = Arrays.stream(aopObjClass.getMethods())
                    .filter(m -> m.isAnnotationPresent(Before.class)).findFirst().get();
            Before pointCut = aopMethod.getAnnotation(Before.class);

            String pointCutName = pointCut.value();
            String[] strings = pointCutName.split("\\.");
            int len = strings.length;
            String key = Util.firstCharToLow(strings[len - 2]);
            String value = strings[len - 1];

            aopObjectAndMethod.put(key, qualifiedName + "#" + aopMethod.getName());
            targetMethod.put(key, value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AspectHandler getAspectHandler(String beanName) {
        AspectHandler aspectHandler = new AspectHandler();
        String[] aopObjToMethod = aopObjectAndMethod.get(beanName).split("#");

        try {
            Class<?> aopObjClass = Class.forName(aopObjToMethod[0]);
            Method before = aopObjClass.getMethod(aopObjToMethod[1]);
            aspectHandler.setAopObj(aopObjClass.newInstance());
            aspectHandler.setBefore(before);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return aspectHandler;
    }

    public String getTargetMethodName(String beanName) {
        return targetMethod.get(beanName);
    }

    public boolean containKey(String beanName) {
        return targetMethod.containsKey(beanName);
    }
}
