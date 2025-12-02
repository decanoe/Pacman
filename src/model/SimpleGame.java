package model;

/**
 * A simple implementation of the class Game
 * <p>
 * Contains support for multiple PropertyChangeListener
 * @see Game Game doc for a list of all base property
 */
public class SimpleGame extends Game {

    /**
     * Constructs a Game with a maximun number of turns and a running speed
     * <p>
     * At creation, the game isn't initialized and is not running
     * @param max_turn an int representing the maximum number of turns (-1 if not specified)
     * @param speed an double representing the time (in seconds) between turns when running
     */
    public SimpleGame(int max_turn, double speed) { super(max_turn, speed); }
    /**
     * Constructs a Game with a running speed
     * <p>
     * At creation, the game isn't initialized and is not running
     * @param speed an double representing the time (in seconds) between turns when running
     */
    public SimpleGame(double speed) { super(speed); }

    @Override
    protected void initialize_game() {
        System.out.println("SimpleGame.initialize_game()");
    }
    
    @Override
    protected void take_turn() {
        System.out.println("SimpleGame.take_turn() -> " + turn);
    }
    
    @Override
    protected boolean game_continue() {
        return true;
    }
    
    @Override
    protected void game_over() {
        System.out.println("SimpleGame.game_over() -> " + turn);
    }
    
}
