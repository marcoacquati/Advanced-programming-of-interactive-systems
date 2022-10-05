package advUI.yugioh;

import advUI.yugioh.Card.Card;
import advUI.yugioh.match.Match;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application extends JFrame {

    public Application() throws IOException, ParseException {
        initUI();
    }

    private void initUI() throws IOException, ParseException {
        Board board = new Board(ImageIO.read(getClass().getClassLoader().getResourceAsStream("BoardImage.png")));
        Match match = new Match(board);
        for(Object card: match.getPlayer1().getHand()){
            int posx = 0;
            int posy = 0;
            ((Card) card).setLocation(posx, posy);
            board.add((Card)card);
            posx+=100;
            posy+=100;
        }
        for(Object card: match.getPlayer2().getHand()){
            board.add((Card)card);
        }

        this.add(board, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1280, 720));

        setTitle("Yu-Gi-Oh!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true);
        pack();
    }

}
