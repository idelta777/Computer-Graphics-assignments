/*****************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * Bullet class - polygonal shape of a bullet
 *****************************************************/

import java.awt.*;
import java.awt.Rectangle;

/*********************************************************
 * Bullet class derives from BaseVectorShape
 **********************************************************/
public class Bullet extends BaseVectorShape {

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX(), (int) getY(), 10, 10);
        return r;
    }

    Bullet() {
        //create the bullet shape
        setShape(new Rectangle(0, 0, 2, 2));
        setAlive(false);
    }
}
