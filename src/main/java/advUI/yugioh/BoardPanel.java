package advUI.yugioh;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardPanel extends JPanel {
    BufferedImage image;

    public BoardPanel(BufferedImage image) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.image = image;
        this.setPreferredSize(new Dimension(1280, 800));
    }

    @Override
    public void paintComponent(Graphics pen){
        pen = (Graphics2D)pen;
        pen.drawImage(image,0,0,image.getWidth(), image.getHeight(),null);
    }

}
