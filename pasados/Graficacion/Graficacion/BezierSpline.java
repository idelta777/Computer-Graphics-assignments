/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos2DCh2.Tarea;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author Luisz Bautiszta
 */
public class BezierSpline extends JApplet implements ActionListener {
    public static void main(String s[]) {
    JFrame frame = new JFrame();
    frame.setTitle("Curva de Bezier --- Curva B-Spline");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JApplet applet = new BezierSpline();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }

  ProyectoPanel panel = null;
  JSlider slidera;
  Container contenedor = getContentPane();
  public void init() {
    JMenuBar mb = new JMenuBar();
    setJMenuBar(mb);
    JMenu menu = new JMenu("Selecciona");
    mb.add(menu);
    JMenuItem mi = new JMenuItem("Bezier");
    mi.addActionListener(this);
    menu.add(mi);
    JMenuItem mx = new JMenuItem("B-Spline");
    mx.addActionListener(this);
    menu.add(mx);
    panel = new ProyectoPanel();
    getContentPane().add(panel);
    slidera = new JSlider(SwingConstants.HORIZONTAL, 0, 10000, 10000);
        slidera.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panel.setT((float)slidera.getValue()/10000);
            }
        });
        contenedor.add(slidera, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent ev) {
    String command = ev.getActionCommand();
    if ("Bezier".equals(command)) {
      panel.shapeType = panel.Bezier;
    } else if ("B-Spline".equals(command)) {
      panel.shapeType = panel.BSpline;
    } 
 }
}
class ProyectoPanel extends JPanel implements MouseListener, MouseMotionListener {
  static final int Bezier = 0;
  static final int BSpline = 1;
  int shapeType = Bezier;
  Vector spline = new Vector();
  Point p = null;
  public ProyectoPanel() {
    super();
    setBackground(Color.white);
    setPreferredSize(new Dimension(640, 480));
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
    Vector bezier = new Vector();
    boolean bandera=false;
    boolean bandera2=false;
    double t=0,r2=1;	
    
    public void setT(float x){
        r2=x;
        repaint();
    }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    
    switch (shapeType) {
        case Bezier:
                try{
                if(bandera){
                for(int i=0;i<bezier.size();i++){
                    Point p2 =(Point)bezier.get(i);
                    g2.fillOval(p2.x-2 , p2.y-2, 4, 4);
                }
                Point p0=null;
                Point p1=null;
                Point p2=null;
                Point p3=null;
                for(int i=0;i<bezier.size();i+=4){
                    p0 =(Point)bezier.get(i);
                    p1 =(Point)bezier.get(i+1);
                    g2.drawLine(p0.x, p0.y, p1.x, p1.y);
                    p2 =(Point)bezier.get(i+2);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    p3 =(Point)bezier.get(i+3);
                    g2.drawLine(p2.x, p2.y, p3.x, p3.y);
                    double x1,x2,y1,y2;
                    double x3,y3,x4,y4;
                    double x5,y5;
                    double x6,y6,x7,y7;
                    double x8,y8,x9,y9;
                    double x10,y10,x11,y11;
                    double xS,yS,xT,yT;
                    
                    x3 = p1.x - p0.x;
                    y3 = p1.y - p0.y;
                    
                    x4 = p2.x - p1.x;
                    y4 = p2.y - p1.y;
                    
                    x5 = p3.x - p2.x;
                    y5 = p3.y - p2.y;
                    
                    x6 = p0.x + x3*t;
                    y6 = p0.y + y3*t;
                    x7 = p1.x + x4*t;
                    y7 = p1.y + y4*t;
                    
                    x8 = p1.x + x4*t;
                    y8 = p1.y + y4*t;
                    x9 = p2.x + x5*t;
                    y9 = p2.y + y5*t;
                    
                    x1 = p0.x;
                    y1 = p0.y;
                    g2.setColor(Color.BLUE);
                    
                    g2.drawLine((int)x6,(int)y6,(int)x7,(int)y7);
                    g2.drawLine((int)x8,(int)y8,(int)x9,(int)y9);
                    
                    xS = x7 - x6;
                    yS = y7 - y6;
                    xT = x9 - x8;
                    yT = y9 - y8;
                    
                    x10 = x6 + xS*t;
                    y10 = y6 + yS*t;
                    x11 = x8 + xT*t;
                    y11 = y8 + yT*t;
                                       
                    g2.setColor(Color.green);
                    g2.drawLine((int)x10,(int)y10,(int)x11,(int)y11);

                    
                    g2.setColor(Color.red);
                    for(t=0;t<=r2;t+=0.01){
                        
                        x2= p0.x*Math.pow((1-t),3) + 3*t*p1.x*Math.pow((1-t),2)
                                + 3*p2.x*Math.pow(t,2)*(1-t) + p3.x*t*t*t;
                        y2= p0.y*Math.pow((1-t),3) + 3*t*p1.y*Math.pow((1-t),2)
                                + 3*p2.y*Math.pow(t,2)*(1-t) + p3.y*Math.pow(t,3);
                        g2.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
                        x1 = x2;
                        y1 = y2;
                    }
                    g2.setColor(Color.black);
                }
            }
            }catch(Exception e){
                repaint();
            }finally{
                repaint();
            }
            break;
            
            
        case BSpline:
            try{
                if(bandera2){
                for(int i=0;i<spline.size();i++){
                    Point p2 =(Point)spline.get(i);
                    g2.fillOval(p2.x-2 , p2.y-2, 4, 4);
                }
                Point p0=null;
                Point p1=null;
                Point p2=null;
                Point p3=null;
                for(int i=0;i<spline.size();i+=3){
                    if(i==0){
                        p0 =(Point)spline.get(i);
                        p1 =(Point)spline.get(i+1);
                        g2.drawLine(p0.x, p0.y, p1.x, p1.y);
                        p2 =(Point)spline.get(i+2);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                        p3 =(Point)spline.get(i+3);
                        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
                    }else{
                        p0 =(Point)spline.get(i);
                        p1 =(Point)spline.get(i+1);
                        g2.drawLine(p0.x, p0.y, p1.x, p1.y);
                        p2 =(Point)spline.get(i+2);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                        p3 =(Point)spline.get(i+3);
                        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
                    }
                    double x1,x2,y1,y2;
                    double x3,y3,x4,y4;
                    double x5,y5;
                    double x6,y6,x7,y7;
                    double x8,y8,x9,y9;
                    double x10,y10,x11,y11;
                    double xS,yS,xT,yT;
                    
                    x3 = p1.x - p0.x;
                    y3 = p1.y - p0.y;
                    
                    x4 = p2.x - p1.x;
                    y4 = p2.y - p1.y;
                    
                    x5 = p3.x - p2.x;
                    y5 = p3.y - p2.y;
                    
                    x6 = p0.x + x3*t;
                    y6 = p0.y + y3*t;
                    x7 = p1.x + x4*t;
                    y7 = p1.y + y4*t;
                    
                    x8 = p1.x + x4*t;
                    y8 = p1.y + y4*t;
                    x9 = p2.x + x5*t;
                    y9 = p2.y + y5*t;
                    
                    x1 = p0.x;
                    y1 = p0.y;
                    g2.setColor(Color.BLUE);
                    
                    g2.drawLine((int)x6,(int)y6,(int)x7,(int)y7);
                    g2.drawLine((int)x8,(int)y8,(int)x9,(int)y9);
                    
                    xS = x7 - x6;
                    yS = y7 - y6;
                    xT = x9 - x8;
                    yT = y9 - y8;
                    
                    x10 = x6 + xS*t;
                    y10 = y6 + yS*t;
                    x11 = x8 + xT*t;
                    y11 = y8 + yT*t;
                                       
                    g2.setColor(Color.green);
                    g2.drawLine((int)x10,(int)y10,(int)x11,(int)y11);
                    
                    
                    g2.setColor(Color.red);
                    for(t=0;t<=r2;t+=0.01){
                        
                        x2= p0.x*Math.pow((1-t),3) + 3*t*p1.x*Math.pow((1-t),2)
                                + 3*p2.x*Math.pow(t,2)*(1-t) + p3.x*t*t*t;
                        y2= p0.y*Math.pow((1-t),3) + 3*t*p1.y*Math.pow((1-t),2)
                                + 3*p2.y*Math.pow(t,2)*(1-t) + p3.y*Math.pow(t,3);
                        g2.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
                        x1 = x2;
                        y1 = y2;
                    }
                    g2.setColor(Color.black);
                }
            }
            }catch(Exception e){
                repaint();
            }finally{
                repaint();
            }
            break;
    }
  }
  int delB=0,delS=0;
  public void mouseClicked(MouseEvent ev) {
      if ( ev.isMetaDown() ) {  
                System.out.println( "Click Secundario" );  
                switch (shapeType) {
        case Bezier:
                for(int i=0;i<bezier.size();i++){
                    Point p2 =(Point)bezier.get(i);
                    Point d  = ev.getPoint();
                        for(int j=-2;j<3;j++)
                            for(int l=-2;l<3;l++)
                                if(d.equals(new Point(p2.x+j,p2.y+l))){
                                    delB=i;
                                    eliminar(delB);
                                }
                        }
            break;
            
        case BSpline:
            for(int i=0;i<spline.size();i++){
                    Point p2 =(Point)spline.get(i);
                    Point d  = ev.getPoint();
                        for(int j=-2;j<3;j++)
                            for(int l=-2;l<3;l++)
                                if(d.equals(new Point(p2.x+j,p2.y+l))){
                                    delS=i;
                                    eliminar(delS);
                                }
                        }
                }
      }else{
        switch (shapeType) {
            case Bezier:
                    bandera=true;
                    bezier.add(ev.getPoint());
                    repaint();
                banderac=false;
                break;

            case BSpline:
                bandera2=true;
                    spline.add(ev.getPoint());
                    repaint();
                banderad=false;
                break;
        }
     }
  }

