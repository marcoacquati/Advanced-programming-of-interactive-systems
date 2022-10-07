package advUI.yugioh.match;
import advUI.yugioh.BoardPanel;
import advUI.yugioh.Player.Player;
import advUI.yugioh.State;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MatchModel {
    private State currentState;
    private Player playingPlayer;
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    public MatchModel(BoardPanel boardPanel) throws IOException, ParseException {
        this.currentState = State.Draw;
        this.player1.initializeDeck();
        this.player2.initializeDeck();
        this.playingPlayer = this.player2;
    }

    public Player getPlayingPlayer(){
        return playingPlayer;
    }

    public Player getNotPlayingPlayer(){
        if(playingPlayer.equals(this.player1))
                return player2;
        return player1;
    }

    public void changePlayingPlayer(){
        if(playingPlayer.equals(player1)){
            this.playingPlayer = this.player2;
        }else{
            this.playingPlayer = this.player1;
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public State getState(){
        return this.currentState;
    }

    public void setState(State state){
        this.currentState = state;
    }
}