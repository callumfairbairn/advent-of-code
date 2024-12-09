import com.Day02;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.Utils.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {

  private List<String> testInput;
  private List<String> realInput;

  @BeforeEach
  void beforeEach() throws IOException {
    testInput = readInput("Day02_test");
    realInput = readInput("Day02");
  }

  @Test
  void part1TestInput() {
    var day = new Day02(testInput);
    var result = day.part1();
    assertEquals(2, result);
  }

  @Test
  void part1RealInput() {
    var day = new Day02(realInput);
    var result = day.part1();
    assertEquals(0, result);
  }

  @Test
  void part2TestInput() {
    var day = new Day02(testInput);
    var result = day.part2();
    assertEquals(0, result);
  }

  @Test
  void part2RealInput() {
    var day = new Day02(realInput);
    var result = day.part2();
    assertEquals(0, result);
  }
}
