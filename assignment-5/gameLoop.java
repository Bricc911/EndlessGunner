import javax.swing.*;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Math.*;

public class gameLoop extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 60;
    private ShopScreen shopScreen;
    public Timer timer;
    public Timer spawnerTimer;
    public Timer spawnerTimer2;
    public JFrame frame;
    public boolean gameOver;
    public int bulletDamage;
    public int bulletSpeed;
    public int playerMAXHP = 20;
    public int enemyHP = 2;
    public int enemy2HP = 4;
    private ImageIcon shopIcon = new ImageIcon("treasure-chest.png");

    private boolean inShop = false;
    private mouseInput ms;
    private keyInput ks;
    boolean enemyUP = false;
    boolean newEnemy = false;
    Player player;
    int hpbar;
    int timeAlive;
    int maxScore = 0;
    int totalGold = 0;
    double spawnRATE = 2500;
    double spawnRATE2 = 2000;
    double fireRate = (1000 / FPS) * 20;
    int shootRate = 1;

    public ScoreBoard scoreBoard = new ScoreBoard(new File("./score.txt"));

    public LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    public LinkedList<Enemy> enemies = new LinkedList<Enemy>();
    public LinkedList<Coin> coins = new LinkedList<Coin>();

    public gameLoop(JFrame frame) throws FileNotFoundException {
        gameOver = false;
        this.frame = frame;
        bulletDamage = 1;
        bulletSpeed = 10;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Random gen = new Random();
        ActionListener enemySpawner = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int x, y;

                do {

                    x = gen.nextInt(2000) - 500;
                    y = gen.nextInt(2000) - 500;
                } while (x >= 0 && x <= WIDTH && y >= 0 && y <= HEIGHT);
                System.out.println("Enemy Spawned");

                enemies.add(new Enemy(player, x, y, coins, enemyHP));
            }
        };

        spawnerTimer = new Timer((int) spawnRATE, enemySpawner);
        spawnerTimer.setRepeats(true);
        spawnerTimer.start();

        ActionListener enemySpawner2 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int x, y;

                do {

                    x = gen.nextInt(2000) - 500;
                    y = gen.nextInt(2000) - 500;
                } while (x >= 0 && x <= WIDTH && y >= 0 && y <= HEIGHT);
                System.out.println("Enemy2 Spawned");

                enemies.add(new Enemy2(player, x, y, coins, enemy2HP));
            }
        };

        spawnerTimer2 = new Timer((int) spawnRATE2, enemySpawner2);
        spawnerTimer2.setRepeats(true);
        spawnerTimer2.stop();

        timer = new Timer(1000 / FPS, this);
        timer.setRepeats(true);
        timer.start();

        shopScreen = new ShopScreen(this);
        shopScreen.setBounds(0, 0, WIDTH, HEIGHT);
        shopScreen.setVisible(false); // Initially, the shop screen is hidden
        shopScreen.shopTimer.stop();
        frame.add(shopScreen);

        // Add a "Shop" button to pause the game and show the shop screen
        JButton shopButton = new JButton(shopIcon);
        shopButton.setBounds(10, 50, 48, 38);
        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shopButtonClicked();
            }
        });
        this.setLayout(null);
        this.add(shopButton);

        // Add a "Return" button on the shop screen to return to the game
        JButton returnButton = new JButton(shopIcon);
        returnButton.setBounds(10, 50, 48, 38);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnButtonClicked();
            }
        });
        shopScreen.setLayout(null);
        shopScreen.add(returnButton);

        ks = new keyInput();
        player = new Player(HEIGHT / 2, WIDTH / 2, ks, bullets, this);
        ms = new mouseInput();

        this.addMouseListener(ms);
        this.addKeyListener(ks);
        // Initialize the square's position
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

    }

    public void shopButtonClicked() {
        // Pause the game by stopping timers and hiding the game screen
        System.out.println("Go to shop");
        timer.stop();
        spawnerTimer.stop();
        spawnerTimer2.stop();
        shopScreen.shopTimer.start();
        this.setVisible(false);
        this.setFocusable(false);
        this.setFocusTraversalKeysEnabled(true);
        shopScreen.setFocusable(true);
        shopScreen.setFocusTraversalKeysEnabled(false);
        inShop = true;
        // Show the shop screen
        shopScreen.setVisible(true);
        ks.pressedA = false;
        ks.pressedW = false;
        ks.pressedD = false;
        ks.pressedS = false;
    }

    public void returnButtonClicked() {
        // Return to the game by starting timers and hiding the shop screen
        System.out.println("Return from shop");
        timer.start();
        spawnerTimer.start();
        if (timeAlive >= 60000) {

            spawnerTimer2.start();
        }
        shopScreen.shopTimer.stop();
        this.setVisible(true);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        shopScreen.setFocusable(false);
        shopScreen.setFocusTraversalKeysEnabled(true);
        inShop = false;
        // Hide the shop screen
        shopScreen.setVisible(false);
    }

    public boolean isInShop() {
        return inShop;
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            enemyUP = false;
            newEnemy = false;
            if (ks.pressedQ && !inShop) { // Handle "Q" key press here
                ks.pressedQ = false;
                shopButtonClicked();
            }
            // Check the elapsed time or player's score and adjust spawn rate
            if (timeAlive == 10000) { // Increase spawn rate after 10 seconds (adjust as needed)\

                spawnRATE = 2000; // Decrease the spawn rate to 2 seconds
            }

            if (timeAlive >= 10000 && timeAlive <= 12000) {

                enemyUP = true;
            }
            if (timeAlive == 20000) { // Increase spawn rate after 20 seconds (adjust as needed)

                enemyHP = 4; // Increase hp
            }
            if (timeAlive >= 20000 && timeAlive <= 22000) {
                enemyUP = true;
            }
            if (timeAlive == 60000) {

                spawnerTimer2.start();

            }
            if (timeAlive >= 60000 && timeAlive <= 62000) {
                newEnemy = true;
            }
            update();
            timeAlive += timer.getDelay();

            // Adjust spawn rate continuously if needed

            if (timeAlive % 40000 == 0 && timeAlive >= 80000) { // Increase spawn rate every 100 seconds (adjust as
                                                                // needed)
                spawnRATE = Math.max(500, spawnRATE - 250);
                spawnRATE2 = Math.max(500, spawnRATE2 - 250);
                // Decrease spawn rate by 500 milliseconds but keep it above 0.5 seconds
                enemyHP += 2;
                enemy2HP += 2;

            }
            if (timeAlive % 70000 >= 0 && timeAlive % 70000 <= 2000 && timeAlive >= 70000) {
                enemyUP = true;
            }
            spawnerTimer.setDelay((int) spawnRATE);
            spawnerTimer2.setDelay((int) spawnRATE2);

            if (ms.isHeld) {
                if (timeAlive % ((int) fireRate) == 0) {
                    PointerInfo a = MouseInfo.getPointerInfo();
                    Point b = a.getLocation();
                    Point c = getLocationOnScreen();

                    double x = (double) b.getX();
                    double y = (double) b.getY();
                    double xc = (double) c.getX();
                    double yc = (double) c.getY();
                    x -= xc;
                    y -= yc;
                    player.shootDirection = atan2(y - player.yCenter, x - player.xCenter);
                    player.shoot();
                }
            }
            repaint();
        }
    }

    public void update() {
        ListIterator<Enemy> enemyIterator = enemies.listIterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            ListIterator<Bullet> bulletIterator = bullets.listIterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();

                if (bullet.collide(enemy)) {
                    enemy.hit(bullet);
                    bulletIterator.remove();
                }
            }
            ListIterator<Coin> coinIterator = coins.listIterator();
            while (coinIterator.hasNext()) {
                Coin coin = coinIterator.next();
                if (player.collide(coin)) {
                    totalGold++;
                    coinIterator.remove();
                }
            }

            // Loop over subsection of the list.
            // It is not the best performence O(x^2)
            // It could be done in O(x), but with Java LinkedList it would be
            // messy code and unnecessarily complex.
            ListIterator<Enemy> subIterator = enemies.listIterator(enemyIterator.nextIndex());
            while (subIterator.hasNext()) {

                Enemy enemy2 = subIterator.next();

                enemy.collide(enemy2);
            }

            if (player.collide(enemy) && enemy.isAlive) {
                player.hit();
                System.out.println("collision");
            }
            if (!enemy.isAlive) {
                enemyIterator.remove();
            }
        }

        for (Enemy enemy : enemies) {
            enemy.update();

        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        player.update();
        if (player.HP <= 0) {
            maxScore = timeAlive / 600;
            gameOver = true;
            displayGameOverScreen();
        }
    }

    public void HPU() {
        playerMAXHP += 5;

    }

    public void PSU() {
        player.velocity++;
    }

    public void BSU() {
        bulletSpeed += 2;
    }

    public void Refill() {
        player.HP = playerMAXHP;
    }

    public void BDU() {
        bulletDamage++;
    }

    public void FRU() {
        fireRate -= (1000 / FPS);
        shootRate++;
    }

    public void displayGameOverScreen() {
        // Remove the game screen or stop the game loop
        // Add the game over screen to the frame
        spawnerTimer.stop();
        spawnerTimer2.stop();
        timer.stop();
        GameOverScreen GameOverScreen = new GameOverScreen(this);
        frame.getContentPane().remove(this);
        frame.add(GameOverScreen);
        frame.validate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);

    }

    public void render(Graphics g) {
        // Clear the screen

        Image bgImage = Toolkit.getDefaultToolkit().getImage("bgimg.png");
        g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);

        player.render(g);
        for (Bullet bullet : bullets) {
            bullet.render(g);
        }
        for (Enemy enemy : enemies) {
            enemy.render(g);
        }
        for (Coin coin : coins) {
            coin.render(g);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + timeAlive / 600, WIDTH - 100, 30);
        g.setColor(Color.decode("#b37202"));
        Image coinsImage = Toolkit.getDefaultToolkit().getImage("coinAmount.png");
        g.drawImage(coinsImage, 750, 43, 16, 16, null);
        g.drawString("" + totalGold, 727, 57);
        g.setColor(Color.black);
        g.drawString("Shop (Q)", 70, 70);
        g.setColor(Color.GRAY);
        g.fillRect(10, 10, 200, 30);
        g.setColor(Color.GREEN);

       
        double hpbar = (200.0/playerMAXHP)*player.HP;
        g.fillRect(10, 10, (int)hpbar, 30);

        if (enemyUP) {
            g.setColor(Color.decode("#cc0a37"));
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Enemy power increased", 300, 50);
        }

        if (newEnemy) {
            g.setColor(Color.decode("#cc0a37"));
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("New Enemy Type Added", 300, 50);
        }

        Image img1 = Toolkit.getDefaultToolkit().getImage("hpicon.png");
        g.drawImage(img1, WIDTH - 50, 75, null);
        Image img2 = Toolkit.getDefaultToolkit().getImage("spdicon.png");
        g.drawImage(img2, WIDTH - 50, 125, null);
        Image img3 = Toolkit.getDefaultToolkit().getImage("dmgicon.png");
        g.drawImage(img3, WIDTH - 50, 175, null);
        Image img4 = Toolkit.getDefaultToolkit().getImage("bulletspd.png");
        g.drawImage(img4, WIDTH - 50, 225, null);
        Image img5 = Toolkit.getDefaultToolkit().getImage("bulletfr.png");
        g.drawImage(img5, WIDTH - 50, 275, null);

        g.setColor(Color.decode("#720a94"));
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("" + playerMAXHP, WIDTH - 80, 95);
        g.drawString("" + player.velocity, WIDTH - 80, 145);
        g.drawString("" + bulletDamage, WIDTH - 80, 195);
        g.drawString("" + bulletSpeed, WIDTH - 80, 245);
        g.drawString("" + shootRate, WIDTH - 80, 295);
    }
    public static void main(String[] args) throws FileNotFoundException {

        JFrame frame = new JFrame("CBL Assignment");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameLoop game = new gameLoop(frame);

        frame.setSize(WIDTH, HEIGHT);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}