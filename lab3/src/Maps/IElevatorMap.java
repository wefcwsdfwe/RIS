package Maps;

import State.IState;

public interface IElevatorMap {
    IState GetVerifiedState(int currentFloor, IState currentState);
}
