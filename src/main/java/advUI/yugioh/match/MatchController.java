package advUI.yugioh.match;

import advUI.yugioh.BoardPanel;
import advUI.yugioh.Card.Card;
import advUI.yugioh.Card.CardModel;
import advUI.yugioh.Placeholder;
import advUI.yugioh.Player.Player;
import advUI.yugioh.State;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MatchController extends JPanel {
    private BoardPanel boardPanel;
    private JPanel handContainerPanel = new JPanel(new FlowLayout());
    private JPanel boardContainerPanel = new JPanel(new FlowLayout());
    private JPanel enemyContainerPanel = new JPanel(new FlowLayout());
    private Card selectedCard;
    private final MatchModel matchModel;
    private final MatchUI ui;

    public MatchController() throws IOException, ParseException {
        this.ui = new MatchUI(this);
        this.boardPanel = new BoardPanel(ImageIO.read(getClass().getClassLoader().getResourceAsStream("BoardImage.png")));
        this.matchModel = new MatchModel(boardPanel);

        JButton drawButton = new JButton("Draw card");
        this.add(drawButton);
        drawButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState().equals(State.Draw)){
                    Card card = getMatchModel().getPlayingPlayer().drawCard();
                    System.out.println(card);
                    card.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            if(matchModel.getState().equals(State.Position)){
                                if(matchModel.getPlayer2().getBoard().size()<5) {
                                    selectedCard = card;
                                    hilightBoard();
                                    repaint();
                                    revalidate();
                                }
                            }
                        }
                    });
                    displayPlayingPlayer();
                    matchModel.setState(State.Position);
                }
            }
        });

        JButton attackButton = new JButton("Attack");
        this.add(attackButton);
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState().equals(State.Position)){
                    matchModel.setState(State.Attack);
                }
            }
        });

        for(Card card : this.matchModel.getPlayer1().getHand()){
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(matchModel.getState().equals(State.Position)){
                        if(matchModel.getPlayer1().getBoard().size()<5) {
                            selectedCard = card;
                            hilightBoard();
                            repaint();
                            revalidate();
                        }else{
                            JOptionPane.showConfirmDialog(null, "You have already positioned 5 cards");
                            System.out.println("I'm here");
                        }
                    }
                }
            });
        }

        for(Card card : this.matchModel.getPlayer2().getHand()){
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(matchModel.getState().equals(State.Position)){
                        if(matchModel.getPlayer2().getBoard().size()<5) {
                            selectedCard = card;
                            hilightBoard();
                            repaint();
                            revalidate();
                        }
                    }
                }
            });
        }

        for(Card card : this.matchModel.getPlayer1().getBoard()){
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(matchModel.getState().equals(State.Attack)){
                        selectedCard = card;
                        hilightEnemies();
                        repaint();
                        revalidate();
                    }
                }
            });
        }

        for(Card card : this.matchModel.getPlayer2().getBoard()){
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(matchModel.getState().equals(State.Attack)){
                        selectedCard = card;
                        hilightEnemies();
                        repaint();
                        revalidate();
                    }
                }
            });
        }

        JButton endButton = new JButton("End turn");
        this.add(endButton);
        endButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState()!=State.Draw){
                    matchModel.setState(State.Draw);
                    matchModel.changePlayingPlayer();
                    displayPlayingPlayer();
                    revalidate();
                    repaint();
                }
            }
        });

        for(Object card : this.enemyContainerPanel.getComponents()){
            Card castedCard = (Card)card;
            castedCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if(matchModel.getState().equals(State.Attack)){
                        deHilightEnemies();
                        //TODO MANAGE CARD ATTACKS
                    }
                }
            });
        }

        handContainerPanel.setOpaque(false);
        handContainerPanel.setMaximumSize(new Dimension(1280, 720/3));
        boardPanel.add(handContainerPanel);
        boardContainerPanel.setOpaque(false);
        //boardContainerPanel.setPreferredSize(new Dimension(1280, 720/3));
        //boardContainerPanel.setMaximumSize(new Dimension(1280, 720/3));
        boardPanel.add(boardContainerPanel);
        enemyContainerPanel.setOpaque(false);
        //enemyContainerPanel.setPreferredSize(new Dimension(1280, 720/3));
        //enemyContainerPanel.setMaximumSize(new Dimension(1280, 720/3));
        boardPanel.add(enemyContainerPanel);
        this.add(boardPanel);

        displayPlayingPlayer();
    }


    @Override
    public void paintComponent(Graphics pen){
        this.ui.paint(this);
    }

    public void displayPlayingPlayer(){
        boardContainerPanel.removeAll();
        for(Component o : handContainerPanel.getComponents()){
            if(o.getClass().equals(Card.class)){
                handContainerPanel.remove(o);
            }
        }
        for(Object card: matchModel.getPlayingPlayer().getHand()){
            Card castedCard = (Card)card;
            handContainerPanel.add(castedCard);
        }

        for(Object card: matchModel.getPlayingPlayer().getBoard()){
            Card castedCard = (Card)card;
            boardContainerPanel.add(castedCard);
        }

        for(int i=0; boardContainerPanel.getComponents().length!=5; i++){
            Placeholder placeholder = new Placeholder();
            placeholder.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if(matchModel.getState().equals(State.Position) && selectedCard!=null){
                        matchModel.getPlayingPlayer().getHand().remove(selectedCard);
                        //TODO Manage Card position
                        matchModel.getPlayingPlayer().getBoard().add(selectedCard);
                        boardContainerPanel.remove(placeholder);
                        selectedCard = null;
                        displayPlayingPlayer();
                        dehilightBoard();
                        revalidate();
                        System.out.println("QUI");
                        repaint();
                    }
                }
            });
            boardContainerPanel.add(placeholder);
        }


        for(Object card: matchModel.getNotPlayingPlayer().getBoard()){
            Card castedCard = (Card)card;
            enemyContainerPanel.add(castedCard);
        }

        revalidate();
        repaint();
    }

    public BoardPanel getBoard() {
        return boardPanel;
    }

    public void setBoard(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public MatchModel getMatchModel() {
        return matchModel;
    }

    public MatchUI getUi() {
        return ui;
    }

    public void hilightBoard(){
        for(Object placeholder : this.boardContainerPanel.getComponents()){
            if(placeholder.getClass().equals(Placeholder.class)){
                Placeholder placeholderCasted = (Placeholder)placeholder;
                placeholderCasted.setOpaque(true);
            }
        }
    }

    public void dehilightBoard(){
        for(Object placeholder : this.boardContainerPanel.getComponents()){
            if(placeholder.getClass().equals(Placeholder.class)){
                Placeholder placeholderCasted = (Placeholder)placeholder;
                placeholderCasted.setOpaque(false);
            }
        }
    }

    public void hilightEnemies(){
        for(Card card : matchModel.getNotPlayingPlayer().getBoard()){
            card.setHighlighted(true);
        }
    }

    public void deHilightEnemies(){
        for(Card card : matchModel.getNotPlayingPlayer().getBoard()){
            card.setHighlighted(false);
        }
    }


}
