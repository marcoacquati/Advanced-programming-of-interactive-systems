package advUI.yugioh.match;
import advUI.yugioh.Board;
import advUI.yugioh.Card.CardModel;
import advUI.yugioh.Player.Player;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Match{
    enum State{
        Start, Draw, Position, Attack, CheckPoints, End;
        private static final State[] states = State.values();
        private static State getState(int i){
            return State.states[i];
        }
    }

    private State state;
    private Player playingPlayer;
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    public Match(Board board) throws IOException, ParseException {
        state = State.Start;
        player1.initializeDeck();
        player2.initializeDeck();
        playingPlayer = player1;
    }

    public Player getPlayingPlayer(){
        return playingPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}