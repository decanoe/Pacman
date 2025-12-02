package view;

public interface ViewCommandStates {
    public void init();
    public void restart();
    public void run();
    public void pause();
    public void step();
    public void game_over();

    public class InitState implements ViewCommandStates {
        ViewCommand command;
        InitState(ViewCommand command) {
            this.command = command;
            command.button_restart.setEnabled(false);
            command.button_pause.setEnabled(false);
            command.button_run.setEnabled(true);
            command.button_step.setEnabled(true);
        }

        public void init() {}
        public void restart() {}
        public void run() { command.set_command_states(new RunState(command)); }
        public void pause() {}
        public void step() { command.set_command_states(new PauseState(command)); }
        public void game_over() { command.set_command_states(new GameOver(command)); }
    }

    public class RunState implements ViewCommandStates {
        ViewCommand command;
        RunState(ViewCommand command) {
            this.command = command;
            command.button_restart.setEnabled(true);
            command.button_pause.setEnabled(true);
            command.button_run.setEnabled(false);
            command.button_step.setEnabled(false);
        }

        public void init() { command.set_command_states(new InitState(command)); }
        public void restart() {}
        public void run() {}
        public void pause() { command.set_command_states(new PauseState(command)); }
        public void step() {}
        public void game_over() { command.set_command_states(new GameOver(command)); }
    }

    public class PauseState implements ViewCommandStates {
        ViewCommand command;
        PauseState(ViewCommand command) {
            this.command = command;
            command.button_restart.setEnabled(true);
            command.button_pause.setEnabled(false);
            command.button_run.setEnabled(true);
            command.button_step.setEnabled(true);
        }

        public void init() { command.set_command_states(new InitState(command)); }
        public void restart() { command.set_command_states(new RunState(command)); }
        public void run() { command.set_command_states(new RunState(command)); }
        public void pause() {}
        public void step() {}
        public void game_over() { command.set_command_states(new GameOver(command)); }
    }

    public class GameOver implements ViewCommandStates {
        ViewCommand command;
        GameOver(ViewCommand command) {
            this.command = command;
            command.button_restart.setEnabled(true);
            command.button_pause.setEnabled(false);
            command.button_run.setEnabled(false);
            command.button_step.setEnabled(false);
        }

        public void init() { command.set_command_states(new InitState(command)); }
        public void restart() { command.set_command_states(new RunState(command)); }
        public void run() {}
        public void pause() {}
        public void step() {}
        public void game_over() {}
    }
}
