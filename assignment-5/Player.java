import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import static java.lang.Math.*;

class Player extends Entity {





    double xCenter, yCenter;
    private gameLoop game;
    boolean isAlive = true;
    protected double shootDirection = 0;
    keyInput ks;
    LinkedList<Bullet> bullets;
    int HP;
    private BufferedImage playerImage;

    public Player(double xCenter, double yCenter, keyInput ks, LinkedList<Bullet> b,gameLoop game) {
        this.game = game;
        HP = game.playerMAXHP;
        height = 36;
        width = 36;
        velocity = 5;
        this.ks = ks;
        bullets = b;

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        x = xCenter - width / 2;
        y = yCenter - height / 2;
        try {
            playerImage = ImageIO.read(new File("player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // movement

        if (ks.pressedW && ks.pressedD && !ks.pressedA && !ks.pressedS) {
            moveUpRight();
        } else if (ks.pressedW && ks.pressedA && !ks.pressedD && !ks.pressedS) {
            moveUpLeft();
        } else if (ks.pressedS && ks.pressedD && !ks.pressedA && !ks.pressedW) {
            moveDownRight();
        } else if (ks.pressedS && ks.pressedA && !ks.pressedW && !ks.pressedD) {
            moveDownLeft();
        } else {

            if (ks.pressedW) {
                moveUp();
            }
            if (ks.pressedA) {
                moveLeft();
            }
            if (ks.pressedS) {
                moveDown();
            }
            if (ks.pressedD) {
                moveRight();
            }
        }

        xCenter = x + width / 2;
        yCenter = y + height / 2;
    }

    public void hit(){
        HP--;
        if(HP<=0){
            System.out.println("Player dead");
            isAlive=false;
        }

    }

    public void shoot() {
        Bullet bullet = new Bullet(x + width / 2, y + height / 2, shootDirection, game.bulletDamage, game.bulletSpeed);
        bullets.add(bullet);
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(playerImage, (int) (x + 0.5), (int) (y + 0.5), (int)width, (int)height, null);
    }
}