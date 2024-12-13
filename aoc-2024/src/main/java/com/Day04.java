package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day04 {
  List<List<String>> grid = new ArrayList<>();
  static final String[] LETTERS = new String[]{"X", "M", "A", "S"};
  static final Map<Coord, Coord> CORNER_TO_CORNER = Map.of(
          new Coord(-1, -1), new Coord(1, 1),
          new Coord(-1, 1), new Coord(1, -1)
  );

  public Day04(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    for (String line : input) {
      grid.add(Arrays.stream(line.split("")).toList());
    }
  }

  private boolean inBounds(Coord coord) {
    return coord.x() >= 0 && coord.x() < grid.size() && coord.y() >= 0 && coord.y() < grid.getFirst().size();
  }

  private String getChar(Coord coord) {
    return grid.get(coord.x()).get(coord.y());
  }

  boolean xmasExistsInDirection(Coord coord, Coord direction, int level) {
    if (getChar(coord).equals(LETTERS[level])) {
      if (level == LETTERS.length - 1) return true;
      var newCoord = coord.plus(direction);
      return inBounds(newCoord) && xmasExistsInDirection(newCoord, direction, level + 1);
    }
    return false;
  }

  public int part1() {
    var counter = 0;
    for (var x = 0; x < grid.size(); x++) {
      for (var y = 0; y < grid.getFirst().size(); y++) {
        for (var i = -1; i <= 1; i++) {
          for (var j = -1; j <= 1; j++) {
            if (xmasExistsInDirection(new Coord(x, y), new Coord(i, j), 0)) {
              counter++;
            }
          }
        }
      }
    }
    return counter;
  }

  boolean masExists(Coord coord) {
    if (getChar(coord).equals("A")) {
      ArrayList<String> foundCorners = new ArrayList<>();

      for (Map.Entry<Coord, Coord> pair : CORNER_TO_CORNER.entrySet()) {
        var corner1 = coord.plus(pair.getKey());
        var corner2 = coord.plus(pair.getValue());

        if (inBounds(corner1) && inBounds(corner2) && !getChar(corner1).equals(getChar(corner2))) {
          foundCorners.add(getChar(corner1));
          foundCorners.add(getChar(corner2));
        }
      }

      var mCount = foundCorners.stream().filter(str -> str.equals("M")).count();
      var sCount = foundCorners.stream().filter(str -> str.equals("S")).count();
      return mCount == 2 && sCount == 2;
    }
    return false;
  }

  public int part2() {
    var counter = 0;
    for (var x = 1; x < grid.size() - 1; x++) {
      for (var y = 1; y < grid.getFirst().size() - 1; y++) {
        if (masExists(new Coord(x, y))) {
          counter++;
        }
      }
    }
    return counter;
  }
}
