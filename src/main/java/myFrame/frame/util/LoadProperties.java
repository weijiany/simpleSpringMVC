package myFrame.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {

    private String SCAN_PACKAGE;

    public LoadProperties(String path) {
        loadProperties(path);
    }

    private void loadProperties(String path) {
        Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SCAN_PACKAGE = properties.getProperty("scanPackage");
    }

    public String getSCAN_PACKAGE() {
        return SCAN_PACKAGE;
    }
}
