public class Node {
    private String name;

    boolean goesToOut = false;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGoesToOut() {
        return goesToOut;
    }

    public void setGoesToOut(boolean goesToOut) {
        this.goesToOut = goesToOut;
    }
}
