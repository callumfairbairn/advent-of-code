package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

public class Day01 {
  private final List<Integer> list1 = new ArrayList<>();
  private final List<Integer> list2 = new ArrayList<>();

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

  private HashMap<Integer, Integer> createCounterMap(List<Integer> list) {
    var map = new HashMap<Integer, Integer>();
    list.forEach(num -> {
      map.compute(num, (k, v) -> v == null ? 1 : v + 1);
    });
    return map;
  }

  public int part1() {
    list1.sort(null);
    list2.sort(null);
    var total = 0;
    for (var i = 0; i < list1.size(); i++) {
      total += abs(list1.get(i) - list2.get(i));
    }
    return total;
  }

  public int part2() {
    var counterMap2 = createCounterMap(list2);
    var total = 0;

    for (Integer integer : list1) {
      var toAdd = counterMap2.getOrDefault(integer, 0) * integer;
      total += toAdd;
    }

    return total;
  }
}
