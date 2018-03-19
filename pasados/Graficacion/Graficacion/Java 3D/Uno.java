/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos2DCh2.Tarea;

/**
 *
 * @author ASUS U46S
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.Screen3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Uno extends Applet implements ChangeListener, ActionListener {
    //Universo
    SimpleUniverse u;
    boolean aplicacion;
    String snapImageString = "Capturar Pantalla";
    //Canvas 3D
    Canvas3D canvas;
    OffScreenCanvas3D offCanvas;
    JTabbedPane tabbedPane;
    //Vista 3D
    View view;
    String outFileBase = "Humano";
    int outFileSeq = 0;
    //Los TransformGroups
    TransformGroup cuerpo;
    TransformGroup hombrod;
    TransformGroup hombroi;
    TransformGroup codod;
    TransformGroup codoi;
    TransformGroup base;
    //Transformaciones
    int hdRot=0;
    AxisAngle4f hdAA=new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
    JSlider hdSlider;
    JLabel hdLabel;
    int cdRot=0;
    AxisAngle4f cdAA=new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
    JSlider cdSlider;
    JLabel cdLabel;
    int hiRot=0;
    AxisAngle4f hiAA=new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f);
    JSlider hiSlider;
    JLabel hiLabel;
    int ciRot=0;
    AxisAngle4f ciAA=new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f);
    JSlider ciSlider;
    JLabel ciLabel;
    
    float escOffScreen=1.5f;
    //Objetos Temporables Reutilizables
    Transform3D tmpTrans = new Transform3D();
    Vector3f tmpVector = new Vector3f();
    AxisAngle4f tmpAxisAngle = new AxisAngle4f();
    Cylinder tmpCil;
    Sphere tmpEsf;
    TransformGroup tmpTG;
    //Colores
    Color3f rojo=new Color3f(1f,0f,0f);
    Color3f negro=new Color3f(0f,0f,0f);
    //Puntos en el plano
    Point3f origen=new Point3f();
    Vector3f yAxis=new Vector3f(0.0f, 1.0f, 0.0f);
    //Número que solo podrá mostrar 2 dígitos
    NumberFormat nf;
    //Regresa el TransformGroup que se va a estar editando
    void creaHumano(){
        cuerpo=new TransformGroup();
        //Posicionamos el cuerpo
        tmpVector.set(0.0f, -1.5f, 0.0f);
        tmpTrans.set(tmpVector);
        cuerpo.setTransform(tmpTrans);
        //Apariencia
        Material material=new Material(rojo,negro,rojo,negro,30);
        Appearance apariencia=new Appearance();
        apariencia.setMaterial(material);
        //Cuerpo
        tmpTG=new TransformGroup();
        tmpVector.set(0.0f, 1.5f, 0.0f);
        tmpTrans.set(tmpVector);
        tmpTG.setTransform(tmpTrans);
        tmpCil = new Cylinder(0.75f, 3.0f, apariencia);
        tmpTG.addChild(tmpCil);
        cuerpo.addChild(tmpTG);
        
        //Hombro derecho
        hombrod=new TransformGroup();
        hombrod.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        hombrod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tmpVector.set(-0.95f, 2.9f, -0.2f);
        tmpTrans.set(tmpVector);
        hombrod.setTransform(tmpTrans);
        tmpEsf = new Sphere(0.22f, apariencia);
        hombrod.addChild(tmpEsf);
        tmpTG = new TransformGroup();
        tmpVector.set(0.0f, -0.5f, 0.0f);
        tmpTrans.set(tmpVector);
        tmpTG.setTransform(tmpTrans);
        tmpCil = new Cylinder(0.2f, 1.0f, apariencia);
        tmpTG.addChild(tmpCil);
        hombrod.addChild(tmpTG);
        cuerpo.addChild(hombrod);
        
        //Codo derecho
        codod=new TransformGroup();
        codod.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        codod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tmpVector.set(0.0f, -1.054f, 0.0f);
        tmpTrans.set(tmpVector);
        codod.setTransform(tmpTrans);
        tmpEsf = new Sphere(0.22f, apariencia);
        codod.addChild(tmpEsf);
        tmpTG = new TransformGroup();
        tmpVector.set(0.0f, -0.5f, 0.0f);
        tmpTrans.set(tmpVector);
        tmpTG.setTransform(tmpTrans);
        tmpCil = new Cylinder(0.2f, 1.0f, apariencia);
        tmpTG.addChild(tmpCil);
        codod.addChild(tmpTG);
        hombrod.addChild(codod);
        
        //Hombro izquierdo
        hombroi = new TransformGroup();
        hombroi.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        hombroi.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tmpVector.set(0.95f, 2.9f, -0.2f);
        tmpTrans.set(tmpVector);
        hombroi.setTransform(tmpTrans);
        tmpEsf = new Sphere(0.22f, apariencia);
        hombroi.addChild(tmpEsf);
        tmpTG = new TransformGroup();
        tmpVector.set(0.0f, -0.5f, 0.0f);
        tmpTrans.set(tmpVector);
        tmpTG.setTransform(tmpTrans);
        tmpCil = new Cylinder(0.2f, 1.0f, apariencia);
        tmpTG.addChild(tmpCil);
        hombroi.addChild(tmpTG);
        cuerpo.addChild(hombroi);
        
        //Codo izquierdo
        codoi = new TransformGroup();
        codoi.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        codoi.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tmpVector.set(0.0f, -1.054f, 0.0f);
        tmpTrans.set(tmpVector);
        codoi.setTransform(tmpTrans);
        tmpEsf = new Sphere(0.22f, apariencia);
        codoi.addChild(tmpEsf);
        tmpTG = new TransformGroup();
        tmpVector.set(0.0f, -0.5f, 0.0f);
        tmpTrans.set(tmpVector);
        tmpTG.setTransform(tmpTrans);
        tmpCil = new Cylinder(0.2f, 1.0f, apariencia);
        tmpTG.addChild(tmpCil);
        codoi.addChild(tmpTG);
        hombroi.addChild(codoi);
        
        //Base
        base = new TransformGroup();
        tmpVector.set(0.0f, 3.632f, 0.0f);
        tmpTrans.set(tmpVector);
        base.setTransform(tmpTrans);
        tmpEsf = new Sphere(0.5f, apariencia);
        base.addChild(tmpEsf);
        cuerpo.addChild(base);
    }
    
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        Object source = e.getSource();
        if (action == snapImageString) {
            Point loc = canvas.getLocationOnScreen(); 
            offCanvas.setOffScreenLocation(loc);
            Dimension dim = canvas.getSize();
            dim.width *= escOffScreen;
            dim.height *= escOffScreen;
            nf.setMinimumIntegerDigits(3);
            offCanvas.snapImageFile(outFileBase + nf.format(outFileSeq++), dim.width,dim.height);
            nf.setMinimumIntegerDigits(0);
        }
    }
    //Rotación Hombro Derecho
    public void setRotHombroD(int rotation) {
        hdRot = rotation;
        hdAA.angle = (float) Math.toRadians(hdRot);
        hombrod.getTransform(tmpTrans);
        tmpTrans.setRotation(hdAA);
        hombrod.setTransform(tmpTrans);
    }
    //Rotación Codo Derecho
    public void setRotCodoD(int rotation) {
        float angle = (float) Math.toRadians(rotation);
        cdRot = rotation;
        cdAA.angle = (float) Math.toRadians(cdRot);
        codod.getTransform(tmpTrans);
        tmpTrans.setRotation(cdAA);
        codod.setTransform(tmpTrans);
    }
    //Rotación Hombro Izquierdo
    public void setRotHombroI(int rotation) {
        hiRot = rotation;
        hiAA.angle = (float) Math.toRadians(hiRot);
        hombroi.getTransform(tmpTrans);
        tmpTrans.setRotation(hiAA);
        hombroi.setTransform(tmpTrans);
    }
    //Rotación Codo Izquierdo
    public void setRotCodoI(int rotation) {
        float angle = (float) Math.toRadians(rotation);
        ciRot = rotation;
        ciAA.angle = (float) Math.toRadians(ciRot);
        codoi.getTransform(tmpTrans);
        tmpTrans.setRotation(ciAA);
        codoi.setTransform(tmpTrans);
    }
    //Listener Sliders
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int value = source.getValue();
        if (source == hdSlider) {
            setRotHombroD(value);
            hdLabel.setText(Integer.toString(value));
        } else if (source == cdSlider) {
            setRotCodoD(value);
            cdLabel.setText(Integer.toString(value));
        } else if (source == hiSlider) {
            setRotHombroI(value);
            hiLabel.setText(Integer.toString(value));
        } else if (source == ciSlider) {
            setRotCodoI(value);
            ciLabel.setText(Integer.toString(value));
        }
    }
    //Branch Group
    BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        TransformGroup objScale = new TransformGroup();
        Transform3D scaleTrans = new Transform3D();
        scaleTrans.set(1 / 3.5f); 
        objScale.setTransform(scaleTrans);
        objRoot.addChild(objScale);

        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objScale.addChild(objTrans);
        
        creaHumano(); 
        objTrans.addChild(cuerpo);
        
        BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
        
        Background bg = new Background(new Color3f(1.0f, 1.0f, 1.0f));
        bg.setApplicationBounds(bounds);
        objTrans.addChild(bg);

        MouseRotate mr = new MouseRotate();
        mr.setTransformGroup(objTrans);
        mr.setSchedulingBounds(bounds);
        mr.setFactor(0.007);
        objTrans.addChild(mr);

        Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        
        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f light1Direction = new Vector3f(0.0f, -0.2f, -1.0f);
        
        DirectionalLight light1 = new DirectionalLight(light1Color,light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        return objRoot;
    }
    
    public Uno() {
        this(false, 1.0f);
    }

    public Uno(boolean aplicacion, float escOffScreen) {
        this.aplicacion = aplicacion;
        this.escOffScreen = escOffScreen;
    }
    
    public void init() {

        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);

        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        
        canvas = new Canvas3D(config);

        add("Center", canvas);
        
        u = new SimpleUniverse(canvas);

        if (aplicacion) {
            offCanvas = new OffScreenCanvas3D(config, true);
            Screen3D sOn = canvas.getScreen3D();
            Screen3D sOff = offCanvas.getScreen3D();
            Dimension dim = sOn.getSize();
            dim.width *= escOffScreen;
            dim.height *= escOffScreen;
            sOff.setSize(dim);
            sOff.setPhysicalScreenWidth(sOn.getPhysicalScreenWidth()* escOffScreen);
            sOff.setPhysicalScreenHeight(sOn.getPhysicalScreenHeight()* escOffScreen);
            u.getViewer().getView().addCanvas3D(offCanvas);
        }
        //Escena
        BranchGroup scene = createSceneGraph();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
        view = u.getViewer().getView();
        add("East", guiPanel());
    }

 //creamos el panel giratorio
  JPanel guiPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        //Rotación hombro derecho
        panel.add(new JLabel("Mover el hombro derecho"));
        hdSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hdRot);
        hdSlider.addChangeListener(this);
        hdLabel = new JLabel(Integer.toString(hdRot));
        panel.add(hdSlider);
        panel.add(hdLabel);

        //Rotacion codo derecho
        panel.add(new JLabel("Mover el codo derecho"));
        cdSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, cdRot);
        cdSlider.addChangeListener(this);
        cdLabel = new JLabel(Integer.toString(cdRot));
        panel.add(cdSlider);
        panel.add(cdLabel);
        
        //Rotación hombro izquierdo
        panel.add(new JLabel("Mover el hombro izquierdo"));
        hiSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hiRot);
        hiSlider.addChangeListener(this);
        hiLabel = new JLabel(Integer.toString(hiRot));
        panel.add(hiSlider);
        panel.add(hiLabel);

        //Rotación codo izquierdo
        panel.add(new JLabel("Mover el codo izquierdo"));
        ciSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, ciRot);
        ciSlider.addChangeListener(this);
        ciLabel = new JLabel(Integer.toString(ciRot));
        panel.add(ciSlider);
        panel.add(ciLabel);

        if (aplicacion) {
            JButton snapButton = new JButton(snapImageString);
            snapButton.setActionCommand(snapImageString);
            snapButton.addActionListener(this);
            panel.add(snapButton);
        }

        return panel;
  }

  public void destroy() {
    u.removeAllLocales();
  }
  //método principal
  public static void main(String[] args) {
    float initOffScreenScale = 2.5f;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-s")) {
        if (args.length >= (i + 1)) {
          initOffScreenScale = Float.parseFloat(args[i + 1]);
          i++;
        }
      }
    }
    new MainFrame(new Uno(true, initOffScreenScale), 950, 600);
  }

}
class OffScreenCanvas3D extends Canvas3D{

  OffScreenCanvas3D(GraphicsConfiguration graphicsConfiguration,
      boolean offScreen) {

    super(graphicsConfiguration, offScreen);
  }

  private BufferedImage doRender(int width, int height) {

    BufferedImage bImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);

    ImageComponent2D buffer = new ImageComponent2D(
        ImageComponent.FORMAT_RGB, bImage);
    //buffer.setYUp(true);

    setOffScreenBuffer(buffer);
    renderOffScreenBuffer();
    waitForOffScreenRendering();
    bImage = getOffScreenBuffer().getImage();
    return bImage;
  }

  void snapImageFile(String filename, int width, int height) {
    BufferedImage bImage = doRender(width, height);

    try {
      FileOutputStream fos = new FileOutputStream(filename + ".jpg");
      BufferedOutputStream bos = new BufferedOutputStream(fos);

      JPEGImageEncoder jie = JPEGCodec.createJPEGEncoder(bos);
      JPEGEncodeParam param = jie.getDefaultJPEGEncodeParam(bImage);
      param.setQuality(1.0f, true);
      jie.setJPEGEncodeParam(param);
      jie.encode(bImage);

      bos.flush();
      fos.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
