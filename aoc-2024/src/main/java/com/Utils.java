package com;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;


public class Utils {
  /**
   * Reads lines from the given input txt file.
   */
  public static List<String> readInput(String name) throws IOException {
    return Files.readAllLines(Paths.get("src/main/resources/" + name + ".txt"));
  }

  public static void println(Object o) {
    System.out.println(o);
  }
}
