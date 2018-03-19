package Graficos2DCh2.Tarea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpirographSliders extends JApplet implements ActionListener{
    SpiroPanel panel = null;
    JSlider slider;JSlider slidera;
    JSlider sliderb;JSlider sliderc;
    Container contenedor = getContentPane();
    JButton boton1;JButton boton2;JButton boton3;
    JButton boton4;JButton boton5;JButton boton6;
  
  public static void main(String s[]) {
    JFrame frame = new JFrame();
    frame.setTitle("Spirograph");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JApplet applet = new SpirographSliders();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }
  
    @Override
  public void init() {
        panel = new SpiroPanel();
        CSlider();
        getContentPane().add(panel);
    }
  
   public void CSlider() {
      slider = new JSlider( SwingConstants.HORIZONTAL, 0, 10000, 100 );
      slider.addChangeListener(new ChangeListener() {
            @Override
             public void stateChanged( ChangeEvent e ){
               panel.VarPuntos( slider.getValue() );
             }}); 
      contenedor.add( slider, BorderLayout.PAGE_END );
      slidera = new JSlider( SwingConstants.VERTICAL, 0, 100, 1 );
      slidera.addChangeListener(new ChangeListener() {
          @Override
             public void stateChanged( ChangeEvent e ){
               panel.a( slidera.getValue() );
             }}); 
      contenedor.add( slidera, BorderLayout.WEST );
      sliderb = new JSlider( SwingConstants.VERTICAL, 0, 100, 1 );
      sliderb.addChangeListener(new ChangeListener() {
          @Override
             public void stateChanged( ChangeEvent e ){
               panel.b( sliderb.getValue() );
             }}); 
      contenedor.add( sliderb, BorderLayout.EAST );
      sliderc = new JSlider( SwingConstants.HORIZONTAL, 1, 500, 5);
      sliderc.addChangeListener(new ChangeListener() {
          @Override
             public void stateChanged( ChangeEvent e ){
               panel.c( sliderc.getValue() );
             }}); 
      contenedor.add( sliderc, BorderLayout.NORTH );
      botones();
      sliderc.setVisible(false);
    }
   public void botones(){
       JMenuBar mb = new JMenuBar();
       setJMenuBar(mb);
       boton1=new JButton("Epicicloide");boton2=new JButton("Hipocicloide");
       boton3=new JButton("Epitrocoide");boton4=new JButton("Hipotrocoide");
       boton5=new JButton("Inventada");boton6=new JButton("Curva Mariposa");
       boton1.addActionListener(this);boton2.addActionListener(this);
       boton3.addActionListener(this);boton4.addActionListener(this);
       boton5.addActionListener(this);boton6.addActionListener(this);
       mb.add(boton1);mb.add(boton2);mb.add(boton3);
       mb.add(boton4);mb.add(boton5);mb.add(boton6);
   }
   
    @Override
   public void actionPerformed(ActionEvent e) {
       if(e.getSource()==boton1)
        {sliderc.setVisible(false);panel.bandera(1);}
       if(e.getSource()==boton2)
        {sliderc.setVisible(false);panel.bandera(2);}
       if(e.getSource()==boton3)
        { sliderc.setVisible(true);panel.bandera(3);}
       if(e.getSource()==boton4)
        {sliderc.setVisible(false);panel.bandera(4);}
       if(e.getSource()==boton5)
        {sliderc.setVisible(false);panel.bandera(5);}
       if(e.getSource()==boton6)
        {sliderc.setVisible(false);panel.bandera(6);}
   } 
}


class SpiroPanel extends JPanel{
    int nPoints = 1;
    double a = 67;
    double b = 53;
    double c = 100;
    int bandera=1;
  
  public SpiroPanel(){
    setPreferredSize(new Dimension(600, 600));
    setBackground(Color.RED);
  }
  
  public void bandera(int x){
      if(x==1)
      {setBackground(Color.RED);bandera=1;repaint();}
      if(x==2)
      {setBackground(Color.blue);bandera=2;repaint();}
      if(x==3)
      {setBackground(Color.MAGENTA);bandera=3;repaint();}
      if(x==4)
      {setBackground(Color.CYAN);bandera=4;repaint();}
      if(x==5)
      {setBackground(Color.YELLOW);bandera=5;repaint();}
      if(x==6)
      {setBackground(Color.GREEN);bandera=6;repaint();}
  }
   public void VarPuntos( int NewP )
   {nPoints= NewP;repaint();}    
   public void a( double NewP )
   {a= NewP;repaint();}
   public void b( double NewP )
   {b= NewP;repaint();}
   public void c( double NewP )
   {c= NewP;repaint();}
   
    @Override
   public void paintComponent( Graphics g ){
    super.paintComponent( g );
    Graphics2D g2 = (Graphics2D)g;
    g2.translate(300,300);
    int x1=(int)a;
    int y1=0;
    int x2;
    int y2;
    if(bandera==1){
        for (int i=0; i<nPoints; i++) {
        double t = i*Math.PI/90;
        x2 = (int)((a+b)*Math.cos(t) - b*Math.cos((a+b)*t/b));
        y2 = (int)((a+b)*Math.sin(t) - b*Math.sin((a+b)*t/b));     
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        }
    }
    if(bandera==2){
    for (int i=0; i<nPoints; i++) {
        double t = i*Math.PI/90;
        x2 = (int)(2*((a-b)*Math.sin(t) - b*Math.sin(t*((a/b)-1))));
        y2 = (int)(2*((a-b)*Math.cos(t) + b*Math.cos(t*((a/b)-1))));
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
    }
    }
    if(bandera==3){
        for (int i=0; i<nPoints; i++) {
        double t = i*Math.PI/90;
        x2 = (int)(((a+b)*Math.cos(t) - c*Math.cos(t*(((a+b)/b)))));
        y2 = (int)(((a+b)*Math.sin(t) - c*Math.sin(t*(((a+b)/b)))));     
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        }
    }
    if(bandera==4){
        for (int i=0; i<nPoints; i++) {
        double t = i*Math.PI/90;
        x2 = (int)((a+b)*Math.cos(t) - b*Math.cos((a-b)*t/b));
        y2 = (int)((a+b)*Math.sin(t) - b*Math.sin((a-b)*t/b));     
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        }
    }
    if(bandera==5){
        for (int i=0; i<nPoints; i++) {
        double t = i*Math.PI/90;
        x2 = (int)(((a+b)*Math.sin(t) - a*Math.cos(t/15) - Math.pow(2*Math.E,Math.sin(t))));
        y2 = (int)(((a+b)*Math.cos(t) - b*Math.sin(t/15) - Math.pow(3*Math.E,Math.cos(t))));
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        }
    }
    if(bandera==6){
        for (int i=0; i<nPoints; i++) {
        double t = (i*Math.PI/90)/4;
        x2 = (int)(a*Math.sin(t)*(Math.pow(Math.E,Math.cos(t))-2*Math.cos(6*t)-Math.pow(Math.sin(t/12),5)));
        y2 = (int)(b*Math.cos(t)*(Math.pow(Math.E,Math.cos(t))-2*Math.cos(6*t)-Math.pow(Math.sin(t/12),5)));
        g2.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        }
    }
   }
}