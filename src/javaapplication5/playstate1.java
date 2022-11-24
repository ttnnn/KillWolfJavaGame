package javaapplication5;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;

public class playstate1 extends JPanel implements ActionListener {

    private final ImageIcon imgstate1 = new ImageIcon(this.getClass().getResource("background.gif"));
    private final ImageIcon imgstate2 = new ImageIcon(this.getClass().getResource("background3.gif"));
    private final ImageIcon imgmeleon = new ImageIcon(this.getClass().getResource("wolf2.gif"));
    private final ImageIcon pause = new ImageIcon(this.getClass().getResource("pause.png"));
    private final ImageIcon resum = new ImageIcon(this.getClass().getResource("play.png"));
    private final ImageIcon back = new ImageIcon(this.getClass().getResource("back.png"));

    Knight m = new Knight();

    homegames hg = new homegames();
    ImageIcon feildover = new ImageIcon(this.getClass().getResource("background3.gif"));
    ImageIcon exitover = new ImageIcon(this.getClass().getResource("exit.png"));
    ImageIcon restart = new ImageIcon(this.getClass().getResource("start.png"));
    JButton BStartover = new JButton(restart);
    JButton BExitover = new JButton(exitover);

    private JLabel score = new JLabel();
    public JButton BPause = new JButton(pause);
    public JButton BExithome = new JButton(back);
    public JButton Bresum = new JButton(resum);

    public ArrayList<Fireball> fireball = new ArrayList<Fireball>();
    public ArrayList<Ghost> ghost = new ArrayList<Ghost>();
    public ArrayList<ball5> ba5 = new ArrayList<ball5>();
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

