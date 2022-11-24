package javaapplication5;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.net.URL;

public class wolfwhite {

    Image img;
    public int x = 800;
    public int y = 240;
    public int count = 0;
    public int HP = 2;
    int speed0 = 2;
    int speed = 5;

    wolfwhite() {
        String imageLocation = "pic/wolfwhite.gif";
        URL imageURL = this.getClass().getResource(imageLocation);
        img = Toolkit.getDefaultToolkit().getImage(imageURL);
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

    public int getHP() {
        return this.HP;
    }

    public void setHP(int hp) {
        this.HP = hp;
    }

    public Thread getRunner() {
        return runner;
    }

    public void setRunner(Thread runner) {
        this.runner = runner;
    }

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

    public Rectangle2D getbound() {
        return (new Rectangle2D.Double(x, 0, 840, 800));
    }
}
