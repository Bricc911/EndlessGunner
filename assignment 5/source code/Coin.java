import javax.naming.directory.DirContext;
import javax.swing.*;
import java.awt.*;
import static java.lang.Math.*;

public class Coin extends Entity{

    int value;

    public Coin(double x, double y, int value){
        this.value=value;
        this.x=x;
        this.y=y;

        height = 10;
        width = 10;
        mass = 0;
    }

    @Override
    public void update() {
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int)x,(int)y, (int) width, (int) height);
    }

}
