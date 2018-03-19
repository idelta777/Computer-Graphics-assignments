package graficacion2D;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class BarraHerramientas extends JPanel implements ActionListener {
	
	public BarraHerramientas() {
		JButton boton = new JButton();
		Container contenedor = new Container();
		
		contenedor.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		for(int i=0; i<VentanaPrincipal.CONSTANTES.length; i++) {
			boton = new JButton(VentanaPrincipal.CONSTANTES[i].substring(0, 1).toUpperCase() + VentanaPrincipal.CONSTANTES[i].substring(1, VentanaPrincipal.CONSTANTES[i].length()));
			boton.setActionCommand(VentanaPrincipal.CONSTANTES[i]);
			boton.addActionListener(this);
			//boton.setPreferredSize(new Dimension(40, 40));
			//boton.setIcon(new ImageIcon(ImageIO.read(BarraHerramientas.class.getClassLoader().getResource(i+".png"))));
			contenedor.add(boton);
		}
		/*
		try {
			
		} catch(IOException ex) {
			System.out.println("No se pudo abrir la imagen");
		}*/
		
		this.add(contenedor);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b = (JButton) arg0.getSource();
		VentanaPrincipal.FIGURA = b.getActionCommand();
	}
}