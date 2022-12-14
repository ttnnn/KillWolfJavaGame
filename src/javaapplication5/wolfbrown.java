package javaapplication5;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.net.URL;

public class wolfbrown {

    Image img;
    public int x = 800;
    public int y = 250;
    public int w = 200;
    public int h = 200;

    public int count = 0;
    public int HP = 3;

    int speed0 = 3;
    int speed = 3;

    wolfbrown() {
        String imageLocation = "pic/wolfbrown.gif";
        URL imageURL1 = this.getClass().getResource(imageLocation);
        img = Toolkit.getDefaultToolkit().getImage(imageURL1);
        runner.start();
    }

    Thread runner = new Thread(new Runnable() {
        public void run() {
            while (true) {
                x -= speed;
                if (x <= 0) {
                    img = null;
                    runner = null;
                    x = 1300;
                }
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                }
            }
        }
    });

    public Image getImage() {
        return img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int hp) {
        this.HP = hp;
    }

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, y, w, h));
    }
}
