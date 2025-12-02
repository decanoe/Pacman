package view;
import java.io.File;
import java.awt.GridLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.PacmanGameController;
import model.PacmanGame;

public class ViewPacmanCommand extends ViewCommand {
    JButton button_file;

    public ViewPacmanCommand(PacmanGame game, PacmanGameController controller) {
        super(game, controller);
    }
    protected void create_interface() {
        super.create_interface();

        button_file = new JButton("layout");

        row2_col1.setLayout(new GridLayout(3, 1));
        row2_col1.add(button_file);
    }
    protected void create_listeners() {
        super.create_listeners();

        button_file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser file_chooser = new JFileChooser();
                file_chooser.setFileFilter(new FileNameExtensionFilter("Layout files", "lay"));
                file_chooser.setCurrentDirectory(new File("./layouts"));
                int returnVal = file_chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this layout file: " + file_chooser.getSelectedFile().getName());
                    ((PacmanGameController)controller).set_layout(file_chooser.getSelectedFile().getAbsolutePath());
                    controller.init();
                    command_state.init();
                }
            }
        });
    }

    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        if (evt.getPropertyName() == "game_over") {
            controller.pause();
            command_state.game_over();
        }
    }
}
