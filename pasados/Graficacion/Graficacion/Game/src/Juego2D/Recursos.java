package Juego2D;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Recursos {
   //La instacia particular de esta clase
    private static Recursos unica=new Recursos();
    //Obtener la instancia única de esta clase
    public static Recursos get(){
        return unica;
    }
    private HashMap escenas=new HashMap();
    //Recuperamos una escena de nuestrios Recursos
    public Escena getEscena(String ruta){
        //Si ya tenemos la escena solo la devolvemos
        if(escenas.get(ruta)!=null){
            return (Escena) escenas.get(ruta);
        }
        //Si no tenemos ninguna escena hay que ir por ella
        BufferedImage imagenFuente=null;
        try{
            URL url=this.getClass().getClassLoader().getResource(ruta);
            if(url==null){
                falla("Falló la carga: "+ruta);
            }
            imagenFuente=ImageIO.read(url);
        }catch(IOException e){
            falla("Falló la carga: "+ruta);
        }
        //Creamos la imagen
        GraphicsConfiguration gc=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image imagen=gc.createCompatibleImage(imagenFuente.getWidth(),imagenFuente.getHeight(),Transparency.BITMASK);
        //La dibujamos
        imagen.getGraphics().drawImage(imagenFuente, 0, 0, null);
        //Creamos la escena
        Escena escena=new Escena(imagen);
        escenas.put(ruta, escena);
        return escena;
    }
    //Método que muestra los errores en pantalla y nos saca del juego
    private void falla(String s){
        System.err.println(s);
	System.exit(0);
    }
}
