/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos2DCh2.Tarea;

/**
 *
 * @author Luisz
 */
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public final class Nombre3D extends Applet {

  public BranchGroup createSceneGraph() {
      
    BranchGroup nombreBranchGroup = new BranchGroup();

    Transform3D t3D = new Transform3D();
    t3D.setTranslation(new Vector3f(0.0f, 0.0f, -3.0f));
    TransformGroup transformaciones = new TransformGroup(t3D);
    nombreBranchGroup.addChild(transformaciones);
    TransformGroup objSpin = new TransformGroup();
    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    transformaciones.addChild(objSpin);

    Appearance textAppear = new Appearance();
    ColoringAttributes textColor = new ColoringAttributes();
    textColor.setColor(0.0f, 0.0f, 0.0f);
    textAppear.setColoringAttributes(textColor);
    textAppear.setMaterial(new Material());
    
    Font3D fuente = new Font3D(new Font("Helvetica", Font.PLAIN,1),new FontExtrusion());
    Text3D texto = new Text3D(fuente, "Luisz BP");
    texto.setAlignment(Text3D.ALIGN_CENTER);
    Shape3D textoShape = new Shape3D();
    textoShape.setGeometry(texto);
    textoShape.setAppearance(textAppear);
    objSpin.addChild(textoShape);
    
    Alpha rotationAlpha = new Alpha(-1, 10000);
    Transform3D yAxis = new Transform3D();
    RotationInterpolator  rotator =
        new RotationInterpolator(rotationAlpha, objSpin, yAxis,
        0.0f, (float) Math.PI*2.0f);//velocidad de giro

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    rotator.setSchedulingBounds(bounds);
    objSpin.addChild(rotator);
    

    DirectionalLight lightD = new DirectionalLight();
    lightD.setInfluencingBounds(bounds);
    lightD.setDirection(new Vector3f(0.0f, 0.0f, -1.0f));
    lightD.setColor(new Color3f(1.0f, 0.0f, 1.0f));
    transformaciones.addChild(lightD);

    AmbientLight lightA = new AmbientLight();
    lightA.setInfluencingBounds(bounds);
    transformaciones.addChild(lightA);

    return nombreBranchGroup;
  } 
  
    GraphicsConfiguration graphicsConfiguration;
    Canvas3D canvas3D;
    SimpleUniverse simpleUniverse;
    BranchGroup contentBranchGraph;
    
    public Nombre3D() {
        setLayout(new BorderLayout());

        graphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(graphicsConfiguration);

        contentBranchGraph = createSceneGraph();
        contentBranchGraph.compile();

        simpleUniverse = new SimpleUniverse(canvas3D);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        simpleUniverse.addBranchGraph(contentBranchGraph);
        add("Center", canvas3D);
        
    }

  public static void main(String[] args) {
    Frame frame = new MainFrame(new Nombre3D(), 256, 256);
  }
} 
