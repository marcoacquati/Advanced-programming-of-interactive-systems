package advUI.yugioh;

import advUI.yugioh.Player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BoardPanel extends JPanel {
    BufferedImage image;
    BufferedImage playerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Player Image.png"));
    private String playingPlayerUsername;
    private String playingPlayerLifePoints;
    private String notPlayingPlayerUsername;
    private String notPlayingPlayerLifePoints;

    private Player damagedPlayer;
    private int damage = 0;

    public BoardPanel(BufferedImage image) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.image = image;
        this.setPreferredSize(new Dimension(1280, 800));
    }

    @Override
    public void paintComponent(Graphics pen) {
        pen = (Graphics2D) pen;
        pen.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        //Playing player
        pen.drawImage(playerImage, 30, 80, 80, 80, null);
        pen.drawString(playingPlayerUsername + ", you are playing", 30, 200);
        pen.drawString(playingPlayerLifePoints, 30, 230);
        //Not playing player
        pen.drawImage(playerImage, 30, 480, 80, 80, null);
        pen.drawString(notPlayingPlayerUsername, 30, 600);
        pen.drawString(notPlayingPlayerLifePoints, 30, 630);
        if(damage != 0){
            try {
                showLifePointsDecreasement(damage, damagedPlayer, pen);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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

    public Player getDamagedPlayer() {
        return damagedPlayer;
    }

    public void setDamagedPlayer(Player damagedPlayer) {
        this.damagedPlayer = damagedPlayer;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void showLifePointsDecreasement(int damage, Player player, Graphics pen) throws InterruptedException {
        int points = player.getLifePoints();
        int i=0;
        while(i < damage){
            points--;
            i+=100;
            pen.drawString(player.getUsername() + " life points: " + points, 30, 630);
            TimeUnit.SECONDS.sleep(1);
            repaint();
            revalidate();
        }
        this.damage = 0;
        player = null;
    }
}
