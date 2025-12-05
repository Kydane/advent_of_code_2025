import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5Solution {

    private static class Range {
        private long start = 0;
        private long end = 0;

        public Range() {
        }

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean isInRange(long testableNumber){
            return testableNumber >=start && testableNumber <=end;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }

    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    /**
     * I/O
     * @return
     * @throws IOException
     */
    private static Pair<List<Range>, List<Long>> readInput() throws IOException {
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("day5/ids.txt"))) {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("-")){
                    String[] newRange = line.split("-");
                    ranges.add(new Range(Long.parseLong(newRange[0]), Long.parseLong(newRange[1])));
                }
                else if (!line.isEmpty()){
                    ids.add(Long.parseLong(line));
                }
                line = br.readLine();
            }
        }
        System.out.printf("Read in a total of %s ranges and %s ids%n", ranges.size(), ids.size());
        return new Pair<>(ranges,ids);
    }

    private static long countFreshIngredients(Pair<List<Range>, List<Long>> input) {
        long freshIngredients = 0;
        for (Long ingredient : input.second){
            boolean spoiled = true;
            for (Range range : input.first){
                if (range.isInRange(ingredient)){
                    System.out.printf("%s is not spoiled because it is in range %s and %s%n", ingredient, range.getStart(), range.getEnd());
                    spoiled = false;
                    break;
                }
            }
            if (!spoiled){
                freshIngredients++;
            }
        }
        return freshIngredients;
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pair<List<Range>, List<Long>> input = readInput();
        long output = countFreshIngredients(input);

        System.out.printf("Output: %s%n ", output);

    }

}