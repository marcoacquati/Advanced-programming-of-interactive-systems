package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class GetUserNamePanel extends JPanel {
    SpringLayout layout = new SpringLayout();
    private String enteredName;
    ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/BoardImage.png"));
    Image backgroundImage = imageIcon.getImage();


    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(new Dimension(200, 100));
    }

    public GetUserNamePanel() {
        this.setLayout(layout);

        setPreferredSize(new Dimension(300, 300));
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

        layout.putConstraint(SpringLayout.WEST, enterNameLabel,
                450,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, enterNameLabel,
                300,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, enterNameField,
                15,
                SpringLayout.EAST, enterNameLabel);
        layout.putConstraint(SpringLayout.NORTH, enterNameField,
                295,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, confirmButton,
                570,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, confirmButton,
                330,
                SpringLayout.NORTH, this);

    }

    @Override
    public SpringLayout getLayout() {
        return layout;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D pen = (Graphics2D) g;
        if (backgroundImage != null) {
            pen.drawImage(backgroundImage, 0, 0,this);
        }
    }
}
