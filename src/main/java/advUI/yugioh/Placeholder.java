package advUI.yugioh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Placeholder extends JLabel {
    //This object is used to display a yellow rectangle to highlight the position of cards on the board
    public Placeholder(){
        this.setPreferredSize(new Dimension(100, 146));
        this.setBackground(Color.yellow);
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
    }
}
