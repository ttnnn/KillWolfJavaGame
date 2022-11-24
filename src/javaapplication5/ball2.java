package javaapplication5;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ball2 extends ball1{
    Image img;
    ball2(){
        String imageLocation = "g0.png";
        URL imageURL1 = this.getClass().getResource(imageLocation);
        img = Toolkit.getDefaultToolkit().getImage(imageURL1);
        runner.start();
    }
    Thread runner = new Thread(new Runnable() {
	public void run() {
            while(true){
		x += 2;
		if(x >= 1100){
                    img = null;
                    runner = null;
                    x = -500;
                    y = -500;
                }
                try{
                    Thread.sleep(30);
                }catch(InterruptedException e){}
            }
	}
    });

    public Image getImage(){
	return img;
    }
}
