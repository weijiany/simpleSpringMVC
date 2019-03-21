package myFrame.frame.core.process;

public interface BeanFactory {

    <T> T getBean(String name, Class<T> resultClass);

    void addBean(Class<?> aClass, String qualifiedName);
}
