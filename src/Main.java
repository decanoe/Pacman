import controllers.PacmanBehaviorController;
import controllers.PacmanGameController;
import model.PacmanGame;
import view.ViewPacmanGame;

public class Main {
    public static void main(String[] args) throws Exception {
        PacmanGame game = new PacmanGame("layouts/originalClassic_warp.lay", 100);
        game.init();
        
        ViewPacmanGame view = new ViewPacmanGame(game);
        new PacmanGameController(game, view);
        new PacmanBehaviorController(game, view);
    }
}
