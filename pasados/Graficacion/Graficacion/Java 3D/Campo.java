/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos2DCh2.Tarea;

/**
 *
 * @author ASUS U46S
 */
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
//EMANUEL CEJUDO CEJUDO
//LUIS ALBERTO BAUTISTA PERDOMO
public class Campo extends Frame implements ActionListener {
  GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
  Canvas3D canvas = new Canvas3D(config);
  Button salir = new Button("SALIR");

  public BranchGroup construyeVista(Canvas3D c) {
    BranchGroup verBranch = new BranchGroup();
    Transform3D trans = new Transform3D();
    trans.set(new Vector3f(0.0f, 0.0f, 7.0f));
    TransformGroup TG = new TransformGroup(trans);
    ViewPlatform vista = new ViewPlatform();
    PhysicalBody cuerpo = new PhysicalBody();
    PhysicalEnvironment ambiente = new PhysicalEnvironment();
    TG.addChild(vista);
    verBranch.addChild(TG);
    View miVista = new View();
    miVista.addCanvas3D(c);
    miVista.attachViewPlatform(vista);
    miVista.setPhysicalBody(cuerpo);
    miVista.setPhysicalEnvironment(ambiente);
    return verBranch;
  }
  //Iluminación 
  public void iluminacion(BranchGroup b) {
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
    Color3f colorA = new Color3f(1.0f, 1.0f, 1.0f);
    Vector3f direccionA = new Vector3f(-1.0f, -1.0f, -1.0f);
    Color3f colorB = new Color3f(1.0f, 1.0f, 1.0f);
    Vector3f direccionB = new Vector3f(1.0f, -1.0f, -1.0f);
    DirectionalLight luzA = new DirectionalLight(colorA, direccionA);
    luzA.setInfluencingBounds(bounds);
    DirectionalLight luzB = new DirectionalLight(colorB, direccionB);
    luzB.setInfluencingBounds(bounds);
    b.addChild(luzA);
    b.addChild(luzB);
  }
  //Hacemos el Branch Group
  public BranchGroup construyeBranchGroup(Node forma) {
    BranchGroup branch = new BranchGroup();
    Transform3D cubo = new Transform3D();
    cubo.set(new AxisAngle4d(0.3, 1.0, 0.0, Math.PI / 4.0));
    TransformGroup rotacion = new TransformGroup(cubo);
    branch.addChild(rotacion);
    rotacion.addChild(forma);
    iluminacion(branch);
    return branch;
  }
  //Construye las formas de la escena
  public Node construyeFormas() {
    //Raiz del BranchGroup
    BranchGroup raiz = new BranchGroup();
    //Apariencia del suelo
    Appearance ApPiso = new Appearance();
    Color3f ColorPiso = new Color3f(new Color(139,69,19));
    Color3f EColour = new Color3f(0.0f, 0.0f, 0.0f);
    Color3f SColour = new Color3f(0.2f, 0.2f, 0.2f);
    float brillo = 10.0f;
    ApPiso.setMaterial(new Material(ColorPiso, EColour, ColorPiso, SColour, brillo));
    Box suelo = new Box(100.0f, 0.1f, 100.0f, ApPiso);
    //TransformGroups
    Transform3D transS = new Transform3D();
    transS.set(new Vector3f(0.0f, -1.0f, 0.0f));
    TransformGroup transGS = new TransformGroup(transS);
    transGS.addChild(suelo);
    raiz.addChild(transGS);
    //Apariencia de la pared del edificio
    Appearance ApEdif = new Appearance();
    Color3f PColour = new Color3f(0.3f, 0.3f, 0.3f);
    ApEdif.setMaterial(new Material(PColour, EColour, PColour, SColour, brillo));
    Box Edificio = new Box(0.5f, 0.5f, 0.5f,ApEdif);
    TransformGroup transGE = new TransformGroup();
    transGE.addChild(Edificio);
    //2do Piso del edificio
    Appearance ApEdif2 = new Appearance();
    Color3f P2Colour = new Color3f(0.6f, 0.6f, 0.6f);
    ApEdif2.setMaterial(new Material(P2Colour, EColour, P2Colour, SColour, brillo));
    Box Piso = new Box(0.3f, 0.5f, 0.3f, ApEdif2);
    Transform3D trans2P = new Transform3D();
    trans2P.set(new Vector3f(0.0f, 1.0f, 0.0f));
    TransformGroup transG2P = new TransformGroup(trans2P);
    transG2P.addChild(Piso);
    transGE.addChild(transG2P);
    //Arboles
    //Tronco
    Appearance ApTronco = new Appearance();
    Color3f TrColour = new Color3f(0.2f, 0.2f, 0.0f);
    ApTronco.setMaterial(new Material(TrColour, EColour, TrColour, SColour, brillo));
    //Hojas
    Appearance ApHojas = new Appearance();
    Color3f HoColour = new Color3f(0.0f, 0.2f, 0.0f);
    ApHojas.setMaterial(new Material(HoColour, EColour, HoColour, SColour, brillo));
    Transform3D transAr = new Transform3D();
    transAr.set(new Vector3f(-2.0f, 0.0f, 0.5f));
    TransformGroup transGAr = new TransformGroup(transAr);
    Cylinder tronco = new Cylinder(0.1f, 1.0f, ApTronco);
    transGAr.addChild(tronco);
    Transform3D transHo = new Transform3D();
    transHo.set(new Vector3f(0.0f, 1.0f, 0.0f));
    TransformGroup transGHo = new TransformGroup(transHo);
    Sphere hojas = new Sphere(0.5f, ApHojas);
    transGHo.addChild(hojas);
    transGAr.addChild(transGHo);
    //Nuevo Árbol
    Transform3D transHo2 = new Transform3D();
    transHo2.set(new Vector3f(1.2f, 0.0f, 1.0f));
    TransformGroup transGHo2 = new TransformGroup(transHo2);
    Cylinder myTrunk2 = new Cylinder(0.1f, 1.0f, ApTronco);
    transGHo2.addChild(myTrunk2);
    Transform3D leaf2Xfm = new Transform3D();
    leaf2Xfm.set(new Vector3f(0.0f, 1.0f, 0.0f));
    TransformGroup leaf2XfmGrp = new TransformGroup(leaf2Xfm);
    Sphere hojas2 = new Sphere(0.5f, ApHojas);
    leaf2XfmGrp.addChild(hojas2);
    transGHo2.addChild(leaf2XfmGrp);
    //Añadimos los TransformGroup a la raíz
    raiz.addChild(transGE);
    raiz.addChild(transGAr);
    raiz.addChild(transGHo2);
    return raiz;
  }
  //Listener Botón Cerrar
  public void actionPerformed(ActionEvent e) {
    dispose();
    System.exit(0);
  }

  public Campo() {
    VirtualUniverse myUniverse = new VirtualUniverse();
    Locale myLocale = new Locale(myUniverse);
    myLocale.addBranchGraph(construyeVista(canvas));
    myLocale.addBranchGraph(construyeBranchGroup(construyeFormas()));
    this.setIconImage(Toolkit.getDefaultToolkit().getImage("frIcon.png"));
    setTitle("Arboles");
    setSize(400, 400);
    setLocation(300,100);
    setLayout(new BorderLayout());
    add("Center", canvas);
    add("South", salir);
    salir.addActionListener(this);
    setVisible(true);
  }

  public static void main(String[] args) {
    Campo sw = new Campo();
  }
}

