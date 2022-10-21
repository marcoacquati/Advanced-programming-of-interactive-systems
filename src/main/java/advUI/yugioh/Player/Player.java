package advUI.yugioh.Player;
import advUI.yugioh.Card.Card;
import advUI.yugioh.Card.CardModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String username;
    private int lifePoints;
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Monsters.json")));
    JSONArray cardArray = (JSONArray) obj;


    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> board = new ArrayList<>();

    public Player(String username) throws IOException, ParseException, ParseException {
        this.username = username;
        this.lifePoints = 8000;
        initializeDeck();
        initializeHand();
        //method to set deck
        //method to set hand
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getLifePoints(){
        return this.lifePoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<Card> board) {
        this.board = board;
    }

    public void initializeDeck() throws IOException {
        for(Object o : cardArray){
            JSONObject card = (JSONObject) o;
            Card newCard = new Card("CardImages/"+card.get("Name")+".png");
            newCard.setAtk(((Number)card.get("Attack")).intValue());
            newCard.setDef(((Number)card.get("Defense")).intValue());
            newCard.setName((String) card.get("Name"));
            this.deck.add(newCard);
            System.out.println();
        }
    }

    public void initializeHand(){
        for(int i=0; i<7; i++){
            int random = new Random().nextInt(deck.size());
            this.deck.get(random).setPosition(CardModel.Position.hand);
            this.hand.add(deck.get(random));
            this.deck.remove(random);
        }
    }

    public Card drawCard(){
        Card card = this.deck.get(deck.size()-1);
        this.hand.add(card);
        card.setPosition(CardModel.Position.hand);
        this.deck.remove(card);
        return card;
    }

    public void removeLifePoints(int damage){
        this.lifePoints = this.lifePoints - damage;
    }
}
