package xyz.jonam.dispatcher.servlet;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AnnotationScannerTest {

  private AnnotationScanner annotationScanner;

  @Before
  public void setup() {
    annotationScanner = new AnnotationScanner();
  }

  @Test
  public void getClassesAnnotated() throws IOException {
    assertTrue(annotationScanner.getClassesAnnotated("test").size() > 0);
  }
}