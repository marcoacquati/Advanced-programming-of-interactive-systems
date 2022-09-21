import Board.Board;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application() {
        initUI();

    }

    private void initUI() {
        add(new Board());
        setPreferredSize(new Dimension(250, 200));

        setTitle("Yu-Gi-Oh!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
