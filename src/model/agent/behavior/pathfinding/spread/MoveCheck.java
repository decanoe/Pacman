package model.agent.behavior.pathfinding.spread;

import model.agent.AgentAction;
import model.agent.AgentAction.Direction;
import model.agent.behavior.pathfinding.PathFindingBehavior.Cell;
import model.maze.Maze;

/** A pathfinding module that determines which movement can be done */
public class MoveCheck {
    /**
     * wether the cell can spread in the given direction
     * @param cell the cell to check
     * @param d the direction to check
     * @param maze the maze in wich check
     * @return True if the cell can spread, False else
     */
    public boolean can_spread(Cell cell, Direction d, Maze maze) {
        return (maze.isLegalMove(cell.position, new AgentAction(d)));
    }
}
