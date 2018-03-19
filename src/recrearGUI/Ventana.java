package recrearGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Ventana extends JFrame {
	
	public Ventana() {
		super("Recreacion Geogebra");
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(new BarraMenus());
		
		this.add(new BarraHerramientas(), BorderLayout.NORTH);
		
		this.add(new Grafica(), BorderLayout.CENTER);
		this.add(new ArbolObjetos(), BorderLayout.WEST);
		this.add(new BarraEntrada(), BorderLayout.SOUTH);
		
		this.setLocation(100, 100);
		this.setVisible(true);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Ventana v = new Ventana();
	}
}
