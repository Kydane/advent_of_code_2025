import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9SolutionPart2 {

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

        List<Coordinate> coordinateList = input.stream().map(integerIntegerPair -> new Coordinate(integerIntegerPair.first, integerIntegerPair.second)).collect(Collectors.toCollection(ArrayList::new));
        coordinateList.add(new Coordinate(input.get(0).first, input.get(0).second));
        GeometryFactory gf = new GeometryFactory();

        Polygon polygon = gf.createPolygon(coordinateList.toArray(Coordinate[]::new));
        buildRectangleMap(input);

        Set<Rectangle> removalSet = new HashSet<Rectangle>();
        for (Rectangle rectangle : rectangleSet){
            System.out.printf("\"Given Rectangle: %s,%s: %s,%s", rectangle.givenCorner1X, rectangle.givenCorner1Y, rectangle.givenCorner2X, rectangle.givenCorner2Y);
            boolean contains = polygon.covers(gf.createPolygon(new Coordinate[]{new Coordinate(rectangle.topLeftX,rectangle.topLeftY),
                    new Coordinate(rectangle.bottomLeftX, rectangle.bottomLeftY),
                    new Coordinate(rectangle.bottomRightX, rectangle.bottomRightY),
                    new Coordinate(rectangle.topRightX, rectangle.topRightY),
                    new Coordinate(new Coordinate(rectangle.topLeftX,rectangle.topLeftY))}));
            System.out.printf(".........%s%n", contains);
            if (!contains){
                removalSet.add(rectangle);
            }
        }

        rectangleSet.removeAll(removalSet);
        List<Rectangle> rectangleList = rectangleSet.stream().sorted((o1, o2) -> Long.compare(o1.getArea(), o2.getArea())).collect(Collectors.toCollection(ArrayList::new));
        for (Rectangle rectangle: rectangleList){
            System.out.printf("Given Rectangle: %s,%s : %s,%s has area %s%n", rectangle.givenCorner1X, rectangle.givenCorner1Y, rectangle.givenCorner2X, rectangle.givenCorner2Y, rectangle.getArea());


        }

        System.out.printf("%nOutput: %s%n ", rectangleList.getLast().getArea());

    }


}