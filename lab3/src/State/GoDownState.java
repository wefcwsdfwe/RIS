package State;

import Elevator.IElevator;
import Maps.MoveMap;

public class GoDownState implements IState {

    public IState NextState(IElevator elevator) {
        IState nextState = MoveMap.map.get(Math.signum(elevator.GetTargetFloor() - elevator.GetCurrentFloor()));

        return nextState;
    }

    public void Execute(IElevator elevator) {
        elevator.GoDown();
    }
}
