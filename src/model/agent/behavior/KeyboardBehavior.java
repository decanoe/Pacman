package model.agent.behavior;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controllers.KeyboardController;
import model.agent.Agent;
import model.agent.AgentAction;
import model.agent.AgentAction.Direction;
import model.maze.Maze;

/**
 * a behavior class for controlling an agent with the keyboard
 */
public class KeyboardBehavior extends Behavior implements KeyListener {
    protected Direction d = Direction.STOP;

    @Override
    public void set_agent(Agent agent) { super.set_agent(agent); KeyboardController.add_controller(this); }
    @Override
    public void clear_agent() { super.clear_agent(); KeyboardController.remove_controller(this); }

    @Override
    protected AgentAction get_action(Maze maze, boolean ghost_scared) {
        Direction current_d = agent.get_position().getDir();
        if (d == Direction.STOP && current_d != Direction.STOP) return new AgentAction(current_d);

        return new AgentAction(d);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key ==      KeyEvent.VK_LEFT)   d = Direction.WEST;
        else if (key == KeyEvent.VK_RIGHT)  d = Direction.EAST;
        else if (key == KeyEvent.VK_UP)     d = Direction.NORTH;
        else if (key == KeyEvent.VK_DOWN)   d = Direction.SOUTH;
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public BehaviorFactory.BehaviorType get_behavior_type() { return BehaviorFactory.BehaviorType.Keyboard; }
}
