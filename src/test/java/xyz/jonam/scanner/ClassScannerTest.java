package xyz.jonam.scanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

public class ClassScannerTest {

  @Test
  public void getAllFilesWithinPackage() throws IOException {
    assertTrue(ClassScanner.getAllFilesWithinPackage("xyz/jonam/scanner").size() > 0);
  }

  @Test
  public void getAllClassesWithinPackage() throws IOException {
    assertTrue(ClassScanner.getAllClassesWithinPackage("xyz.jonam.scanner").size() > 0);
  }
}