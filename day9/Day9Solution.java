import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Solution {

    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    private static Set<Rectangle> rectangleSet = new HashSet<>();

    /**
     * I/O
     *
     * @return
     * @throws IOException
     */
    private static List<Pair<Integer,Integer>> readInput() throws IOException {
        List<Pair<Integer,Integer>> coordinates = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("day9/vertices.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] coordinateLine = line.split(",");
                int coordinate1 = Integer.parseInt(coordinateLine[0]);
                int coordinate2 = Integer.parseInt(coordinateLine[1]);
                Pair<Integer,Integer> coordinatePair = new Pair<Integer,Integer>(coordinate1,coordinate2);
                coordinates.add(coordinatePair);
                line = br.readLine();
            }
        }
        return coordinates;
    }


    private static void buildRectangleMap(List<Pair<Integer,Integer>> coordinates) {
        Set<String> cache = new HashSet<String>();
        for (Pair<Integer,Integer> pair1 : coordinates){
            for (Pair<Integer,Integer> pair2 : coordinates){
                String keyStr = pair1.first+"-"+pair1.second+"+"+pair2.first+"-"+pair2.second;
                String reverseKeyStr = pair2.first+"-"+pair2.second+"+"+pair1.first+"-"+pair1.second;
                if (!pair1.equals(pair2) && !cache.contains(keyStr) && !cache.contains(reverseKeyStr)){
                    rectangleSet.add(new Rectangle(pair1.first, pair1.second, pair2.first, pair2.second));
                    cache.add(keyStr);
                }
            }
        }
    }



    /**
     * Main Method for just starting it up and spitting out the answer
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<Pair<Integer,Integer>> input = readInput();
        buildRectangleMap(input);
        List<Rectangle> rectangleList = rectangleSet.stream().sorted((o1, o2) -> Long.compare(o1.getArea(), o2.getArea())).collect(Collectors.toCollection(ArrayList::new));

        for (Rectangle rectangle: rectangleList){
            System.out.printf("Given Rectangle: %s,%s : %s,%s has area %s%n", rectangle.givenCorner1X, rectangle.givenCorner1Y, rectangle.givenCorner2X, rectangle.givenCorner2Y, rectangle.getArea());
        }

        System.out.printf("%nOutput: %s%n ", rectangleList.getLast().getArea());

    }


}