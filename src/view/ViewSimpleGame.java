package view;
import javax.swing.*;
import java.awt.*;

import model.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewSimpleGame implements PropertyChangeListener {
    JFrame window;
    JLabel label;

    public ViewSimpleGame(Game game) {
        game.addPropertyChangeListener("turn", this);

        create_interface();
    }
    private void create_interface() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Game");
        jFrame.setSize(new Dimension(700, 700));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);

        label = new JLabel("waiting", JLabel.CENTER);
        jFrame.add(label);

        jFrame.setVisible(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        label.setText("turn " + evt.getNewValue());
    }
}
