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

    private Card attackingCard;
    private final MatchModel matchModel;
    private final MatchUI ui;

    public MatchController() throws IOException, ParseException {
        this.ui = new MatchUI(this);
        this.boardPanel = new BoardPanel(ImageIO.read(getClass().getClassLoader().getResourceAsStream("BoardImage.png")));
        this.matchModel = new MatchModel(boardPanel);

        setupButtons();

        setupListeners();

        setupUI();

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
                    if(matchModel.getState().equals(State.Position) && selectedCard!=null && selectedCard.getPosition().equals(CardModel.Position.hand)){
                        matchModel.getPlayingPlayer().getHand().remove(selectedCard);
                        String[] options = {"Attack", "Defense", "Covered defense"};
                        int choice = JOptionPane.showOptionDialog(null, "Select how you want to position your card", "Card positon", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                        switch (choice){
                            case 0:
                                selectedCard.setPosition(CardModel.Position.attack);
                                break;
                            case 1:
                                selectedCard.setPosition(CardModel.Position.uncovered_defense);
                                break;
                            case 2:
                                selectedCard.setPosition(CardModel.Position.covered_defense);
                                break;
                        }
                        matchModel.getPlayingPlayer().getBoard().add(selectedCard);
                        //This method sets the listener used to select the card that is actively attacking an opponent card
                        setupAttackerListener(selectedCard);
                        //This method sets the listener used to select the card that is being attacked
                        setupAttackedListener(selectedCard);
                        boardContainerPanel.remove(placeholder);
                        selectedCard = null;
                        displayPlayingPlayer();
                        dehilightBoard();
                        revalidate();
                        repaint();
                    }
                }
            });
            boardContainerPanel.add(placeholder);
            this.boardPanel.setPlayingPlayerLifePoints(Integer.toString(matchModel.getPlayingPlayer().getLifePoints()));
            this.boardPanel.setPlayingPlayerUsername(matchModel.getPlayingPlayer().getUsername());

            this.boardPanel.setNotPlayingPlayerLifePoints(Integer.toString(matchModel.getNotPlayingPlayer().getLifePoints()));
            this.boardPanel.setNotPlayingPlayerUsername(matchModel.getNotPlayingPlayer().getUsername());
        }


        for(Object card: matchModel.getNotPlayingPlayer().getBoard()){
            Card castedCard = (Card)card;
            enemyContainerPanel.add(castedCard);
        }

        revalidate();
        repaint();
    }

    public void setupButtons(){
        JButton drawButton = new JButton("Draw card");
        drawButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState().equals(State.Draw)){
                    Card card = getMatchModel().getPlayingPlayer().drawCard();
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
                }else{
                    JOptionPane.showMessageDialog(null, "You are not in the drawing phase");
                }
            }
        });
        this.add(drawButton);

        JButton attackButton = new JButton("Attack");
        this.add(attackButton);
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState().equals(State.Position)){
                    matchModel.setState(State.Attack);
                    System.out.println("Attack phase");
                }
            }
        });

        JButton endButton = new JButton("End turn");
        this.add(endButton);
        endButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(matchModel.getState()!=State.Draw){
                    JOptionPane.showMessageDialog(null, "Pass the device to player " + matchModel.getNotPlayingPlayer().getUsername());
                    attackingCard = null;
                    selectedCard = null;
                    matchModel.setState(State.Draw);
                    matchModel.changePlayingPlayer();
                    displayPlayingPlayer();
                    revalidate();
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(null, "You must draw before ending your turn");

                }
            }
        });
    }

    public void setupListeners(){
        //MANAGE CRD POSITIONING FOR PLAYER 1
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
                        }
                    }
                }
            });
        }

        //MANAGE CRD POSITIONING FOR PLAYER 2
        //TODO REFACTOR WITH FUNCTION
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

    }

    public void setupUI(){
        handContainerPanel.setOpaque(false);
        handContainerPanel.setMaximumSize(new Dimension(1280, 720/3));
        boardPanel.add(handContainerPanel);
        boardContainerPanel.setOpaque(false);
        boardPanel.add(boardContainerPanel);
        enemyContainerPanel.setOpaque(false);
        boardPanel.add(enemyContainerPanel);

        this.add(boardPanel);
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

    public void setupAttackerListener(Card card){
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(matchModel.getState().equals(State.Attack) && card.getPosition().equals(CardModel.Position.attack)){
                    attackingCard = card;
                    hilightEnemies();
                    repaint();
                    revalidate();
                }
            }
        });
    }

    private void setupAttackedListener(Card card){
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                //TODO CARD ATTACKS
                if(card.getBounds().contains(e.getX(), e.getY())){
                    if(matchModel.getState().equals(State.Attack) && attackingCard != null && attackingCard.getPosition().equals(CardModel.Position.attack )){
                        deHilightEnemies();
                        if(card.getPosition().equals(CardModel.Position.attack)){
                            System.out.print(attackingCard.getName() +  " is attacking " + card.getName());
                            if(attackingCard.getAtk()>=card.getAtk()){
                                matchModel.getNotPlayingPlayer().getBoard().remove(card);
                                enemyContainerPanel.remove(card);
                                matchModel.getNotPlayingPlayer().removeLifePoints(attackingCard.getAtk()-card.getAtk());
                                attackingCard = null;
                                repaint();
                                revalidate();
                                System.out.println(matchModel.getNotPlayingPlayer().getUsername() + " now has " + matchModel.getNotPlayingPlayer().getLifePoints() + " Life Points");
                            }
                        }
                    }
                }
            }
        });
    }

}
