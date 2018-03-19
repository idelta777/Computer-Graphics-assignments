package figuras;

import java.awt.Graphics2D;

public class Triforce extends Figura {
	public Triforce(int x, int y) {
		setPosition(x, y);
		escalaWidth = 4;
		escalaHeight = 4;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		double puntos[][] = { {2, 0}, {1, 2}, {2, 4}, {3, 2}, {1, 2}, {0, 4}, {4, 4}, {2, 0} };
		
		path.moveTo(x + puntos[0][0] * width, y + puntos[0][1]); // posicion inicial
		
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
