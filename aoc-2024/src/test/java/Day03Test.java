import com.Day03;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.Utils.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

  private List<String> testInput;
  private List<String> testInput2;
  private List<String> realInput;

  @BeforeEach
  void beforeEach() throws IOException {
    testInput = readInput("Day03_test");
    testInput2 = readInput("Day03_test2");
    realInput = readInput("Day03");
  }

  @Test
  void part1TestInput() {
    var day = new Day03(testInput);
    var result = day.part1();
    assertEquals(161, result);
  }

  @Test
  void part1RealInput() {
    var day = new Day03(realInput);
    var result = day.part1();
    assertEquals(184122457, result);
  }

  @Test
  void part2TestInput() {
    var day = new Day03(testInput2);
    var result = day.part2();
    assertEquals(48, result);
  }

  @Test
  void part2RealInput() {
    var day = new Day03(realInput);
    var result = day.part2();
    assertEquals(107862689, result);
  }
}
