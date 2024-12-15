package com;

import java.util.*;

import static com.Utils.println;

enum Direction { UP, DOWN, LEFT, RIGHT }

public class Day06 {
  List<LinkedList<String>> grid = new ArrayList<>();
  Coord pos;
  Direction currentDir = Direction.UP;
  Map<Direction, Coord> nextPosModifier = Map.of(
          Direction.UP, new Coord(-1, 0),
          Direction.DOWN, new Coord(1, 0),
          Direction.LEFT, new Coord(0, -1),
          Direction.RIGHT, new Coord(0, 1)
  );
  Map<Direction, Direction> nextDir = Map.of(
          Direction.UP, Direction.RIGHT,
          Direction.RIGHT, Direction.DOWN,
          Direction.DOWN, Direction.LEFT,
          Direction.LEFT, Direction.UP
  );
  Map<Direction, String> directionToChar = Map.of(
          Direction.UP, "^",
          Direction.RIGHT, ">",
          Direction.DOWN, "∨",
          Direction.LEFT, "<"
  );

  public Day06(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    for (var i = 0; i < input.size(); i++) {
      var line = input.get(i);
      var split = Arrays.stream(line.split("")).toList();
      var j = split.indexOf("^");
      if (j != -1) pos = new Coord(i, j);
      grid.add(new LinkedList<>(split));
    }
  }

  private boolean inBounds(Coord coord) {
    return coord.x() >= 0 && coord.x() < grid.size() && coord.y() >= 0 && coord.y() < grid.getFirst().size();
  }

  private String getChar(Coord coord) {
    return grid.get(coord.x()).get(coord.y());
  }

  private void printGrid() {
    for (List<String> line : grid) {
      for (String str : line) {
        System.out.print(str);
      }
      println();
    }
  }

  public int part1() {
    Set<Coord> seen = new HashSet<>();
    while (inBounds(pos)) {
      if (getChar(pos).equals("#")) {
        // We hit a wall so step back and change direction
        seen.remove(pos);
        pos = pos.minus(nextPosModifier.get(currentDir));
        currentDir = nextDir.get(currentDir);
      };
      grid.get(pos.x()).set(pos.y(), directionToChar.get(currentDir));
      pos = pos.plus(nextPosModifier.get(currentDir));
      seen.add(pos);
    }
    printGrid();
    return seen.size() - 1;
  }

  public int part2() {
    return 0;
  }
}
