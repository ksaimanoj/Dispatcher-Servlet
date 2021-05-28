package xyz.jonam.scanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationScanner {

  public static List<Class> getClassesAnnotatedWith(String basePackage, Class<? extends Annotation> annotation) throws IOException {
    List<Class> classes = ClassScanner.getAllClassesWithinPackage(basePackage);
    return classes.stream()
            .filter(clazz -> clazz.isAnnotationPresent(annotation))
            .collect(Collectors.toList());
  }
}
