package com;

import java.util.*;

public class Day05 {
  Map<String, List<String>> rules = new HashMap<>();
  List<List<String>> updates = new ArrayList<>();

  public Day05(List<String> input) {
    parseInput(input);
  }

  void parseRule(String line) {
    var split = line.split("\\|");
    rules.putIfAbsent(split[0], new ArrayList<>());
    rules.get(split[0]).add(split[1]);
  }

  void parseUpdates(String line) {
    var split = line.split(",");
    updates.add(Arrays.stream(split).toList());
  }

  void parseInput(List<String> input) {
    var parseRules = true;
    for (String line : input) {
      if (line.isEmpty()) {
        parseRules = false;
      } else if (parseRules) {
        parseRule(line);
      } else {
        parseUpdates(line);
      }
    }
  }

  int mid(List<String> update) {
    var midpoint = Math.floor((double) update.size() / 2);
    return Integer.parseInt(update.get((int) midpoint));
  }

  int valid(List<String> update) {
    var counter = 0;
    Set<String> seen = new HashSet<>();
    for (String item : update) {
      var correspondingRules = rules.get(item);
      if (correspondingRules != null && correspondingRules.stream().anyMatch(seen::contains)) {
        return counter;
      }
      seen.add(item);
      counter++;
    }
    return -1;
  }

  public int part1() {
    var total = 0;
    for (List<String> update : updates) {
      if (valid(update) == -1) {
        total += mid(update);
      }
    }
    return total;
  }

  List<String> swapIndexToLeft(List<String> update, int index) {
    var updateCopy = new LinkedList<>(update);
    Collections.swap(updateCopy, index, index - 1);
    return updateCopy;
  }

  public int part2() {
    var total = 0;

    for (List<String> update : updates) {
      var newUpdate = update;
      var validityResult = valid(update);
      if (validityResult ==  -1) continue;
      while (validityResult != -1) {
        newUpdate = swapIndexToLeft(newUpdate, validityResult);
        validityResult = valid(newUpdate);
      }
      total += mid(newUpdate);
    }
    return total;
  }
}
