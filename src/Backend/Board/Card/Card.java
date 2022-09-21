package Backend.Board.Card;

public class Card{
    enum Position{
        hand, attack, covered_defense, uncovered_defense;

        private static final Position[] positions = Position.values();
        private static Position getPosition(int i){
            return Position.positions[i];
        }
    }

    private Position position;
    private int atk;
    private int def;
    private String name;

    public Card(String name, int atk, int def){
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.position = Position.hand;
    }

    public int getAtk(){
        return this.atk;
    }
    public int getDef(){
        return this.def;
    }
    public Position getPosition(){
        return this.position;
    }
    public void setPosition(Position position){
        this.position = position;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
