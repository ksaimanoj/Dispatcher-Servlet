package xyz.jonam.dispatcher.servlet;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import xyz.jonam.dispatcher.servlet.annotation.TestAnnotation;

import java.io.IOException;

public class AnnotationScannerTest {

  private AnnotationScanner annotationScanner;

  @Before
  public void setup() {
    annotationScanner = new AnnotationScanner();
  }

  @Test
  public void getAllClassesTest() throws IOException {
    assertTrue(annotationScanner.getAllClasses().size() > 0);
  }

  @Test
  public void getAllClassesAnnotated() throws IOException {
    assertEquals(2,
            annotationScanner.getClassesAnnotatedWith(TestAnnotation.class).size());
  }
}