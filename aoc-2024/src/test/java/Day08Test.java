import com.Day08;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.Utils.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

  private List<String> testInput;
  private List<String> realInput;

  @BeforeEach
  void beforeEach() throws IOException {
    testInput = readInput("Day08_test");
    realInput = readInput("Day08");
  }

  @Test
  void part1TestInput() {
    var day = new Day08(testInput);
    var result = day.part1();
    assertEquals(14, result);
  }

  @Test
  void part1RealInput() {
    var day = new Day08(realInput);
    var result = day.part1();
    assertEquals(0, result);
  }

  @Test
  void part2TestInput() {
    var day = new Day08(testInput);
    var result = day.part2();
    assertEquals(0, result);
  }

  @Test
  void part2RealInput() {
    var day = new Day08(realInput);
    var result = day.part2();
    assertEquals(0, result);
  }
}
