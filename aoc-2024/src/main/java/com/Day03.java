package com;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static com.Utils.println;

public class Day03 {
  public Day03(List<String> input) {
    parseInput(input);
  }

  List<String> matches = new ArrayList<>();

  void parseInput(List<String> input) {
    var pattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
    input.forEach(line -> {
      pattern.matcher(line).results().map(MatchResult::group).forEach(match -> matches.add(match));
    });
  }

  public int part1() {
    return matches.stream().filter(match -> match.startsWith("mul")).mapToInt(match -> {
      var strippedMatch = match.substring(4,match.length() - 1);
      var split = strippedMatch.split(",");
      return Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
    }).sum();
  }

  public int part2() {
    var multiplicationEnabled = true;
    var total = 0;
    for (String match : matches) {
      if (match.equals("do()")) {
        multiplicationEnabled = true;
      } else if (match.equals("don't()")) {
        multiplicationEnabled = false;
      } else if (multiplicationEnabled) {
        var strippedMatch = match.substring(4,match.length() - 1);
        var split = strippedMatch.split(",");
        total += Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
      }
    }
    return total;
  }
}
