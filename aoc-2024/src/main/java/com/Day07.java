package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 {
  Map<Long, List<Long>> requests = new HashMap<>();

  public Day07(List<String> input) {
    parseInput(input);
  }

  void parseInput(List<String> input) {
    for (String line : input) {
      var split1 = Arrays.stream(line.split(": ")).toList();
      var key = Long.parseLong(split1.getFirst());
      var split2 = split1.getLast();
      requests.put(key, Arrays.stream(split2.split(" ")).map(Long::parseLong).toList());
    }
  }

  public static String addLeadingZeroes(String string, int bits) {
    StringBuilder newString = new StringBuilder(string);
    while (newString.length() < bits) {
      newString.insert(0, "0");
    }
    return newString.toString();
  }

  static Long calculate(String[] operators, List<Long> parts) {
    var counter = parts.getFirst();
    for (var j = 1; j < parts.size(); j++) {
      // j - 1 because operators is one element fewer than parts
      var operator = operators[j - 1];
      var part = parts.get(j);
      if (operator.equals("0")) {
        counter += part;
      } else {
        counter *= part;
      }
    }
    return counter;
  }

  public static boolean canCreateResult(Long key, List<Long> parts) {
    // use parts.size() - 1 because we only need operators between each part
    for (var i = 0; i <= Math.pow(2, parts.size() - 1); i++) {
      var operators = addLeadingZeroes(Long.toBinaryString(i), parts.size() - 1).split("");
      var result = calculate(operators, parts);
      if (result.equals(key)) return true;
    }
    return false;
  }

  public long part1() {
    var counter = 0L;
    for (Map.Entry<Long, List<Long>> entry : requests.entrySet()) {
      if (canCreateResult(entry.getKey(), entry.getValue())) {
        counter += entry.getKey();
      }

    }
    return counter;
  }

  public int part2() {
    return 0;
  }
}
