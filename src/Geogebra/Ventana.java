package geogebra;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Ventana extends JFrame {
	
	final static String ESTRELLA = "estrella";
	final static String TRIANGULO = "triangulo";
	final static String NOTA = "nota";
	final static String TRIFORCE = "triforce";
	final static String CUBO = "cubo";
	final static String CREEPER = "creeper";
	final static String FEZ = "fez";
	final static String TETRIS_1 = "tetris 1";
	final static String TETRIS_2 = "tetris 2";
	final static String TETRIS_3 = "tetris 3";
	final static String CONSTANTES[] = {ESTRELLA, TRIANGULO, NOTA, TRIFORCE, CUBO, CREEPER, FEZ, TETRIS_1, TETRIS_2, TETRIS_3};
	
	// Seleccion de la figura a dibujar
	static String FIGURA = CONSTANTES[0];
	
	Grafica grafica;
	
	// Guarda que tipo de figura dibujar
	static boolean geogebra = true;
	
	public Ventana() {
		super("Recreacion Geogebra");
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(new BarraMenus());
		
		this.add(new BarraHerramientas(), BorderLayout.NORTH);
		
		grafica = new Grafica();
		
		this.add(grafica, BorderLayout.CENTER);
		this.add(new ArbolObjetos(), BorderLayout.WEST);
		this.add(new BarraEntrada(), BorderLayout.SOUTH);
		
		this.setLocation(100, 100);
		this.setVisible(true);
		this.setSize(1000, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Ventana v = new Ventana();
	}
}
