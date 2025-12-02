package view;
import javax.swing.*;

import controllers.KeyboardController;

import java.awt.*;

import model.PacmanGame;
import model.agent.PositionAgent;
import model.maze.Maze;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewPacmanGame implements PropertyChangeListener {
    JFrame window;
    PanelPacmanGame panel_pacman;

    public ViewPacmanGame(PacmanGame game) {
        game.addPropertyChangeListener("maze", this);
        game.addPropertyChangeListener("turn", this);
        game.addPropertyChangeListener("ghost_scared", this);

        create_interface(game);
        window.addKeyListener(KeyboardController.get_KeyListener());
    }
    private void create_interface(PacmanGame game) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
        resize_window(compute_dimensions(game.get_maze().getSizeX(), game.get_maze().getSizeY()));

        panel_pacman = new PanelPacmanGame(game.get_maze());
        window.add(panel_pacman);
        panel_pacman.setVisible(true);
        
        window.setVisible(true);
    }
    private Dimension compute_dimensions(int maze_width, int maze_height) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int max_height = ge.getMaximumWindowBounds().height - ViewCommand.WINDOW_HEIGHT;
        int max_width = ge.getMaximumWindowBounds().width;
        
        if (max_height * maze_width / maze_height > max_width) return new Dimension(max_width, max_width * maze_height / maze_width);
        else return new Dimension(max_height * maze_width / maze_height, max_height);
    }
    private void resize_window(Dimension dimension) {
        window.setSize(dimension);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int dx = ge.getCenterPoint().x - dimension.width / 2;
        int dy = ge.getMaximumWindowBounds().height - ViewCommand.WINDOW_HEIGHT - dimension.height;
        window.setLocation(dx, dy);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "turn") {
            panel_pacman.setTurn((int)evt.getNewValue());
            panel_pacman.repaint();
        }
        else if (evt.getPropertyName() == "ghost_scared") {
            panel_pacman.setGhostsScarred((boolean)evt.getNewValue());
            panel_pacman.repaint();
        }
        else if (evt.getPropertyName() == "maze") {
            Maze maze = (Maze)evt.getNewValue();
            resize_window(compute_dimensions(maze.getSizeX(), maze.getSizeY()));
            
            panel_pacman.setMaze(maze);
            panel_pacman.setPacmans_pos(maze.getPacman_start());
            panel_pacman.setGhosts_pos(maze.getGhosts_start());
            panel_pacman.setGhosts_colors(maze.getGhosts_colors());
            panel_pacman.repaint();
        }

        else if (evt.getPropertyName() == "behavior_target") {
            panel_pacman.setTarget((PositionAgent)evt.getNewValue());
            panel_pacman.repaint();
        }
    }
}
