package Juego2D;

public class ObjetoAlien extends Objeto {
    //La velocidad a la que los aliens se meuven horizontalmente
    private double velH=75;
    //El jeugo donde el se va a colocar el objeto, o donde se encuentra el objeto
    private Juego juego;
    //Crea un nuevo objeto "ALIEN"
    public ObjetoAlien(Juego juego,String ruta,int x,int y){
        super(ruta,x,y);
        this.juego=juego;
        dx=-velH;
    }
    //Darle movimiento a los aliens
    public void movimiento(long tiempo){
        /*Si llegamos al lado izquierdo de la pantalla y nos estamos 
        moviendo hacia allá solicitamos cambio en los eventos*/
        if((dx<0)&&(x<10)){
            juego.actualizaLogica();
        }
        /*Si llegamos al lado derecho de la pantalla y nos estamos 
        moviendo hacia allá solicitamos cambio en los eventos*/
        if((dx>0)&&(x>750)){
            juego.actualizaLogica();      
        }
        //Seguimos moviendo el objeto normalmente
        super.movimiento(tiempo);
    }
    //Actualiza la lógica del juego sobre los aliens
    public void Logica(){
        //Cambiar de dirección y bajar un poco
        dx=-dx;
        y+=10;
        //Si llegamos al sur de la pantalla entonces el jugador pierde
        if(y>750){
            juego.avisaMuerte();
        }
    }
    //Aviso de que un alien colisionó con otro objeto
    public void colisionoCon(Objeto aux){
        //Estas colisiones son manejadas donde sea
    }
}
