import com.sun.tools.jconsole.JConsoleContext;

import java.util.*;
import java.util.stream.Collectors;

enum MovementType {
    Single,
    Multiple
}

class Instruction {
    int move;
    int from;
    int to;

    Instruction(String line) {
        String[] split = line.split(",");
        this.move = Integer.parseInt(split[0]);
        this.from = Integer.parseInt(split[1]);
        this.to = Integer.parseInt(split[2]);
    }
}

class CrateStack {
    private final ArrayList<String> crates;
    CrateStack(ArrayList<String> crates) {
        this.crates = crates;
    }
    public String top() {
        return crates.get(crates.size() - 1);
    }
    public String pop() {
        int lastIndex = crates.size() - 1;
        String lastItem = crates.get(lastIndex);
        crates.remove(lastIndex);
        return lastItem;
    }
    public void push(String item) {
        crates.add(item);
    }
    public void push(List<String> items) {
        crates.addAll(items);
    }

    public List<String> slice(int amount) {
        int toIndex = crates.size();
        int fromIndex = toIndex - amount;
        List<String> subList = new ArrayList<>(crates.subList(fromIndex, toIndex));
        crates.subList(fromIndex, toIndex).clear();
        return subList;
    }
}

abstract class Input {
    private final List<Instruction> instructions;

    Input(List<String> input) {
        this.instructions = input.stream().map(Instruction::new).collect(Collectors.toList());
    }

    abstract List<CrateStack> getStacks();

    List<Instruction> getInstructions() {
        return this.instructions;
    }
}

class TestInput extends Input {
    TestInput(List<String> input) {
        super(input);
    }

    List<CrateStack> getStacks() {
        return List.of(
                new CrateStack(new ArrayList<>(List.of("Z", "N"))),
                new CrateStack(new ArrayList<>(List.of("M", "C", "D"))),
                new CrateStack(new ArrayList<>(List.of("P")))
        );
    }
}

class RealInput extends Input {
    RealInput(List<String> input) {
        super(input);
    }

    List<CrateStack> getStacks() {
        return List.of(
                new CrateStack(new ArrayList<>(List.of("Q", "S", "W", "C", "Z", "V", "F", "T"))),
                new CrateStack(new ArrayList<>(List.of("Q", "R", "B"))),
                new CrateStack(new ArrayList<>(List.of("B", "Z", "T", "Q", "P", "M", "S"))),
                new CrateStack(new ArrayList<>(List.of("D", "V", "F", "R", "Q", "H"))),
                new CrateStack(new ArrayList<>(List.of("J", "G", "L", "D", "B", "S", "T", "P"))),
                new CrateStack(new ArrayList<>(List.of("W", "R", "T", "Z"))),
                new CrateStack(new ArrayList<>(List.of("H", "Q", "M", "N", "S", "F", "R", "J"))),
                new CrateStack(new ArrayList<>(List.of("R", "N", "F", "H", "W"))),
                new CrateStack(new ArrayList<>(List.of("J", "Z", "T", "Q", "P", "R", "B")))
        );
    }
}

public class Day05 {

    private static final TestInput testInput = new TestInput(UtilsKt.readInput("Day05_test"));
    private static final RealInput realInput = new RealInput(UtilsKt.readInput("Day05"));

    private static void moveCrates(List<CrateStack> stacks, List<Instruction> instructions, MovementType movementType) {
        for (Instruction instruction : instructions) {
            if (movementType == MovementType.Single) {
                for (int i = 0; i < instruction.move; i++) {
                    String item = stacks.get(instruction.from - 1).pop();
                    stacks.get(instruction.to - 1).push(item);
                }
            } else {
                List<String> items = stacks.get(instruction.from - 1).slice(instruction.move);
                stacks.get(instruction.to - 1).push(items);
            }
        }
    }

    private static String collectTopCrates(List<CrateStack> stacks) {
        return stacks.stream().map(CrateStack::top).collect(Collectors.joining(""));
    }

    private static String part1(Input input) {
        List<CrateStack> stacks = input.getStacks();
        moveCrates(stacks, input.getInstructions(), MovementType.Single);
        return collectTopCrates(stacks);
    }

    private static String part2(Input input) {
        List<CrateStack> stacks = input.getStacks();
        moveCrates(stacks, input.getInstructions(), MovementType.Multiple);
        return collectTopCrates(stacks);
    }

    public static void main(String[] args) {
//        System.out.println(part1(testInput));
//        System.out.println(part1(realInput));
        System.out.println(part2(testInput));
        System.out.println(part2(realInput));
    }
}