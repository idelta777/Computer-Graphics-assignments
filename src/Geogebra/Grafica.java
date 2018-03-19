package geogebra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import figuras.Creeper;
import figuras.Cubo;
import figuras.Estrella;
import figuras.Fez;
import figuras.Figura;
import figuras.Nota;
import figuras.Tetris1;
import figuras.Tetris2;
import figuras.Tetris3;
import figuras.Triangulo;
import figuras.Triforce;
import graficacion2D.VentanaPrincipal;

public class Grafica extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
	
	
	// Cosas de geogebra
	int xInicialGeogebra, yInicialGeogebra;
	int xGeogebra, yGeogebra;
	ArrayList<Point> inicialesRecta = new ArrayList<Point>();
	ArrayList<Point> finalesRecta = new ArrayList<Point>();
	
	ArrayList<Point> inicialesCirculo = new ArrayList<Point>();
	ArrayList<Point> finalesCirculo = new ArrayList<>(); // guarda el ancho y alto
	
	ArrayList<Point> inicialesCuadrado = new ArrayList<Point>();
	ArrayList<Point> finalesCuadrado = new ArrayList<Point>(); // guarda el ancho y alto
	
	
	// Cosas de figuras
	int xInicial, yInicial;
	ArrayList<Figura> figuras = new ArrayList<Figura>();;	// arreglo que contendra las figuras presentes a dibujar
	Figura figuraActual;
	boolean arrastrado = false;
	
	JFrame parametros;
	JTextField parametroX, parametroY, parametroAncho, parametroAlto, parametroStroke;
	JButton aplicaCambios;
	JComboBox parametroColor;
	
	int xFinal, yFinal, widthFinal, heightFinal;
	
	public Grafica() {
		figuras.clear();
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
	}
	
	public void dibujaFigurasGeogebra(int x, int y) {
		Graphics g = this.getGraphics();
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		
		//if(inicialesRecta.size() == finalesRecta.size()) {	// Dibuja las figuras creadas anteriormente
			for(int i=0; i<inicialesRecta.size(); i++) {
				g.drawLine(inicialesRecta.get(i).x, inicialesRecta.get(i).y, finalesRecta.get(i).x, finalesRecta.get(i).y); 
			}
		//} else if(inicialesCirculo.size() == finalesCirculo.size()) {
			for(int i=0; i<inicialesCirculo.size(); i++) {
				g.drawOval(inicialesCirculo.get(i).x, inicialesCirculo.get(i).y, finalesCirculo.get(i).x, finalesCirculo.get(i).y); 
			}
		//} else if(inicialesCuadrado.size() == finalesCuadrado.size()) {
			for(int i=0; i<inicialesCuadrado.size(); i++) {
				g.drawRect(inicialesCuadrado.get(i).x, inicialesCuadrado.get(i).y, finalesCuadrado.get(i).x, finalesCuadrado.get(i).y); 
			}
		//}
		
		Graphics2D g2 = (Graphics2D) g;
			
		for(int i=0; i<figuras.size(); i++) {
			g2.setStroke(new BasicStroke(figuras.get(i).getStroke()));
			g2.setColor(figuras.get(i).getColor());
			figuras.get(i).draw(g2, false);
		}	
		
		if(BarraHerramientas.OPCION == 1) { 		// linea recta
			g.drawLine(xInicialGeogebra, yInicialGeogebra, x, y);
		} else if(BarraHerramientas.OPCION == 2) {	// circulo
			g.drawOval(xInicialGeogebra, yInicialGeogebra, x - xInicialGeogebra, y - yInicialGeogebra);
		} else if(BarraHerramientas.OPCION == 3) {	// cuadrado
			g.drawRect(xInicialGeogebra, yInicialGeogebra, x - xInicialGeogebra, y - yInicialGeogebra);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!Ventana.geogebra){ 
			Point click = new Point(arg0.getX(), arg0.getY());
	
			for(int i=0; i<figuras.size(); i++) {
				if(figuras.get(i).getRectangle().contains(click)) {
					figuraActual = figuras.get(i);
					parametrosFigura();
					break;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(Ventana.geogebra) {
			xInicialGeogebra = arg0.getX();
			yInicialGeogebra = arg0.getY();
		} else {
			arrastrado = false;
			
			xInicial = arg0.getX();
			yInicial = arg0.getY();
			
			if(Ventana.FIGURA.equals(Ventana.ESTRELLA))
				figuraActual = new Estrella(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.TRIANGULO))
				figuraActual = new Triangulo(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.NOTA))
				figuraActual = new Nota(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.TRIFORCE))
				figuraActual = new Triforce(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.CUBO))
				figuraActual = new Cubo(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.CREEPER))
				figuraActual = new Creeper(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.FEZ))
				figuraActual = new Fez(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.TETRIS_1))
				figuraActual = new Tetris1(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.TETRIS_2))
				figuraActual = new Tetris2(xInicial, yInicial);
			if(Ventana.FIGURA.equals(Ventana.TETRIS_3))
				figuraActual = new Tetris3(xInicial, yInicial);
			
			//figuras.add(figuraActual);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(Ventana.geogebra) {
			if(BarraHerramientas.OPCION == 1) {
				inicialesRecta.add(new Point(xInicialGeogebra, yInicialGeogebra));
				finalesRecta.add(new Point(arg0.getX(), arg0.getY()));
			} else if(BarraHerramientas.OPCION == 2) {
				inicialesCirculo.add(new Point(xInicialGeogebra, yInicialGeogebra));
				finalesCirculo.add(new Point(arg0.getX() - xInicialGeogebra, arg0.getY() - yInicialGeogebra));
			} else if(BarraHerramientas.OPCION == 3) {
				inicialesCuadrado.add(new Point(xInicialGeogebra, yInicialGeogebra));
				finalesCuadrado.add(new Point(arg0.getX() - xInicialGeogebra, arg0.getY() - yInicialGeogebra));
			}
		} else {
			figuraActual.setRectangleValues(xFinal, yFinal, widthFinal, heightFinal);
			//dibujaFiguras();
		}
		dibujaFiguras();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(Ventana.geogebra) {
			xGeogebra = arg0.getX();
			yGeogebra = arg0.getY();
			this.dibujaFigurasGeogebra(xGeogebra, yGeogebra);
		} else {
			if(!arrastrado)
				figuras.add(figuraActual);
			
			arrastrado = true;
			
			figuraActual.setSize(arg0.getX() - xInicial, arg0.getY() - yInicial);
			creaFigura(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	// Metodos Figuras
	
	// Solo pinta las figuras en pantalla
	private void dibujaFiguras() {
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.RED);
		
		g.setColor(Color.BLACK);
		
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		
		for(int i=0; i<figuras.size(); i++) {
			g.setStroke(new BasicStroke(figuras.get(i).getStroke()));
			g.setColor(figuras.get(i).getColor());
			figuras.get(i).draw(g, false);
		}	
		
		// Geogebra
		for(int i=0; i<inicialesRecta.size(); i++) {
			g.drawLine(inicialesRecta.get(i).x, inicialesRecta.get(i).y, finalesRecta.get(i).x, finalesRecta.get(i).y); 
		}
		for(int i=0; i<inicialesCirculo.size(); i++) {
			g.drawOval(inicialesCirculo.get(i).x, inicialesCirculo.get(i).y, finalesCirculo.get(i).x, finalesCirculo.get(i).y); 
		}
		for(int i=0; i<inicialesCuadrado.size(); i++) {
			g.drawRect(inicialesCuadrado.get(i).x, inicialesCuadrado.get(i).y, finalesCuadrado.get(i).x, finalesCuadrado.get(i).y); 
		}
	}
	
	// Dibuja las figuras y el rectangulo conforme se arrastra el mouse
	private void creaFigura(int x, int y) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));
		
		if(x > xInicial && y > yInicial) {
			xFinal = xInicial;
			yFinal = yInicial;
			widthFinal = x - xInicial;
			heightFinal = y - yInicial;
			
			g.drawRect(xInicial, yInicial, x - xInicial, y - yInicial);
		} else if(x > xInicial && y < yInicial) {
			xFinal = xInicial;
			yFinal = y;
			widthFinal = x - xInicial;
			heightFinal = yInicial - y;
			
			g.drawRect(xInicial, y, x - xInicial, yInicial - y);
		} else if(x < xInicial && y > yInicial) {
			xFinal = x;
			yFinal = yInicial;
			widthFinal = xInicial - x;
			heightFinal = y - yInicial;
			
			g.drawRect(x, yInicial, xInicial - x, y - yInicial);
		} else if(x < xInicial && y < yInicial) {
			xFinal = x;
			yFinal = y;
			widthFinal = xInicial - x;
			heightFinal = yInicial - y;
			
			g.drawRect(x, y, xInicial - x, yInicial - y);
		}
		
		g.setColor(Color.BLACK);
		
		// Inicia geogebra
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		
		for(int i=0; i<inicialesRecta.size(); i++) {
			g.drawLine(inicialesRecta.get(i).x, inicialesRecta.get(i).y, finalesRecta.get(i).x, finalesRecta.get(i).y); 
		}
		for(int i=0; i<inicialesCirculo.size(); i++) {
			g.drawOval(inicialesCirculo.get(i).x, inicialesCirculo.get(i).y, finalesCirculo.get(i).x, finalesCirculo.get(i).y); 
		}
		for(int i=0; i<inicialesCuadrado.size(); i++) {
			g.drawRect(inicialesCuadrado.get(i).x, inicialesCuadrado.get(i).y, finalesCuadrado.get(i).x, finalesCuadrado.get(i).y); 
		}
		
		// Termina geogebra
		
		figuraActual.draw(g, true);
		
		for(int i=0; i<figuras.size(); i++) {
			g.setStroke(new BasicStroke(figuras.get(i).getStroke()));
			g.setColor(figuras.get(i).getColor());
			figuras.get(i).draw(g, false);
		}
	}
	
	private void parametrosFigura() {
		parametros = new JFrame("Editar Parametros");
		parametros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		parametros.setVisible(true);
		
		parametros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		parametroX = new JTextField(String.valueOf(figuraActual.getX()), 10);
		parametroX.addActionListener(this);
		parametroX.setActionCommand("aplicaCambios");
		parametroY = new JTextField(String.valueOf(figuraActual.getY()), 10);
		parametroY.addActionListener(this);
		parametroY.setActionCommand("aplicaCambios");
		parametroAncho = new JTextField(String.valueOf(figuraActual.getWidth()), 10);
		parametroAncho.addActionListener(this);
		parametroAncho.setActionCommand("aplicaCambios");
		parametroAlto = new JTextField(String.valueOf(figuraActual.getHeight()), 10);
		parametroAlto.addActionListener(this);
		parametroAlto.setActionCommand("aplicaCambios");
		parametroStroke = new JTextField(String.valueOf(figuraActual.getStroke()), 10);
		parametroStroke.addActionListener(this);
		parametroStroke.setActionCommand("aplicaCambios");
		
		parametroColor = new JComboBox();
		parametroColor.addItem("Negro");
		parametroColor.addItem("Rojo");
		parametroColor.addItem("Azul");
		parametroColor.addItem("Verde");
		parametroColor.addItem("Magenta");
		parametroColor.addItem("Amarillo");
		
		aplicaCambios = new JButton("Aplicar");
		aplicaCambios.addActionListener(this);
		aplicaCambios.setActionCommand("aplicaCambios");
		
		parametros.add(new Label("X"));
		parametros.add(parametroX);
		parametros.add(new Label("Y"));
		parametros.add(parametroY);
		parametros.add(new Label("Ancho"));
		parametros.add(parametroAncho);
		parametros.add(new Label("Alto"));
		parametros.add(parametroAlto);
		parametros.add(new Label("Stroke"));
		parametros.add(parametroStroke);
		parametros.add(new Label("Color"));
		parametros.add(parametroColor);
		parametros.add(aplicaCambios);
		
		parametros.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("aplicaCambios"))
			editaParametros();
	}
	
	private void editaParametros() {
		try {
			int iX, iY, iAncho, iAlto;
			
			iX = Integer.valueOf(parametroX.getText().replace(" ", ""));
			iY = Integer.valueOf(parametroY.getText().replace(" ", ""));
			iAncho = Integer.valueOf(parametroAncho.getText().replace(" ", "")) * figuraActual.escalaWidth;
			iAlto = Integer.valueOf(parametroAlto.getText().replace(" ", "")) * figuraActual.escalaHeight;
			
			// Si la figura esta volteada horizontal o verticalmente respeta esa direccion
			if(figuraActual.getWidth() < 0)
				iAncho *= -1;
			
			if(figuraActual.getHeight() < 0)
				iAlto *= -1;
			
			figuraActual.setPosition(iX, iY);
			figuraActual.setSize(iAncho, iAlto);
			figuraActual.setStroke(Integer.valueOf(parametroStroke.getText()));
			
			// Colores
			
			if(parametroColor.getSelectedItem().toString().equals("Negro")) {
				figuraActual.setColor(Color.BLACK);
			} else if(parametroColor.getSelectedItem().toString().equals("Rojo")) {
				figuraActual.setColor(Color.RED);
			} else if(parametroColor.getSelectedItem().toString().equals("Azul")) {
				figuraActual.setColor(Color.BLUE);
			} else if(parametroColor.getSelectedItem().toString().equals("Verde")) {
				figuraActual.setColor(Color.GREEN);
			} else if(parametroColor.getSelectedItem().toString().equals("Magenta")) {
				figuraActual.setColor(Color.MAGENTA);
			} else if(parametroColor.getSelectedItem().toString().equals("Amarillo")) {
				figuraActual.setColor(Color.YELLOW);
			}
			
			figuraActual.forma();
			dibujaFiguras();
			parametros.dispose();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Verifique que ha introducido solamente números", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limpia() {
		figuras.clear();
		dibujaFiguras();
	}
}
