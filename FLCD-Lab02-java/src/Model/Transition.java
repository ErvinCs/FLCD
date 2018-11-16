package Model;

public class Transition {
    private String fromState;
    private String toState;
    private String path;

    public Transition() {
        this.fromState = "";
        this.toState = "";
        this.path = "";
    }

    public Transition(String fromState, String toState, String path) {
        this.fromState = fromState;
        this.toState = toState;
        this.path = path;
    }

    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }

    public String getPath() {
        return path;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        String output = "ς(" + fromState + "," + path + ")=" + toState;
        return output;
    }
}
