package advUI.yugioh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Placeholder extends JLabel {
    public Placeholder(){
        this.setPreferredSize(new Dimension(100, 146));
        this.setBackground(Color.yellow);
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
    }
}
