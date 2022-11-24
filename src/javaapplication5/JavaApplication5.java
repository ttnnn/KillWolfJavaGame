package javaapplication5;

import javax.swing.*;

public class JavaApplication5 {

    public static void main(String[] args) {
        JFrame jf = new PlayGames();
        jf.setSize(1000, 500);
        jf.setTitle("Animation Example");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
    }

}
