package view;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controllers.GameController;
import controllers.KeyboardController;
import model.Game;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

public class ViewCommand implements PropertyChangeListener {
    public static int WINDOW_HEIGHT = 350;
    public static int WINDOW_WIDTH = 700;
    GameController controller;
    ViewCommandStates command_state;

    JFrame window;
    JPanel row1;
    JPanel row2;
    JPanel row2_col1;
    JPanel row2_col2;

    JSlider time_slider;
    JLabel turn_label;
    JLabel tps_label;
    JButton button_restart;
    JButton button_run;
    JButton button_step;
    JButton button_pause;

    public ViewCommand(Game game, GameController controller) {
        game.addPropertyChangeListener("turn", this);
        game.addPropertyChangeListener("frame_rate", this);
        game.addPropertyChangeListener("game_over", this);
        this.controller = controller;

        create_interface();
        create_listeners();
        
        command_state = new ViewCommandStates.InitState(this);
        window.addKeyListener(KeyboardController.get_KeyListener());
    }
    protected void create_interface() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("GameCommands");
        window.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        Dimension windowSize = window.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int dx = ge.getCenterPoint().x - windowSize.width / 2;
        int dy = ge.getMaximumWindowBounds().height - WINDOW_HEIGHT;
        window.setLocation(dx, dy);

        window.setLayout(new GridLayout(2, 1));
        {
            row1 = new JPanel(new GridLayout(1, 4));
            button_restart = new JButton(new ImageIcon("icons/icon_restart.png"));
            button_run = new JButton(new ImageIcon("icons/icon_run.png"));
            button_step = new JButton(new ImageIcon("icons/icon_step.png"));
            button_pause = new JButton(new ImageIcon("icons/icon_pause.png"));

            row1.add(button_restart);
            row1.add(button_run);
            row1.add(button_step);
            row1.add(button_pause);
            window.add(row1);
        }
        {
            row2 = new JPanel(new GridBagLayout());
            row2_col1 = new JPanel(new GridLayout(2, 1));
            row2_col2 = new JPanel(new GridLayout(2, 1));
            JLabel time_label = new JLabel("second per turns", JLabel.CENTER);
            time_slider = new JSlider(1, 10, 2);
            time_slider.setMajorTickSpacing(1);
            time_slider.setPaintTicks(true);
            time_slider.setPaintLabels(true);
            var dict = new Hashtable<Integer, JLabel>();
            for (int i=1; i<=10; i++) {  
                dict.put(i, new JLabel(String.valueOf(i / 10f)));
            }
            time_slider.setLabelTable(dict);
            
            row2_col1.add(time_label);
            row2_col1.add(time_slider);

            turn_label = new JLabel("waiting", JLabel.CENTER);
            tps_label = new JLabel("0 turns per second", JLabel.CENTER);
            row2_col2.add(turn_label);
            row2_col2.add(tps_label);

            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 3;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            row2.add(row2_col1, c);
            GridBagConstraints c2 = new GridBagConstraints();
            c2.weightx = 1;
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.gridx = 1;
            c2.gridy = 0;
            row2.add(row2_col2, c2);
            window.add(row2);
        }

        window.setVisible(true);
    }
    protected void create_listeners() {
        controller.set_speed(time_slider.getValue() / 10f);

        button_restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.restart();
                command_state.restart();
                window.requestFocus();
            }
        });
        button_run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.run();
                command_state.run();
                window.requestFocus();
            }
        });
        button_step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.step();
                command_state.step();
                window.requestFocus();
            }
        });
        button_pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.pause();
                command_state.pause();
                window.requestFocus();
            }
        });
        time_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                controller.set_speed(time_slider.getValue() / 10.);
                window.requestFocus();
            }
        });
    }
    
    public void set_command_states(ViewCommandStates state) { command_state = state; }
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "turn") {
            turn_label.setText("turn " + evt.getNewValue());
        }
        else if (evt.getPropertyName() == "frame_rate") {
            tps_label.setText(evt.getNewValue() + " second per turns");
        }
        else if (evt.getPropertyName() == "game_over") {
            turn_label.setText("game_over, turn " + evt.getNewValue());
        }
    }
}
