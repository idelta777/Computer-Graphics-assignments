package Juego2D;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Objeto {
    //La posición actual del objeto en x
    protected double x;
    //La posición actual del objeto en y
    protected double y;
    //La escena del objeto
    protected Escena escena;
    //La velocidad horizontal actual del objeto (pixeles/segundo)
    protected double dx;
    //La velocidad vertical actual del objeto (pixeles/segundo)
    protected double dy;
    //La forma rectangulo usada por el objeto para detectar colisiones
    private Rectangle este=new Rectangle();
    //La forma rectangulo usada por los otros objetos para detectar colisiones
    private Rectangle ellos=new Rectangle();
    //Constructor que recibe una ruta, y las coordenadas de posición
    public Objeto(String rut,int x,int y) {
        this.escena=Recursos.get().getEscena(rut);
        this.x=x;
        this.y=y;
    }
    //Darle movimiento por si mismo al objeto
    public void movimiento(long tiempo){
        //Actualiza la posición del objeto basado al tiempo
        x+=(tiempo*dx)/1000;
        y+=(tiempo*dy)/1000;
    }
    //Velocidad de desplazamiento horizontal
    public void setVelocidadHorizontal(double dx){
        this.dx=dx;
    }
    //Velocidad de desplazamiento vertical
    public void setVelocidadVertical(double dy){
        this.dy=dy;
    }
    //Obtener la velocidad horizontal
    public double getVelocidadHorizontal(){
        return dx;
    }
    //Obtener la velocidad vertical
    public double getVelocidadVertical(){
        return dy;
    }
    //Dibujar escena
    public void draw(Graphics g){
        escena.draw(g,(int) x,(int) y);
    }
    //Lógica del evento (No la hay)
    public void Logica(){
        
    }
    //Obtener la posición horizontal del objeto
    public int getX(){
        return (int) x;
    }
    //Obtener la posición vertical del objeto
    public int getY(){
        return (int) y;
    }
    //Revisar si este objeto chocó con otro
    public boolean colisionaCon(Objeto aux){
        este.setBounds((int) x,(int) y,escena.getAncho(),escena.getAlto());
        ellos.setBounds((int) aux.x,(int) aux.y,aux.escena.getAncho(),aux.escena.getAlto());
        return este.intersects(ellos);
    }
    //Aviso de que este objeto chocó con otro
    public abstract void colisionoCon(Objeto aux);
}
