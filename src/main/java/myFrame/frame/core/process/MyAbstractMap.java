package myFrame.frame.core.process;

import lombok.Getter;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class MyAbstractMap {

    protected String fileName;

    @Getter
    protected Map<String, Object> map;

    public MyAbstractMap(String fileName) {
        this.fileName = fileName;
    }

    public abstract Map<String, Object> getBeanMapClass(String fileName);

    public void init() {
        this.map = getBeans(fileName, "");
    }

    public Map<String, Object> getBeans(String fileName, String className) {
        Map<String, Object> result = new HashMap<>();
        File packageFile = new File(fileName);
        if (packageFile.isDirectory()) {
            result.putAll(Arrays.stream(Objects.requireNonNull(packageFile.listFiles()))
                    .map(file -> getBeans(fileName + "/" + file.getName(), className + "." + file.getName()))
                    .reduce((map, m) -> {
                        map.putAll(m);
                        return map;
                    }).orElse(new HashMap<>()));
        } else {
            if (!className.endsWith(".class")) return result;

            String path = className.substring(1);
            Map<String, Object> beanNameMapClass = getBeanMapClass(path.replaceAll(".class", ""));
            if (beanNameMapClass != null && !beanNameMapClass.isEmpty())
                result.putAll(beanNameMapClass);
        }
        return result;
    }
}