  public void mouseEntered(MouseEvent ev) {
  }

  public void mouseExited(MouseEvent ev) {
  }
  boolean banderac=false;
  boolean banderad=false;
  int r,r3;
  public void mousePressed(MouseEvent ev) {
      if ( ev.isMetaDown() ) {  
                System.out.println( "Click Secundario" );  
      }else{ 
      switch (shapeType) {
        case Bezier:
                for(int i=0;i<bezier.size();i++){
                    Point p2 =(Point)bezier.get(i);
                    Point d  = ev.getPoint();
                        for(int j=-2;j<3;j++)
                            for(int l=-2;l<3;l++)
                                if(d.equals(new Point(p2.x+j,p2.y+l))){
                                    banderac=true;
                                    r3=i;
                                }
                        }
            break;
            
        case BSpline:
            for(int i=0;i<spline.size();i++){
                    Point p2 =(Point)spline.get(i);
                    Point d  = ev.getPoint();
                        for(int j=-2;j<3;j++)
                            for(int l=-2;l<3;l++)
                                if(d.equals(new Point(p2.x+j,p2.y+l))){
                                    banderad=true;
                                    r=i;
                                }
                        }
            
            break;
        }
     }
  }

  public void mouseReleased(MouseEvent ev) {
      
   }
  public void mouseMoved(MouseEvent ev) {
  }

  public void mouseDragged(MouseEvent ev) {
   switch (shapeType) {
        case Bezier:
            if(banderac){
                bezier.set(r3,ev.getPoint());
                repaint();
            }
            break;
            
        case BSpline:
            if(banderad){
                spline.set(r,ev.getPoint());
                repaint();
            }
            break;
        }        
    }

public void eliminar(int x) {
    
    switch (shapeType) {
        case Bezier:
            /*for(int i=0;i<bezier.size();i=i+4){
                System.out.println(x);
                if(x<i){
                    i=i-5;
                    System.out.println(i);
                    for(int j=0;j<5;j++){
                        bezier.remove(j+i);
                    }
                    break;
                }
            }*/
            bezier.remove(x);
            repaint();
            break;
            
        case BSpline:      
            spline.remove(x);
            repaint();
            break;
        } 
    }
}