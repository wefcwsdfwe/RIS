package state;

public class State implements IState {
    private int stateNumber;

    public State() {
        stateNumber = 0;
    }

    public int getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(int stateNumber) {
        this.stateNumber = stateNumber;
    }
}
