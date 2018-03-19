package graficacion2D;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class VentanaPrincipal extends JApplet implements ActionListener {
	
	PanelDibujo panel;
	
	JMenuBar barraMenu;
	JMenu figura;
	JMenuItem limpia;
	
	// Constantes
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
	
	@Override
	public void init() {
		panel = new PanelDibujo();
		getContentPane().add(panel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Graficacion 2013B - Figuras Personalizadas");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		VentanaPrincipal v = new VentanaPrincipal();
		v.init();
		
		v.construye(frame);
		
		frame.getContentPane().add(new BarraHerramientas(), BorderLayout.NORTH);
		frame.getContentPane().add(v, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void construye(JFrame frame) {
		barraMenu = new JMenuBar();
		
		figura = new JMenu("Figura");
		
		for(int i=0; i<CONSTANTES.length; i++) {
			JMenuItem menu = new JMenuItem(CONSTANTES[i].substring(0, 1).toUpperCase() + CONSTANTES[i].substring(1, CONSTANTES[i].length()));
			menu.addActionListener(this);
			menu.setActionCommand(CONSTANTES[i]);
			figura.add(menu);
		}
		
		limpia = new JMenuItem("Limpia Todo");
		limpia.addActionListener(this);
		figura.add(limpia);
		
		barraMenu.add(figura);
		
		frame.setJMenuBar(barraMenu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(limpia)) {
			panel.limpia();
		} else {
			FIGURA = e.getActionCommand();
		}
	}
}
