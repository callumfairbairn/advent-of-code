package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day08 {
  List<List<String>> grid = new ArrayList<>();
  List<List<String>> antinodes = new ArrayList<>();
  HashMap<String, List<Coord>> reverseGrid = new HashMap<>();

  public Day08(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    for (var i = 0; i < input.size(); i++) {
      var split = List.of(input.get(i).split(""));
      grid.add(split);
      antinodes.add(List.of(new String[input.get(i).length()]));

      for (var j = 0; j < split.size(); j++) {
        var freq = split.get(j);
        reverseGrid.putIfAbsent(freq, new ArrayList<>());
        reverseGrid.get(freq).add(new Coord(i, j));
      }
    }
  }

  public int part1() {
    for (var i = 0; i < grid.size(); i++) {
      for (var j = 0; j < grid.get(i).size(); j++) {
        var freq = grid.get(i).get(j);
        if (!freq.equals(".")) {

        }
      }
    }

    return 0;
  }

  public int part2() {
    return 0;
  }
}
