package Graficos2DCh2.Tarea;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;


public class CustomPathTarea extends JApplet {
  public static void main(String s[]) {
    JFrame frame = new JFrame();
    frame.setTitle("GeneralPath and Winding Rules");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JApplet applet = new CustomPathTarea();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void init() {
    JPanel panel = new PathPanelTarea();
    getContentPane().add(panel);
  }
}

class PathPanelTarea extends JPanel {
  public PathPanelTarea() {
    setPreferredSize(new Dimension(700, 280));
  }

  public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        float x1=5;
        float y1=11;
        float x2=5;
        float y2=7;
        float x3=1;
        float y3=7;
        float x4=1;
        float y4=4;
        float x5=7;
        float y5=4;
        float x6=7;
        float y6=8; 
        float x7=3;
        float y7=8;
        float x8=3;
        float y8=1;
        float x9=10;  
        float y9=1;
        float x10=10;
        float y10=11;

        path.moveTo(x1,y1);
        path.lineTo(x2,y2);
        path.lineTo(x3,y3);
        path.lineTo(x4,y4); 
        path.lineTo(x5,y5);
        path.lineTo(x6,y6);
        path.lineTo(x7,y7);
        path.lineTo(x8,y8);
        path.lineTo(x9,y9);
        path.lineTo(x10,y10);
        path.closePath();

        AffineTransform tr=new AffineTransform();
        tr.setToScale(20,20);
        g2.translate(40,20);

        path=(GeneralPath)(path.createTransformedShape(tr));
        g2.draw(path);
        g2.translate(200,0);
        g2.fill(path);

        path.setWindingRule(GeneralPath.WIND_NON_ZERO);
        g2.draw(path);
        g2.translate(200,0);
        g2.fill(path);
       }
   }


