package advUI.yugioh;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardPanel extends JPanel {
    BufferedImage image;
    BufferedImage playerImage =  ImageIO.read(getClass().getClassLoader().getResourceAsStream("Player Image.png"));
    private String playingPlayerUsername;
    private String playingPlayerLifePoints;
    private String notPlayingPlayerUsername;
    private String notPlayingPlayerLifePoints;

    public BoardPanel(BufferedImage image) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.image = image;
        this.setPreferredSize(new Dimension(1280, 800));
    }

    @Override
    public void paintComponent(Graphics pen){
        pen = (Graphics2D)pen;
        pen.drawImage(image,0,0,image.getWidth(), image.getHeight(),null);
        //Playing player
        pen.drawImage(playerImage,30,30,80, 80,null);
        pen.drawString(playingPlayerUsername + ", you are playing", 30, 150);
        pen.drawString(playingPlayerLifePoints, 30, 180);
        //Not playing player
        pen.drawImage(playerImage,1170,480,80, 80,null);
        pen.drawString(notPlayingPlayerUsername, 1170, 600);
        pen.drawString(notPlayingPlayerLifePoints, 1170, 630);
    }

    public String getPlayingPlayerUsername() {
        return playingPlayerUsername;
    }

    public void setPlayingPlayerUsername(String playingPlayerUsername) {
        this.playingPlayerUsername = playingPlayerUsername;
    }

    public String getPlayingPlayerLifePoints() {
        return playingPlayerLifePoints;
    }

    public void setPlayingPlayerLifePoints(String playingPlayerLifePoints) {
        this.playingPlayerLifePoints = playingPlayerLifePoints;
    }

    public String getNotPlayingPlayerUsername() {
        return notPlayingPlayerUsername;
    }

    public void setNotPlayingPlayerUsername(String notPlayingPlayerUsername) {
        this.notPlayingPlayerUsername = notPlayingPlayerUsername;
    }

    public String getNotPlayingPlayerLifePoints() {
        return notPlayingPlayerLifePoints;
    }

    public void setNotPlayingPlayerLifePoints(String notPlayingPlayerLifePoints) {
        this.notPlayingPlayerLifePoints = notPlayingPlayerLifePoints;
    }
}
