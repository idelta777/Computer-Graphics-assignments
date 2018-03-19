package figuras;

import java.awt.Graphics2D;

public class Triangulo extends Figura {
	public Triangulo(int x, int y) {
		setPosition(x, y);
		escalaHeight = 1;
		escalaWidth = 2;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		double puntos[][] = {{1, 0}, {0, 1}, {2, 1}};
		
		path.moveTo(x + puntos[0][0] * width, y + puntos[0][1] * height); // posicion inicial
		
		for(int i=1; i<puntos.length; i++) {
			path.lineTo(x + puntos[i][0] * width, y + puntos[i][1] * height);
		}
		
		path.closePath();
	}
	
	@Override
	public void draw(Graphics2D g, boolean cambiandoTam) {
		if(cambiandoTam)
			forma();
		g.draw(path);
	}
}
