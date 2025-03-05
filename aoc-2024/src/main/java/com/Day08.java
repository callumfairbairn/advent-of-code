package com;

import java.util.*;

public class Day08 {
  List<List<String>> grid = new ArrayList<>();
  HashSet<Coord> antinodes = new HashSet<>();
  HashMap<String, List<Coord>> reverseGrid = new HashMap<>();

  boolean getAllAntinodesOn;

  public Day08(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    for (var i = 0; i < input.size(); i++) {
      var split = List.of(input.get(i).split(""));
      grid.add(split);

      for (var j = 0; j < split.size(); j++) {
        var freq = split.get(j);
        if (!freq.equals(".")) {
          reverseGrid.putIfAbsent(freq, new ArrayList<>());
          reverseGrid.get(freq).add(new Coord(i, j));
        }
      }
    }
  }

  boolean nodeInGrid(Coord node) {
    return node.x() >= 0 && node.x() < grid.size() && node.y() >= 0 && node.y() < grid.getFirst().size();
  }

   public static List<Coord> getAntinodes(Coord a, Coord b) {
    var dX = a.x() - b.x();
    var dY = a.y() - b.y();

    var node1 = new Coord(a.x() + dX, a.y() + dY);
    var node2 = new Coord(b.x() - dX, b.y() - dY);
    return Arrays.asList(node1, node2);
  }

  public List<Coord> getAllAntinodes(Coord a, Coord b) {
    var dX = a.x() - b.x();
    var dY = a.y() - b.y();

    var antinodes = new ArrayList<Coord>();

    var currentPositiveDir = new Coord(b.x() + dX, b.y() + dY);
    while (nodeInGrid(currentPositiveDir)) {
      antinodes.add(currentPositiveDir);
      currentPositiveDir = new Coord(currentPositiveDir.x() + dX, currentPositiveDir.y() + dY);
    }

    var currentNegativeDir = new Coord(a.x() - dX, a.y() - dY);
    while (nodeInGrid(currentNegativeDir)) {
      antinodes.add(currentNegativeDir);
      currentNegativeDir = new Coord(currentNegativeDir.x() - dX, currentNegativeDir.y() - dY);
    }

    return antinodes;
  }

  void addAntinodes(Coord a, Coord b) {
    var antinodesToAdd = getAllAntinodesOn ? getAllAntinodes(a, b).stream() : getAntinodes(a, b).stream().filter(this::nodeInGrid);
    antinodesToAdd.forEach(antinodes::add);
  }

  void addAntinodesForEachCoordCombo(List<Coord> coords) {
    for (var i = 0; i < coords.size(); i++) {
      for (var j = 1; j < coords.size(); j++) {
        if (j > i) {
          addAntinodes(coords.get(i), coords.get(j));
        }
      }
    }
  }

  Integer countNumberOfAntinodes() {
    for (Map.Entry<String, List<Coord>> entry : reverseGrid.entrySet()) {
      var coords = entry.getValue();

      if (coords.size() > 1) {
        addAntinodesForEachCoordCombo(coords);
      }
    }

    return antinodes.size();
  }

  public int part1() {
    getAllAntinodesOn = false;
    return countNumberOfAntinodes();
  }

  public int part2() {
    getAllAntinodesOn = true;
    return countNumberOfAntinodes();
  }
}
