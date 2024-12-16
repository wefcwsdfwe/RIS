package Maps;

import java.util.HashMap;
import java.util.Map;

import State.CloseDoorState;
import State.ErrorState;
import State.GoDownState;
import State.GoUpState;
import State.IState;
import State.IdleState;
import State.OpenDoorState;

public class ElevatorMap implements IElevatorMap {

        @SuppressWarnings("rawtypes")
        Map<Class, IState> statesMap = Map.of(
                        IdleState.class, new IdleState(),
                        CloseDoorState.class, new CloseDoorState(),
                        OpenDoorState.class, new OpenDoorState(),
                        GoDownState.class, new GoDownState(),
                        GoUpState.class, new GoUpState(),
                        ErrorState.class, new ErrorState());

        @SuppressWarnings("rawtypes")
        Map<Integer, Map<Class, IState>> map;

        public ElevatorMap(int floorsNum) {
                map = new HashMap<>();

                map.put(1, Map.of(
                                IdleState.class, statesMap.get(IdleState.class),
                                CloseDoorState.class, statesMap.get(CloseDoorState.class),
                                OpenDoorState.class, statesMap.get(OpenDoorState.class),
                                GoDownState.class, statesMap.get(ErrorState.class),
                                GoUpState.class, statesMap.get(GoUpState.class)));

                map.put(floorsNum, Map.of(
                                IdleState.class, statesMap.get(IdleState.class),
                                CloseDoorState.class, statesMap.get(CloseDoorState.class),
                                OpenDoorState.class, statesMap.get(OpenDoorState.class),
                                GoDownState.class, statesMap.get(GoDownState.class),
                                GoUpState.class, statesMap.get(ErrorState.class)));
        }

        public IState GetVerifiedState(int currentFloor, IState currentState) {

                IState nextState = map.getOrDefault(currentFloor, Map.of(
                                IdleState.class, statesMap.get(IdleState.class),
                                CloseDoorState.class, statesMap.get(CloseDoorState.class),
                                OpenDoorState.class, statesMap.get(OpenDoorState.class),
                                GoDownState.class, statesMap.get(GoDownState.class),
                                GoUpState.class, statesMap.get(GoUpState.class))).get(currentState.getClass());

                return nextState;
        }
}
