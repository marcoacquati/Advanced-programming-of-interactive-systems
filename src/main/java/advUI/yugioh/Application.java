package advUI.yugioh;

import advUI.yugioh.Card.Card;
import advUI.yugioh.Player.Player;
import advUI.yugioh.match.Match;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application extends JFrame {
    private Board board;
    private Match match;

    public Application() throws IOException, ParseException {
        initUI();
    }

    private void initUI() throws IOException, ParseException {
        startMatch();

        this.add(board, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1280, 720));

        setTitle("Yu-Gi-Oh!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        pack();
    }

    public void startMatch() throws IOException, ParseException {
        this.board = new Board(ImageIO.read(getClass().getClassLoader().getResourceAsStream("BoardImage.png")));
        this.match = new Match(board);
        for(Object card: match.getPlayer1().getHand()){
            board.add((Card)card);
            board.getLayout().putConstraint(SpringLayout.WEST, (Card)card, 10, SpringLayout.WEST, board);
            board.getLayout().putConstraint(SpringLayout.SOUTH, (Card)card, -100, SpringLayout.SOUTH, board);
        }
        //for(Object card: match.getPlayer2().getHand()){
        //    board.add((Card)card);
        //}
    }

    public void drawPhase(Player playingPlayer){
        //TODO
    }

    public void positionPhase(Player playingPlayer){
        //TODO
    }

    public void attackPhase(Player playingPlayer){
        //TODO
    }

    public boolean checkPhase(Player player1, Player player2){
        //TODO
        return true;
    }

    public void endMatch(){
        //TODO
    }


}
