package GUI;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private String enteredName;

    public MainGUI(){
        setupUI();
    }
    private void setupEnterUserName() {
        GetUserNamePanel getUserNamePanel = new GetUserNamePanel();
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createVerticalGlue());
        box.add(getUserNamePanel);
        box.add(Box.createVerticalGlue());

        this.add(box);
    }

    public void setupUI() {
        this.setPreferredSize(new Dimension(1280, 720));
        setTitle("Yu-Gi-Oh!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        this.setContentPane(new ImagePanel());


        setupEnterUserName();

        pack();
    }




}
