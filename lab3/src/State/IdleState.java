package State;

import Elevator.IElevator;

public class IdleState implements IState {

    public IState NextState(IElevator elevator) {
        return this;
    }

    public void Execute(IElevator elevator) {
        System.out.printf("ElevatorID: %-2d State: %-30s Floor: %-2d TargetFloor: %-2d \n", elevator.GetID(),
                elevator.GetCurrentState().getClass(),
                elevator.GetCurrentFloor(),
                elevator.GetTargetFloor());
    }
    
}
