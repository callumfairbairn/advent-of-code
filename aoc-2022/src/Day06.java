import java.util.*;

class Window {
    private final int size;
    List<String> items;
    Window(int size) {
        this.size = size;
        this.items = new ArrayList<>(size);
    }

    public void add(String item) {
        // if items is not full, add item
        if (items.size() < size) {
            items.add(item);
            return;
        }

        // shift items left and add new item to the end
        for (int i = 0; i < size - 1; i++) {
            String nextItem = items.get(i + 1);
            items.set(i, nextItem);
        }
        items.set(size - 1, item);
    }

    public boolean containsRepeats() {
        Set<String> set = new HashSet<>(this.items);
        return set.size() == size;
    }
}

public class Day06 {

    private static final List<String> testInput = UtilsKt.readInput("Day06_test");
    private static final List<String> realInput = UtilsKt.readInput("Day06");

    private static int getMarkerPosition(String input, int windowSize) {
        Window window = new Window(windowSize);

        List<String> characters = List.of(input.split(""));
        for (int i = 0; i < characters.size(); i++) {
            window.add(characters.get(i));
            if (window.containsRepeats()) {
                return i + 1;
            }
        }

        return 0;
    }

    private static int part1(List<String> input) {
        return getMarkerPosition(input.get(0), 4);
    }

    private static int part2(List<String> input) {
        return getMarkerPosition(input.get(0), 14);
    }

    public static void main(String[] args) {
        System.out.println(part1(testInput));
        System.out.println(part1(realInput));
        System.out.println(part2(testInput));
        System.out.println(part2(realInput));
    }
}