package Juego2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends Canvas{
    //Acelera el cambio de gráficos en el frame
    private BufferStrategy estrategia;
    //Bandera que indica si el juego está corriendo
    private boolean jugando=true;
    //La lista de todos los objetos que están en el juego
    private ArrayList objetos=new ArrayList();
    //La lista de objetos que están muertos
    private ArrayList removidos=new ArrayList();
    //El objeto NAVE
    private Objeto nave;
    //La velocidad a la que se mueve la nave (pixeles/segundo)
    private double velMov=300;
    //El momento en el que es diparada la "última" bala
    private long ultDisp=0;
    //Intervalo de disparos
    private long intDisparos=500;
    //Número de aliens que quedan en pantalla
    private int aliensP;
    //Mensaje que mostramos en pantalla
    private String mensaje="";
    //Bandera que espera una tecla para iniciar el juego
    private boolean presionaTecla=true;
    //Bandera que indica si la flecha izquierda ha sido apretada
    private boolean izqPres=false;
    //Bandera que indica si la flecha derecha ha sido presionada
    private boolean derPres=false;
    //Bandera que indica si la barra espaciadora se presionó
    private boolean disparoPres=false;
    //Bandera que se activa después de algún evento en el juego
    private boolean logicaJuego=false;
    
    //Constructor del juego y correrlo
    public Juego(){
        //Creamos el Frame
        JFrame contenedor=new JFrame("Aliens");
        //Lo preparamos
        JPanel panel=(JPanel) contenedor.getContentPane();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);
        //Establecer el tamaño del canvas y ponerlo en el panel
        setBounds(0,0,800,600);
        panel.add(this);
        //Ignoramos el repaint, eso lo vamos a hacer nosotros
        setIgnoreRepaint(true);
        //Hacemos visible el juego
        contenedor.pack();
        contenedor.setResizable(false);
        contenedor.setVisible(true);
        /*Añadimos un listener para cerrar el juego cuando el usuario 
         cierre la ventana*/
        contenedor.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		System.exit(0);
            }
	});
        //Añadimos el listener del teclado
        addKeyListener(new KeyInputHandler());
        //Los eventos del teclado son manejados por nosotros
        requestFocus();
        //Creamos el Buffer
        createBufferStrategy(2);
        estrategia=getBufferStrategy();
        //Inicializamos los objetos
        iniciaObjetos();
    }
    //Método para iniciar un jeugo completamente nuevo
    private void iniciaJuego(){
        //Creamos un uevo atreglo de objetos
        objetos.clear();
        iniciaObjetos();
        //Quitamos cualquier evento del teclado que esté inicializado
        izqPres=false;
        derPres=false;
        disparoPres=false;
    }
    //Método que inicializa los componentes del juego
    private void iniciaObjetos(){
        //Creamos la nave y la ponemos en pantalla
        nave=new ObjetoNave(this,"ship.png",370,550);
        objetos.add(nave);
        //Creamos los aliens
        aliensP=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<12;j++){
                Objeto alien=new ObjetoAlien(this,"ufo.png",100+(j*50),(50)+i*30);
                objetos.add(alien);
                aliensP++;
            }
        }
    }
    //Activamos la bandera de la lógica del juego
    public void actualizaLogica(){
        logicaJuego=true;
    }
    //Borrar un objeto del juego
    public void eliminaObjeto(Objeto objeto){
        removidos.add(objeto);
    }
    //Notificación de que el jugador ha perdido
    public void avisaMuerte(){
        mensaje="¡PERDISTE! INTENTALO OTRA VEZ";
        presionaTecla=true;
        iniciaJuego();
    }
    //Notificación de que el jugador ha ganado
    public void avisaVictoria(){
        mensaje="¡BIEN HECHO! ¡GANASTE!";
        presionaTecla=true;
        iniciaJuego();
    }
    //Notificación de que se ha destruido un alien
    public void avisaMuerteAlien(){
        //Reduce el número de aliens, si es 0 el jugador gana
        aliensP--;
        if(aliensP==0){
            avisaVictoria();
        }
        //Si aún hay aliens vamos a incrementar la velocidad 2%
        for(int i=0;i<objetos.size();i++){
            Objeto objeto=(Objeto) objetos.get(i);
            //Si el objeto que sacamos es un alien aumentamos su velocidad
            if(objeto instanceof ObjetoAlien){
                objeto.setVelocidadHorizontal(objeto.getVelocidadHorizontal()*1.02);
            }
        }
    }
    //Intento de disparo del jugador
    public void intentoDisparo(){
        //Checamos que haya un tiempo de espera entre disparos
        if(System.currentTimeMillis()-ultDisp<intDisparos){
            return;
        }
        //Si ya esperamos lo suficiente, creamos el disparo
        ultDisp=System.currentTimeMillis();
        ObjetoDisparo disparo=new ObjetoDisparo(this,"brillo.png",nave.getX()+10,nave.getY()-30);
        objetos.add(disparo);
    }
    //El ciclo principal del juego
    public void cicloJuego(){
        long ultimociclo=System.currentTimeMillis();
        //El ciclo se mantiene hasta que el juego termina
        while(jugando){
            long tiempo=System.currentTimeMillis()-ultimociclo;
            ultimociclo=System.currentTimeMillis();
            //Ponemos el fondo
            Graphics2D g=(Graphics2D) estrategia.getDrawGraphics();
            Color color=new Color(0,0,0);
            g.setColor(color);
            g.fillRect(0,0,800,600);
            //Vamos dandole movimiento a cada objeto
            if(!presionaTecla){
                for(int i=0;i<objetos.size();i++){
                    Objeto objeto=(Objeto) objetos.get(i);
                    objeto.movimiento(tiempo);
                }
            }
            //Ahora vamos a dibujar todos los objetos que va a haber en el juego
            for(int i=0;i<objetos.size();i++){
                Objeto objeto=(Objeto) objetos.get(i);
                objeto.draw(g);
            }
            //Comparamos todos los objetos para ver si hay colisiones
            for(int i=0;i<objetos.size();i++){
                for(int j=i+1;j<objetos.size();j++){
                    Objeto a=(Objeto) objetos.get(i);
                    Objeto b=(Objeto) objetos.get(j);
                    if(a.colisionaCon(b)){
                        a.colisionoCon(b);
                        b.colisionoCon(a);
                    }
                }
            }
            //Quitamos todos los objetos que han sido marcados como muertos
            objetos.removeAll(removidos);
            removidos.clear();
            //Checamos si ha ocurrido algun evento en los objetos para cambiar la logica
            if(logicaJuego){
                for(int i=0;i<objetos.size();i++){
                    Objeto objeto=(Objeto) objetos.get(i);
                    objeto.Logica();
                }
                logicaJuego=false;
            }
            //Si estamos esperando a que se presione la tecla mandamos el mensaje a pantalla
            if(presionaTecla){
                g.setColor(Color.WHITE);
                g.drawString(mensaje,(800-g.getFontMetrics().stringWidth(mensaje))/2,250);
                g.drawString("Presiona una tecla",(800-g.getFontMetrics().stringWidth("Presiona una tecla"))/2,300);
            }
            //Ya que dibujamos vaciamos los graficos y mostramos la escena
            g.dispose();
            estrategia.show();
            //Aquí manejamos el movimiento de la nave
            nave.setVelocidadHorizontal(0);
            if((izqPres)&&(!derPres)){
                nave.setVelocidadHorizontal(-velMov);
            }else if((derPres)&&(!izqPres)){
                nave.setVelocidadHorizontal(velMov);
            }
            //Si apretamos la tecla de disparo, disparamos
            if(disparoPres){
                intentoDisparo();
            }
            //Finalmente dormimos el juego para que no se haga lento
            try {
                Thread.sleep(10);
            }catch(Exception e) {
            
            }
        }
    }
    //Una clase para manejar los eventos del teclado
    private class KeyInputHandler extends KeyAdapter{
        //El número de veces que se va apresionar la tecla antes de empezar un juego
        private int cuenta=1;
        //Notificacion de que una tecla ha sido presionada
        public void keyPressed(KeyEvent e){
            //Si estamos esperando por "cualquier tecla" entonces no hacemos nada cuando se presione una tecla
            if(presionaTecla){
                return;
            }
            if (e.getKeyCode()==KeyEvent.VK_LEFT) {
		izqPres=true;
            }
            if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
		derPres=true;
            }
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {
		disparoPres=true;
            }
        }
        //Notificación de que una tecla ha sido dejada de apretar
        public void keyReleased(KeyEvent e){
            if(presionaTecla){
                return;
            }
            if (e.getKeyCode()==KeyEvent.VK_LEFT) {
		izqPres=false;
            }
            if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
		derPres=false;
            }
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {
		disparoPres=false;
            }
        }
        //Que hacer en caso de teclear una tecla diferente a los controles
        public void keyTyped(KeyEvent e){
            if(cuenta==1){
                presionaTecla=false;
                //iniciaJuego();
                cuenta=0;
            }else{
                cuenta++;
            }
            //Si apretamos la tecla "ESC" entonces terminamos el juego
            if(e.getKeyChar()==27){
                System.exit(0);
            }
        }
    }
    //El método main
    public static void main(String[] args){
        Juego g=new Juego();
        g.cicloJuego();
    }
}
