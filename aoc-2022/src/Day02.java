import java.util.*;

abstract class Move {
    public abstract int getValue();
    public abstract Move winsAgainst();
    public abstract Move losesAgainst();
    public abstract Move drawsAgainst();

    public int getScore(Move otherMove) {
        if (this.winsAgainst().getClass() == otherMove.getClass()) {
            return 6;
        }
        if (this.drawsAgainst().getClass() == otherMove.getClass()) {
            return 3;
        }
        return 0;
    }

    public Move getOpposingMove(Character instruction) {
        if (instruction == 'X') {
            return this.winsAgainst();
        }
        if (instruction == 'Y') {
            return this.drawsAgainst();
        }
        return this.losesAgainst();
    }
}

class Rock extends Move {
    public int getValue() {
        return 1;
    }
    public Move winsAgainst() { return new Scissors(); }
    public Move losesAgainst() { return new Paper(); }
    public Move drawsAgainst() { return new Rock(); }
}

class Paper extends Move {
    public int getValue() {
        return 2;
    }
    public Move winsAgainst() { return new Rock(); }
    public Move losesAgainst() { return new Scissors(); }
    public Move drawsAgainst() { return new Paper(); }
}

class Scissors extends Move {
    public int getValue() {
        return 3;
    }
    public Move winsAgainst() { return new Paper(); }
    public Move losesAgainst() { return new Rock(); }
    public Move drawsAgainst() { return new Scissors(); }
}

public class Day02 {

    private static final List<String> testInput = UtilsKt.readInput("Day02_test");
    private static final List<String> realInput = UtilsKt.readInput("Day02");

    static Map<Character, Move> letterToMove = Map.of(
            'A', new Rock(),
            'X', new Rock(),
            'B', new Paper(),
            'Y', new Paper(),
            'C', new Scissors(),
            'Z', new Scissors()
    );

    static int calculateScore(Move myMove, Move theirMove) {
        int myMoveScore = myMove.getValue();
        int resultScore = myMove.getScore(theirMove);
        return myMoveScore + resultScore;
    };

    private static int part1(List<String> input) {
        int total = 0;

        for (String line: input) {
            Move theirMove = letterToMove.get(line.charAt(0));
            Move myMove = letterToMove.get(line.charAt(2));
            total += calculateScore(myMove, theirMove);
        }

        return total;
    }

    private static int part2(List<String> input) {
        int total = 0;

        for (String line: input) {
            Move theirMove = letterToMove.get(line.charAt(0));
            Move myMove = theirMove.getOpposingMove(line.charAt(2));
            total += calculateScore(myMove, theirMove);
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