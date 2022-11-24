package javaapplication5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class homegames extends JPanel {
        private ImageIcon feild = new ImageIcon(this.getClass().getResource("pic/bghome.gif"));
        private ImageIcon exit = new ImageIcon(this.getClass().getResource("pic/exit.png"));
        private ImageIcon starts = new ImageIcon(this.getClass().getResource("pic/play.png"));
        private ImageIcon wolfIcon = new ImageIcon(this.getClass().getResource("pic/wolfhome.gif"));
        public JButton BStart = new JButton(starts);
        public JButton BExit1 = new JButton(exit);

        homegames() {
                setLayout(null);
                BExit1.setBounds(200, 360, 100, 50);
                add(BExit1);
                add(BStart);
                BStart.setBounds(700, 360, 100, 50);
                add(BStart);
        }

        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(feild.getImage(), 0, 0, 1000, 500, this);
                g.drawImage(wolfIcon.getImage(), 250, 100, 480, 480, this);
                g.setColor(Color.WHITE);
                g.setFont(new Font("2005_iannnnnTKO", Font.CENTER_BASELINE, 90));
                g.drawString("Kill the Wolf", 250, 100);
        }
}