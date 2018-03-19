/*****************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * Ship class - polygonal shape of the player's ship
 *****************************************************/
import java.awt.Polygon;
import java.awt.Rectangle;

public class Ship extends BaseVectorShape {
    //define the ship polygon
    private int[] shipx = { -14,-8,-2,2,8,14,14,12,10,10,6,6,0,-6,-6,-10,-10,-12,-14 };
    private int[] shipy = { -6,-2,-4,-4,-2,-6,-14,-16,-14,-10,-6,-18,-22,-18,-6,-10,-14,-16,-14 };

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX() - 6, (int) getY() - 6, 12,12);
        return r;
    }

    Ship() {
        setShape(new Polygon(shipx, shipy, shipx.length));
        setAlive(true);
    }
}
