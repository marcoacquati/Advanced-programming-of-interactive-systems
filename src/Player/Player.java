package Player;

import Card.Card;

import java.util.ArrayList;

public class Player {
    private String username;
    private int lifePoints;

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    private ArrayList<Card> deck = new ArrayList<>(40);
    private ArrayList<Card> hand = new ArrayList<>(7);

    public Player(String username){
        this.username = username;
        this.lifePoints = 8000;
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

    public ArrayList<Card> getHand() {
        return hand;
    }
}
