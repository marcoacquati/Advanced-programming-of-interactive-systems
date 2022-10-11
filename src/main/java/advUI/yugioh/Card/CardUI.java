package advUI.yugioh.Card;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardUI implements PositionListener{
    Image covered_defense_img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("CardImages/Card Back Set.png")).getScaledInstance(146, 100, Image.SCALE_SMOOTH);

    public CardUI(Card card) throws IOException {
        /*
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                try {
                    card.setImage(card.getImagePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                card.setSize(new Dimension(300, 437));
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                try {
                    card.setImage(card.getImage().getScaledInstance(100, 146, Image.SCALE_SMOOTH));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                card.setSize(new Dimension(100, 146));
                card.repaint();
            }
        });

         */
    }

    public void paint(Graphics2D pen, Card card) throws IOException {
        if(card.getPosition().equals(CardModel.Position.attack) || card.getPosition().equals(CardModel.Position.hand)){
            card.setPreferredSize(new Dimension(100,146));
            card.repaint();
            card.revalidate();
            pen.drawImage(card.getImage(), null, null);
        }else if(card.getPosition().equals(CardModel.Position.covered_defense)){
            card.setMinimumSize(new Dimension(146,100));
            card.repaint();
            card.revalidate();
            pen.drawImage(covered_defense_img, null, null);
        }else{
            //BufferedImage dest = new BufferedImage(146, 100, card.getImage().getType());
            card.setPreferredSize(new Dimension(146,100));
            pen.translate((146 - 100) / 2, (146 - 100) / 2);
            pen.rotate(Math.PI / 2, 100/2, 146/2);
            pen.drawImage(card.getImage(), null, null);

            card.repaint();
            card.revalidate();
        }

        if(card.isHighlighted()){
            pen.setColor(Color.YELLOW);
            pen.setStroke(new BasicStroke(10));
            pen.drawRect(card.getX(), card.getY(), 100, 146);
        }
    }

    @Override
    public void positionChanged() {
        System.out.println("SONO QUI");
    }
}
