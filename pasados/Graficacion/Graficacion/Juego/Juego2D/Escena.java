package Juego2D;

import java.awt.Graphics;
import java.awt.Image;

public class Escena {
    //La imagen que vamos a dibujar
    private Image imagen;
    //Creamos una nueva escena a partir de una imagen
    public Escena(Image imagen){
        this.imagen=imagen;
    }
    //Obtener el ancho de la escena
    public int getAncho(){
        return imagen.getWidth(null);
    }
    //Obtener el alto
    public int getAlto(){
        return imagen.getHeight(null);
    }
    //Dibujamos la escena
    public void draw(Graphics g,int x,int y){
        g.drawImage(imagen, x, y, null);
    }
}
