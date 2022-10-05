import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class GetUserNamePanel extends JPanel {
    private String enteredName;
    ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/BoardImage.png"));
    Image backgroundImage = imageIcon.getImage();


    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(new Dimension(200,100));
    }

     public GetUserNamePanel() {

        setPreferredSize(new Dimension(300,200));
        JLabel enterNameLabel = new JLabel("Please enter your user name:");
        JTextField enterNameField = new JTextField(10);
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(
                 e -> {
                     enteredName = enterNameField.getText();
                     System.out.println(enteredName);
                     //TODO: pass this value to backend
                 }
         );

        add(enterNameLabel);
        add(enterNameField);
        add(confirmButton);
     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}
