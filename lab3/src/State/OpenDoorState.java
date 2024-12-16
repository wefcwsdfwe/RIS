package State;

import Elevator.IElevator;

public class OpenDoorState implements IState {

    public IState NextState(IElevator elevator) {
        return new CloseDoorState();
    }

    public void Execute(IElevator elevator) {
        elevator.OpenDoor();
    }

}
