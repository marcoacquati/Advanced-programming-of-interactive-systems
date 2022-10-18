package advUI.yugioh.match;

import advUI.yugioh.BoardPanel;
import advUI.yugioh.Card.Card;
import advUI.yugioh.Card.CardModel;
import advUI.yugioh.Card.PositionListener;
import advUI.yugioh.EndMatchPanel;
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

    Card deck = new Card("CardImages/Card Back.png");
    private ArrayList<PositionListener> listeners = new ArrayList<PositionListener>();
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
    public void paintComponent(Graphics pen) {
        this.ui.paint(this);
    }

    public void displayPlayingPlayer() throws IOException {
        boardContainerPanel.removeAll();
        enemyContainerPanel.removeAll();
        if (matchModel.getState().equals(State.Draw)) {
            deck.setHighlighted(true);
        } else {
            deck.setHighlighted(false);
        }

        for (Component o : handContainerPanel.getComponents()) {
            if (o.getClass().equals(Card.class)) {
                handContainerPanel.remove(o);
            }
        }
        for (Object card : matchModel.getPlayingPlayer().getHand()) {
            Card castedCard = (Card) card;
            handContainerPanel.add(castedCard);
        }

        handContainerPanel.add(deck);

        for (Object card : matchModel.getPlayingPlayer().getBoard()) {
            Card castedCard = (Card) card;
            boardContainerPanel.add(castedCard);
        }

        for (int i = 0; boardContainerPanel.getComponents().length != 5; i++) {
            Placeholder placeholder = new Placeholder();
            placeholder.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if (matchModel.getState().equals(State.Position) && selectedCard != null && selectedCard.getPosition().equals(CardModel.Position.hand)) {
                        matchModel.getPlayingPlayer().getHand().remove(selectedCard);
                        positionCard(selectedCard);
                        matchModel.getPlayingPlayer().getBoard().add(selectedCard);
                        //This method sets the listener used to select the card that is actively attacking an opponent card
                        setupAttackerListener(selectedCard);
                        //This method sets the listener used to select the card that is being attacked
                        setupAttackedListener(selectedCard);
                        for (PositionListener listener : listeners) {
                            listener.positionChanged();
                        }
                        boardContainerPanel.remove(placeholder);
                        selectedCard = null;
                        try {
                            displayPlayingPlayer();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
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


        for (Object card : matchModel.getNotPlayingPlayer().getBoard()) {
            Card castedCard = (Card) card;
            enemyContainerPanel.add(castedCard);
        }

        revalidate();
        repaint();
    }

    public void setupButtons() {
        /*
        JButton attackButton = new JButton("Attack");
        this.add(attackButton);
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (matchModel.getState().equals(State.Position) && !matchModel.getPlayingPlayer().getBoard().isEmpty()) {
                    matchModel.setState(State.Attack);
                } else if (matchModel.getState().equals(State.Draw)) {
                    JOptionPane.showMessageDialog(null, "You must draw before attacking");
                } else if (matchModel.getPlayingPlayer().getBoard().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must have at least one monster on your board to attack");
                }
            }
        });
*/
        JButton endButton = new JButton("End turn");
        this.add(endButton);
        endButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (matchModel.getState() != State.Draw) {
                    JOptionPane.showMessageDialog(null, "Pass the device to player " + matchModel.getNotPlayingPlayer().getUsername());
                    attackingCard = null;
                    selectedCard = null;
                    matchModel.setState(State.Draw);
                    matchModel.changePlayingPlayer();
                    resetAttackingCards();
                    try {
                        displayPlayingPlayer();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "You must draw before ending your turn");
                }
            }
        });
    }

    public void setupListeners() {
        //MANAGE CRD POSITIONING FOR PLAYER 1
        for (Card card : this.matchModel.getPlayer1().getHand()) {
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                        if (matchModel.getPlayer1().getBoard().size() < 5) {
                            selectedCard = card;
                            hilightBoard();
                            repaint();
                            revalidate();
                        } else {
                            JOptionPane.showMessageDialog(null, "You have already positioned 5 cards");
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                        card.setGlow(true);
                        repaint();
                        revalidate();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                        card.setGlow(false);
                        repaint();
                        revalidate();
                    }
                }
            });


        }

        //MANAGE CRD POSITIONING FOR PLAYER 2
        //TODO REFACTOR WITH FUNCTION
        for (Card card : this.matchModel.getPlayer2().getHand()) {
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (matchModel.getState().equals(State.Position)) {
                        if (matchModel.getPlayer2().getBoard().size() < 5) {
                            selectedCard = card;
                            hilightBoard();
                            repaint();
                            revalidate();
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                        card.setGlow(true);
                        repaint();
                        revalidate();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                        card.setGlow(false);
                        repaint();
                        revalidate();
                    }
                }
            });
        }
        //DECK CLICK LISTENER
        deck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (matchModel.getState().equals(State.Draw)) {
                    Card card = getMatchModel().getPlayingPlayer().drawCard();
                    deck.setHighlighted(false);
                    matchModel.setState(State.Position);
                    repaint();
                    revalidate();
                    card.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                                if (matchModel.getPlayer2().getBoard().size() < 5) {
                                    selectedCard = card;
                                    hilightBoard();
                                    repaint();
                                    revalidate();
                                }
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            super.mouseEntered(e);
                            if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                                card.setGlow(true);
                                repaint();
                                revalidate();
                            }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            super.mouseExited(e);
                            if (matchModel.getState().equals(State.Position) && isCardInHand(card)) {
                                card.setGlow(false);
                                repaint();
                                revalidate();
                            }
                        }
                    });
                    try {
                        displayPlayingPlayer();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You can't draw more than once");
                }
            }
        });
    }

    public void setupUI() {
        handContainerPanel.setOpaque(false);
        handContainerPanel.setMaximumSize(new Dimension(1280, 720 / 3));
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

    public void hilightBoard() {
        for (Object placeholder : this.boardContainerPanel.getComponents()) {
            if (placeholder.getClass().equals(Placeholder.class)) {
                Placeholder placeholderCasted = (Placeholder) placeholder;
                placeholderCasted.setOpaque(true);
            }
        }
    }

    public void dehilightBoard() {
        for (Object placeholder : this.boardContainerPanel.getComponents()) {
            if (placeholder.getClass().equals(Placeholder.class)) {
                Placeholder placeholderCasted = (Placeholder) placeholder;
                placeholderCasted.setOpaque(false);
            }
        }
    }

    public void hilightEnemies() {
        for (Card card : matchModel.getNotPlayingPlayer().getBoard()) {
            card.setHighlighted(true);
        }
    }

    public void deHilightEnemies() {
        for (Card card : matchModel.getNotPlayingPlayer().getBoard()) {
            card.setHighlighted(false);
        }
    }

    public void setupAttackerListener(Card card) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(isCardInBoard(card)){
                    String[] options = {};
                    if((card.getPosition().equals(CardModel.Position.covered_defense) || card.getPosition().equals(CardModel.Position.uncovered_defense))){
                        options = new String[]{"Change Position"};
                    }else{
                        options = new String[]{"Change Position", "Attack"};
                    }
                    int choice = JOptionPane.showOptionDialog(null, "Which action do you want to perform?", "Card action", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(choice==1){
                        matchModel.setState(State.Attack);
                        if (card.isCanAttack()) {
                            if (matchModel.getState().equals(State.Attack) && card.getPosition().equals(CardModel.Position.attack) && isCardInBoard(card)) {
                                attackingCard = card;
                                hilightEnemies();
                            }
                            if (enemyContainerPanel.getComponents().length == 0) {
                                JLabel directAttackLabel = new JLabel("Direct Attack");
                                directAttackLabel.setFont(directAttackLabel.getFont().deriveFont(64.0f));
                                directAttackLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                                setupAttackedListener(directAttackLabel);
                                enemyContainerPanel.add(directAttackLabel);
                            }
                            repaint();
                            revalidate();
                        } else {
                            JOptionPane.showMessageDialog(null, "You can attack at most once with a single monster");
                        }
                    }else if(choice == 0){
                        positionCard(card);
                        repaint();
                        revalidate();
                    }
                }

            }
        });
    }

    private void setupAttackedListener(Component component) {
        if (component.getClass().equals(Card.class)) {
            Card card = (Card) component;
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //TODO CARD ATTACKS
                    if (matchModel.getState().equals(State.Attack) && attackingCard != null && attackingCard.getPosition().equals(CardModel.Position.attack) && isCardInEnemy(card)) {
                        deHilightEnemies();
                        if (card.getPosition().equals(CardModel.Position.attack)) {
                            if (attackingCard.getAtk() >= card.getAtk()) {
                                matchModel.getNotPlayingPlayer().getBoard().remove(card);
                                enemyContainerPanel.remove(card);
                                matchModel.getNotPlayingPlayer().removeLifePoints(attackingCard.getAtk() - card.getAtk());
                                //boardPanel.setDamage(attackingCard.getAtk() - card.getAtk());
                                //boardPanel.setDamagedPlayer(matchModel.getNotPlayingPlayer());
                                try {
                                    displayPlayingPlayer();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }else{
                                matchModel.getPlayingPlayer().getBoard().remove(attackingCard);
                                boardContainerPanel.remove(attackingCard);
                                matchModel.getPlayingPlayer().removeLifePoints(card.getAtk() - attackingCard.getAtk());
                            }
                        } else {
                            if (attackingCard.getAtk() >= card.getDef()) {
                                matchModel.getNotPlayingPlayer().getBoard().remove(card);
                                enemyContainerPanel.remove(card);
                            } else {
                                matchModel.getPlayingPlayer().getBoard().remove(attackingCard);
                                boardContainerPanel.remove(attackingCard);
                                matchModel.getPlayingPlayer().removeLifePoints(card.getDef() - attackingCard.getAtk());
                                //boardPanel.setDamage(card.getDef() - attackingCard.getAtk());
                                //boardPanel.setDamagedPlayer(matchModel.getPlayingPlayer());
                            }
                        }
                        attackingCard.setCanAttack(false);
                        attackingCard = null;
                        try {
                            displayPlayingPlayer();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            checkLifePoints();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    repaint();
                    revalidate();
                }
            });
        }
        if (component.getClass().equals(JLabel.class)) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //TODO CARD ATTACKS
                    if (matchModel.getState().equals(State.Attack) && attackingCard != null && attackingCard.getPosition().equals(CardModel.Position.attack)) {
                        deHilightEnemies();
                        matchModel.getNotPlayingPlayer().removeLifePoints(attackingCard.getAtk());
                        //boardPanel.setDamage(attackingCard.getAtk());
                        //boardPanel.setDamagedPlayer(matchModel.getPlayingPlayer());
                        attackingCard.setCanAttack(false);
                        attackingCard = null;
                        try {
                            displayPlayingPlayer();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            checkLifePoints();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    repaint();
                    revalidate();
                }
            });
        }

    }

    public boolean isCardInHand(Card card) {
        return this.matchModel.getPlayingPlayer().getHand().contains(card);
    }

    public boolean isCardInBoard(Card card) {
        return this.matchModel.getPlayingPlayer().getBoard().contains(card);
    }

    public boolean isCardInEnemy(Card card) {
        return this.matchModel.getNotPlayingPlayer().getBoard().contains(card);
    }

    public void addListener(PositionListener listener) {
        listeners.add(listener);
    }

    public void checkLifePoints() throws IOException {
        if (matchModel.getPlayer1().getLifePoints() <= 0) {
            this.remove(this.boardPanel);
            this.add(new EndMatchPanel(matchModel.getPlayer2()));
        } else if (matchModel.getPlayer2().getLifePoints() <= 0) {
            this.remove(this.boardPanel);
            this.add(new EndMatchPanel(matchModel.getPlayer1()));
        }
    }

    public void resetAttackingCards() {
        for (Card card : matchModel.getPlayingPlayer().getBoard()) {
            card.setCanAttack(true);
        }
    }

    public void positionCard(Card card){
        String[] options = {"Attack", "Defense", "Covered defense"};
        int choice = JOptionPane.showOptionDialog(null, "Select how you want to position your card", "Card positon", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                card.setPosition(CardModel.Position.attack);
                break;
            case 1:
                card.setPosition(CardModel.Position.uncovered_defense);
                break;
            case 2:
                card.setPosition(CardModel.Position.covered_defense);
                break;
        }
    }


}
