package xyz.jonam.scanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ClassScannerTest {

  @Test
  public void getAllFilesWithinPackage() throws IOException {
    assertTrue(ClassScanner.getAllFilesWithinPackage("xyz/jonam/scanner").size() > 0);
  }

  @Test
  public void getAllClassesWithinPackage() throws IOException {
    List<Class> classes = ClassScanner.getAllClassesWithinPackage("xyz.jonam.scanner");
    assertTrue(classes.size() > 0);
    for(Class clazz : classes) assertNotNull(clazz);
  }
}