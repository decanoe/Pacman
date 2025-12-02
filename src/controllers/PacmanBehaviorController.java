package controllers;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.PacmanGame;
import model.agent.Agent;
import model.agent.PositionAgent;
import model.agent.behavior.Behavior;
import model.agent.behavior.BehaviorFactory;
import model.agent.behavior.DualDistanceBehavior;
import model.agent.behavior.pathfinding.PathFindingBehavior;
import model.maze.Maze.EntityType;
import view.ViewPacmanCommandBehavior;
import view.ViewPacmanGame;

public class PacmanBehaviorController {
    ViewPacmanGame view;
    ViewPacmanCommandBehavior commands;

    PacmanGame game;

    PositionAgent target = null;

    public PacmanBehaviorController(PacmanGame game, ViewPacmanGame view) {
        this.view = view;
        this.game = game;
        addPropertyChangeListener("behavior_target", view);

        commands = new ViewPacmanCommandBehavior(game, this);
    }

    /** support for PropertyChangeListeners */
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
    /**
     * adds a PropertyChangeListener to this game
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    /**
     * adds a PropertyChangeListener to this game for a specific property
     * @param property the property to listen, as a string
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        support.addPropertyChangeListener(property, listener);
    }

    public Agent getAgent() {
        for (Agent agent : game.agents) {
            if (agent.get_position() == target) return agent;
        }
        return null;
    } 
    public int getAgentIndex() {
        int i = 0;
        for (Agent agent : game.agents) {
            if (agent.get_position() == target) return i;
            i++;
        }
        return -1;
    } 
    public void SelectNextAgent() {
        target = game.agents.get((getAgentIndex() + 1) % game.agents.size()).get_position();

        support.firePropertyChange("behavior_target", null, target);
    }
    public void UnSelectAgent() {
        target = null;
        support.firePropertyChange("behavior_target", null, null);
    }
    public void SelectPreviousAgent() {
        int index = getAgentIndex();
        if (index == -1) index = 0;
        target = game.agents.get((index + game.agents.size() - 1) % game.agents.size()).get_position();

        support.firePropertyChange("behavior_target", null, target);
    }

    public void ChangeBehavior(Behavior old_behavior, BehaviorFactory.BehaviorType new_behavior) {
        if (old_behavior.get_behavior_type() == new_behavior) return;

        old_behavior.replace_this_behavior(BehaviorFactory.create_behavior(new_behavior));
        support.firePropertyChange("behavior_target", null, target);
    }
    public void ChangeBehavior(PathFindingBehavior old_behavior, BehaviorFactory.PathfindingPreset preset) {
        if (old_behavior.preset == preset) return;
        old_behavior.replace_this_behavior(BehaviorFactory.create_pathfinding(preset));
        support.firePropertyChange("behavior_target", null, target);
    }
    public void ChangeEntityTarget(DualDistanceBehavior behavior, EntityType new_entity) {
        if (behavior.get_entity_target() == new_entity) return;

        behavior.set_entity_target(new_entity);
        support.firePropertyChange("behavior_target", null, target);
    }
    public void ChangeEntityDistance(DualDistanceBehavior behavior, float new_distance) {
        if (behavior.get_distance_threashold() == new_distance) return;

        behavior.set_distance_threashold(new_distance);
        support.firePropertyChange("behavior_target", null, target);
    }

    public ViewPacmanGame get_view() { return view; }
}
