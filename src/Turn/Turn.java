package Turn;

import Player.Player;

public class Turn {

    private TurnState state;
    private Player playingPlayer;

    public Turn(Player playingPlayer) {
        state = new StartTurn(this);
        this.playingPlayer = playingPlayer;
    }

    public Player getPlayingPlayer(){
        return playingPlayer;
    }

    public void timePasses() {
        if (state.getClass().equals(StartTurn.class)) {
            changeStateTo(new DrawPhase(this));
        }
    }

    private void changeStateTo(TurnState newState) {
        this.state = newState;
        this.state.onEnterState();
    }

    @Override
    public String toString() {
        return "The mammoth";
    }

    public void observe() {
        this.state.observe();
    }
}
