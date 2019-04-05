package myFrame.frame.core;

import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class AspectHandler implements InvocationHandler {

    private Object target;

    @Setter
    private Object aopObj;

    private Set<String> aopMethods;

    @Setter
    private Method before;

    public AspectHandler() {
        aopMethods = new HashSet<>();
    }

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    public void registerMethod(Method method) {
        aopMethods.add(method.getName());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (aopMethods.contains(method.getName())) {
            Object result;
            before.invoke(aopObj);
            result = method.invoke(target, args);
            return result;
        }
        return method.invoke(target, args);
    }
}
