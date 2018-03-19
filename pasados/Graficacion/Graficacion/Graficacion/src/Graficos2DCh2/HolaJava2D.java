package Graficos2DCh2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;


public class HolaJava2D extends JApplet {
  public static void main(String s[]) {
    JFrame frame = new JFrame();
    frame.setTitle("Hola Java 2D");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JApplet applet = new HolaJava2D();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void init() {
    JPanel panel = new Hello2DPanel();
    getContentPane().add(panel);
  }
}
class Hello2DPanel extends JPanel {
  public Hello2DPanel() {
    setPreferredSize(new Dimension(640, 480));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.blue);
    Ellipse2D e = new Ellipse2D.Double(-100, -50, 200, 100);
    AffineTransform tr = new AffineTransform();
    tr.rotate(Math.PI / 6.0);
    Shape shape = tr.createTransformedShape(e);
    g2.translate(300,200);
    g2.scale(2,2);
    g2.draw(shape);
    g2.setColor(Color.red);
    tr.rotate(-Math.PI / 6.0);
    Shape shape2 = tr.createTransformedShape(e);
    g2.draw(shape2);
    g2.drawString("Hola Java 2D", 0, 0);
  }
}