package figuras;

import java.awt.Graphics2D;

public class Fez extends Figura {
	
	public Fez(int x, int y) {
		setPosition(x, y);
		escalaWidth = 24;
		escalaHeight = 20;
		forma();
	}
	
	@Override
	public void forma() {
		path.reset();
		width /= escalaWidth;
		height /= escalaHeight;
		
		// F
		/*
		double puntos[][] = { {6, 8}, {0, 11}, {0, 19}, {2, 20}, {4, 19}, {4, 15}, {6, 14}, {6, 12}, {8, 11}, {8, 9}, {6, 8}, {6, 10}, {8, 9},
				              {8, 11}, {6, 10}, {4, 11}, {6, 12}, {6, 14}, {4, 13}, {4, 11}, {2, 12}, {2, 14}, {4, 13}, {6, 14}, {4, 15},
				              {2, 14}, {2, 18}, {0, 19}, {2, 20}, {2, 18}, {4, 19}, {2, 18}, {2, 12}, {0, 11}, {6, 8} }; */
		
		double puntos[][] = { {6, 8}, {0, 11}, {0, 19}, {2, 20}, {4, 19}, {4, 15}, {6, 14}, {6, 12}, {8, 11}, {8, 9}, {6, 8} };
		
		path.moveTo(x + puntos[0][0] * width, y + puntos[0][1] * height); // posicion inicial
		
		for(int i=1; i<puntos.length; i++) {
			path.lineTo(x + puntos[i][0] * width, y + puntos[i][1] * height);
		}
		
		path.closePath();
		
		// E
		double puntos2[][] = { {14, 4}, {8, 7}, {8, 15}, {10, 16}, {16, 13}, {16, 11}, {14, 10}, {14, 8}, {16, 7}, {16, 5}, {14, 4} };
		
		path.moveTo(x + puntos2[0][0] * width, y + puntos2[0][1] * height); // posicion inicial
		
		for(int i=1; i<puntos2.length; i++) {
			path.lineTo(x + puntos2[i][0] * width, y + puntos2[i][1] * height);
		}
		
		path.closePath();
		
		// Z
		double puntos3[][] = { {22, 0}, {16, 3}, {16, 5}, {18, 6}, {16, 7}, {16, 11}, {18, 12}, {24, 9}, {24, 7}, {22, 6}, {24, 5}, {24, 1},
							   {22, 0} };

		path.moveTo(x + puntos3[0][0] * width, y + puntos3[0][1] * height); // posicion inicial
		
		for(int i=1; i<puntos3.length; i++) {
			path.lineTo(x + puntos3[i][0] * width, y + puntos3[i][1] * height);
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
