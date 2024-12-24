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
      } else if (operator.equals("1")) {
        counter *= part;
      } else {
        counter = Long.parseLong(counter.toString() + part.toString());
      }
    }
    return counter;
  }

  private static final char[] CHARS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

  static String convertIntToBase(int i, int base){
    final StringBuilder builder = new StringBuilder();
    do{
      builder.append(CHARS[i % base]);
      i /= base;
    } while(i > 0);
    return builder.reverse().toString();
  }

  public static boolean canCreateResult(Long key, List<Long> parts, int operatorNumber) {
    // use parts.size() - 1 because we only need operators between each part
    for (var i = 0; i <= Math.pow(operatorNumber, parts.size() - 1); i++) {
      var operators = addLeadingZeroes(convertIntToBase(i, operatorNumber), parts.size() - 1).split("");
      var result = calculate(operators, parts);
      if (result.equals(key)) return true;
    }
    return false;
  }

  public long part1() {
    var counter = 0L;
    for (Map.Entry<Long, List<Long>> entry : requests.entrySet()) {
      if (canCreateResult(entry.getKey(), entry.getValue(), 2)) {
        counter += entry.getKey();
      }
    }
    return counter;
  }

  public long part2() {
    var counter = 0L;
    for (Map.Entry<Long, List<Long>> entry : requests.entrySet()) {
      if (canCreateResult(entry.getKey(), entry.getValue(), 3)) {
        counter += entry.getKey();
      }
    }
    return counter;
  }
}
