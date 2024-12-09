package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {
  List<List<Integer>> reports = new ArrayList<>();

  public Day02(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    input.forEach(line -> {
      var ints = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
      reports.add(ints);
    });
  }

  private boolean reportIsSafe(List<Integer> report) {
    var increasing = report.get(0) < report.get(1);
    var iterator = report.iterator();
    var current = iterator.next();
    while (iterator.hasNext()) {
      var next = iterator.next();
      if (Math.abs(current - next) > 3) {
        return false;
      }
      if (increasing && next <= current) return false;
      if (!increasing && next >= current) return false;
      current = next;
    }
    return true;
  }

  public int part1() {
    var reportsSafety = reports.stream().map(this::reportIsSafe).collect(Collectors.toList());
    return reportsSafety.stream().mapToInt(safe -> safe ? 1 : 0).sum();
  }

  public int part2() {
    return 0;
  }
}
