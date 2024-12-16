package State;

import Elevator.IElevator;

public class ErrorState implements IState {

    public IState NextState(IElevator elevator) {
        return null;
    }

    public void Execute(IElevator elevator) {
        throw new IllegalArgumentException("Error");
    }
}
