package State;

import Maps.MoveMap;

import java.util.Optional;

import Elevator.IElevator;

public class CloseDoorState implements IState {

    public IState NextState(IElevator elevator) {

        elevator.NextTargetFloor();

        IState nextState = Optional.ofNullable(elevator.GetTargetFloor())
                .map(targetFloor -> MoveMap.map.get(Math.signum(targetFloor - elevator.GetCurrentFloor())))
                .orElseGet(IdleState::new);

        return nextState;

    }

    public void Execute(IElevator elevator) {
        elevator.CloseDoor();
    }

}
