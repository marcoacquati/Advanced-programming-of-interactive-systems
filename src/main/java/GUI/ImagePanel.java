import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JComponent {
    ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/BoardImage.png"));
    Image backgroundImage = imageIcon.getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}
