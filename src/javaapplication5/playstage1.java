package javaapplication5;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;

public class playstage1 extends JPanel implements ActionListener {

    private final ImageIcon imgstate1 = new ImageIcon(this.getClass().getResource("pic/bgstage1.gif"));
    private final ImageIcon imgstate2 = new ImageIcon(this.getClass().getResource("pic/bgstage2.gif"));
    private final ImageIcon imgstate3 = new ImageIcon(this.getClass().getResource("pic/bgstage3.gif"));
    private final ImageIcon imgmeleon = new ImageIcon(this.getClass().getResource("pic/wolfend.gif"));
    private final ImageIcon pause = new ImageIcon(this.getClass().getResource("pic/puse.png"));
    private final ImageIcon resum = new ImageIcon(this.getClass().getResource("pic/resum.png"));

    shooter m = new shooter();

    homegames hg = new homegames();
    ImageIcon feildover = new ImageIcon(this.getClass().getResource("pic/bgstage2.gif"));
    ImageIcon exitover = new ImageIcon(this.getClass().getResource("pic/exit.png"));
    ImageIcon restart = new ImageIcon(this.getClass().getResource("pic/play.png"));
    JButton BStartover = new JButton(restart);
    JButton BExitover = new JButton(exitover);

    private JLabel score = new JLabel();
    public JButton BPause = new JButton(pause);
    public JButton Bresum = new JButton(resum);

    public ArrayList<bullet> bullets = new ArrayList<bullet>();
    public ArrayList<wolfwhite> wolfwhites = new ArrayList<wolfwhite>();
    public ArrayList<wolfbrown> wolfbrowns = new ArrayList<wolfbrown>();
    public int times;
    public int HP = 30;
    public int rs1 = 1;
    public int rs2 = 2;
    public int amount = 500;
    boolean timestart = true;
    boolean startball = false;

    private gameover gover = new gameover();
    public int scor = 0;
    boolean paralyze1 = false;
    int time_paralyze = 5;

