package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {
  List<List<Integer>> reports = new ArrayList<>();

  public Day02(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    input.forEach(line -> {
      var ints = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
      reports.add(ints);
    });
  }

  private int calculateReportIsSafe(List<Integer> report) {
    var increasing = report.get(0) < report.get(1);
    var current = report.get(0);
    for (var i = 1; i < report.size(); i++) {
      var next = report.get(i);
      if (Math.abs(current - next) > 3) return i;
      if (increasing && next <= current) return i;
      if (!increasing && next >= current) return i;
      current = next;
    }
    return -1;
  }

  private boolean reportIsSafe(List<Integer> report) {
    return calculateReportIsSafe(report) == -1;
  }

  private boolean reportIsSafeWithDampening(List<Integer> report) {
    var result = calculateReportIsSafe(report);
    if (result == -1) return true;

    for (var i = -2; i < 3; i++) {
      var newList = new ArrayList<>(report);
      if (result + i >= 0 && result + i < report.size()) {
        newList.remove(result + i);
        var newResult = calculateReportIsSafe(newList);
        if (newResult == -1) return true;
      }
    }
    return false;
  }

  public int part1() {
    var reportsSafety = reports.stream().map(this::reportIsSafe).toList();
    return reportsSafety.stream().mapToInt(safe -> safe ? 1 : 0).sum();
  }

  public int part2() {
    var reportsSafety = reports.stream().map(this::reportIsSafeWithDampening).toList();
    return reportsSafety.stream().mapToInt(safe -> safe ? 1 : 0).sum();
  }
}
