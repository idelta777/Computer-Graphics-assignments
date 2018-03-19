package Graficos2DCh2;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelDibujo extends JPanel{
    
	//Variables globales
	private int a = 60,b = 50,p = 50,n = 90;
        private Color figura = Color.CYAN, fondo = Color.black;
	
	public PanelDibujo(){
            //Valores iniciales del panel
		this.setPreferredSize(new Dimension(690,690));
	}
	
	public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            this.setBackground(fondo);
            g2.setColor(figura);
            //Trasladar el contexto gráfico al centro del panel
	    g2.translate(345,345);
	    int x1=a;
	    int y1=0;
	    int x2;
	    int y2;
	    for (int i=0; i<1000; i++) {
	      double t = i*Math.PI / n;
                //Fórmula geométrica para el espirógrafo
	      x2 = (int)((a+b)*Math.cos(t) - p*Math.cos((a+b)*t/b));
	      y2 = (int)((a+b)*Math.sin(t) - p*Math.sin((a+b)*t/b));     
	      g2.drawLine(x1, y1, x2, y2);
                //El punto final se convierte en el inicio de la siguiente iteración
	      x1 = x2;
	      y1 = y2;
	    }
	}
	
        //Métodos accesores
	public void setA(int a){
		this.a = a;
	}
	
	public void setB(int b){
		this.b = b;
	}
	
	public void setP(int p){
		this.p = p;
	}
	
	public void setN(int n){
		this.n = n;
	}

    public void setFigura(Color figura) {
        this.figura = figura;
    }

    public void setFondo(Color fondo) {
        this.fondo = fondo;
    }
}
