package recrearGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class BarraMenus extends JMenuBar implements ActionListener {
	JMenu archivo;
	JMenuItem salir;
	
	public BarraMenus() {
		super();
		
		archivo = new JMenu("Archivo");
		salir = new JMenuItem("Salir");
		archivo.add(salir);
		
		salir.addActionListener(this);
		
		this.add(archivo);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}
}
