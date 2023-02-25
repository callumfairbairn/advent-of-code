import java.util.*;

public class Day04 {

    private static final List<String> testInput = UtilsKt.readInput("Day04_test");
    private static final List<String> realInput = UtilsKt.readInput("Day04");

    private static List<int[]> parseLine(String line) {
        String[] split = line.split(",");
        int[] firstPair = Arrays.stream(split[0].split("-")).mapToInt(Integer::parseInt).toArray();
        int[] secondPair = Arrays.stream(split[1].split("-")).mapToInt(Integer::parseInt).toArray();
        return Arrays.asList(firstPair, secondPair);
    }

    private static boolean aContainsB(int[] pairA, int[] pairB) {
        return pairA[0] <= pairB[0] && pairA[1] >= pairB[1];
    }

    private static boolean aOverlapsB(int[] pairA, int[] pairB) {
        Boolean leftAOverlapsWithB = pairA[0] >= pairB[0] && pairA[0] <= pairB[1];
        Boolean rightAOverlapsWithB = pairA[1] >= pairB[0] && pairA[1] <= pairB[1];
        return leftAOverlapsWithB || rightAOverlapsWithB;
    }

    private static int part1(List<String> input) {
        int counter = 0;
        for (String line : input) {
            List<int[]> pairs = parseLine(line);
            if (aContainsB(pairs.get(0), pairs.get(1)) || aContainsB(pairs.get(1), pairs.get(0))) {
                counter++;
            }
        }
        return counter;
    }

    private static int part2(List<String> input) {
        int counter = 0;
        for (String line : input) {
            List<int[]> pairs = parseLine(line);
            if (aOverlapsB(pairs.get(0), pairs.get(1)) || aOverlapsB(pairs.get(1), pairs.get(0))) {
                counter++;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(part1(testInput));
        System.out.println(part1(realInput));
        System.out.println(part2(testInput));
        System.out.println(part2(realInput));
    }
}