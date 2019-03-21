package myFrame.frame.core;

import myFrame.frame.core.process.InitContainer;

import java.net.URL;
import java.util.Objects;

public class Application {

    private InitContainer initContainer;

    public Application() {
        initContainer = new InitContainer();
    }

    public void init(Class<?> aClass) {
        initContainer.init(getApplicationRootPath(aClass));
    }

    private String getApplicationRootPath(Class aClass) {
        URL url = aClass.getClassLoader().getResource("");
        String filePath = Objects.requireNonNull(url).getFile();
        return filePath.substring(0, filePath.length() - 1);
    }
}