    Thread actor = new Thread(new Runnable() {
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
    Thread tballs1 = new Thread(new Runnable() {
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
                    ghost.add(new Ghost());
                }
            }
        }
    });
    Thread tballs5 = new Thread(new Runnable() {
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
                    ba5.add(new ball5());
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

    playstate1() {
        this.setFocusable(true);
        this.setLayout(null);
        BPause.setBounds(500, 50, 40, 40);
        // BExithome.setBounds(850,30,40,40);
        Bresum.setBounds(600, 50, 40, 40);
        BPause.addActionListener(this);
        BExithome.addActionListener(this);
        Bresum.addActionListener(this);
        BExithome.addActionListener(this);
        this.add(BPause);
        this.add(BExithome);
        this.add(score);
        this.add(Bresum);

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int a = e.getKeyCode();
                if (!paralyze1) {
                    if (a == KeyEvent.VK_A) {
                        if (m.x > 0) {
                            m.x -= 10;
                        }
                    } else if (a == KeyEvent.VK_D) {
                        if (m.x < 1100) {
                            m.x += 10;
                        }
                    }
                    if (m.count > 3) {
                        // m.count = 0;
                    } else if (a == KeyEvent.VK_UP || a == KeyEvent.VK_SPACE) {
                        m.count = 5;
                        if (amount > 0) {
                            for (int i = 0; i < 2; i++) {
                                m.count = i;
                            }
                            fireball.add(new Fireball(m.x + 250, m.y)); // ตำแหน่งที่กระสุนออก
                            amount--;//
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                m.count = 0;
            }
        });
        m.x = 100;// ตำแหน่งเกิด
        time.start();
        actor.start();
        t.start();
        tballs1.start();
        tballs5.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (times <= 0 || HP <= 0) // ก่อนเริ่มเกม หรือ ตาย
        {
            this.remove(BPause);
            this.remove(Bresum);
            this.remove(BExithome);
            this.setLayout(null);
            g.drawImage(feildover.getImage(), 0, 0, 1300, 800, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 40));
            g.drawString("SCORE   " + scor, 380, 200);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 70));
            g.drawString("GAME OVER", 290, 150);
            g.drawImage(imgmeleon.getImage(), 580, 100, 591, 591, this);// รูปwolfหลังสุด

        } else if (times <= 80) // ไม่ตายและเวลาน้อยกว่า 50
        {
            g.drawImage(imgstate2.getImage(), 0, 0, 1300, 800, this);

            if (paralyze1) {
                g.drawImage(m.im[m.count].getImage(), m.x, 550, 250, 220, this); // ขนาดตัวละคร
            }

            if (m.x < 0) {
                m.x = this.getWidth() - 10;
            }
            if (m.x > this.getWidth()) {
                m.x = 20;
            }

            for (int i = 0; i < fireball.size(); i++) {
                Fireball ba = fireball.get(i);
                g.drawImage(ba.imfire[ba.count % 5].getImage(), ba.x, ba.y, 100, 100, null); // กระสุน
                ba.move();
                ba.count++;
                if (ba.y < 0) {
                    fireball.remove(i);
                }
            }
            // วาดซอมบี้
            for (int i = 0; i < ghost.size(); i++) {
                g.drawImage(ghost.get(i).getImage(), ghost.get(i).getX(), ghost.get(i).getY(), 300, 224, this);
            }
            // ทำดาเมจ zonbie
            for (int i = 0; i < fireball.size(); i++) {
                for (int j = 0; j < ghost.size(); j++) {
                    if (Intersect(fireball.get(i).getbound(), ghost.get(j).getbound())) { // intersect การชน

                        int HPzombie = ghost.get(j).getHP();

                        if (HPzombie > 1) {
                            fireball.remove(i);
                            ghost.get(j).setHP(HPzombie - 1);
                        } else {
                            fireball.remove(i);
                            ghost.remove(j);
                        }

                        scor += 10;
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            for (int i = 0; i < ba5.size(); i++) {
                g.drawImage(ba5.get(i).getImage(), ba5.get(i).getX(),
                        ba5.get(i).getY(), 400, 250, this);

            }
            // zombie
            for (int i = 0; i < fireball.size(); i++) {
                for (int j = 0; j < ba5.size(); j++) {
                    if (Intersect(fireball.get(i).getbound(), ba5.get(j).getbound())) {
                        int HPzombie2 = ba5.get(j).getHP();

                        if (HPzombie2 > 1) {
                            fireball.remove(i);
                            ba5.get(j).setHP(HPzombie2 - 1);
                        } else {
                            fireball.remove(i);
                            ba5.remove(j);
                        }

                        scor += 10;
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            // ชนคน
            for (int j = 0; j < ghost.size(); j++) {
                if (Intersect(ghost.get(j).getbound(), m.getbound())) {
                    ghost.get(j).setX(ghost.get(j).getX() + 50);
                    HP = HP - 1;
                }
            }

            for (int j = 0; j < ba5.size(); j++) {
                if (Intersect(ba5.get(j).getbound(), m.getbound())) {
                    ba5.get(j).setX(ba5.get(j).getX() + 100);
                    HP = HP - 2;
                }
            }
            // ตำแหน่งตัวอักษรด่าน2
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE =  " + scor, 50, this.getHeight() - 700);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 50));
            g.drawString("Time " + times, this.getWidth() - 250, this.getHeight() - 700);
            g.setColor(Color.WHITE);
            g.drawString("HP  " + HP, 50, this.getHeight() - 650);

        } else if (times <= 0 || HP <= 0) // ตาย และหมดเวลา
        {
            this.remove(BPause);
            this.remove(Bresum);
            this.remove(BExithome);
            this.setLayout(null);
            g.drawImage(feildover.getImage(), 0, 0, 1300, 800, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 40));
            g.drawString("SCORE   " + scor, 380, 200);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 70));
            g.drawString("GAME OVER", 290, 150);
            g.drawImage(imgmeleon.getImage(), 580, 360, 400, 400, this);
        } else // เล่นปกติ??
        {
            g.drawImage(imgstate1.getImage(), 0, 0, 1300, 800, this);
            if (paralyze1) {

            } else {
                g.drawImage(m.im[m.count].getImage(), m.x, 550, 250, 220, this); // ขนาดตัวละคร
            }

            if (m.x < 0) {
                m.x = this.getWidth() - 10;
            }
            if (m.x > this.getWidth()) {
                m.x = 20;
            }

            for (int i = 0; i < fireball.size(); i++) {
                Fireball ba = fireball.get(i);
                g.drawImage(ba.imfire[ba.count % 5].getImage(), ba.x, ba.y, 100, 100, null); // กระสุน
                ba.move();
                ba.count++;
                if (ba.y < 0) {
                    fireball.remove(i);
                }
            }
            // ===============================ball1=================
            for (int i = 0; i < ghost.size(); i++) {
                g.drawImage(ghost.get(i).getImage(), ghost.get(i).getX(), ghost.get(i).getY(), 300, 224, this);// ขนาดหมาป่า1
            }
            // ทำดาเมจ zonbie
            for (int i = 0; i < fireball.size(); i++) {
                for (int j = 0; j < ghost.size(); j++) {
                    if (Intersect(fireball.get(i).getbound(), ghost.get(j).getbound())) {

                        int HPzombie = ghost.get(j).getHP();

                        if (HPzombie > 1) {
                            fireball.remove(i);
                            ghost.get(j).setHP(HPzombie - 1);
                        } else {
                            fireball.remove(i);
                            ghost.remove(j);
                        }

                        scor += 10;
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            for (int i = 0; i < ba5.size(); i++) {
                g.drawImage(ba5.get(i).getImage(), ba5.get(i).getX(),
                        ba5.get(i).getY(), 400, 250, this);

            }
            // zombie
            for (int i = 0; i < fireball.size(); i++) {
                for (int j = 0; j < ba5.size(); j++) {
                    if (Intersect(fireball.get(i).getbound(), ba5.get(j).getbound())) {
                        int HPzombie2 = ba5.get(j).getHP();

                        if (HPzombie2 > 1) {
                            fireball.remove(i);
                            ba5.get(j).setHP(HPzombie2 - 1);
                        } else {
                            fireball.remove(i);
                            ba5.remove(j);
                        }

                        scor += 10;
                        g.drawString("+10", m.x + 100, 650);
                    }
                }
            }
            // ชนคน
            for (int j = 0; j < ghost.size(); j++) {
                if (Intersect(ghost.get(j).getbound(), m.getbound())) {
                    ghost.get(j).setX(ghost.get(j).getX() + 50);
                    HP = HP - 1;
                }
            }

            for (int j = 0; j < ba5.size(); j++) {
                if (Intersect(ba5.get(j).getbound(), m.getbound())) {
                    ba5.get(j).setX(ba5.get(j).getX() + 100);
                    HP = HP - 2;
                }
            }
            // ตำแหน่งตัวอักษร
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE =  " + scor, 50, this.getHeight() - 700);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 50));
            g.drawString("Time " + times, this.getWidth() - 300, this.getHeight() - 700);
            g.setColor(Color.WHITE);
            g.drawString("HP  " + HP, 50, this.getHeight() - 650);
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

    public ImageIcon getImgstate1() {
        return imgstate1;
    }

    public ImageIcon getImgstate2() {
        return imgstate2;
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

    public ImageIcon getBack() {
        return back;
    }

    public Knight getM() {
        return m;
    }

    public void setM(Knight m) {
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

    public JButton getBExithome() {
        return BExithome;
    }

    public void setBExithome(JButton bExithome) {
        BExithome = bExithome;
    }

    public JButton getBresum() {
        return Bresum;
    }

    public void setBresum(JButton bresum) {
        Bresum = bresum;
    }

    public ArrayList<Fireball> getFireball() {
        return fireball;
    }

    public void setFireball(ArrayList<Fireball> fireball) {
        this.fireball = fireball;
    }

    public ArrayList<Ghost> getGhost() {
        return ghost;
    }

    public void setGhost(ArrayList<Ghost> ghost) {
        this.ghost = ghost;
    }

    public ArrayList<ball5> getBa5() {
        return ba5;
    }

    public void setBa5(ArrayList<ball5> ba5) {
        this.ba5 = ba5;
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

    public void setStartball(boolean startball) {
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

    public Thread getActor() {
        return actor;
    }

    public void setActor(Thread actor) {
        this.actor = actor;
    }

    public Thread getTballs1() {
        return tballs1;
    }

    public void setTballs1(Thread tballs1) {
        this.tballs1 = tballs1;
    }

    public Thread getTballs5() {
        return tballs5;
    }

    public void setTballs5(Thread tballs5) {
        this.tballs5 = tballs5;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
