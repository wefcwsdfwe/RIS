package Elevator;

import State.IState;

public interface IElevator {

    public int GetID();

    public Integer GetCurrentFloor();

    public Integer GetTargetFloor();

    public void NextTargetFloor();

    public IState GetCurrentState();

    public void SetCurrentState(IState newState);

    public IState Next();

    public void AddFloorInQueue(Integer target);

    public void ClearQueue();

    public void CloseDoor();

    public void OpenDoor();

    public void GoUp();

    public void GoDown();
}
