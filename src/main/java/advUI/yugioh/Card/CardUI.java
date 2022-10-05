package advUI.yugioh.Card;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CardUI {

    public CardUI(Card card) {
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
    }

    public void paint(Graphics2D pen, Card card) throws IOException {
        pen.drawImage(card.getImage(), null, null);
    }

}
