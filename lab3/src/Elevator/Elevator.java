package Elevator;

import java.util.LinkedList;
import java.util.Queue;

import Maps.IElevatorMap;
import State.IState;

public class Elevator implements IElevator {
    public final int id;
    private IState currentState;
    private Integer currentFloor;
    private Integer targetFloor;
    private Queue<Integer> queueCalls;

    private IElevatorMap map;

    public Elevator(int id, IState state, int floor, IElevatorMap map) {
        this.id = id;
        currentState = state;
        currentFloor = floor;
        queueCalls = new LinkedList<Integer>();
        this.map = map;
    }

    public IState Next() {
        currentState.Execute(this);
        currentState = map.GetVerifiedState(currentFloor, currentState.NextState(this));

        return currentState;
    }

    public int GetID() {
        return id;
    }

    public IState GetCurrentState() {
        return currentState;
    }

    public void SetCurrentState(IState newState) {
        currentState = newState;
    }

    public Integer GetTargetFloor() {
        return targetFloor;
    }

    public void NextTargetFloor() {
        targetFloor = queueCalls.poll();
    }

    public void AddFloorInQueue(Integer target) {
        queueCalls.add(target);
    }

    public void ClearQueue() {
        queueCalls.clear();
    }

    public Integer GetCurrentFloor() {
        return currentFloor;
    }

    public void CloseDoor() {
        System.out.printf("ElevatorID: %-2d State: %-30s Floor: %-2d TargetFloor: %-2d \n", id, currentState.getClass(),
                currentFloor,
                targetFloor);
    }

    public void OpenDoor() {
        System.out.printf("ElevatorID: %-2d State: %-30s Floor: %-2d TargetFloor: %-2d \n", id, currentState.getClass(),
                currentFloor,
                targetFloor);
    }

    public void GoUp() {
        currentFloor += 1;
        System.out.printf("ElevatorID: %-2d State: %-30s Floor: %-2d TargetFloor: %-2d \n", id, currentState.getClass(),
                currentFloor,
                targetFloor);
    }

    public void GoDown() {
        currentFloor -= 1;
        System.out.printf("ElevatorID: %-2d State: %-30s Floor: %-2d TargetFloor: %-2d \n", id, currentState.getClass(),
                currentFloor,
                targetFloor);
    }
}
