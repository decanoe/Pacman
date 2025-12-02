package controllers;

import model.Game;

public abstract class GameController {
    public Game game;

    protected GameController(Game game) { this.game = game; }

    public void init() { game.pause(); game.init(); }
    public void restart() { game.init(); game.launch(); }
    public void run() { game.launch(); }
    public void pause() { game.pause(); }
    public void step() { game.step(); }
    public void set_speed(double speed) { game.set_speed(speed); }
}
