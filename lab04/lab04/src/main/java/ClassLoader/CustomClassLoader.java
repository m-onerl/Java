package ClassLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

    private final Path classPath;

    public CustomClassLoader(Path classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            System.out.println("Class not found: " + name);
            throw new ClassNotFoundException("Class not found: " + name);
        }
        System.out.println("Class loaded: " + name);
        return defineClass(name, classData, 0, classData.length);
    }


    private byte[] loadClassData(String name) {
        String fileName = name.replace('.', File.separatorChar) + ".class";
        Path classFilePath = Paths.get(classPath.toString(), fileName);

        if (!Files.exists(classFilePath)) {
            return null;
        }

        try {
            return Files.readAllBytes(classFilePath);
        } catch (IOException e) {
            return null;
        }
    }
}
