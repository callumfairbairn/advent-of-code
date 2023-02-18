import org.jetbrains.annotations.NotNull;

import java.util.*;

class MinAndIndex {
    public int min;
    public int index;

    public MinAndIndex(int min, int index) {
        this.min = min;
        this.index = index;
    }
}

class CalorieCache {
    int[] store;

    public CalorieCache(int size) {
        this.store = new int[size];
        Arrays.fill(this.store, 0);
    }

    private MinAndIndex min() {
        MinAndIndex minAndIndex = new MinAndIndex(Integer.MAX_VALUE, 0);
        for (int i = 0; i < this.store.length; i++) {
            int current = this.store[i];
            if (current < minAndIndex.min) {
                minAndIndex = new MinAndIndex(current, i);
            }
        }
        return minAndIndex;
    }

    public void add(int newValue) {
        MinAndIndex minAndIndex = min();
        if (newValue > minAndIndex.min) {
            this.store[minAndIndex.index] = newValue;
        }
    }

    public int sum() {
        return Arrays.stream(this.store).sum();
    }
}

public class Day01 {

    private static final List<String> testInput = UtilsKt.readInput("Day01_test");
    private static final List<String> realInput = UtilsKt.readInput("Day01");

    private static int getAnswer(@NotNull List<String> input, int sizeOfCache) {
        CalorieCache calorieCache = new CalorieCache(sizeOfCache);
        ArrayList<String> currentCalories = new ArrayList<>();

        for (String calorie: input) {
            if (Objects.equals(calorie, "")) {
                int currentCalorieSum = currentCalories.stream().mapToInt(Integer::parseInt).sum();
                calorieCache.add(currentCalorieSum);
                currentCalories = new ArrayList<>();
            } else {
                currentCalories.add(calorie);
            }
        }

        return calorieCache.sum();
    };

    private static int part1(List<String> input) {
        return getAnswer(input, 1);
    }

    private static int part2(List<String> input) {
        return getAnswer(input, 3);
    }

    public static void main(String[] args) {
        System.out.println(part1(testInput));
        System.out.println(part1(realInput));
        System.out.println(part2(testInput));
        System.out.println(part2(realInput));
    }
}