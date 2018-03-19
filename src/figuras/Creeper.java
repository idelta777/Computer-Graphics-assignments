package figuras;

import java.awt.Graphics2D;

public class Creeper extends Figura {
	public Creeper(int x, int y) {
		setPosition(x, y);
		escalaWidth = 8;
		escalaHeight = 8;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		double puntos[][] = { {0, 0}, {0, 8}, {8, 8}, {8, 0} };
		
		path.moveTo(x + puntos[0][0] * width, y + puntos[0][1]); // posicion inicial
		
		for(int i=1; i<puntos.length; i++) {
			path.lineTo(x + puntos[i][0] * width, y + puntos[i][1] * height);
		}
		
		path.closePath();
		
		double puntos2[][] = { {3, 3}, {3, 1}, {1, 1}, {1, 3}, {3, 3}, {3, 4}, {2, 4}, {2, 7}, {3, 7}, {3, 6}, {5, 6},
							   {5, 7}, {6, 7}, {6, 4}, {5, 4}, {5, 3}, {7, 3}, {7, 1}, {5, 1}, {5, 3}, {3, 3} };
		
		path.moveTo(x + puntos2[0][0] * width, y + puntos2[0][1] * height); // posicion inicial
		
		for(int i=1; i<puntos2.length; i++) {
			path.lineTo(x + puntos2[i][0] * width, y + puntos2[i][1] * height);
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
