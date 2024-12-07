package com;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Day01 {
  private List<Integer> list1 = new ArrayList<>();
  private List<Integer> list2 = new ArrayList<>();

  public Day01(List<String> input) {
    parseInput(input);
  }

  private void parseInput(List<String> input) {
    input.forEach(line -> {
      var split = line.split(" {3}");
      list1.add(Integer.parseInt(split[0]));
      list2.add(Integer.parseInt(split[1]));
    });
  }

  public int part1() {
    list1.sort(null);
    list2.sort(null);
    var counter = 0;
    for (var i = 0; i < list1.size(); i++) {
      counter += abs(list1.get(i) - list2.get(i));
    }
    return counter;
  }

  public int part2() {
    return 0;
  }
}
