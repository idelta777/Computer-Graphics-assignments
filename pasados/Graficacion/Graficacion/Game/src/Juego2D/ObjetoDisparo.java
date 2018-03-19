package Juego2D;

public class ObjetoDisparo extends Objeto{
    //La velocidad vertical del disparo
    private double velV=-300;
    //El juego donde el disparo existe
    private Juego juego;
    //Bandera para indicar si el disparo hizo contacto o fue usado
    private boolean usado=false;
    //Creamos un objeto tipo "DISPARO"
    public ObjetoDisparo(Juego juego,String ruta,int x,int y){
        super(ruta,x,y);
        this.juego=juego;
        dy=velV;
    }
    //Movimiento del disparo
    public void movimiento(long tiempo){
        //Movimiento normal
        super.movimiento(tiempo);
        //Si no le dimos a nada y el disparo salió de pantalla lo quitamos
        if(y<-100){
            juego.eliminaObjeto(this);
        }
    }
    //Revisamos si hicimos contacto con algún objeto
    public void colisionoCon(Objeto aux){
        //Prevenimos dobles colisiones, si ya le dimos a algo quitamos el disparo y el elemento
        if(usado){
            return;
        }
        //Si le dimos a un alien lo eliminamos de pantalla
        if(aux instanceof ObjetoAlien){
            //Eliminamos
            juego.eliminaObjeto(this);
            juego.eliminaObjeto(aux);
            //Avisamos que el alien fue destruido
            juego.avisaMuerteAlien();
            usado=true;
        }
    }
}
