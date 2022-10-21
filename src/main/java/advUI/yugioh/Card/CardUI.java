package advUI.yugioh.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CardUI {
    Image covered_defense_img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("CardImages/Card Back Set.png")).getScaledInstance(146, 100, Image.SCALE_SMOOTH);

    public CardUI(Card card) throws IOException {

    }

    public void paint(Graphics2D pen, Card card) throws IOException {
        if (card.getPosition().equals(CardModel.Position.attack) || card.getPosition().equals(CardModel.Position.hand) || card.getPosition().equals(CardModel.Position.deck)) {
            card.setPreferredSize(new Dimension(100, 146));
            pen.drawImage(card.getImage(), null, null);
            card.repaint();
            card.revalidate();
        } else if (card.getPosition().equals(CardModel.Position.covered_defense)) {
            pen.drawImage(covered_defense_img, null, null);
            card.repaint();
            card.revalidate();
        } else if (card.getPosition().equals(CardModel.Position.uncovered_defense)) {
            //BufferedImage dest = new BufferedImage(146, 100, card.getImage().getType());
            card.setPreferredSize(new Dimension(146, 100));
            pen.translate((146 - 100) / 2, (146 - 100) / 2);
            pen.rotate(Math.PI / 2, 146 / 2, 100 / 2);
            pen.drawImage(card.getImage(), null, null);
            card.repaint();
            card.revalidate();
        }

        if (card.isHighlighted()) {
            pen.setColor(Color.YELLOW);
            ((Graphics2D) pen).setStroke(new BasicStroke(10));
            pen.drawRect(0, 0, 100, 146);
        }

        if (card.isGlow()) {
            pen.setColor(new Color(245, 236, 39, 145));
            ((Graphics2D) pen).setStroke(new BasicStroke(100));
            pen.drawRect(0, 0, 100, 146);
        }
    }

}
