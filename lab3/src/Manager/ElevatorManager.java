package Manager;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Elevator.IElevator;
import Maps.IElevatorMap;
import Maps.MoveMap;
import State.IState;
import State.IdleState;

public class ElevatorManager implements IElevatorManager {

    private final IElevator[] elevators;
    private final IElevatorMap elevatorMap;

    public ElevatorManager(IElevatorMap elevatorMap, IElevator elevatorFirst, IElevator elevatorSecond) {
        elevators = new IElevator[] { elevatorFirst, elevatorSecond };
        this.elevatorMap = elevatorMap;
    }

    public void Run(List<int[]> queueCalls) {

        do {

            List<int[]> processedCalls = queueCalls.stream().limit(2).filter(call -> 
                Arrays.stream(elevators).filter(e -> e.GetCurrentState() instanceof IdleState)
                        .sorted(Comparator.comparingInt(e -> Math.abs(e.GetCurrentFloor() - call[0])))
                        .limit(1)
                        .peek(e -> {
                            e.AddFloorInQueue(call[0]);
                            e.AddFloorInQueue(call[1]);
                            e.NextTargetFloor();

                            IState newState = elevatorMap.GetVerifiedState(e.GetCurrentFloor(),
                                    MoveMap.map.get(Math.signum(e.GetTargetFloor() - e.GetCurrentFloor())));

                            e.SetCurrentState(newState);
                        }).count() != 0
            ).collect(Collectors.toList());

            queueCalls.removeAll(processedCalls);

            try {

                elevators[0].Next();
                elevators[1].Next();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.println();

        } while (!(elevators[0].GetCurrentState() instanceof IdleState) ||
                !(elevators[1].GetCurrentState() instanceof IdleState) || 
                queueCalls.size() != 0);
    }
}
