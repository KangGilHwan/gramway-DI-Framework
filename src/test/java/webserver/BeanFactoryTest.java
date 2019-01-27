package webserver;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BeanFactoryTest {

    private String directoryString;

    @Test
    public void findClassTest() {
        String packageNameSlashed = "./" + "".replace(".", "/");
        URL packageDirURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);

        System.out.println(packageDirURL);
        this.directoryString = packageDirURL.getFile();
        System.out.println(directoryString);
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(directoryString);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            classes = findFile(files, directoryString, classes);
        }
        for (Class<?> name : classes) {
            System.out.println(name.getName());
        }
    }

    public List<Class<?>> findFile(File[] files, String directoryName, List<Class<?>> classes) {
        String anotherPath = "";
        for (File file : files) {
            if (file.isDirectory()) {
                anotherPath = directoryName + file.getName();
                File fileChild = new File(anotherPath);
                findFile(fileChild.listFiles(), anotherPath, classes);
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    fileName = fileName.substring(0, fileName.length() - 6);
                    try {
                        System.out.println("Path ! : " + directoryName);
                        System.out.println("File Name! : " + fileName);
                        Class<?> clazz = Class.forName(directoryName.substring(directoryString.length()) + "." + fileName);
                        classes.add(clazz);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return classes;
    }
}
