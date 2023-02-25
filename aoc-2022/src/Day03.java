import java.util.*;
import java.util.stream.Collectors;


public class Day03 {

    private static final List<String> testInput = UtilsKt.readInput("Day03_test");
    private static final List<String> realInput = UtilsKt.readInput("Day03");

    private static Set<Character> stringToSet(String string) {
        return Arrays.stream(string.split(""))
                .map(item -> item.charAt(0))
                .collect(Collectors.toSet());
    };

    private static Character getCommonCharacter(Set<Character> firstSet, Set<Character> secondSet) throws Exception {
        for (Character character : firstSet) {
            if (secondSet.contains(character)) {
                return character;
            }
        }
        throw new Exception("No common character found");
    };

    private static Character getCommonCharacter2(ArrayList<Set<Character>> sets) throws Exception {
        for (Character character : sets.get(0)) {
            if (sets.get(1).contains(character)) {
                if (sets.get(2).contains(character)) {
                    return character;
                }
            }
        }
        throw new Exception("No common character found");
    }

    private static int getValue(Character character) {
        int value = character - 'a' + 1;
        if (value < 0) {
            return value + ('a' - 'A' + 26);
        }
        return value;
    };

    private static int part1(List<String> input) {
        int total = 0;

        for (String line : input) {
            int halfLengthOfLine = line.length() / 2;
            Set<Character> firstHalf = stringToSet(line.substring(0, halfLengthOfLine));
            Set<Character> secondHalf = stringToSet(line.substring(halfLengthOfLine));
            try {
                Character commonCharacter = getCommonCharacter(firstHalf, secondHalf);
                total += getValue(commonCharacter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return total;
    }

    private static int part2(List<String> input) {
        int total = 0;
        ArrayList<Set<Character>> sets = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            if ((i + 1) % 3 == 0) {
                sets.add(stringToSet(input.get(i)));
                try {
                    Character commonCharacter = getCommonCharacter2(sets);;
                    total += getValue(commonCharacter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                sets = new ArrayList<>();
            }
            else {
                sets.add(stringToSet(input.get(i)));
            }
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(part1(testInput));
        System.out.println(part1(realInput));
        System.out.println(part2(testInput));
        System.out.println(part2(realInput));
    }
}