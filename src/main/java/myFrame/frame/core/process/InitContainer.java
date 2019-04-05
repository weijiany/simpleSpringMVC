package myFrame.frame.core.process;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Objects;

public class InitContainer {

    public void init(String initPath, String className) {
        createBeans(initPath, className);
    }

    private void createBeans(String initPath, String className) {
        File file = new File(initPath);
        if (file.isDirectory())
            for (File child : Objects.requireNonNull(file.listFiles()))
                createBeans(initPath + "/" + child.getName(), className + "." + child.getName());
        else {
            if (!className.endsWith(".class")) return;

            try {
                String qualifiedName = className.replace(".class", "");
                Class<?> aClass = Class.forName(qualifiedName);
                Annotation[] annotations = aClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    BeanFactory beanFactory = BeanCoreFactory.createBeanFactory(annotation.annotationType());
                    if (beanFactory == null) continue;

                    beanFactory.addBean(aClass, qualifiedName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
