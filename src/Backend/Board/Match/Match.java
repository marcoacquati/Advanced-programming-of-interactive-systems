package Backend.Board.Match;
import Backend.Board.Player.*;
public class Match{

    private MatchState state;
    private Player playingPlayer;
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    public Match() {
        state = new StartMatch(this);
        playingPlayer = player1;
    }

    public Player getPlayingPlayer(){
        return playingPlayer;
    }

    public void timePasses() {
        if (state.getClass().equals(StartMatch.class)) {
            changeStateTo(new Player1Turn(this));
        } else if (state.getClass().equals(Player1Turn.class)) {
            changeStateTo(new CheckLifePoints(this));
            playingPlayer = player2;
        } else if (state.getClass().equals(Player2Turn.class)) {
            changeStateTo(new CheckLifePoints(this));
            playingPlayer = player1;
        } else if (state.getClass().equals(CheckLifePoints.class)) {
            if(player1.getLifePoints() <=0 || player2.getLifePoints()<=0){
                changeStateTo(new EndMatch(this));
            }
            if(playingPlayer == player1) {
                changeStateTo(new Player2Turn(this));
            }else{
                changeStateTo(new Player1Turn(this));
            }
        }

    }

    private void changeStateTo(MatchState newState) {
        this.state = newState;
        this.state.onEnterState();
    }

    @Override
    public String toString() {
        return "The match";
    }

    public void observe() {
        this.state.observe();
    }
}