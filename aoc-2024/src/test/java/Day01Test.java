import com.Day01;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.Utils.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

  private List<String> testInput;
  private List<String> realInput;

  @BeforeEach
  void beforeEach() throws IOException {
    System.out.println(new java.io.File(".").getCanonicalPath());
    testInput = readInput("Day01_test");
    realInput = readInput("Day01");
  }

  @Test
  void part1TestInput() {
    var day = new Day01();
    var result = day.part1(testInput);
    assertEquals(0, result);
  }

  @Test
  void part1RealInput()  {
    var day = new Day01();
    var result = day.part1(realInput);
    assertEquals(0, result);
  }

  @Test
  void part2TestInput()  {
    var day = new Day01();
    var result = day.part2(testInput);
    assertEquals(0, result);
  }

  @Test
  void part2RealInput()  {
    var day = new Day01();
    var result = day.part2(realInput);
    assertEquals(0, result);
  }
}
