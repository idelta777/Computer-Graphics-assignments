package Graficos2DCh2.Tarea;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorWheel extends JApplet {

    Panel panel = null;
    JSlider slider;JSlider slidera;
    JSlider sliderb;JSlider sliderc;
    Container contenedor = getContentPane();
    Container contenedor1 = getContentPane();
    FlowLayout experimentLayout = new FlowLayout();

    public static void main(String s[]) {
        JFrame frame = new JFrame();
        frame.setTitle("ColorWheel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JApplet applet = new ColorWheel();
        applet.init();
        frame.getContentPane().add(applet);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void init() {
        panel = new Panel();
        CSlider();
        getContentPane().add(panel);
    }

    public void CSlider() {

        slidera = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 255);
        slidera.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                panel.a(slidera.getValue());
            }
        });
        contenedor.add(slidera, BorderLayout.SOUTH);
        sliderb = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
        sliderb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                panel.b(sliderb.getValue());
            }
        });
        contenedor.add(sliderb, BorderLayout.EAST);
        sliderc = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
        sliderc.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                panel.c(sliderc.getValue());
            }
        });
        contenedor.add(sliderc, BorderLayout.WEST);
       
    }
}

class Panel extends JPanel {
    int r = 255;
    int b = 255;
    int g = 255;
    Shape s = new Ellipse2D.Float(125, 175, 250, 250);
    Shape s1 = new Ellipse2D.Float(175, 250, 250, 250);
    Shape s2 = new Ellipse2D.Float(220, 175, 250, 250);
    Area R;Area B;Area G;
    Color RGB = new Color(r, g, b);Color RG = new Color(r, g, 0);
    Color GB = new Color(0, g, b);Color RB = new Color(r, 0, b);
    Color RED = new Color(r, 0, 0);Color BLUE = new Color(0, 0, g);
    Color GREEN = new Color(0, g, 0);

    public Panel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
    }

    public void a(int NewP) {
        b = NewP;
        BLUE = new Color(0, 0, b);RGB = new Color(r, g, b);
        RG = new Color(r, g, 0);GB = new Color(0, g, b);RB =new Color(r,0,b);
        repaint();
    }

    public void b(int NewP) {
        g = NewP;
        GREEN = new Color(0, g, 0);RGB = new Color(r, g, b);
        RG = new Color(r, g, 0);GB = new Color(0, g, b);RB =new Color(r,0,b);
        repaint();
    }

    public void c(int NewP) {
        r = NewP;
        RED = new Color(r, 0, 0);RGB = new Color(r, g, b);
        RG = new Color(r, g, 0);GB = new Color(0, g, b);
        RB =new Color(r,0,b);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath path = new GeneralPath();

        g2.setColor(RED);
        AffineTransform tr = new AffineTransform();
        path.append(s, false);path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        R = new Area(s);
        path.reset();

        g2.setColor(BLUE);
        path.append(s1, false);path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        path.reset();

        B = new Area(s1);
        g2.setColor(GREEN);
        path.append(s2, false);path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        path.reset();
        G = new Area(s2);
        
        R.intersect(G);
        g2.setColor(RG);
        path.append(R, false);path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        path.reset();
        R=new Area(s);
        
        R.intersect(B);
        g2.setColor(RB);
        path.append(R, false);
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        path.reset();
        
        B.intersect(G);
        g2.setColor(GB);
        path.append(B , false);
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        R= new Area(s);
        path.reset(); 
        
        R=new Area(s);
        R.intersect(G);R.intersect(B);
        g2.setColor(RGB);
        path.append(R, false);
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.fill(path);g2.draw(path);
        R= new Area(s);
        path.reset();
        
        g2.setColor(Color.BLACK);
        path.append(s, false);
        path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.draw(path);
        path.append(s1, false);
        path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.draw(path);
        path.append(s2, false);
        path.closePath();
        path = (GeneralPath) (path.createTransformedShape(tr));
        g2.draw(path);
    }
}