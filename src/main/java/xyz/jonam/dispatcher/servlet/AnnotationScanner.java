package xyz.jonam.dispatcher.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AnnotationScanner {

  private static final String DEFAULT_PACKAGE = "xyz.jonam.dispatcher.servlet";

  List<String> getAllClasses() throws IOException {
    return getClassesWithinPackage(DEFAULT_PACKAGE);
  }

  public List<String> getClassesWithinPackage(String basePackage) throws IOException {
    String basePath = basePackage.replace(".", "/");
    Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources(basePath);

    List<String> allFiles = new ArrayList<>();

    while(urlEnumeration.hasMoreElements()) {
      URL url = urlEnumeration.nextElement();
      String filePath = url.getFile();
      File file = new File(filePath);
      allFiles.addAll(analyseFile(file, basePackage));
    }
    return allFiles;
  }

  private List<String> analyseFile(File file, String packageName) {
    if (file.isDirectory()) {
      return Arrays.stream(Objects.requireNonNull(file.listFiles()))
          .map(subFile -> analyseFile(subFile, packageName + "." + subFile.getName()))
          .flatMap(Collection::stream)
          .collect(Collectors.toList());
    } else {
      List<String> files = new ArrayList<>();
      if (file.getName().endsWith(".class")) {
        files.add(packageName);
      }
      return files;
    }
  }

  public List<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) throws IOException {
    List<String> classes = getAllClasses();
    List<Class> annotatedClasses = new ArrayList<>();
    for (String s : classes) {
      try{
        Class clazz = Class.forName(s.substring(0, s.length() - 6));
        if(clazz.isAnnotationPresent(annotation)) {
          annotatedClasses.add(clazz);
        }
      } catch (ClassNotFoundException ex) {
        System.out.println("Error while trying to create class object for " + s);
        ex.printStackTrace();
      }
    }
    return annotatedClasses;
  }
}