    Thread time = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }

                if (timestart == false) {
                    repaint();
                }
            }
        }
    });

    Thread timeshooter = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                repaint();
            }
        }
    });
    Thread timewolfwhite = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    if (startball == false) {
                        Thread.sleep((long) (Math.random() * 10000) + 2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (startball == false) {
                    wolfwhites.add(new wolfwhite());
                }
            }
        }
    });
    Thread timewolfbrown = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    if (startball == false) {
                        Thread.sleep((long) (Math.random() * 10000) + 2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (startball == false) {
                    wolfbrowns.add(new wolfbrown());
                }
            }
        }
    });
    Thread t = new Thread(new Runnable() {
        public void run() {
            while (true) {
                if (timestart == false) {
                    times = (times - 1);
                    if (paralyze1) {
                        time_paralyze--;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    playstage1() {
        // button
        this.setFocusable(true);
        this.setLayout(null);
        BPause.setBounds(850, 100, 40, 40);
        // BExithome.setBounds(850,30,40,40);
        Bresum.setBounds(900, 100, 40, 40);
        BPause.addActionListener(this);
        Bresum.addActionListener(this);
        this.add(BPause);
        this.add(score);
        this.add(Bresum);

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int a = e.getKeyCode();
                if (!paralyze1) {
                    if (a == KeyEvent.VK_A) {
                        if (m.x > 0) {
                            m.GoLeft();
                            ;
                        }
                    } else if (a == KeyEvent.VK_D) {
                        if (m.x < 1100) {
                            m.GoRight();
                        }
                    }

                    else if (a == KeyEvent.VK_UP || a == KeyEvent.VK_SPACE) {
                        if (amount > 0 && !timestart) {
                            for (int i = 0; i < 2; i++) {
                                m.count = i;
                            }
                            bullets.add(new bullet(m.x + 150, m.y)); // bullet out
                            amount--;//
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                m.count = 0;
            }
        });
        m.x = 100;// shooter main
        time.start();
        timeshooter.start();
        t.start();
        timewolfwhite.start();
        timewolfbrown.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (times <= 0 || HP <= 0) // start
        {
            this.remove(BPause);
            this.remove(Bresum);
            this.setLayout(null);
            g.drawImage(feildover.getImage(), 0, 0, 1000, 500, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 40));
            g.drawString("SCORE   " + scor, 300, 200);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 70));
            g.drawString("GAME OVER", 250, 150);
            g.drawImage(imgmeleon.getImage(), 580, 100, 300, 300, this);// size wolfend

        } else if (times <= 40) // go to stage3
        {
            g.drawImage(imgstate3.getImage(), 0, 0, 1000, 500, this);
            if (paralyze1) {

            } else {
                g.drawImage(m.im[m.count].getImage(), m.x, 250, 250, 220, this); // ขนาดshooter
            }

            if (m.x < 0) {
                m.x = this.getWidth() - 10;
            }
            if (m.x > this.getWidth()) {
                m.x = 20;
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullet ba = bullets.get(i);
                g.drawImage(ba.imfire[ba.count % 5].getImage(), ba.x, ba.y,
                        100, 100, null); // กระสุน
                ba.move();
                // ba.count++;
                if (ba.y < 0) {
                    bullets.remove(i);
                }
            }
            for (int i = 0; i < wolfwhites.size(); i++) {
                g.drawImage(wolfwhites.get(i).getImage(), wolfwhites.get(i).getX(),
                        wolfwhites.get(i).getY(), 280, 240, this); // size wolfwhites3
            }
            // damage bullet wolfwhite
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfwhites.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfwhites.get(j).getbound())) {

                        int HPwolf = wolfwhites.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfwhites.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfwhites.remove(j);
                        }
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            for (int i = 0; i < wolfbrowns.size(); i++) {
                g.drawImage(wolfbrowns.get(i).getImage(), wolfbrowns.get(i).getX(),
                        wolfbrowns.get(i).getY(), 300, 250, this); // size wolfbrown3

            }
            // damage bullet wolfbrown
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfbrowns.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfbrowns.get(j).getbound())) {
                        int HPwolf = wolfbrowns.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfbrowns.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfbrowns.remove(j);
                        }

                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            // ชนคน
            for (int j = 0; j < wolfwhites.size(); j++) {
                if (Intersect(wolfwhites.get(j).getbound(), m.getbound())) {
                    wolfwhites.get(j).setX(wolfwhites.get(j).getX() + 50);
                    HP = HP - 1;
                }
            }

            for (int j = 0; j < wolfbrowns.size(); j++) {
                if (Intersect(wolfbrowns.get(j).getbound(), m.getbound())) {
                    wolfbrowns.get(j).setX(wolfbrowns.get(j).getX() + 100);
                    HP = HP - 2;
                }
            }
            // front size stage3
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE =  " + scor, 50, this.getHeight() - 350);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 50));
            g.drawString("Time " + times, this.getWidth() - 220, this.getHeight() - 400);
            g.setColor(Color.WHITE);
            g.drawString("HP  " + HP, 50, this.getHeight() - 400);

        } else if (times <= 70) // go to stage2
        {
            g.drawImage(imgstate2.getImage(), 0, 0, 1000, 500, this);
            if (paralyze1) {

            } else {
                g.drawImage(m.im[m.count].getImage(), m.x, 250, 250, 220, this); // ขนาดshooter
            }

            if (m.x < 0) {
                m.x = this.getWidth() - 10;
            }
            if (m.x > this.getWidth()) {
                m.x = 20;
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullet ba = bullets.get(i);
                g.drawImage(ba.imfire[ba.count % 5].getImage(), ba.x, ba.y,
                        100, 100, null); // กระสุน
                ba.move();
                // ba.count++;
                if (ba.y < 0) {
                    bullets.remove(i);
                }
            }
            for (int i = 0; i < wolfwhites.size(); i++) {
                g.drawImage(wolfwhites.get(i).getImage(), wolfwhites.get(i).getX(),
                        wolfwhites.get(i).getY(), 280, 240, this); // size wolfwhites2
            }
            // damage bullet wolfwhite
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfwhites.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfwhites.get(j).getbound())) {

                        int HPwolf = wolfwhites.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfwhites.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfwhites.remove(j);
                        }
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            for (int i = 0; i < wolfbrowns.size(); i++) {
                g.drawImage(wolfbrowns.get(i).getImage(), wolfbrowns.get(i).getX(),
                        wolfbrowns.get(i).getY(), 300, 250, this); // size wolfbrown2

            }
            // damage bullet wolfbrown
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfbrowns.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfbrowns.get(j).getbound())) {
                        int HPwolf = wolfbrowns.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfbrowns.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfbrowns.remove(j);
                        }

                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            // ชนคน
            for (int j = 0; j < wolfwhites.size(); j++) {
                if (Intersect(wolfwhites.get(j).getbound(), m.getbound())) {
                    wolfwhites.get(j).setX(wolfwhites.get(j).getX() + 50);
                    HP = HP - 1;
                }
            }

            for (int j = 0; j < wolfbrowns.size(); j++) {
                if (Intersect(wolfbrowns.get(j).getbound(), m.getbound())) {
                    wolfbrowns.get(j).setX(wolfbrowns.get(j).getX() + 100);
                    HP = HP - 2;
                }
            }
            // front size stage2
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE =  " + scor, 50, this.getHeight() - 350);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 50));
            g.drawString("Time " + times, this.getWidth() - 220, this.getHeight() - 400);
            g.setColor(Color.WHITE);
            g.drawString("HP  " + HP, 50, this.getHeight() - 400);

        } else if (times <= 0 || HP <= 0) // ตาย และหมดเวลา
        {
            this.remove(BPause);
            this.remove(Bresum);
            this.setLayout(null);
            g.drawImage(feildover.getImage(), 0, 0, 1000, 500, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 40));
            g.drawString("SCORE   " + scor, 500, 200);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 70));
            g.drawString("GAME OVER", 500, 150);
            g.drawImage(imgmeleon.getImage(), 300, 250, 50, 100, this);
        } else // page end
        {
            g.drawImage(imgstate1.getImage(), 0, 0, 1000, 500, this);
            if (paralyze1) {

            } else {
                g.drawImage(m.im[m.count].getImage(), m.x, 250, 250, 220, this); // ขนาดshooter
            }

            if (m.x < 0) {
                m.x = this.getWidth() - 10;
            }
            if (m.x > this.getWidth()) {
                m.x = 20;
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullet ba = bullets.get(i);
                g.drawImage(ba.imfire[ba.count % 5].getImage(), ba.x, ba.y,
                        100, 100, null); // กระสุน
                ba.move();
                // ba.count++;
                if (ba.y < 0) {
                    bullets.remove(i);
                }
            }

            // stage1
            for (int i = 0; i < wolfwhites.size(); i++) {
                g.drawImage(wolfwhites.get(i).getImage(), wolfwhites.get(i).getX(),
                        wolfwhites.get(i).getY(), 280, 240, this); // size wolfwhite1
            }
            // damage bullet wolfwhite
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfwhites.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfwhites.get(j).getbound())) {

                        int HPwolf = wolfwhites.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfwhites.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfwhites.remove(j);
                        }
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            for (int i = 0; i < wolfbrowns.size(); i++) {
                g.drawImage(wolfbrowns.get(i).getImage(), wolfbrowns.get(i).getX(),
                        wolfbrowns.get(i).getY(), 300, 250, this); // size wolfbrown1

            }
            // damage bullet wolfbrown
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < wolfbrowns.size(); j++) {
                    if (Intersect(bullets.get(i).getbound(), wolfbrowns.get(j).getbound())) {
                        int HPwolf = wolfbrowns.get(j).getHP();

                        if (HPwolf > 1) {
                            bullets.remove(i);
                            wolfbrowns.get(j).setHP(HPwolf - 1);
                        } else {
                            scor += 10;
                            bullets.remove(i);
                            wolfbrowns.remove(j);
                        }

                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            // shooter
            for (int j = 0; j < wolfwhites.size(); j++) {
                if (Intersect(wolfwhites.get(j).getbound(), m.getbound())) {
                    wolfwhites.get(j).setX(wolfwhites.get(j).getX() + 50);
                    HP = HP - 1;
                }
            }

            for (int j = 0; j < wolfbrowns.size(); j++) {
                if (Intersect(wolfbrowns.get(j).getbound(), m.getbound())) {
                    wolfbrowns.get(j).setX(wolfbrowns.get(j).getX() + 100);
                    HP = HP - 2;
                }
            }
            // front size stage1
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE =  " + scor, 50, this.getHeight() - 350);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 50));
            g.drawString("Time " + times, this.getWidth() - 220, this.getHeight() - 400);
            g.setColor(Color.WHITE);
            g.drawString("HP  " + HP, 50, this.getHeight() - 400);
        }
    }

    public boolean Intersect(Rectangle2D a, Rectangle2D b) {
        return (a.intersects(b));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BStartover) {
            this.setSize(100, 800);
            this.add(hg);
            this.setLocation(null);
            timestart = true;
            startball = true;
        } else if (e.getSource() == BExitover) {
            System.exit(0);
        }
    }

    public void Pause() {
        timestart = true;
        startball = true;
        Stop();
    }

    public void Continue() {
        timestart = false;
        startball = false;
        Move();
    }

    public void Stop() {
        m.speed = 0;
        for (int i = 0; i < wolfwhites.size(); i++) {
            wolfwhites.get(i).speed = 0;
        }
        for (int i = 0; i < wolfbrowns.size(); i++) {
            wolfbrowns.get(i).speed = 0;
        }

    }

    public void Move() {
        m.speed = m.speed0;
        for (int i = 0; i < wolfwhites.size(); i++) {
            wolfwhites.get(i).speed = wolfwhites.get(i).speed0;
        }
        for (int i = 0; i < wolfbrowns.size(); i++) {
            wolfbrowns.get(i).speed = wolfbrowns.get(i).speed0;
        }

    }

    // =================================================get set

    public ImageIcon getImgstate1() {
        return imgstate1;
    }

    public ImageIcon getImgstate2() {
        return imgstate2;
    }

    public ImageIcon getImgstate3() {
        return imgstate3;
    }

    public ImageIcon getImgmeleon() {
        return imgmeleon;
    }

    public ImageIcon getPause() {
        return pause;
    }

    public ImageIcon getResum() {
        return resum;
    }

    public shooter getM() {
        return m;
    }

    public void setM(shooter m) {
        this.m = m;
    }

    public homegames getHg() {
        return hg;
    }

    public void setHg(homegames hg) {
        this.hg = hg;
    }

    public ImageIcon getFeildover() {
        return feildover;
    }

    public void setFeildover(ImageIcon feildover) {
        this.feildover = feildover;
    }

    public ImageIcon getExitover() {
        return exitover;
    }

    public void setExitover(ImageIcon exitover) {
        this.exitover = exitover;
    }

    public ImageIcon getRestart() {
        return restart;
    }

    public void setRestart(ImageIcon restart) {
        this.restart = restart;
    }

    public JButton getBStartover() {
        return BStartover;
    }

    public void setBStartover(JButton bStartover) {
        BStartover = bStartover;
    }

    public JButton getBExitover() {
        return BExitover;
    }

    public void setBExitover(JButton bExitover) {
        BExitover = bExitover;
    }

    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }

    public JButton getBPause() {
        return BPause;
    }

    public void setBPause(JButton bPause) {
        BPause = bPause;
    }

    public JButton getBresum() {
        return Bresum;
    }

    public void setBresum(JButton bresum) {
        Bresum = bresum;
    }

    public ArrayList<bullet> getFireball() {
        return bullets;
    }

    public void setFireball(ArrayList<bullet> bullets) {
        this.bullets = bullets;
    }

    public ArrayList<wolfwhite> gWolfwhites() {
        return wolfwhites;
    }

    public void setGhost(ArrayList<wolfwhite> wolfwhites) {
        this.wolfwhites = wolfwhites;
    }

    public ArrayList<wolfbrown> gWolfbrowns() {
        return wolfbrowns;
    }

    public void setBa5(ArrayList<wolfbrown> wolfbrowns) {
        this.wolfbrowns = wolfbrowns;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getRs1() {
        return rs1;
    }

    public void setRs1(int rs1) {
        this.rs1 = rs1;
    }

    public int getRs2() {
        return rs2;
    }

    public void setRs2(int rs2) {
        this.rs2 = rs2;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isTimestart() {
        return timestart;
    }

    public void setTimestart(boolean timestart) {
        this.timestart = timestart;
    }

    public boolean isStartball() {
        return startball;
    }

    public void setStartwolfwhite(boolean startball) {
        this.startball = startball;
    }

    public gameover getGover() {
        return gover;
    }

    public void setGover(gameover gover) {
        this.gover = gover;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public boolean isParalyze1() {
        return paralyze1;
    }

    public void setParalyze1(boolean paralyze1) {
        this.paralyze1 = paralyze1;
    }

    public int getTime_paralyze() {
        return time_paralyze;
    }

    public void setTime_paralyze(int time_paralyze) {
        this.time_paralyze = time_paralyze;
    }

    public Thread getTime() {
        return time;
    }

    public void setTime(Thread time) {
        this.time = time;
    }

    public Thread setshooter() {
        return timeshooter;
    }

    public void setshooter(Thread timeshooter) {
        this.timeshooter = timeshooter;
    }

    public Thread settimewolfwhite() {
        return timewolfwhite;
    }

    public void settimewolfwhite(Thread timewolfwhite) {
        this.timewolfwhite = timewolfwhite;
    }

    public Thread gettimewolfbrown() {
        return timewolfbrown;
    }

    public void settimewolfbrown(Thread timewolfbrown) {
        this.timewolfbrown = timewolfbrown;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
