package figuras;

import java.awt.Graphics2D;

public class Nota extends Figura {
	
	
	public Nota(int x, int y) {
		setPosition(x, y);
		escalaWidth = 6;
		escalaHeight = 6;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		double puntos[][] = { {2, 5}, {1, 4}, {0, 5}, {1, 6}, {2, 5}, {2, 1}, {6, 0}, {6, 4}, {5, 5}, {4, 4}, {5, 3}, {6, 4}, {6, 0}, {2, 1}, {2, 5}};
		
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
