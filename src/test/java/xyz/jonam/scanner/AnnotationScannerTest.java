package xyz.jonam.scanner;

import static org.junit.Assert.*;
import org.junit.Test;
import xyz.jonam.scanner.utils.TestAnnotation;

import java.io.IOException;
import java.util.List;

public class AnnotationScannerTest {

  @Test
  public void testAnnotations() throws IOException {
    List<Class> classes = AnnotationScanner.getClassesAnnotatedWith("xyz.jonam.scanner", TestAnnotation.class);
    assertFalse(classes.isEmpty());
    for(Class clazz : classes) {
      assertNotNull(clazz);
    }
  }

}