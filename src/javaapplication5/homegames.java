package javaapplication5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class homegames extends JPanel {
        private ImageIcon feild = new ImageIcon(this.getClass().getResource("background2.gif"));
        private ImageIcon exit = new ImageIcon(this.getClass().getResource("exit.png"));
        private ImageIcon starts = new ImageIcon(this.getClass().getResource("start.png"));
        private ImageIcon wolfIcon = new ImageIcon(this.getClass().getResource("wolf3.gif"));
        public JButton BStart = new JButton(starts);
        public JButton BExit1 = new JButton(exit);

        homegames() {
                setLayout(null);
                BExit1.setBounds(300, 650, 170, 90);
                add(BExit1);
                add(BStart);
                BStart.setBounds(830, 650, 170, 90);
                add(BStart);
        }

        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(feild.getImage(), 0, 0, 1300, 800, this);
                g.drawImage(wolfIcon.getImage(), 400, 250, 480, 480, this);
                g.setColor(Color.WHITE);
                g.setFont(new Font("2005_iannnnnTKO", Font.CENTER_BASELINE, 90));
                g.drawString("Kill the Wolf", 400, 200);
        }
}