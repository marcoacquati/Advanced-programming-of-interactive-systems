package advUI.yugioh;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Board extends JPanel {
    BufferedImage image;
    SpringLayout layout = new SpringLayout();

    public Board(BufferedImage image) throws IOException {
        this.image = image;
        this.setLayout(layout);
    }

    @Override
    public SpringLayout getLayout() {
        return layout;
    }

    public void setLayout(SpringLayout layout) {
        this.layout = layout;
    }

    @Override
    public void paintComponent(Graphics pen){
        pen = (Graphics2D)pen;
        pen.drawImage(image,0,0,image.getWidth(), image.getHeight(),null);
    }

}
