package advUI.yugioh.Card;

public class CardModel {
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
    private String imagePath;

    public int getAtk(){
        return this.atk;
    }
    public void setAtk(int atk){
        this.atk = atk;
    }
    public int getDef(){
        return this.def;
    }
    public void setDef(int def){
        this.def = def;
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

    public String getImagePath(){
        return this.imagePath;
    }
    public void setImagePath(String path){
        this.imagePath = path;
    }
}
