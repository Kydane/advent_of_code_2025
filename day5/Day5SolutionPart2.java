import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day5SolutionPart2 {

    private static class Range {
        private long start = 0;
        private long end = 0;
        private boolean hasFreshStuff = false;

        public Range() {
        }

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean isInRange(long testableNumber){
            return testableNumber >=start && testableNumber <=end;
        }

        public long getTotalNumberOfIds(){
            return end - start +1;
        }
        public boolean isHasFreshStuff() {
            return hasFreshStuff;
        }

        public void setHasFreshStuff(boolean hasFreshStuff) {
            this.hasFreshStuff = hasFreshStuff;
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
                    range.setHasFreshStuff(true);
                }
            }
            if (!spoiled){
                freshIngredients++;
            }
        }
        return freshIngredients;
    }

    private static List<Range> optimalRanges(List<Range> rangesContainingFreshStuff) {
        LinkedList<Range> optimal = new LinkedList<>();
        for (Range range : rangesContainingFreshStuff){
            Iterator<Range> iter = optimal.iterator();
            boolean rangeFixed = false;
            int index = -1;
            if (optimal.isEmpty()){
                optimal.add(range);
                continue;
            }
            while (iter.hasNext()){
                index++;
                Range currRange = iter.next();
                // If the range we are looking at it overlaps, expand it to be this range's start and that range's end, whatever's bigger.
                if (currRange.isInRange(range.start) || currRange.isInRange(range.end)){
                    currRange.start = Long.min(currRange.start, range.start);
                    currRange.end = Long.max(currRange.end, range.end);
                    rangeFixed = true;
                    break;
                }

                //If the range we are looking is higher but doesn't overlap, lets just add this range here and iterate
                if (currRange.end > range.start ){
                    optimal.add(index, range);
                    rangeFixed = true;
                    break;
                }
            }
            if (!rangeFixed)
                optimal.add(range);

        }
        return optimal;
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pair<List<Range>, List<Long>> input = readInput();
        long output = countFreshIngredients(input);
        System.out.printf("Output: %s%n%n", output);

        AtomicLong totalFreshIds = new AtomicLong();
        List<Range> rangesContainingFreshStuff = input.first.stream().collect(Collectors.toCollection(ArrayList::new));

        System.out.printf("Starting optimization at %s lists%n", rangesContainingFreshStuff.size());

        List<Range> optimalRangeList = optimalRanges(rangesContainingFreshStuff);
        int lastOptimalList = 0;
        int optionalListAmount = optimalRangeList.size();

        while (lastOptimalList != optionalListAmount){
            System.out.printf("Optimizing %s lists", optimalRangeList.size());
            lastOptimalList = optimalRangeList.size();
            optimalRangeList=optimalRanges(optimalRangeList);
            optionalListAmount = optimalRangeList.size();
            System.out.printf(".....optimized to %s lists%n%n", optimalRangeList.size());
        }

        optimalRangeList.stream().forEach(range -> {
            totalFreshIds.addAndGet(range.getTotalNumberOfIds());
        });

        System.out.printf("Total Fresh Ids: %s%nA total of %s lists to optimal ", totalFreshIds, optimalRangeList.size());

    }




}