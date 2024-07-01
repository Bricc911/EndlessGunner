import javax.swing.*;
import java.awt.*;
import static java.lang.Math.*;

abstract class Entity {

    protected double x, y;

    protected double width=36, height=36;

    protected double mass = 1;

    protected double direction;

    protected double velocity;

    public abstract void update();

    public abstract void render(Graphics g);

    public boolean collide(Entity e) {

        if(
            (x + width >= e.x && y + height >= e.y)
            &&
            (x <= e.x + e.width && y <= e.y + e.height)
        ){
            knockback(e);
            e.knockback(this);
            return true;
        }

        return false;        
    }

    public void knockback(Entity e){

        double d = atan2(y - e.y, x - e.x);

        double f = 10; // Knockback force

        y = y + (f * sin(d) * e.mass / mass);
        x = x + (f * cos(d) * e.mass / mass);
    }

    public void moveLeft() {
        if(x>=2){

        x -= velocity;
        }

        direction = PI;
    }

    public void moveRight() {
        if(x<=748){

        x += velocity;
        }

        direction = 0;
    }

    public void moveDown() {
        if(y<=526){

        y += velocity;
        }

        direction = PI/2;
    }

    public void moveUp() {
        if(y>=3){

        y -= velocity;
        }

        direction = -PI/2;
    }

    public void moveUpRight() {
        if(x<=748&&y>=3){
        x += velocity / sqrt(2);
        y -= velocity / sqrt(2);
        }

        direction = PI/4;
    }

    public void moveUpLeft() {
        if(x>=2&&y>=3){

        
        x -= velocity / sqrt(2);
        y -= velocity / sqrt(2);
        }
        direction = 3 * PI/4;
    }

    public void moveDownRight() {
        if(y<=526&&x<=748){
        x += velocity / sqrt(2);
        y += velocity / sqrt(2);
        }
        direction = -PI/4;
    }

    public void moveDownLeft() {
        if(y<=526&&x>=2){
        x -= velocity / sqrt(2);
        y += velocity / sqrt(2);
        }
        direction = 3 * -PI/4;
    }
}