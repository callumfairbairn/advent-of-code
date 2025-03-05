import com.Coord;
import com.Day08;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.Day08.getAntinodes;
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
    assertEquals(329, result);
  }

  @Test
  void part2TestInput() {
    var day = new Day08(testInput);
    var result = day.part2();
    assertEquals(34, result);
  }

  @Test
  void part2RealInput() {
    var day = new Day08(realInput);
    var result = day.part2();
    assertEquals(1190, result);
  }

  @Test
  void testGetAntinodes1() {
    var freqALocation = new Coord(4, 3);
    var freqBLocation = new Coord(5, 5);

    var antiondes = getAntinodes(freqALocation, freqBLocation);
    assertEquals(new Coord(3, 1), antiondes.get(0));
    assertEquals(new Coord(6, 7), antiondes.get(1));
  }

  @Test
  void testGetAntinodes2() {
    var freqALocation = new Coord(4, 3);
    var freqBLocation = new Coord(8, 4);

    var antiondes = getAntinodes(freqALocation, freqBLocation);
    assertEquals(new Coord(0, 2), antiondes.get(0));
    assertEquals(new Coord(12, 5), antiondes.get(1));
  }

  @Test
  void testGetAntinodes3() {
    var freqALocation = new Coord(5, 5);
    var freqBLocation = new Coord(8, 4);

    var antiondes = getAntinodes(freqALocation, freqBLocation);
    assertEquals(new Coord(2, 6), antiondes.get(0));
    assertEquals(new Coord(11, 3), antiondes.get(1));
  }
}
