package javaapplication5;

import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class shooter {
    public ImageIcon[] im = new ImageIcon[2];
    public int x = 495;
    public int y = 324;
    public int w = 128;
    public int h = 102;

    int speed0 = 10;
    int speed = 10;

    public int count = 0;

    shooter() {
        for (int i = 0; i < im.length; i++) {
            im[i] = new ImageIcon(this.getClass().getResource("pic/shooter.gif"));
        }
    }

    void Fireball(int x, int y) {
        for (int i = 0; i < im.length; i++) {
            String imageLocation = "pic/bullet.png";
            im[i] = new ImageIcon(this.getClass().getResource(imageLocation));
        }
        this.x = x;
        this.y = y;
    }

    public void GoLeft() {
        this.x -= speed;
    }

    public void GoRight() {
        this.x += speed;
    }

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, 0, this.w, 800));
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }
}
