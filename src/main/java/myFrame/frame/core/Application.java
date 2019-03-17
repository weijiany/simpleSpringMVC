package myFrame.frame.core;

import lombok.Getter;
import myFrame.frame.core.process.MapBean;
import myFrame.frame.core.process.MapRouter;

import java.net.URL;
import java.util.Objects;

public class Application {

    @Getter
    private MapBean mapBean;

    @Getter
    private MapRouter mapRouter;

    public Application(Class aClass) {
        String fileName = getFileName(aClass);
        mapBean = new MapBean(fileName);
        mapRouter = new MapRouter(fileName, mapBean);
    }

    public void init() {
        mapBean.init();
        mapRouter.init();
    }

    private String getFileName(Class aClass) {
        URL url = aClass.getClassLoader().getResource("");
        String filePath = Objects.requireNonNull(url).getFile();
        return filePath.substring(0, filePath.length() - 1);
    }
}
