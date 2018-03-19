package geogebra;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


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
			
			/*
			for(int i=0; i<Ventana.CONSTANTES.length; i++) {
				boton = new JButton(Ventana.CONSTANTES[i].substring(0, 1).toUpperCase() + Ventana.CONSTANTES[i].substring(1, Ventana.CONSTANTES[i].length()));
				boton.setActionCommand(Ventana.CONSTANTES[i]);
				boton.addActionListener(this);
				//boton.setPreferredSize(new Dimension(40, 40));
				//boton.setIcon(new ImageIcon(ImageIO.read(BarraHerramientas.class.getClassLoader().getResource(i+".png"))));
				contenedor.add(boton);
			}*/
			
			final JPopupMenu popup = new JPopupMenu();
			
			for(int i=1; i<=Ventana.CONSTANTES.length; i++) {
				boton = new JButton();
				boton.setActionCommand(Ventana.CONSTANTES[i-1]);
				boton.addActionListener(this);
				//boton.setPreferredSize(new Dimension(40, 40));
				boton.setIcon(new ImageIcon(ImageIO.read(BarraHerramientas.class.getClassLoader().getResource("_" + i + ".png"))));
				popup.add(boton);
			}
			
			JButton bFiguras = new JButton();
			bFiguras.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	                popup.show(e.getComponent(), e.getX(), e.getY());
	            }
	        });
			bFiguras.setIcon(new ImageIcon(ImageIO.read(BarraHerramientas.class.getClassLoader().getResource("_1.png"))));
			
			contenedor.add(bFiguras);
			
		} catch(IOException ex) {
			System.out.println("No se pudo abrir la imagen");
		}
		
		this.add(contenedor);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {	// es de geogebra
			OPCION = Integer.valueOf(e.getActionCommand());
			Ventana.geogebra = true;
		} catch(Exception ex) {	// Es una figura
			Ventana.FIGURA = e.getActionCommand();
			Ventana.geogebra = false;
		}
	}
}