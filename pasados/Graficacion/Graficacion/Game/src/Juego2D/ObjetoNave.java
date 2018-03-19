
package Juego2D;

public class ObjetoNave extends Objeto{
    //El juego donde la nave va aser colocada, o donde se encuentra
    private Juego juego;
    //Creamos un objeto tipo "NAVE"
    public ObjetoNave(Juego juego,String ruta,int x,int y){
        super(ruta,x,y);
        this.juego=juego;
    }
    //Aplicamos movimiento al objeto
    public void movimiento(long tiempo){
        /*Si nos estamos moviendo a la izquierda y hemos llegado al extremo
         izquierdo la nave no deberá moverse */
        if ((dx<0) && (x<10)) {
            return;
	}
        /*Si nos estamos moviendo a la derecha y hemos llegado al extremo
         derecho la nave no deberá moverse */
        if ((dx>0) && (x>750)) {
            return;
	}
        //Nos movemos normal
	super.movimiento(tiempo);
    }
    //Revisamos si la nave no ha chocado con algun alien
    public void colisionoCon(Objeto aux){
        //Si es alien el jugador pierde
        if(aux instanceof ObjetoAlien){
            juego.avisaMuerte();
        }
    }
}
