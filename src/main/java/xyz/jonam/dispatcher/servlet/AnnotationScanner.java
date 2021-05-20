package xyz.jonam.dispatcher.servlet;

import java.io.File;
import java.io.IOException;
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

  List<String> getClassesAnnotated(String annotation) throws IOException {
    return getClassesAnnotatedWithinBasePackage(annotation, DEFAULT_PACKAGE);
  }

  public List<String> getClassesAnnotatedWithinBasePackage(String annotation, String basePackage) throws IOException {
    String basePath = basePackage.replace(".", "/");
    Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources(basePath);

    List<String> allFiles = new ArrayList<>();

    while(urlEnumeration.hasMoreElements()) {
      URL url = urlEnumeration.nextElement();
      String filePath = url.getFile();
      File file = new File(filePath);
      allFiles.addAll(analyseFile(file));
    }
    return allFiles;
  }

  private List<String> analyseFile(File file) {
    if(file.isDirectory()) {
      return Arrays
              .stream(Objects.requireNonNull(file.listFiles()))
              .map(this::analyseFile)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());
    } else {
      List<String> files = new ArrayList<>();
      files.add(file.getName());
      return files;
    }
  }
}
