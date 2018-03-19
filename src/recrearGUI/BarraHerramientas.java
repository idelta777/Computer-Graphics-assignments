package recrearGUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class BarraHerramientas extends JPanel implements ActionListener {
	
	static int OPCION = 1;
	
	public BarraHerramientas() {
		
		JButton boton;
		
		Container contenedor = new Container();
		contenedor.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		try {
			for(int i=1; i<=3; i++) {
				boton = new JButton();
				boton.setPreferredSize(new Dimension(31, 31));
				boton.setIcon(new ImageIcon(ImageIO.read(BarraHerramientas.class.getClassLoader().getResource(i+".png"))));
				boton.setActionCommand(String.valueOf(i));
				boton.addActionListener(this);
				contenedor.add(boton);
			}
		} catch(IOException ex) {
			System.out.println("No se pudo abrir la imagen");
		}
		
		this.add(contenedor);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		OPCION = Integer.valueOf(e.getActionCommand());
		System.out.println(OPCION);
	}
}