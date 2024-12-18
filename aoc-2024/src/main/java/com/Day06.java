package com;

import java.util.*;

import static com.Utils.println;

enum Direction {UP, DOWN, LEFT, RIGHT}

public class Day06 {
  List<LinkedList<String>> grid = new ArrayList<>();
  Coord pos;
  Direction currentDir = Direction.UP;
  Map<Coord, LinkedList<Direction>> seen = new HashMap<>();
  Set<Coord> loopOptions = new HashSet<>();

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

  private void saveSeen() {
    if (seen.containsKey(pos)) {
      seen.get(pos).add(currentDir);
    } else {
      seen.put(pos, new LinkedList<>(List.of(currentDir)));
    }
  }

  private boolean inLoop() {
    return seen.containsKey(pos) && seen.get(pos).contains(currentDir);
  }

  private void reset() {
    seen = new HashMap<>();
  }

  private void simulateUntilOutOfBounds(Coord loopOption) {
//    saveSeen();
    while (inBounds(pos)) {
      if (getChar(pos).equals("#") || pos.equals(loopOption)) {
        // We hit a wall so step back and change direction
        seen.remove(pos);
        pos = pos.minus(nextPosModifier.get(currentDir));
        currentDir = nextDir.get(currentDir);
//        saveSeen();
      }
      pos = pos.plus(nextPosModifier.get(currentDir));
      if (inLoop()) {
        loopOptions.add(loopOption);
        reset();
        return;
      }
      saveSeen();
    }
  }

  public int part1() {
    simulateUntilOutOfBounds(new Coord(-1, -1));
    return seen.size() - 1;
  }

  public int part2() {
    for (var i = 0; i < grid.size(); i++) {
      for (var j = 0; j < grid.get(i).size(); j++) {
        var loopOption = new Coord(i, j);
        if (getChar(loopOption).equals(".")) {
          simulateUntilOutOfBounds(loopOption);
        }
      }
    }
    return loopOptions.size();
  }
}
