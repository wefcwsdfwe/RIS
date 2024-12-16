package Maps;

import java.util.Map;

import State.GoDownState;
import State.GoUpState;
import State.IState;
import State.OpenDoorState;

public class MoveMap {

    public static Map<Float, IState> map = Map.of(
        Math.signum(1), new GoUpState(),
        Math.signum(0), new OpenDoorState(),
        Math.signum(-1), new GoDownState()
    );

}
