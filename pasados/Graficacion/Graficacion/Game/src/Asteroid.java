/*****************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * Asteroid class - For polygonal asteroid shapes
 *****************************************************/

import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;

/*********************************************************
 * Asteroid class derives from BaseVectorShape
 **********************************************************/
public class Asteroid extends BaseVectorShape {
    //define the asteroid polygon shape
    private int[] astx = {-10,10,10,-10};
    private int[] asty = {0,0,10,10};

    //rotation speed
    protected double rotVel;
    public double getRotationVelocity() { return rotVel; }
    public void setRotationVelocity(double v) { rotVel = v; }

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX() - 20, (int) getY() - 20, 40, 40);
        return r;
    }

    //default constructor
    Asteroid() {
        /*Toolkit tk=Toolkit.getDefaultToolkit();
        Image ok=tk.getImage("/ship.png");
        setImage(ok);*/
        setShape(new Polygon(astx, asty, astx.length));
        setAlive(true);
        setRotationVelocity(0.0);
    }
}

