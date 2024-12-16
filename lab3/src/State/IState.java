package State;

import Elevator.IElevator;

public interface IState {

    IState NextState(IElevator elevator);

    void Execute(IElevator elevator);
}
