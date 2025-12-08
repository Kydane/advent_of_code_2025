import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class Circuit {

    Set<JunctionBox> junctionBoxes;

    public Circuit() {
        junctionBoxes = new HashSet<>();
    }


    public Circuit(Set<JunctionBox> boxes) {
        this.junctionBoxes = boxes;
    }

    public Set<JunctionBox> getJunctionBoxes() {
        return junctionBoxes;
    }

    public void setJunctionBoxes(Set<JunctionBox> junctionBoxes) {
        this.junctionBoxes = junctionBoxes;
    }

    public void addJunctionBox(JunctionBox box){
        this.junctionBoxes.add(box);
    }

    public void addJunctionBox(Set<JunctionBox> junctionBoxes) {
        this.junctionBoxes.addAll(junctionBoxes);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Circuit circuit = (Circuit) o;
        return Objects.equals(junctionBoxes, circuit.junctionBoxes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(junctionBoxes);
    }


}
