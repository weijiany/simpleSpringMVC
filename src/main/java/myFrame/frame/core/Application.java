package myFrame.frame.core;

import myFrame.frame.core.process.InitContainer;

import java.net.URL;
import java.util.Objects;

public class Application {

    private InitContainer initContainer;

    public Application() {
        initContainer = new InitContainer();
    }

    public void init(String packageName) {
        String path = packageName.replace(".", "/");
        URL resource = this.getClass().getClassLoader().getResource("");
        initContainer.init(Objects.requireNonNull(resource).getPath() + path, packageName);
    }
}
