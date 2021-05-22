package xyz.jonam.scanner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassScanner {

  public static List<Class> getAllClassesWithinPackage(String basePackage) throws IOException {
    String basePath = convertPackageToPath(basePackage);
    List<String> filePaths = getAllFilesWithinPackage(basePath);
    return filePaths.stream()
            .filter(filePath -> filePath.endsWith(".class"))
            .map(ClassScanner::getClassFromFilePath)
            .collect(Collectors.toList());
  }

  private static Class getClassFromFilePath(String filePath) {
    try {
      return Class.forName(filePath);
    } catch (ClassNotFoundException e) {
      // ignored. To Log.
    }
    return null;
  }

  private static String convertPackageToPath(String basePackage) {
    return basePackage.replace(".", "/");
  }

  public static List<String> getAllFilesWithinPackage(String basePackage) throws IOException {
    List<String> files = new ArrayList<>();
    Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources(basePackage);
    while(urlEnumeration.hasMoreElements()) {
      File file = new File(urlEnumeration.nextElement().getFile());
      if(file.isDirectory()) {
        files.addAll(getAllFilesWithinDirectory(file, basePackage));
      } else {
        files.add(basePackage + File.separator + file.getName());
      }
    }
    return files;
  }

  private static List<String> getAllFilesWithinDirectory(File directory, String directoryPath) {
    List<String> files = new ArrayList<>();
    for(File file : Objects.requireNonNull(directory.listFiles())) {
      if(file.isDirectory()) files.addAll(getAllFilesWithinDirectory(file,
              directoryPath + File.separator + file.getName()));
      else files.add(directory + File.pathSeparator + file.getName());
    }
    return files;
  }
}
