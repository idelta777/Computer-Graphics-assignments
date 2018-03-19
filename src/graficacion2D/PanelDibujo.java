package graficacion2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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

public class PanelDibujo extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
	
	int xInicial, yInicial;
	ArrayList<Figura> figuras = new ArrayList<Figura>();;	// arreglo que contendra las figuras presentes a dibujar
	Figura figuraActual;
	boolean arrastrado = false;
	
	JFrame parametros;
	JTextField parametroX, parametroY, parametroAncho, parametroAlto, parametroStroke;
	JButton aplicaCambios;
	JComboBox parametroColor;
	
	int xFinal, yFinal, widthFinal, heightFinal;
	
	public PanelDibujo() {
		
		figuras.clear();
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	// Solo pinta las figuras en pantalla
	private void dibujaFiguras() {
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.RED);
		
		g.setColor(Color.BLACK);
		
		for(int i=0; i<figuras.size(); i++) {
			g.setStroke(new BasicStroke(figuras.get(i).getStroke()));
			g.setColor(figuras.get(i).getColor());
			figuras.get(i).draw(g, false);
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
	public void mouseDragged(MouseEvent arg0) {
		if(!arrastrado)
			figuras.add(figuraActual);
		
		arrastrado = true;
		
		figuraActual.setSize(arg0.getX() - xInicial, arg0.getY() - yInicial);
		creaFigura(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point click = new Point(arg0.getX(), arg0.getY());

		for(int i=0; i<figuras.size(); i++) {
			if(figuras.get(i).getRectangle().contains(click)) {
				figuraActual = figuras.get(i);
				parametrosFigura();
				break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		arrastrado = false;
		
		xInicial = arg0.getX();
		yInicial = arg0.getY();
		
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.ESTRELLA))
			figuraActual = new Estrella(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.TRIANGULO))
			figuraActual = new Triangulo(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.NOTA))
			figuraActual = new Nota(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.TRIFORCE))
			figuraActual = new Triforce(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.CUBO))
			figuraActual = new Cubo(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.CREEPER))
			figuraActual = new Creeper(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.FEZ))
			figuraActual = new Fez(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.TETRIS_1))
			figuraActual = new Tetris1(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.TETRIS_2))
			figuraActual = new Tetris2(xInicial, yInicial);
		if(VentanaPrincipal.FIGURA.equals(VentanaPrincipal.TETRIS_3))
			figuraActual = new Tetris3(xInicial, yInicial);
		
		//figuras.add(figuraActual);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		figuraActual.setRectangleValues(xFinal, yFinal, widthFinal, heightFinal);
		dibujaFiguras();
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
