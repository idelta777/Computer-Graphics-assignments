package Graficos2DCh2;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelTablero extends JPanel{
    
    private int lado, x, y, filas, columnas, valor = 1;
    private Color color1, color2, fondo;
    
    public PanelTablero() {
        this.setPreferredSize(new Dimension(1200,700));
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        int sw = 0;
        this.setBackground(fondo);
        y = 50;
        x = 200;
        filas = valor;
        columnas = filas;
        lado = 800 / valor;
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                if(sw % 2 == 0)
                    g2.setColor(color1);
                else
                    g2.setColor(color2);
                g2.fillRect(x, y, lado, lado);
                x = x + lado;
                sw++;
            }
            sw++;
            x = 200;
            y = y + lado;
        }
        
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public void setFondo(Color fondo) {
        this.fondo = fondo;
    }
}