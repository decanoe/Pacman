package model.agent.behavior;

import model.agent.AgentAction;
import model.agent.AgentAction.Direction;
import model.maze.Maze;

/**
 * a behavior class that stops any movements
 */
public class StopBehavior extends Behavior {
    @Override
    public AgentAction get_action(Maze maze, boolean ghost_scared) {
        return new AgentAction(Direction.STOP);
    }

    public BehaviorFactory.BehaviorType get_behavior_type() { return BehaviorFactory.BehaviorType.Stop; }
}
