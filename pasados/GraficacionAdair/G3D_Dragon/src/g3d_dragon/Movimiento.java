package g3d_dragon;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

import javax.swing.event.ChangeEvent;

public class Movimiento extends Behavior {
    int X, Y, Z;
    final static int ALA = 0;
    TransformGroup AlaD = new TransformGroup();
    TransformGroup AlaI = new TransformGroup();
    private Transform3D tr;
    private Transform3D rox = new Transform3D(), roy = new Transform3D(), roz = new Transform3D();
    double i,j;
    WakeupOnAWTEvent trigger = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
    boolean aletea = true;

    public Movimiento() {
        trigger = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
    }

    public void initialize() {
        this.wakeupOn(trigger);
    }

    public void processStimulus(Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            WakeupCriterion wakeup = (WakeupCriterion)enumeration.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) {
                AWTEvent[] arr = ((WakeupOnAWTEvent)(wakeup)).getAWTEvent();
                KeyEvent ke = (KeyEvent)arr[0];
                switch (ke.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    System.out.println("enter");
                    break;
                case KeyEvent.VK_UP:
                    j=j+5;
                    /*if (place == HOMBRO) {
                        if (bhom > 0)
                            bhom -= Math.PI / 20.0;
                    } else if (place == CODO) {
                        if (bcod > 0)
                            bcod -= Math.PI / 20.0;
                    }*/
                    break;
                case KeyEvent.VK_DOWN:
                    j=j-5;
                    /*if (place == HOMBRO) {
                        if (bhom < Math.PI / 2.0)
                            bhom += Math.PI / 20.0;
                    } else if (place == CODO) {
                        if (bcod < Math.PI / 2.0)
                            bcod += Math.PI / 20.0;
                    }*/
                    break;
                case KeyEvent.VK_LEFT:
                    //System.out.println(i);
                    i = i + 5;
                    /*if (place == PISO) {
                        if (ypiso > -2)
                            ypiso -= 0.02;
                    } else if (place == HOMBRO) {
                        if (ahom > -Math.PI / 2.0)
                            ahom -= Math.PI / 20.0;
                    } else if (place == CODO) {
                        if (acod > -Math.PI / 2.0)
                            acod -= Math.PI / 20.0;
                    } else {
                        if (amun > -Math.PI / 2.0)
                            amun -= Math.PI / 20.0;
                    }*/
                    break;
                case KeyEvent.VK_RIGHT:
                    if (i > -110 && aletea) {
                        i = i - 5;
                        j=j-j;
                    } else {
                        if (i < -10) {
                            aletea = false;
                            i = i + 5;
                            j=j+j;
                        } else {
                            aletea = true;
                        }
                    }
                default:
                    break;
                }
            }

        }
        rox.rotX((i * 1.84) / 100);
        roz.rotZ(-Math.PI/5);
        roz.mul(rox);
        AlaI.setTransform(roz);
        AlaD.setTransform(roz);
        this.wakeupOn(trigger);
    }

    void setX(double x) {
        //i = x;
    }

    public void setAla(TransformGroup Ala, String E) {
        if (E.equals("D")) {
            this.AlaI = Ala;
            i = -24;
            Z=-2;
        } else {
            this.AlaD = Ala;
            i = 24;            
            Z=2;
        }
    }
}
