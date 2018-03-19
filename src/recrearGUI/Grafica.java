package recrearGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grafica extends JPanel implements MouseListener, MouseMotionListener {
	
	int xInicial, yInicial;
	int x, y;
	ArrayList<Point> inicialesRecta = new ArrayList<Point>();
	ArrayList<Point> finalesRecta = new ArrayList<Point>();
	
	ArrayList<Point> inicialesCirculo = new ArrayList<Point>();
	ArrayList<Point> finalesCirculo = new ArrayList<>(); // guarda el ancho y alto
	
	ArrayList<Point> inicialesCuadrado = new ArrayList<Point>();
	ArrayList<Point> finalesCuadrado = new ArrayList<Point>(); // guarda el ancho y alto
	
	public Grafica() {
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
	
	public void dibujaFiguras(int x, int y) {
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
		
		if(BarraHerramientas.OPCION == 1) { 		// linea recta
			g.drawLine(xInicial, yInicial, x, y);
		} else if(BarraHerramientas.OPCION == 2) {	// circulo
			g.drawOval(xInicial, yInicial, x - xInicial, y - yInicial);
		} else if(BarraHerramientas.OPCION == 3) {	// cuadrado
			g.drawRect(xInicial, yInicial, x - xInicial, y - yInicial);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		xInicial = arg0.getX();
		yInicial = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(BarraHerramientas.OPCION == 1) {
			inicialesRecta.add(new Point(xInicial, yInicial));
			finalesRecta.add(new Point(arg0.getX(), arg0.getY()));
		} else if(BarraHerramientas.OPCION == 2) {
			inicialesCirculo.add(new Point(xInicial, yInicial));
			finalesCirculo.add(new Point(arg0.getX() - xInicial, arg0.getY() - yInicial));
		} else if(BarraHerramientas.OPCION == 3) {
			inicialesCuadrado.add(new Point(xInicial, yInicial));
			finalesCuadrado.add(new Point(arg0.getX() - xInicial, arg0.getY() - yInicial));
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x = arg0.getX();
		y = arg0.getY();
		this.dibujaFiguras(x, y);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
