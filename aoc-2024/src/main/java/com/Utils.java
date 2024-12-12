package com;
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

  public static void println(Object ...objects) {
    for (Object obj : objects) {
      System.out.print(obj);
      System.out.print(" ");
    }
    System.out.print("\n");
  }
}
