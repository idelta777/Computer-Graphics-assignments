package figuras;

import java.awt.Graphics2D;

public class Tetris2 extends Figura {
	
	public Tetris2(int x, int y) {
		setPosition(x, y);
		escalaWidth = 3;
		escalaHeight = 2;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		double puntos[][] = { {1, 0}, {1, 1}, {0, 1}, {0, 2}, {2, 2}, {2, 1}, {3, 1}, {3, 0}, {1, 0} };
		
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
