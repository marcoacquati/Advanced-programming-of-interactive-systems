package Match;

import Turn.Turn;

public class Player2Turn implements MatchState {

    private final Match match;
    private final Turn turn;

    public Player2Turn(Match match) {
        this.match = match;
        this.turn = new Turn(match.getPlayingPlayer());
    }

    @Override
    public void observe() {
        System.out.println(turn.getPlayingPlayer().getUsername() + " is playing");
        //HERE WE MANAGE THE TURN OF PLAYER 2
        turn.observe();
    }

    @Override
    public void onEnterState() {
        System.out.println(turn.getPlayingPlayer().getUsername() + ", it's up to you...");
    }
}
