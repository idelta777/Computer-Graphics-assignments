package Graficos2DCh2.Tarea;

import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
    
public class AppletSliders extends Applet{
   private JSlider slider;
   private SpiroSlaide miPanel;
   public void init() {
        setSize(400,400);
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    miPanel = new SpiroSlaide();
                    miPanel.setBackground( Color.RED );
                    slider = new JSlider( SwingConstants.HORIZONTAL, 0, 15000, 1 );
                    slider.setPaintTicks( true );
                    slider.addChangeListener(
                        new ChangeListener() {
                                public void stateChanged( ChangeEvent e ){
                            miPanel.puntos( slider.getValue() );
                            }}); 
                    setLayout(new java.awt.BorderLayout());
                    add(slider, java.awt.BorderLayout.SOUTH);
                    add(miPanel, java.awt.BorderLayout.CENTER);
                    setVisible( true );
                }
            });
        } catch (Exception ex) {
        }
    }

}
  class SpiroSlaide extends JPanel {
  int nPoints = 1;
  double a = 67;
  double b = 53;  
  public SpiroSlaide(){
    setPreferredSize(new Dimension(500, 500));
    setBackground(Color.RED);
  }

   public void paintComponent( Graphics g ){
    super.paintComponent( g );
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
   public void puntos( int nuevo ){
       nPoints= nuevo;repaint();
   }
} 