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
    String basePath = convertPackageToPath(basePackage);
    Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources(basePath);

    List<String> allFiles = new ArrayList<>();

    while(urlEnumeration.hasMoreElements()) {
      URL url = urlEnumeration.nextElement();
      File file = new File(url.getFile());
      allFiles.addAll(analyseFile(file, basePackage));
    }
    return allFiles;
  }

  private String convertPackageToPath(String basePackage) {
    return basePackage.replace(".", "/");
  }

  private List<String> analyseFile(File file, String fullyQualifiedName) {
    if (file.isDirectory()) {
      return Arrays.stream(Objects.requireNonNull(file.listFiles()))
          .map(subFile -> analyseFile(subFile, fullyQualifiedName + "." + subFile.getName()))
          .flatMap(Collection::stream)
          .collect(Collectors.toList());
    } else {
      List<String> files = new ArrayList<>();
      if (file.getName().endsWith(".class")) {
        files.add(fullyQualifiedName);
      }
      return files;
    }
  }

  public List<Class> getClassesAnnotatedWith(Class<? extends Annotation> annotation) throws IOException {
    List<String> classes = getAllClasses();
    List<Class> annotatedClasses = new ArrayList<>();
    for (String s : classes) {
      try{
        Class clazz = Class.forName(removeExtension(s));
        if(clazz.isAnnotationPresent(annotation)) {
          annotatedClasses.add(clazz);
        }
      } catch (ClassNotFoundException ex) {
        //ignored
      }
    }
    return annotatedClasses;
  }

  private String removeExtension(String s) {
    return s.substring(0, s.length() - 6);
  }
}
