package Graficos2DCh2;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class Spirograph extends JApplet {
  public static void main(String s[]) {
    JFrame frame = new JFrame();
    frame.setTitle("Spirograph");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JApplet applet = new Spirograph();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void init() {
    JPanel panel = new SpiroPanel();
    getContentPane().add(panel);
  }
}

class SpiroPanel extends JPanel{
  int nPoints = 1000;
  double a = 60;
  double b = 50;
  
           
  public SpiroPanel() {
    setPreferredSize(new Dimension(400, 400));
    setBackground(Color.white);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.translate(200,200);
    int x1=(int)a;
    int y1=0;
    int x2;
    
    
    int y2;
    for (int i=0; i<nPoints; i++) {
      double t = i*Math.PI/90;
      x2 = (int)((a+b)*Math.cos(t) - b*Math.cos((a+b)*t/b));
      y2 = (int)((a+b)*Math.sin(t) - b*Math.sin((a+b)*t/b));     
      g2.drawLine(x1, y1, x2, y2);
      x1 = x2;
      y1 = y2;
    }
  }
}

