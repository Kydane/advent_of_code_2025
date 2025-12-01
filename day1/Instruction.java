public class Instruction {
    private String direction = "";
    private int rotation = 0;

    public Instruction(String direction, int rotation) {
        this.direction = direction;
        this.rotation = rotation;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
