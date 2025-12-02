package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controllers.PacmanBehaviorController;
import model.PacmanGame;
import model.agent.Agent;
import model.agent.behavior.Behavior;
import model.agent.behavior.BehaviorFactory;
import model.agent.behavior.BehaviorFactory.PathfindingPreset;
import model.agent.behavior.pathfinding.PathFindingBehavior;
import model.agent.behavior.DualDistanceBehavior;
import model.maze.Maze.EntityType;

public class ViewPacmanCommandBehavior implements PropertyChangeListener {
    PacmanBehaviorController controller;

    JFrame window;
    JPanel row1;
    JScrollPane row2_scroll;
    JPanel row2;

    JButton button_next_agent;
    JButton button_previous_agent;
    JButton button_unselect_agent;

    public ViewPacmanCommandBehavior(PacmanGame game, PacmanBehaviorController controller) {
        this.controller = controller;

        create_interface();
        create_listeners();

        game.addPropertyChangeListener("turn", this);
        controller.addPropertyChangeListener("behavior_target", this);
    }
    protected void create_interface() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Behaviors");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int dx = ge.getCenterPoint().x + ViewCommand.WINDOW_WIDTH / 2;
        int dy = ge.getMaximumWindowBounds().height - ViewCommand.WINDOW_HEIGHT;
        window.setSize(new Dimension(ge.getMaximumWindowBounds().width - dx, ViewCommand.WINDOW_HEIGHT));
        window.setLocation(dx, dy);
        
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        {
            row1 = new JPanel(new GridLayout(1, 3));
            button_previous_agent = new JButton(new ImageIcon("icons/icon_step_left.png"));
            button_unselect_agent = new JButton(new ImageIcon("icons/icon_stop.png"));
            button_next_agent = new JButton(new ImageIcon("icons/icon_step.png"));

            row1.add(button_previous_agent);
            row1.add(button_unselect_agent);
            row1.add(button_next_agent);
            row1.setMaximumSize(new Dimension(Short.MAX_VALUE, 75));
            window.add(row1);
        }
        {
            row2 = new JPanel();
            row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));
            row2_scroll = new JScrollPane(row2);
            row2_scroll.getVerticalScrollBar().setUnitIncrement(8);            
            
            window.add(row2_scroll);
        }

        window.setVisible(true);
    }
    protected void create_listeners() {
        button_previous_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.SelectPreviousAgent();
                window.requestFocus();
            }
        });
        button_next_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.SelectNextAgent();
                window.requestFocus();
            }
        });
        button_unselect_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.UnSelectAgent();
                window.requestFocus();
            }
        });
    }
    
    protected Color get_color(boolean switch_color) {
        Color c = new Color(200, 200, 255);
        if (switch_color) c = c.darker();
        return c;
    }
    protected JPanel embed_child_interface(JComponent panel, String header, boolean switch_color) {
        JPanel child = new JPanel();
        child.setBackground(get_color(switch_color));
        child.setLayout(new BoxLayout(child, BoxLayout.X_AXIS));

        child.add(Box.createRigidArea(new Dimension(5, 5)));
        JLabel label = new JLabel(header);
        label.setPreferredSize(new Dimension(50, 25));
        child.add(label);
        child.add(Box.createRigidArea(new Dimension(5, 5)));
        child.add(panel);

        return child;
    }
    protected JPanel create_behavior_interface(Behavior behavior, boolean switch_color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        panel.setBackground(get_color(switch_color));

        panel.add(new JLabel(behavior.get_behavior_type().name()));

        JComboBox<BehaviorFactory.BehaviorType> behavior_type = new JComboBox<BehaviorFactory.BehaviorType>(BehaviorFactory.BehaviorType.values());
        behavior_type.setSelectedIndex(behavior.get_behavior_type().ordinal());
        behavior_type.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.ChangeBehavior(behavior, (BehaviorFactory.BehaviorType)behavior_type.getSelectedItem());
                window.requestFocus();
            }
        });
        panel.add(behavior_type);
        panel.add(Box.createRigidArea(new Dimension(0,5)));

        if (behavior.get_behavior_type() == BehaviorFactory.BehaviorType.Dual_Distance) {
            DualDistanceBehavior ddb = (DualDistanceBehavior)behavior;

            JComboBox<EntityType> entity_type = new JComboBox<EntityType>(EntityType.values());
            entity_type.setSelectedIndex(ddb.get_entity_target().ordinal());
            entity_type.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.ChangeEntityTarget(ddb, (EntityType)entity_type.getSelectedItem());
                    window.requestFocus();
                }
            });

            panel.add(embed_child_interface(entity_type, "entity", switch_color));
            panel.add(Box.createRigidArea(new Dimension(5, 5)));

            
            JSlider distance_slider = new JSlider(0, 32, (int)ddb.get_distance_threashold());
            distance_slider.setMajorTickSpacing(4);
            distance_slider.setPaintTicks(true);
            distance_slider.setPaintLabels(true);
            distance_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                controller.ChangeEntityDistance(ddb, distance_slider.getValue());
                window.requestFocus();
            }});

            panel.add(embed_child_interface(distance_slider, "distance", switch_color));
            panel.add(Box.createRigidArea(new Dimension(5, 5)));
        }
        if (behavior.get_behavior_type() == BehaviorFactory.BehaviorType.Pathfinding) {
            PathFindingBehavior pb = (PathFindingBehavior)behavior;

            JComboBox<PathfindingPreset> preset = new JComboBox<PathfindingPreset>(PathfindingPreset.values());
            preset.setSelectedIndex(pb.preset.ordinal());
            preset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.ChangeBehavior(pb, (PathfindingPreset)preset.getSelectedItem());
                    window.requestFocus();
                }
            });

            panel.add(embed_child_interface(preset, "presets", switch_color));
            panel.add(Box.createRigidArea(new Dimension(5, 5)));
        }
        
        List<Behavior> children = behavior.get_child_behaviors();
        List<String> labels = behavior.get_child_header();
        for (int i = 0; i < children.size(); i++) {
            panel.add(embed_child_interface(create_behavior_interface(children.get(i), !switch_color), labels.get(i), switch_color));
            panel.add(Box.createRigidArea(new Dimension(5, 5)));
        }
        return panel;
    }
    protected void set_behavior_chain(Behavior root_behavior) {
        row2.removeAll();
        if (root_behavior != null) row2.add(create_behavior_interface(root_behavior, false));
        window.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "turn") {

        }
        if (evt.getPropertyName() == "behavior_target") {
            Agent agent = controller.getAgent();
            if (agent == null) set_behavior_chain(null);
            else set_behavior_chain(agent.get_behavior());
        }
    }
}
