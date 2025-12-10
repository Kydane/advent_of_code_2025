import java.util.Objects;

public class Rectangle {
    int topLeftX, topRightX, bottomLeftX, bottomRightX;
    int topLeftY, topRightY, bottomLeftY, bottomRightY;

    int givenCorner1X, givenCorner1Y;
    int givenCorner2X, givenCorner2Y;

    private long area;

    public Rectangle() {
    }

    public Rectangle(int point1X, int point1Y, int point2X, int point2Y){
        givenCorner1X = point1X;
        givenCorner1Y = point1Y;

        givenCorner2X = point2X;
        givenCorner2Y = point2Y;

        int minX = Math.min(point1X, point2X);
        int maxX = Math.max(point1X, point2X);

        int minY = Math.min(point1Y, point2Y);
        int maxY = Math.max(point1Y,point2Y);

        topLeftY = maxY;
        topLeftX = minX;

        bottomLeftX = minX;
        bottomLeftY = minY;

        topRightX = maxX;
        topRightY = maxY;

        bottomRightX = maxX;
        bottomRightY = minY;

        area = calculateArea();
    }

    public long calculateArea(){
        return (long) getWidth() * getHeight();
    }

    public long getArea() {
        return area;
    }

    public long getWidth(){
        return (long) (topRightX + 1 - topLeftX);
    }

    public long getHeight(){
        return (long) (topRightY+1 - bottomRightY);
    }

    public boolean insideRectangle(int x, int y){
        return (x >= topLeftX && x <= topRightX) && (y >= bottomRightY && y <= topRightY);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return topLeftX == rectangle.topLeftX && topRightX == rectangle.topRightX && bottomLeftX == rectangle.bottomLeftX && bottomRightX == rectangle.bottomRightX && topLeftY == rectangle.topLeftY && topRightY == rectangle.topRightY && bottomLeftY == rectangle.bottomLeftY && bottomRightY == rectangle.bottomRightY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeftX, topRightX, bottomLeftX, bottomRightX, topLeftY, topRightY, bottomLeftY, bottomRightY);
    }
}
