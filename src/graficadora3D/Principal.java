package graficadora3D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Transform3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Principal extends JFrame implements ActionListener, ChangeListener {
	
	static JTextField funcion;
	JButton graficar;
	
	JSlider rotX, rotY, rotZ;
	
	JPanel panelSliders;
	
	Grafica panelDibujo;
	
	public Principal() {
		super("Graficadora 3D");
		funcion = new JTextField("x", 10);
		
		graficar = new JButton("Graficar");
		graficar.addActionListener(this);
		
		rotX = new JSlider();
		rotX.setMaximum(100);
		rotX.setValue(0);
		rotX.addChangeListener(this);
		
		rotY = new JSlider();
		rotY.setMaximum(100);
		rotY.setValue(0);
		rotY.addChangeListener(this);
		
		rotZ = new JSlider();
		rotZ.setMaximum(100);
		rotZ.setValue(0);
		rotZ.addChangeListener(this);
		
		panelSliders = new JPanel();
		
		panelDibujo = new Grafica();
		panelDibujo.setBackground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		
		panelSliders.setLayout(new GridLayout(4, 2));
		
		this.add(panelDibujo, BorderLayout.CENTER);
		
		
		panelSliders.add(funcion);
		panelSliders.add(graficar);
		panelSliders.add(new JLabel("Rotacion X"));
		panelSliders.add(rotX);
		panelSliders.add(new JLabel("Rotacion Y"));
		panelSliders.add(rotY);
		panelSliders.add(new JLabel("Rotacion Z"));
		panelSliders.add(rotZ);
		
		this.add(panelSliders, BorderLayout.EAST);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1100, 400);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.graficar)) {
			panelDibujo.graficaNueva();
		}
	}
	
	public static void main(String args[]) {
		new Principal();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		panelDibujo.setValores(rotX.getValue(), rotY.getValue(), rotZ.getValue());
	}
}
