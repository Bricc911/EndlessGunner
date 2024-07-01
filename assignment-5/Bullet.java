import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity {


    public  int damage;
    private BufferedImage bulletImage;
    public Bullet(double startX, double startY, double d, int damage, int bulletVelocity) {
        height = 8;
        width = 8;
        this.velocity = bulletVelocity;
        this.damage = damage;
        x = startX;
        y = startY;
        direction = d;

        mass = 0;

        try {
            bulletImage = ImageIO.read(new File("bulletimg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        x += cos(direction) * velocity;
        y += sin(direction) * velocity;
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(bulletImage, (int) (x + 0.5), (int) (y + 0.5), (int)width, (int)height, null);
    }

}
