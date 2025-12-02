package controllers;
import model.PacmanGame;
import view.ViewPacmanCommand;
import view.ViewPacmanGame;

public class PacmanGameController extends GameController {
    ViewPacmanGame view;
    ViewPacmanCommand commands;

    public PacmanGameController(PacmanGame game, ViewPacmanGame view) {
        super(game);
        
        this.view = view;
        commands = new ViewPacmanCommand(game, this);
    }
    
    public void set_layout(String layout_path) { ((PacmanGame)game).set_layout_path(layout_path); }

    public ViewPacmanGame get_view() { return view; }
}
