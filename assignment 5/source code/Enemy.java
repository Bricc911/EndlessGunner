import javax.imageio.ImageIO;
import javax.naming.directory.DirContext;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.*;

public class Enemy extends Entity {
    int hp;
    boolean isAlive = true;
    LinkedList<Coin> coins;
    Player player;
    BufferedImage enemyImage;
    boolean isEnemy2=false;

    public Enemy(Player p, double xf, double yf, LinkedList<Coin> c, int hp){
        this.hp=hp;
        isEnemy2=false;
        player = p;
        x = xf;
        y = yf;
        width = 30;
        height = 30;
        velocity = 3;
        coins = c;
         try {
            enemyImage = ImageIO.read(new File("enemyimg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        direction = atan2(player.y - y, player.x - x);

        x += (cos(direction) * velocity);
        y += (sin(direction) * velocity);
    }

    public void hit(Bullet bullet) {
        hp-=bullet.damage;

        double force = 20;

        y = y + sin(bullet.direction) * force;
        x = x + cos(bullet.direction) * force;

        if (hp <= 0){
            Coin coin = new Coin(x, y, 1);
            coins.add(coin);
            isAlive = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawImage(enemyImage, (int) (x + .5), (int) (y + .5), (int) (height + .5), (int) (width + .5),null);
    }

}
