package Graficos2DCh2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Espirografo extends JApplet {
    
	//Variables de clase
	private JSlider sliderA = new JSlider(0,120); 
	private JSlider sliderB = new JSlider(0,100); 
	private JSlider sliderP = new JSlider(0,100); 
	private JSlider sliderN = new JSlider(0,180); 
        private JButton button1 = new JButton("Figura");
        private JButton button2 = new JButton("Fondo");
        private JLabel label = new JLabel("Color");
	public PanelDibujo panel;
        public Color figura;
        public Color fondo;
        public JColorChooser color;
	
  public static void main(String s[]) {
    //Crea un frame y pega el applet dentro, se dan parametros del frame
    JFrame frame = new JFrame();
    frame.setTitle("Espirografo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    JApplet applet = new Espirografo();
    applet.init();
    frame.getContentPane().add(applet);
    frame.pack();
    frame.setVisible(true);
  }
  
  public void init() {
      //inicializa el applet, crea los componentes para agregarlos al applet
    JPanel panel2 = new JPanel();
    panel = new PanelDibujo();
    this.setLayout(new BorderLayout());
    getContentPane().add(panel, BorderLayout.CENTER);
    panel2.setLayout(new GridLayout (7,1));
    sliderA.addChangeListener(new ChangeListener() {
        //Maneja el evento realizado al mover el sliderA
	    public synchronized void stateChanged(ChangeEvent e) {
	    	panel.setA(sliderA.getValue());
	    	sliderA.setBorder(new TitledBorder("A: " + sliderA.getValue()));
	    	panel.repaint();
	    }
	  });
    sliderB.addChangeListener(new ChangeListener() {
	      //Maneja el evento realizado al mover el sliderB
	    public synchronized void stateChanged(ChangeEvent e) {
	    	panel.setB(sliderB.getValue());
	    	sliderB.setBorder(new TitledBorder("B: " + sliderB.getValue()));
	    	panel.repaint();
	    }
	  });
    sliderP.addChangeListener(new ChangeListener() {
	      //Maneja el evento realizado al mover el sliderP
	    public synchronized void stateChanged(ChangeEvent e) {
	    	panel.setP(sliderP.getValue());
	    	sliderP.setBorder(new TitledBorder("P: " + sliderP.getValue()));
	    	panel.repaint();
	    }
	  });
    sliderN.addChangeListener(new ChangeListener() {
	      //Maneja el evento realizado al mover el sliderN
	    public synchronized void stateChanged(ChangeEvent e) {
	    	panel.setN(sliderN.getValue());
	    	sliderN.setBorder(new TitledBorder("N: " + sliderN.getValue()));
	    	panel.repaint();
	    }
	  });
    button1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //Muestra un panel para elegir el color de la figura
                    figura = color.showDialog(panel, "Color de la figura", Color.black);
                    if(figura != null){
                        panel.setFigura(figura);
                        panel.repaint();
                    }
                }
            });
      button2.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e) {
                      //Muestra un panel para elegir el color del fondo
                      fondo = color.showDialog(panel, "Color de fondo", Color.white);
                      if(figura != null){
                          panel.setFondo(fondo);
                          panel.repaint();
                      }
                  }
              });
    //Coloca titulos a los sliders
    sliderA.setBorder(new TitledBorder("A: 60"));
    sliderB.setBorder(new TitledBorder("B: 50"));
    sliderP.setBorder(new TitledBorder("P: 50"));
    sliderN.setBorder(new TitledBorder("N: 90"));
    //Agrega los componentes graficos (botones y sliders)
    panel2.add(sliderA);
    panel2.add(sliderB);
    panel2.add(sliderP);
    panel2.add(sliderN);
    panel2.add(label);
    panel2.add(button1);
    panel2.add(button2);
    //El panel2 se coloca a la derecha
    getContentPane().add(panel2, BorderLayout.EAST);
  }

}
