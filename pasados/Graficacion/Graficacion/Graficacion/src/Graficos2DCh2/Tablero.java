package Graficos2DCh2;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Tablero extends JApplet{
    
    PanelTablero panel= new PanelTablero();
    JPanel panel2 = new JPanel();
    JSlider slider1 = new JSlider(0, 5, 0);
    JPanel panel3 = new JPanel();
    JColorChooser colorChooser = new JColorChooser();
    JButton button1 = new JButton("Color 1");
    JButton button2 = new JButton("Color 2");
    JButton button3 = new JButton("Fondo");
    Color color1 = new Color(209, 35, 79);
    Color color2 = new Color(34, 122, 210);
    Color fondo = new Color(0, 0, 0);
    Color temp;
    
    
    public static void main(String s[]) {
      JFrame frame = new JFrame();
      frame.setTitle("Tablero");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.setLocation(50, 20);
      JApplet applet = new Tablero();
      applet.init();
      frame.getContentPane().add(applet);
      frame.pack();
      frame.setResizable(true);
      frame.setVisible(true);
    }
    
    public void init() {
        panel.setColor1(this.color1);
        panel.setColor2(this.color2);
        panel.setFondo(this.fondo);
        panel2.setLayout(new GridLayout());
        panel3.setLayout(new GridLayout(1,3));
        panel3.add(button1);
        panel3.add(button2);
        panel3.add(button3);
        button1.setForeground(color1);
        button1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    temp = colorChooser.showDialog(panel3, "Color", color1);
                    if(temp != null)
                        color1 = temp;
                    panel.setColor1(color1);
                    button1.setForeground(color1);
                    panel.repaint();
                }
            });
        button2.setForeground(color2);
        button2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    temp = (colorChooser.showDialog(panel3, "Color", color2));
                    if(temp != null)
                        color2 = temp;
                    panel.setColor2(color2);
                    button2.setForeground(color2);
                    panel.repaint();
                }
            });
        button3.setForeground(fondo);
        button3.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    temp = (colorChooser.showDialog(panel3, "Color", fondo));
                    if(temp != null)
                        fondo = temp;
                    panel.setFondo(fondo);
                    button3.setForeground(fondo);
                    panel.repaint();
                }
            });
        slider1.addChangeListener(new ChangeListener() {
                public synchronized void stateChanged(ChangeEvent e) {
                    panel.setValor((int)Math.pow(2, slider1.getValue()));
                    panel.repaint();
                }
              });
        panel2.add(slider1);
        this.setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panel2, BorderLayout.SOUTH);
        getContentPane().add(panel3, BorderLayout.NORTH);
    }
}