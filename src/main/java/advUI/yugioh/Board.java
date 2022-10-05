package advUI.yugioh;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Board extends JPanel {
    BufferedImage image;
    public Board(BufferedImage image) throws IOException {
        this.image = image;
        this.setLayout(new SpringLayout());
    }

    @Override
    public void paintComponent(Graphics pen){
        pen = (Graphics2D)pen;
        pen.drawImage(image,0,0,image.getWidth(), image.getHeight(),null);
    }

}
