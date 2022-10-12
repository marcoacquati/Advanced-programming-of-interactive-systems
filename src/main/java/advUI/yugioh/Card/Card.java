package advUI.yugioh.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Card extends JComponent {
    private CardModel model;
    private CardUI ui;
    private Image image;

    public Card(String imagePath) throws IOException {
        this.model = new CardModel();
        this.getModel().setImagePath(imagePath);
        this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath)).getScaledInstance(100, 146, Image.SCALE_SMOOTH);
        this.setPosition(CardModel.Position.deck);
        this.ui = new CardUI(this);
        this.setCanAttack(true);
    }

    public CardModel getModel() {
        return model;
    }

    public void setModel(CardModel model) {
        this.model = model;
    }

    public CardUI getUi() {
        return ui;
    }

    public void setUi(CardUI ui) {
        this.ui = ui;
    }

    public int getAtk(){
        return this.getModel().getAtk();
    }
    public void setAtk(int atk){
        this.getModel().setAtk(atk);
    }
    public int getDef(){
        return this.getModel().getDef();
    }
    public void setDef(int def){
        this.getModel().setDef(def);
    }

    public CardModel.Position getPosition(){
        return this.getModel().getPosition();
    }
    public void setPosition(CardModel.Position position){
        this.getModel().setPosition(position);
    }

    public void setName(String name){
        this.getModel().setName(name);
    }
    public String getName(){
        return this.getModel().getName();
    }

    public String getImagePath(){
        return this.getModel().getImagePath();
    }
    public void setImagePath(String path){
        this.getModel().setImagePath(path);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String imagePath) throws IOException {
        this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
    }
    public void setImage(Image image) throws IOException {
        this.image = image;
    }

    public boolean isHighlighted() {
        return model.isHighlighted();
    }

    public void setHighlighted(boolean highlighted) {
        model.setHighlighted(highlighted);
    }

    public boolean isGlow() {
        return model.isGlow();
    }

    public void setGlow(boolean glow) {
        model.setGlow(glow);
    }

    public boolean isCanAttack() {
        return model.isCanAttack();
    }

    public void setCanAttack(boolean canAttack) {
        this.model.setCanAttack(canAttack);
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        try {
            this.ui.paint((Graphics2D)pen, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
    }

    @Override
    public Dimension getSize() {
        return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
    }
}
