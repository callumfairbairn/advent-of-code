import com.Day07;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.Day07.addLeadingZeroes;
import static com.Day07.canCreateResult;
import static com.Utils.readInput;
import static org.junit.jupiter.api.Assertions.*;

public class Day07Test {

  private List<String> testInput;
  private List<String> realInput;

  @BeforeEach
  void beforeEach() throws IOException {
    testInput = readInput("Day07_test");
    realInput = readInput("Day07");
  }

  @Test
  void part1TestInput() {
    var day = new Day07(testInput);
    var result = day.part1();
    assertEquals(3749, result);
  }

  @Test
  void part1RealInput() {
    var day = new Day07(realInput);
    var result = day.part1();
    assertEquals(2654749936343L, result);
  }

  @Test
  void part2TestInput() {
    var day = new Day07(testInput);
    var result = day.part2();
    assertEquals(11387, result);
  }

  @Test
  void part2RealInput() {
    var day = new Day07(realInput);
    var result = day.part2();
    assertEquals(124060392153684L, result);
  }

  @Test
  void canCreateResultTest() {
    assertFalse(canCreateResult(1L, Arrays.asList(2L, 2L, 2L), 2));
    assertTrue(canCreateResult(1L, Arrays.asList(1L, 1L, 1L), 2));
    assertTrue(canCreateResult(2L, Arrays.asList(1L, 1L, 1L), 2));
    assertTrue(canCreateResult(3L, Arrays.asList(1L, 1L, 1L), 2));
    assertFalse(canCreateResult(4L, Arrays.asList(1L, 1L, 1L), 2));
    assertFalse(canCreateResult(100L, Arrays.asList(50L, 50L, 2L), 2));
  }

  @Test
  void addLeadingZeroesTest() {
    assertEquals("001", addLeadingZeroes("1", 3));
    assertEquals("101", addLeadingZeroes("101", 3));
  }
}
