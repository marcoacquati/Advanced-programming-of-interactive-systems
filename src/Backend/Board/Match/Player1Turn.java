package Backend.Board.Match;

import Backend.Board.Turn.Turn;

public class Player1Turn implements MatchState {

    private final Match match;
    private final Turn turn;

    public Player1Turn(Match match) {
        this.match = match;
        this.turn = new Turn(match.getPlayingPlayer());
    }

    @Override
    public void observe() {
        System.out.println(turn.getPlayingPlayer().getUsername() + " is playing");
        //HERE WE MANAGE THE TURN OF PLAYER 1
        turn.observe();
    }

    @Override
    public void onEnterState() {
        System.out.println(turn.getPlayingPlayer().getUsername() + ", it's up to you...");
    }
}
