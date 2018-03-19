package figura3D;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;

import javax.media.j3d.Alpha;
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
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Screen3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Principal extends Applet implements ChangeListener {
	// Universo
	SimpleUniverse u;
	boolean aplicacion;
	// Canvas 3D
	Canvas3D canvas;
	OffScreenCanvas3D_2 offCanvas;
	JTabbedPane tabbedPane;
	// Vista 3D
	View view;
	String outFileBase = "Humano";
	int outFileSeq = 0;
	// Los TransformGroups
	TransformGroup cuerpo;
	TransformGroup brazod;
	TransformGroup brazoi;
	TransformGroup manod;
	TransformGroup manoi;
	TransformGroup base;
	TransformGroup piernai;
	TransformGroup piernad;
	TransformGroup piei;
	TransformGroup pied;
	
	// Transformaciones
	int cabezaRot = 0;
	AxisAngle4f cabezaAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
	JSlider cabezaSlider;
	int hdRot = 0;
	AxisAngle4f hdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
	JSlider hdSlider;
	int cdRot = 0;
	AxisAngle4f cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
	JSlider cdSlider;
	int hiRot = 0;
	AxisAngle4f hiAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
	JSlider hiSlider;
	int ciRot = 0;
	AxisAngle4f ciAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
	JSlider ciSlider;

	float escOffScreen = 1.5f;
	// Objetos Temporables Reutilizables
	Transform3D tmpTrans = new Transform3D();
	Vector3f tmpVector = new Vector3f();
	AxisAngle4f tmpAxisAngle = new AxisAngle4f();
	Box tmpCil;
	Box torso;
	Box tmpEsf;
	TransformGroup tmpTG;
	// Colores
	Color3f verde = new Color3f(0f, 1f, 0f);
	Color3f negro = new Color3f(0f, 0f, 0f);
	Color3f azul = new Color3f(0f, 0f, 1f);
	Color3f carne = new Color3f(255f, 168f, 168f);
	// Puntos en el plano
	Point3f origen = new Point3f();
	Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
	
	
	JRadioButton cabeza, brazoI, brazoD, manoI, manoD, piernaD, piernaI, pieD, pieI;
	ButtonGroup grupoRB;

	// Regresa el TransformGroup que se va a estar editando
	void creaHumano() {
		cuerpo = new TransformGroup();
		// Posicionamos el cuerpo
		tmpVector.set(0.0f, -1.5f, 0.0f);
		tmpTrans.set(tmpVector);
		cuerpo.setTransform(tmpTrans);
		// Apariencia para playera
		Material material = new Material(verde, negro, verde, negro, 30);
		Appearance apariencia = new Appearance();
		apariencia.setMaterial(material);
		
		// Cuerpo
		tmpTG = new TransformGroup();
		tmpVector.set(0.0f, 2.0f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		torso = new Box(.7f,1.0f,.4f,Box.GENERATE_TEXTURE_COORDS, apariencia);
		torso.getShape(Box.FRONT).setAppearance(textura("cuerpoAdelante"));
		torso.getShape(Box.BACK).setAppearance(textura("cuerpoAtras"));
		torso.getShape(Box.LEFT).setAppearance(textura("cuerpoIzquierda"));
		torso.getShape(Box.RIGHT).setAppearance(textura("cuerpoDerecha"));
		torso.getShape(Box.TOP).setAppearance(textura("cuerpoArriba"));
		torso.getShape(Box.BOTTOM).setAppearance(textura("cuerpoAbajo"));
		tmpTG.addChild(torso);
		cuerpo.addChild(tmpTG);

		float xBrazo = -0.92f;
		float yBrazo = 2.5f;
		float zBrazo = 0.0f;
		
		// Brazo derecho
		brazod = new TransformGroup();
		brazod.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		brazod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(xBrazo, yBrazo, zBrazo);
		tmpTrans.set(tmpVector);
		brazod.setTransform(tmpTrans);
		tmpEsf = new Box(0.22f,.5f,0.22f,Box.GENERATE_TEXTURE_COORDS, apariencia);
		tmpEsf.getShape(Box.FRONT).setAppearance(textura("brazoAdelante"));
		tmpEsf.getShape(Box.LEFT).setAppearance(textura("brazoIzquierda"));
		tmpEsf.getShape(Box.RIGHT).setAppearance(textura("brazoDerecha"));
		tmpEsf.getShape(Box.BACK).setAppearance(textura("brazoAtras"));
		tmpEsf.getShape(Box.TOP).setAppearance(textura("hombro"));
		tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("mano"));
		brazod.addChild(tmpEsf);
		tmpTG = new TransformGroup();
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		//tmpCil = new Box(0.22f, 0.4f, 0.22f, apariencia); // aqui
		//tmpTG.addChild(tmpCil);
		brazod.addChild(tmpTG);
		cuerpo.addChild(brazod);
		
		// Brazo izquierdo
		brazoi = new TransformGroup();
		brazoi.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		brazoi.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(xBrazo*-1, yBrazo, zBrazo);
		tmpTrans.set(tmpVector);
		brazoi.setTransform(tmpTrans);
		tmpEsf = new Box(0.22f,.5f,0.22f,Box.GENERATE_TEXTURE_COORDS, apariencia);
		tmpEsf.getShape(Box.FRONT).setAppearance(textura("brazoAdelante"));
		tmpEsf.getShape(Box.LEFT).setAppearance(textura("brazoIzquierda"));
		tmpEsf.getShape(Box.RIGHT).setAppearance(textura("brazoDerecha"));
		tmpEsf.getShape(Box.BACK).setAppearance(textura("brazoAtras"));
		tmpEsf.getShape(Box.TOP).setAppearance(textura("hombro"));
		tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("mano"));
		brazoi.addChild(tmpEsf);
		tmpTG = new TransformGroup();
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		//tmpCil = new Box(0.22f, 0.4f, 0.22f, apariencia); // aqui
		//tmpTG.addChild(tmpCil);
		brazoi.addChild(tmpTG);
		cuerpo.addChild(brazoi);

		// Mano derecha
		manod = new TransformGroup();
		manod.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		manod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1f, 0.0f);
		tmpTrans.set(tmpVector);
		manod.setTransform(tmpTrans);
		tmpEsf = new Box(0.22f,.5f,.22f,Box.GENERATE_TEXTURE_COORDS, apariencia);
		tmpEsf.getShape(Box.FRONT).setAppearance(textura("manoAdelante"));
		tmpEsf.getShape(Box.LEFT).setAppearance(textura("manoIzquierda"));
		tmpEsf.getShape(Box.RIGHT).setAppearance(textura("manoDerecha"));
		tmpEsf.getShape(Box.BACK).setAppearance(textura("manoAtras"));
		tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("mano"));
		tmpEsf.getShape(Box.TOP).setAppearance(textura("mano"));
		manod.addChild(tmpEsf);
		tmpTG = new TransformGroup();
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); //aqui
		//tmpTG.addChild(tmpCil);
		manod.addChild(tmpTG);
		brazod.addChild(manod);

		// Mano izquierda
		manoi = new TransformGroup();
		manoi.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		manoi.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1.0f, 0.0f);
		tmpTrans.set(tmpVector);
		manoi.setTransform(tmpTrans);
		tmpEsf = new Box(0.22f,0.5f,0.22f,Box.GENERATE_TEXTURE_COORDS, apariencia);
		tmpEsf.getShape(Box.FRONT).setAppearance(textura("manoAdelante"));
		tmpEsf.getShape(Box.LEFT).setAppearance(textura("manoIzquierda"));
		tmpEsf.getShape(Box.RIGHT).setAppearance(textura("manoDerecha"));
		tmpEsf.getShape(Box.BACK).setAppearance(textura("manoAtras"));
		tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("mano"));
		tmpEsf.getShape(Box.TOP).setAppearance(textura("mano"));
		manoi.addChild(tmpEsf);
		tmpTG = new TransformGroup();
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); // aqui
		//tmpTG.addChild(tmpCil);
		manoi.addChild(tmpTG);
		brazoi.addChild(manoi);
		
		// Pierna Izquierda
				piernai = new TransformGroup();
				piernai.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				piernai.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tmpVector.set(0.3f, 0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				piernai.setTransform(tmpTrans);
				tmpEsf = new Box(0.3f, 0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS, apariencia);
				tmpEsf.getShape(Box.FRONT).setAppearance(textura("piernaAdelante"));
				tmpEsf.getShape(Box.LEFT).setAppearance(textura("piernaIzquierda"));
				tmpEsf.getShape(Box.RIGHT).setAppearance(textura("piernaDerecha"));
				tmpEsf.getShape(Box.BACK).setAppearance(textura("piernaAtras"));
				tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("piernaArriba"));
				tmpEsf.getShape(Box.TOP).setAppearance(textura("piernaArriba"));
				piernai.addChild(tmpEsf);
				tmpTG = new TransformGroup();
				tmpVector.set(0.0f, -0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				tmpTG.setTransform(tmpTrans);
				//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); // aqui
				//tmpTG.addChild(tmpCil);
				piernai.addChild(tmpTG);
				cuerpo.addChild(piernai);
				
		// Pierna Derecha
				piernad = new TransformGroup();
				piernad.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				piernad.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tmpVector.set(-0.3f, 0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				piernad.setTransform(tmpTrans);
				tmpEsf = new Box(0.3f, 0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS, apariencia);
				tmpEsf.getShape(Box.FRONT).setAppearance(textura("piernaAdelante"));
				tmpEsf.getShape(Box.LEFT).setAppearance(textura("piernaIzquierda"));
				tmpEsf.getShape(Box.RIGHT).setAppearance(textura("piernaDerecha"));
				tmpEsf.getShape(Box.BACK).setAppearance(textura("piernaAtras"));
				tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("piernaArriba"));
				tmpEsf.getShape(Box.TOP).setAppearance(textura("piernaArriba"));
				piernad.addChild(tmpEsf);
				tmpTG = new TransformGroup();
				tmpVector.set(0.0f, -0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				tmpTG.setTransform(tmpTrans);
				//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); // aqui
				//tmpTG.addChild(tmpCil);
				piernad.addChild(tmpTG);
				cuerpo.addChild(piernad);
				
		// Pie izquierdo
				piei = new TransformGroup();
				piei.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				piei.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tmpVector.set(0.0f, -1.0f, 0.0f);
				tmpTrans.set(tmpVector);
				piei.setTransform(tmpTrans);
				tmpEsf = new Box(0.3f, 0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS, apariencia);
				tmpEsf.getShape(Box.FRONT).setAppearance(textura("pieAdelante"));
				tmpEsf.getShape(Box.LEFT).setAppearance(textura("pieIzquierda"));
				tmpEsf.getShape(Box.RIGHT).setAppearance(textura("pieDerecha"));
				tmpEsf.getShape(Box.BACK).setAppearance(textura("pieAtras"));
				tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("pieAbajo"));
				tmpEsf.getShape(Box.TOP).setAppearance(textura("piernaArriba"));
				piei.addChild(tmpEsf);
				tmpTG = new TransformGroup();
				tmpVector.set(0.0f, -0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				tmpTG.setTransform(tmpTrans);
				//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); // aqui
				//tmpTG.addChild(tmpCil);
				piei.addChild(tmpTG);
				piernai.addChild(piei);
				
		// Pie Derecho
				pied = new TransformGroup();
				pied.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				pied.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				tmpVector.set(0.0f, -1.0f, 0.0f);
				tmpTrans.set(tmpVector);
				pied.setTransform(tmpTrans);
				tmpEsf = new Box(0.3f, 0.5f,0.3f,Box.GENERATE_TEXTURE_COORDS, apariencia);
				tmpEsf.getShape(Box.FRONT).setAppearance(textura("pieAdelante"));
				tmpEsf.getShape(Box.LEFT).setAppearance(textura("pieIzquierda"));
				tmpEsf.getShape(Box.RIGHT).setAppearance(textura("pieDerecha"));
				tmpEsf.getShape(Box.BACK).setAppearance(textura("pieAtras"));
				tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("pieAbajo"));
				tmpEsf.getShape(Box.TOP).setAppearance(textura("piernaArriba"));
				pied.addChild(tmpEsf);
				tmpTG = new TransformGroup();
				tmpVector.set(0.0f, -0.5f, 0.0f);
				tmpTrans.set(tmpVector);
				tmpTG.setTransform(tmpTrans);
				//tmpCil = new Box(0.22f, 0.3f, 0.22f, apariencia); // aqui
				//tmpTG.addChild(tmpCil);
				pied.addChild(tmpTG);
				piernad.addChild(pied);

		// Cabeza
		base = new TransformGroup();
		base.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		base.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, 3.6f, 0.0f);
		tmpTrans.set(tmpVector);
		base.setTransform(tmpTrans);
		float dimension = 0.6f;
		tmpEsf = new Box(dimension, dimension, dimension, Box.GENERATE_TEXTURE_COORDS, apariencia);
		tmpEsf.getShape(Box.FRONT).setAppearance(textura("cara"));
		tmpEsf.getShape(Box.LEFT).setAppearance(textura("izquierda"));
		tmpEsf.getShape(Box.RIGHT).setAppearance(textura("derecha"));
		tmpEsf.getShape(Box.BACK).setAppearance(textura("atras"));
		tmpEsf.getShape(Box.TOP).setAppearance(textura("arriba"));
		tmpEsf.getShape(Box.BOTTOM).setAppearance(textura("abajo"));
		base.addChild(tmpEsf);
		cuerpo.addChild(base);
	}
	
	public void setCabeza(int rotation) {
		cabezaRot = rotation;
		cabezaAA.angle = (float) Math.toRadians(cabezaRot);
		base.getTransform(tmpTrans);
		tmpTrans.setRotation(cabezaAA);
		base.setTransform(tmpTrans);
	}

	// Rotacion Brazo Derecho
	public void setRotBrazoD(int rotation) {
		hdRot = rotation;
		hdAA.angle = (float) Math.toRadians(hdRot);
		brazod.getTransform(tmpTrans);
		tmpTrans.setRotation(hdAA);
		brazod.setTransform(tmpTrans);
	}

	// Rotacion Mano Derecha
	public void setRotManoD(int rotation) {
		cdRot = rotation;
		cdAA.angle = (float) Math.toRadians(cdRot);
		manod.getTransform(tmpTrans);
		tmpTrans.setRotation(cdAA);
		manod.setTransform(tmpTrans);
	}

	// Rotacion Brazo Izquierdo
	public void setRotBrazoI(int rotation) {
		hiRot = rotation;
		hiAA.angle = (float) Math.toRadians(hiRot);
		brazoi.getTransform(tmpTrans);
		tmpTrans.setRotation(hiAA);
		brazoi.setTransform(tmpTrans);
	}

	// Rotacion Mano Izquierda
	public void setRotManoI(int rotation) {
		ciRot = rotation;
		ciAA.angle = (float) Math.toRadians(ciRot);
		manoi.getTransform(tmpTrans);
		tmpTrans.setRotation(ciAA);
		manoi.setTransform(tmpTrans);
	}

	// Listener Sliders
	public void stateChanged(ChangeEvent e) {
		
		JSlider source = (JSlider) e.getSource();
		
		if(source.equals(rotX)) {
			if(cabeza.isSelected()) {
				cabezaAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cabezaRot = rotX.getValue() - 50;
				cabezaAA.angle = (float) (cabezaRot * (Math.PI / 4)) / 100;
				base.getTransform(tmpTrans);
				tmpTrans.setRotation(cabezaAA);
				base.setTransform(tmpTrans);
				
			} else if(brazoI.isSelected()) {
				hiAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				hiRot = rotX.getValue() - 20;
				hiAA.angle = (float) (hiRot * (Math.PI) / 100);
				brazoi.getTransform(tmpTrans);
				tmpTrans.setRotation(hiAA);
				brazoi.setTransform(tmpTrans);
			} else if(brazoD.isSelected()) {
				hdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				hdRot = rotX.getValue() - 20;
				hdAA.angle = (float) (hdRot * (Math.PI) / 100);
				brazod.getTransform(tmpTrans);
				tmpTrans.setRotation(hdAA);
				brazod.setTransform(tmpTrans);
			} else if(manoI.isSelected()) {
				ciAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				ciRot = rotX.getValue();
				ciAA.angle = (float) (ciRot * (Math.PI / 2)) / 100;
				manoi.getTransform(tmpTrans);
				tmpTrans.setRotation(ciAA);
				manoi.setTransform(tmpTrans);
			} else if(manoD.isSelected()) {
				cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cdRot = rotX.getValue();
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				manod.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				manod.setTransform(tmpTrans);
			} else if(piernaD.isSelected()) {
				cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cdRot = rotX.getValue();
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				piernad.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernad.setTransform(tmpTrans);
			} else if(piernaI.isSelected()) {
				cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cdRot = rotX.getValue();
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				piernai.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernai.setTransform(tmpTrans);
			} else if(pieD.isSelected()) {
				cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cdRot = rotX.getValue() - 100;
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				pied.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				pied.setTransform(tmpTrans);
			} else if(pieI.isSelected()) {
				cdAA = new AxisAngle4f(-1.0f, 0.0f, 0.0f, 0.0f);
				
				cdRot = rotX.getValue() - 100;
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				piei.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piei.setTransform(tmpTrans);
			}
		} else if(source.equals(rotY)) {
			if(cabeza.isSelected()) {
				cabezaAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cabezaRot = rotY.getValue() - 50;
				cabezaAA.angle = (float) Math.toRadians(cabezaRot);
				base.getTransform(tmpTrans);
				tmpTrans.setRotation(cabezaAA);
				base.setTransform(tmpTrans);
			} else if(brazoI.isSelected()) {
				hiAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				hiRot = rotY.getValue() - 50;
				hiAA.angle = (float) (hiRot * (Math.PI / 2) / 100);
				brazoi.getTransform(tmpTrans);
				tmpTrans.setRotation(hiAA);
				brazoi.setTransform(tmpTrans);
			} else if(brazoD.isSelected()) {
				hdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				hdRot = rotY.getValue() - 50;
				hdAA.angle = (float) (hdRot * (Math.PI / 2) / 100);
				brazod.getTransform(tmpTrans);
				tmpTrans.setRotation(hdAA);
				brazod.setTransform(tmpTrans);
			} else if(manoI.isSelected()) {
				ciAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				ciRot = rotY.getValue() - 50;
				ciAA.angle = (float) (ciRot * (Math.PI / 4)) / 100;
				manoi.getTransform(tmpTrans);
				tmpTrans.setRotation(ciAA);
				manoi.setTransform(tmpTrans);
			} else if(manoD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cdRot = rotY.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 4)) / 100;
				manod.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				manod.setTransform(tmpTrans);
			} else if(piernaD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cdRot = rotY.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 4)) / 100;
				piernad.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernad.setTransform(tmpTrans);
			} else if(piernaI.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cdRot = rotY.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 4)) / 100;
				piernai.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernai.setTransform(tmpTrans);
			} else if(pieD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cdRot = rotY.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 4)) / 100;
				pied.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				pied.setTransform(tmpTrans);
			} else if(pieI.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, -1.0f, 0.0f, 0.0f);
				
				cdRot = rotY.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 4)) / 100;
				piei.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piei.setTransform(tmpTrans);
			}
		} else if(source.equals(rotZ)) {
			if(cabeza.isSelected()) {
				cabezaAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				cabezaRot = rotZ.getValue() - 50;
				cabezaAA.angle = (float) (cabezaRot * (Math.PI / 8)) / 100;
				base.getTransform(tmpTrans);
				tmpTrans.setRotation(cabezaAA);
				base.setTransform(tmpTrans);
			} else if(brazoI.isSelected()) {
				hiAA = new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f);
				
				hiRot = rotZ.getValue();
				hiAA.angle = (float) (hiRot * (Math.PI / 2) / 100);
				brazoi.getTransform(tmpTrans);
				tmpTrans.setRotation(hiAA);
				brazoi.setTransform(tmpTrans);
			} else if(brazoD.isSelected()) {
				hdAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				hdRot = rotZ.getValue();
				hdAA.angle = (float) (hdRot * (Math.PI / 2) / 100);
				brazod.getTransform(tmpTrans);
				tmpTrans.setRotation(hdAA);
				brazod.setTransform(tmpTrans);
			} else if(manoI.isSelected()) {
				ciAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				ciRot = rotZ.getValue() - 50;
				ciAA.angle = (float) (ciRot * (Math.PI / 16)) / 100;
				manoi.getTransform(tmpTrans);
				tmpTrans.setRotation(ciAA);
				manoi.setTransform(tmpTrans);
			} else if(manoD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				cdRot = rotZ.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 16)) / 100;
				manod.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				manod.setTransform(tmpTrans);
			} else if(piernaD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f);
				
				cdRot = rotZ.getValue() - 100;
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				piernad.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernad.setTransform(tmpTrans);
			} else if(piernaI.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				cdRot = rotZ.getValue() - 100;
				cdAA.angle = (float) (cdRot * (Math.PI / 2)) / 100;
				piernai.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piernai.setTransform(tmpTrans);
			} else if(pieD.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				cdRot = rotZ.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 16)) / 100;
				pied.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				pied.setTransform(tmpTrans);
			} else if(pieI.isSelected()) {
				cdAA = new AxisAngle4f(0.0f, 0.0f, -1.0f, 0.0f);
				
				cdRot = rotZ.getValue() - 50;
				cdAA.angle = (float) (cdRot * (Math.PI / 16)) / 100;
				piei.getTransform(tmpTrans);
				tmpTrans.setRotation(cdAA);
				piei.setTransform(tmpTrans);
			}
		}
		
		/*
		JSlider source = (JSlider) e.getSource();
		int value = source.getValue();
		if (source == cabezaSlider) {
			setCabeza(value);
		} else if (source == hdSlider) {
			setRotBrazoD(value);
		} else if (source == cdSlider) {
			setRotManoD(value);
		} else if (source == hiSlider) {
			setRotBrazoI(value);
		} else if (source == ciSlider) {
			setRotManoI(value);
		}*/
	}

	// Branch Group
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
		
		// No funciona para rotar, arreglar despues
		/*
		MouseRotate mr = new MouseRotate();
        mr.setTransformGroup(objTrans);
        mr.setSchedulingBounds(bounds);
        mr.setFactor(0.007);
        objTrans.addChild(mr);*/

		Alpha alpha = new Alpha(-1, 4000);
	    RotationInterpolator rotator = new RotationInterpolator(alpha, objTrans);
	    BoundingSphere bounds2 = new BoundingSphere();
	    rotator.setSchedulingBounds(bounds2);
	    objTrans.addChild(rotator);
	    
		Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f light1Direction = new Vector3f(0.0f, -0.2f, -1.0f);

		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		return objRoot;
	}

	public Principal() {
		this(false, 1.0f);
	}

	public Principal(boolean aplicacion, float escOffScreen) {
		this.aplicacion = aplicacion;
		this.escOffScreen = escOffScreen;
	}

	public void init() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		canvas = new Canvas3D(config);

		add("Center", canvas);

		u = new SimpleUniverse(canvas);

		if (aplicacion) {
			offCanvas = new OffScreenCanvas3D_2(config, true);
			Screen3D sOn = canvas.getScreen3D();
			Screen3D sOff = offCanvas.getScreen3D();
			Dimension dim = sOn.getSize();
			dim.width *= escOffScreen;
			dim.height *= escOffScreen;
			sOff.setSize(dim);
			sOff.setPhysicalScreenWidth(sOn.getPhysicalScreenWidth() * escOffScreen);
			sOff.setPhysicalScreenHeight(sOn.getPhysicalScreenHeight() * escOffScreen);
			u.getViewer().getView().addCanvas3D(offCanvas);
		}
		// Escena
		BranchGroup scene = createSceneGraph();
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);
		view = u.getViewer().getView();
		add("East", guiPanel());
	}

	JSlider rotX, rotY, rotZ;
	
	// creamos el panel giratorio
	JPanel guiPanel() {
		JPanel panel = new JPanel();
		grupoRB = new ButtonGroup();
		
		panel.setLayout(new GridLayout(12, 2));
		
		cabeza = new JRadioButton();
		cabeza.setSelected(true);
		brazoI = new JRadioButton();
		brazoD = new JRadioButton();
		manoI = new JRadioButton();
		manoD = new JRadioButton();
		piernaD = new JRadioButton();
		piernaI = new JRadioButton();
		pieD = new JRadioButton();
		pieI = new JRadioButton();
		
		grupoRB.add(cabeza);
		grupoRB.add(brazoI);
		grupoRB.add(brazoD);
		grupoRB.add(manoI);
		grupoRB.add(manoD);
		grupoRB.add(piernaD);
		grupoRB.add(piernaI);
		grupoRB.add(pieD);
		grupoRB.add(pieI);
		
		panel.add(new JLabel("Cabeza"));
		panel.add(cabeza);
		panel.add(new JLabel("Brazo Izquierdo"));
		panel.add(brazoI);
		panel.add(new JLabel("Brazo Derecho"));
		panel.add(brazoD);
		panel.add(new JLabel("Mano Izquierda"));
		panel.add(manoI);
		panel.add(new JLabel("Mano Derecha"));
		panel.add(manoD);
		panel.add(new JLabel("Pierna Izquierda"));
		panel.add(piernaI);
		panel.add(new JLabel("Pierna Derecha"));
		panel.add(piernaD);
		panel.add(new JLabel("Pie Izquierdo"));
		panel.add(pieI);
		panel.add(new JLabel("Pie Derecho"));
		panel.add(pieD);
		
		rotX = new JSlider();
		rotX.setMaximum(100);
		rotX.setValue(50);
		rotX.addChangeListener(this);
		
		rotY = new JSlider();
		rotY.setMaximum(100);
		rotY.setValue(50);
		rotY.addChangeListener(this);
		
		rotZ = new JSlider();
		rotZ.setMaximum(100);
		rotZ.setValue(50);
		rotZ.addChangeListener(this);
		
		panel.add(new JLabel("X"));
		panel.add(rotX);
		panel.add(new JLabel("Y"));
		panel.add(rotY);
		panel.add(new JLabel("Z"));
		panel.add(rotZ);
		
		/*
		panel.setLayout(new GridLayout(5, 2));

		// Rotacion cabeza
		panel.add(new JLabel("Cabeza"));
		cabezaSlider = new JSlider(JSlider.HORIZONTAL, 0, 45, cabezaRot);
		cabezaSlider.addChangeListener(this);
		panel.add(cabezaSlider);
		
		// Rotacion hombro derecho
		panel.add(new JLabel("Brazo derecho"));
		hdSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hdRot);
		hdSlider.addChangeListener(this);
		panel.add(hdSlider);

		// Rotacion codo derecho
		panel.add(new JLabel("Mano derecha"));
		cdSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, cdRot);
		cdSlider.addChangeListener(this);
		panel.add(cdSlider);

		// Rotacion hombro izquierdo
		panel.add(new JLabel("Brazo izquierdo"));
		hiSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hiRot);
		hiSlider.addChangeListener(this);
		panel.add(hiSlider);

		// Rotacion codo izquierdo
		panel.add(new JLabel("Mano izquierda"));
		ciSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, ciRot);
		ciSlider.addChangeListener(this);
		panel.add(ciSlider);*/

		return panel;
	}

	public void destroy() {
		u.removeAllLocales();
	}
	
	public Appearance textura(String archivo) {
		Appearance ap = new Appearance();
		URL filename = getClass().getClassLoader().getResource(archivo + ".png");
		TextureLoader loader = new TextureLoader(filename, this);
		ImageComponent2D image = loader.getImage();
		if(image == null) {
			System.out.println("No se encontro la imagen");;
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		ap.setTexture(texture);
		return ap;
	}

	// metodo principal
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
		new MainFrame(new Principal(true, initOffScreenScale), 950, 600);
	}
}

class OffScreenCanvas3D_2 extends Canvas3D {

	OffScreenCanvas3D_2(GraphicsConfiguration graphicsConfiguration,
			boolean offScreen) {

		super(graphicsConfiguration, offScreen);
	}

	private BufferedImage doRender(int width, int height) {

		BufferedImage bImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		ImageComponent2D buffer = new ImageComponent2D(
				ImageComponent.FORMAT_RGB, bImage);
		// buffer.setYUp(true);

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
