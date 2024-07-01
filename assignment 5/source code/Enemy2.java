import javax.imageio.ImageIO;
import javax.naming.directory.DirContext;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.*;

public class Enemy2 extends Enemy {
    

    int hp;
    boolean isAlive = true;
    LinkedList<Coin> coins;
    Player player;
    BufferedImage enemy2Image;
    

    public Enemy2(Player p, double xf, double yf, LinkedList<Coin> c, int hp) {
        super(p, xf, yf, c, hp);
        isEnemy2=true;
        this.hp=hp;
        width=16;
        height=16;
        velocity=10;
        coins=c;
        x=xf;
        y=yf;
        player=p;
        direction = atan2(player.y - y, player.x - x);
        try {
            enemy2Image = ImageIO.read(new File("enemy2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO Auto-generated constructor stub
    }

    @Override
    public void update() {

        

        x += (cos(direction) * velocity);
        y += (sin(direction) * velocity);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawImage(enemy2Image, (int) (x + .5), (int) (y + .5), (int) (height + .5), (int) (width + .5),null);
    }

}
