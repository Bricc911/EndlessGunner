import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ShopScreen extends JPanel implements ActionListener {
    private JButton HPUP;
    private JButton speedUP;
    private JButton bulletSpeedUP;
    private JButton bulletDMG;
    private JButton refill;
    private JButton FRUP;
    private gameLoop game;
    private keyInput ksh;
    public Timer shopTimer;

    public ShopScreen(gameLoop game) {
        this.game = game;
        ksh = new keyInput();
        addKeyListener(ksh);
        setFocusable(true);
        requestFocus();
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.DARK_GRAY);
        setLayout(null);

        HPUP = new JButton("Increase Player MAX HP By 5");
        HPUP.setFont(new Font("Arial", Font.BOLD, 12));
        HPUP.setBounds(175, 175, 200, 50);
        HPUP.addActionListener(this);
        add(HPUP);
        speedUP = new JButton("Increase Player Speed By 1");
        speedUP.setFont(new Font("Arial", Font.BOLD, 12));
        speedUP.setBounds(175, 275, 200, 50);
        speedUP.addActionListener(this);
        add(speedUP);
        bulletSpeedUP = new JButton("Increase Bullet Speed By 2");
        bulletSpeedUP.setFont(new Font("Arial", Font.BOLD, 12));
        bulletSpeedUP.setBounds(425, 175, 200, 50);
        bulletSpeedUP.addActionListener(this);
        add(bulletSpeedUP);

        refill = new JButton("Refill Player HP To Full");
        refill.setFont(new Font("Arial", Font.BOLD, 12));
        refill.setBounds(425, 275, 200, 50);
        refill.addActionListener(this);
        add(refill);

        bulletDMG = new JButton("Increase Bullet DMG By 1");
        bulletDMG.setFont(new Font("Arial", Font.BOLD, 12));
        bulletDMG.setBounds(175, 400, 200, 50);
        bulletDMG.addActionListener(this);
        add(bulletDMG);

        FRUP = new JButton("Increase Fire Rate By 1");
        FRUP.setFont(new Font("Arial", Font.BOLD, 12));
        FRUP.setBounds(425, 400, 200, 50);
        FRUP.addActionListener(this);
        add(FRUP);

        ActionListener shopKey = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                if (ksh.pressedQ) {
                    System.out.println("Q pressed");
                }
                if (ksh.pressedQ && game.isInShop()) { // Handle "Q" key press here
                    ksh.pressedQ = false;
                    System.out.println("key pressed");
                    game.returnButtonClicked();
                }
            }
        };

        shopTimer = new Timer(1000 / 60, shopKey);
        shopTimer.setRepeats(true);
        shopTimer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Shop", 335, 100);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Price: 5 coins", 335, 150);
        g.drawString("Price: 10 coins", 335, 375);
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Coins: " + game.totalGold, 650, 50);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString("Return (Click)", 70, 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == HPUP) {
            if (game.totalGold >= 5) {

                game.totalGold -= 5;
                System.out.println("Player HP Increased.");
                game.HPU();

            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }
        if (e.getSource() == speedUP) {
            if (game.totalGold >= 5) {

                game.totalGold -= 5;
                System.out.println("Player Speed Increased.");
                game.PSU();
            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }
        if (e.getSource() == bulletSpeedUP) {
            if (game.totalGold >= 5) {

                game.totalGold -= 5;
                System.out.println("Bullet Speed Increased.");
                game.BSU();
            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }

        if (e.getSource() == refill) {
            if (game.totalGold >= 5) {

                game.totalGold -= 5;
                System.out.println("HP Refilled.");
                game.Refill();
            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }
        if (e.getSource() == bulletDMG) {
            if (game.totalGold >= 10) {

                game.totalGold -= 10;
                System.out.println("Bullet Damage Increased.");
                game.BDU();
            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }
        if (e.getSource() == FRUP) {
            if (game.totalGold >= 10) {
                if (game.shootRate <= 15) {
                    game.totalGold -= 10;
                    System.out.println("Bullet Fire Rate Increased.");
                    game.FRU();
                } else
                    System.out.println("Limit Reached.");

            } else
                System.out.println("Not Enough Gold.");
            repaint();
        }

    }

}