package controllers;
import model.Game;
import view.ViewCommand;
import view.ViewSimpleGame;

public class SimpleGameController extends GameController {
    ViewSimpleGame view;
    ViewCommand commands;

    public SimpleGameController(Game game) {
        super(game);
        
        view = new ViewSimpleGame(game);
        commands = new ViewCommand(game, this);
    }
    
}
