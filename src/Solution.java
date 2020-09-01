import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    private static List<String> result;

    public static List<String> singlePermutations(String s) {
        // Your code here!
        result = new ArrayList<>();
        int length = s.length();

        int index = length - 3;

        switch (length){
            case 0:
            case 1:
                result.add(s);
                break;
            case 2: result.add(s);
                swapLastTwo(s);
                break;
            case 3: char current = s.charAt(index);
                String temp = s.substring(index);
                permuteLastThree(current, temp);
                break;
            default:
                char c = s.charAt(index);
                String t = s.substring(index);
                permuteLastThree(c, t);
                AtomicInteger finalIndex = new AtomicInteger(index);
                AtomicInteger length1 = new AtomicInteger(3);
                IntStream.range(0, index)
                        .forEach(i -> {
                            finalIndex.set(finalIndex.decrementAndGet());
                            length1.incrementAndGet();
                            process(s, finalIndex, length1);});
        }

        return result.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private static void swapLastTwo(String string)
    {
        StringBuilder stringBuilder = new StringBuilder(string);
        int length = stringBuilder.length();
        char last = stringBuilder.charAt(length - 1);
        char secondToLast = stringBuilder.charAt(length - 2);

        stringBuilder.setCharAt(length - 1, secondToLast);
        stringBuilder.setCharAt(length - 2, last);
        result.add(stringBuilder.toString());
    }

    private static void permuteLastThree(char current, String temp)
    {
        int length = temp.length();
        StringBuilder stringBuilder = new StringBuilder(temp);

        AtomicReference<StringBuilder> finalStringBuilder = new AtomicReference<>(stringBuilder);
        IntStream.range(length - 3, length)
                .forEach(i -> {result.add(finalStringBuilder.toString());
                    String string = finalStringBuilder.toString();
                    swapLastTwo(string);
                    if (i < length - 1) {
                        finalStringBuilder.set(new StringBuilder(temp));
                        char next = finalStringBuilder.get().charAt(i + 1);
                        finalStringBuilder.get().setCharAt(length - 3, next);
                        finalStringBuilder.get().setCharAt(i + 1, current);
                    }});
    }

    private static void process (String s, AtomicInteger index, AtomicInteger length1){
        result = result.stream()
                .map(s1 -> s1 = s.charAt(index.get()) + s1)
                .parallel()
                .collect(Collectors.toList());

        for (int i = 0, resultSize = result.size(); i < resultSize; i++) {
            String s1 = result.get(i);

            IntStream.range(1, length1.get())
                    .forEach(j -> {
                        String temp1 = s1.substring(1, j + 1);
                        String temp2 = "";
                        if (j < length1.get() - 1)
                            temp2 = s1.substring(j + 1);
                        result.add(temp1 + s.charAt(index.get()) + temp2);
                    });
        }
    }

    public static void main(String[] args) {
        Solution.singlePermutations("abcde")
                .forEach(System.out::println);
    }
}