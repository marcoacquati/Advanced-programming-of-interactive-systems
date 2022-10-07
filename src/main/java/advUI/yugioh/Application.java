package advUI.yugioh;

import advUI.yugioh.match.MatchController;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Application extends JFrame {
    Application() throws IOException, ParseException {

        this.add(new MatchController(), BorderLayout.CENTER);

        setPreferredSize(new Dimension(1280, 720));

        setTitle("Monsters and Battles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        pack();
    }
}
