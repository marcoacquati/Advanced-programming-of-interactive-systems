package advUI.yugioh;

import advUI.yugioh.Player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndMatchPanel extends JPanel{

        BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("BoardImage.png"));;
        BufferedImage playerImage =  ImageIO.read(getClass().getClassLoader().getResourceAsStream("Player Image.png"));
        private String winningPlayerUsername;

        public EndMatchPanel(Player player) throws IOException {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setPreferredSize(new Dimension(1280, 800));
            this.winningPlayerUsername = player.getUsername();
        }

        @Override
        public void paintComponent(Graphics pen){
            pen = (Graphics2D)pen;
            pen.drawImage(image,0,0,image.getWidth(), image.getHeight(),null);
            //Playing player
            pen.drawImage(playerImage,600,200,80, 80,null);
            pen.drawString(winningPlayerUsername + ", you won!", 580, 320);
        }


}
