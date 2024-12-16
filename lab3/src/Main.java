import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Elevator.Elevator;
import Elevator.IElevator;
import Manager.ElevatorManager;
import Manager.IElevatorManager;
import Maps.ElevatorMap;
import Maps.IElevatorMap;
import State.IdleState;

public class Main {
        public static void main(String[] args) {

                int floorsNum = 10;

                List<int[]> calls = new ArrayList<>(Arrays.asList(
                                new int[] { 1, 3 },
                                new int[] { 2, 4 },
                                new int[] { 2, 10 }));

                IElevatorMap elevatorMap = new ElevatorMap(floorsNum);

                IElevator elevatorFirst = new Elevator(1, new IdleState(), 1, elevatorMap);
                IElevator elevatorSecond = new Elevator(2, new IdleState(), 3, elevatorMap);

                IElevatorManager manager = new ElevatorManager(elevatorMap, elevatorFirst, elevatorSecond);

                manager.Run(calls);
        }
}
