package G3D_Dragon.src.g3d_dragon;

import java.applet.Applet;

import javax.vecmath.*;

import java.awt.*;

import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;

import com.sun.j3d.utils.image.TextureLoader;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Dragon extends Applet {
    final static int CUERPO = 0;
    final static int CUELLO = 1;
    final static int CABEZA = 2;
    final static int COLAS1 = 3;
    final static int COLAS2 = 4;
    final static int COLAS3 = 5;
    final static int PATADIZQ = 6;
    final static int RODILLADIZQ = 7;
    final static int PATADDER = 8;
    final static int RODILLADDER = 9;
    final static int PATATIZQ = 10;
    final static int RODILLATIZQ = 11;
    final static int PATATDER = 12;
    final static int RODILLATDER = 113;
    final static int ALAS = 14;
    private int extremidad = CUERPO;
    double escala = 0.15;
    Movimiento M = new Movimiento();
    TransformGroup tgCuerpo_Dragon, tgCuello_Dragon, tgCabeza_Dragon, tgColaS1_Dragon, tgColaS2_Dragon, tgColaS3_Dragon, tgPataDelanteraI_Dragon, tgRodillaDelanteraI_Dragon, tgPataDelanteraD_Dragon, tgRodillaDelanteraD_Dragon, tgPataTraseraI_Dragon, tgRodillaTraseraI_Dragon, tgPataTraseraD_Dragon, tgRodillaTraseraD_Dragon, tgAlaIzq, tgAlaDer;
    Alpha rotationAlpha;
    PnlOpciones pnlOpc;

    public void init() {
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(cv, BorderLayout.CENTER);
        pnlOpc = new PnlOpciones(this);
        pnlOpc.add(new Panel());
        add(pnlOpc, BorderLayout.EAST);
        BranchGroup bg = createSceneGraph();
        setExtremidad(CUERPO);
        SimpleUniverse su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup dragon = new BranchGroup();
        TransformGroup tg_obj_rotacion = creaRotacion(4000);
        TransformGroup tg_objetos = creaDragon();
        Background background = createBackground(); /**/
        AmbientLight ambientLight = createAmbientLight(); /**/
        PointLight ptlight, ptlight2;
        ptlight = createPointLight1();
        ptlight2 = createPointLight2();
        tg_obj_rotacion.addChild(tg_objetos);
        dragon.addChild(tg_obj_rotacion);
        dragon.addChild(background);
        dragon.addChild(ambientLight);
        dragon.addChild(ptlight);
        dragon.addChild(ptlight2);
        dragon.compile();
        return dragon;
    }

    private TransformGroup creaRotacion(int Alpha) {
        TransformGroup tgRotacion = new TransformGroup();
        rotationAlpha = new Alpha(-1, Alpha);
        RotationInterpolator interpolator = new RotationInterpolator(rotationAlpha, tgRotacion);
        BoundingSphere bounds = new BoundingSphere();
        tgRotacion.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        interpolator.setSchedulingBounds(bounds);
        tgRotacion.addChild(interpolator);
        return tgRotacion;
    }

    private TransformGroup creaDragon() {
        TransformGroup tgCuerpo;
        Transform3D transform3D = new Transform3D();
        Appearance appearance = new Appearance();
        appearance.setMaterial(new Material());
        transform3D.rotZ(-Math.PI / 15.0);
        transform3D.setScale(escala);
        tgCuerpo_Dragon = new TransformGroup(transform3D);
        tgCuerpo_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transform3D.setIdentity();
        float tamini = 1.1f; //ciclo para crear cuerpo central
        float tamfin = .8f;
        float k = tamini;
        float radcont = (tamini - tamfin) / 12;
        for (float i = -1.2f; i < 1.2f; i = i + .2f) {
            Sphere cuerpo = new Sphere(k);
            transform3D.setTranslation(new Vector3f(i, 0f, 0f));
            tgCuerpo = new TransformGroup(transform3D);
            tgCuerpo.addChild(cuerpo);
            tgCuerpo_Dragon.addChild(tgCuerpo);
            k = k - radcont;
        }
        Sphere pcCuello = new Sphere(.75f); //esfera: punto control para cuello
        transform3D.setTranslation(new Vector3f(-1.5f, .3f, 0f));
        TransformGroup tgpcCuello = new TransformGroup(transform3D);
        tgpcCuello.addChild(pcCuello);
        tgCuerpo_Dragon.addChild(tgpcCuello);
        tgCuerpo_Dragon.addChild(creaCuello(transform3D));
        Sphere pcCola = new Sphere(.6f); //esfera: punto control para cola
        transform3D.setTranslation(new Vector3f(1.7f, 0f, 0f));
        TransformGroup tgpcCola = new TransformGroup(transform3D);
        tgpcCola.addChild(pcCola);
        tgCuerpo_Dragon.addChild(tgpcCola);
        tgCuerpo_Dragon.addChild(creaColaS1(transform3D));
        Sphere pcPataDI = new Sphere(.6f); //esfera: punto control para pata derecha
        transform3D.setTranslation(new Vector3f(-1.3f, -.4f, .6f));
        TransformGroup tgpcPataDI = new TransformGroup(transform3D);
        tgpcPataDI.addChild(pcPataDI);
        tgCuerpo_Dragon.addChild(tgpcPataDI);
        tgCuerpo_Dragon.addChild(creaPataD(tgpcPataDI, "I"));
        Sphere pcPataDD = new Sphere(.6f); //esfera: punto control para pata izquierda
        transform3D.setTranslation(new Vector3f(-1.3f, -.4f, -.6f));
        TransformGroup tgpcPataDD = new TransformGroup(transform3D);
        tgpcPataDD.addChild(pcPataDD);
        tgCuerpo_Dragon.addChild(tgpcPataDD);
        tgCuerpo_Dragon.addChild(creaPataD(tgpcPataDD, "D"));
        Sphere pcPataTI = new Sphere(.5f); //esfera: punto control para pata trasera izquierda
        transform3D.setTranslation(new Vector3f(1.3f, -.2f, .45f));
        TransformGroup tgpcPataTI = new TransformGroup(transform3D);
        tgpcPataTI.addChild(pcPataTI);
        tgCuerpo_Dragon.addChild(tgpcPataTI);
        tgCuerpo_Dragon.addChild(creaPataT(tgpcPataTI, "I"));
        Sphere pcPataTD = new Sphere(.5f); //esfera: punto control para pata trasera derecha
        transform3D.setTranslation(new Vector3f(1.3f, -.2f, -.45f));
        TransformGroup tgpcPataTD = new TransformGroup(transform3D);
        tgpcPataTD.addChild(pcPataTD);
        tgCuerpo_Dragon.addChild(tgpcPataTD);
        tgCuerpo_Dragon.addChild(creaPataT(tgpcPataTD, "D"));
        Sphere pcAlaI = new Sphere(.2f); //esfera: punto control para ala izquierda
        transform3D.setTranslation(new Vector3f(-.7f, .8f, .45f));
        TransformGroup tgpcAlaI = new TransformGroup(transform3D);
        tgpcAlaI.addChild(pcAlaI);
        tgCuerpo_Dragon.addChild(tgpcAlaI);
        tgCuerpo_Dragon.addChild(creaAla(tgpcAlaI, "I"));
        Sphere pcAlaD = new Sphere(.2f); //esfera: punto control para ala derecha
        transform3D.setTranslation(new Vector3f(-.7f, .8f, -.45f));
        TransformGroup tgpcAlaD = new TransformGroup(transform3D);
        tgpcAlaD.addChild(pcAlaD);
        tgCuerpo_Dragon.addChild(tgpcAlaD);
        tgCuerpo_Dragon.addChild(creaAla(tgpcAlaD, "D"));
        return tgCuerpo_Dragon;
    }

    public TransformGroup creaAla(TransformGroup pc, String E) {
        TransformGroup ala_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        ala_dragon = new TransformGroup(transform3D);
        TransformGroup tgAla = new TransformGroup();
        Appearance pintura1 = new Appearance();
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        pintura1.setPolygonAttributes(polyAttrib);
        pintura1.setMaterial(new Material());
        Shape3D sAla = null;
        if (E.equals("I")) {
            sAla = new Alas(.7,.84); 
        } else {
            sAla = new Alas(-.7,-.84);
        }
        sAla.setAppearance(pintura1);
        transform3D.rotZ((45 * Math.PI) / 180);
        transform3D.setTranslation(new Vector3f(0f, 0f, 0f));
        TransformGroup tgPielAla = new TransformGroup(transform3D);
        tgPielAla.addChild(sAla);
        tgAla.addChild(tgPielAla);
        transform3D.rotZ((0 * Math.PI) / 180);
        Cylinder ala = new Cylinder(.15f, 1.75f);
        transform3D.setTranslation(new Vector3f(0f, 1f, 0f));
        TransformGroup tgala = new TransformGroup(transform3D);
        tgala.addChild(ala);
        tgAla.addChild(tgala);
        Sphere pcAla = new Sphere(.1f);
        transform3D.setTranslation(new Vector3f(0f, 1.9f, 0f));
        TransformGroup tgpcAla = new TransformGroup(transform3D);
        tgpcAla.addChild(pcAla);
        tgAla.addChild(tgpcAla);
        Transform3D pos = new Transform3D();
        Transform3D pos2 = new Transform3D();
        pos = new Transform3D();
        pos.rotZ((320 * Math.PI) / 180);
        Cone ala4 = new Cone(.09f, 2.2f);
        pos.setTranslation(new Vector3f(.7f, 2.6f, 0f));
        TransformGroup tgala4 = new TransformGroup(pos);
        tgala4.addChild(ala4);
        tgAla.addChild(tgala4);
        Cylinder ala2 = new Cylinder(.1f, 1.4f);
        pos.rotZ((Math.PI) / 2.2);
        if (E.equals("I")) {
            pos2.rotX(Math.PI / 7.0);
            pos.mul(pos2);
            pos.setTranslation(new Vector3f(-.6f, 2f, .3f));
        } else {
            pos2.rotX(-Math.PI / 7.0);
            pos.mul(pos2);
            pos.setTranslation(new Vector3f(-.6f, 2f, -.3f));
        }
        TransformGroup tgala2 = new TransformGroup(pos);
        tgala2.addChild(ala2);
        tgAla.addChild(tgala2);
        Sphere pcAla2 = new Sphere(.1f);
        transform3D.setTranslation(new Vector3f(0f, .7f, 0f));
        TransformGroup tgpcAla2 = new TransformGroup(transform3D);
        tgpcAla2.addChild(pcAla2);
        tgala2.addChild(tgpcAla2);
        pos = new Transform3D();
        pos.rotY((10 * Math.PI) / 180);
        transform3D.rotZ((319 * Math.PI) / 180);
        transform3D.mul(pos);
        Cone ala5 = new Cone(.1f, 3.5f);
        if (E.equals("I")) {
            transform3D.setTranslation(new Vector3f(-.05f, 3.55f, .63f));
        } else {
            transform3D.setTranslation(new Vector3f(-.05f, 3.55f, -.63f));
        }
        TransformGroup tgala5 = new TransformGroup(transform3D);
        tgala5.addChild(ala5);
        tgAla.addChild(tgala5);
        pos = new Transform3D();
        pos.rotZ((-Math.PI) / 2.2);
        Cone ala3 = new Cone(.09f, 2.7f);
        pos.setTranslation(new Vector3f(1.35f, .25f, .0f));
        TransformGroup tgala3 = new TransformGroup(pos);
        tgala3.addChild(ala3);
        tgpcAla2.addChild(tgala3);
        tgAla.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        Transform3D roy = new Transform3D();
        roz.rotZ(-Math.PI / 5);
        if (E.equals("I")) {
            roy.rotX(Math.PI / 7.0);
        } else {
            roy.rotX(-Math.PI / 7.0);
        }
        roz.mul(roy);
        tgAla.setTransform(roz);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        if (E.equals("I")) {
            M.setAla(tgAla, "I");
        } else {
            M.setAla(tgAla, "D");
        }
        M = new Movimiento();
        M.setSchedulingBounds(bounds);
        tgAla.addChild(M);
        if (E.equals("I")) {
            tgAlaIzq = tgAla;
            ala_dragon.addChild(tgAlaIzq);
        } else {
            tgAlaDer = tgAla;
            ala_dragon.addChild(tgAlaDer);
        }
        return ala_dragon;
    }

    public TransformGroup creaCuello(Transform3D transform3D) {
        TransformGroup cuello_dragon;
        cuello_dragon = new TransformGroup(transform3D);
        tgCuello_Dragon = new TransformGroup();
        Cylinder cuello = new Cylinder(.6f, 2f);
        transform3D.setTranslation(new Vector3f(0f, 1f, 0f));
        TransformGroup tgCuello = new TransformGroup(transform3D);
        tgCuello.addChild(cuello);
        tgCuello_Dragon.addChild(tgCuello);
        Sphere pcCabeza = new Sphere(.6f); //esfera: punto control para cabeza
        transform3D.setTranslation(new Vector3f(0f, 2f, 0f));
        TransformGroup tgpcCabeza = new TransformGroup(transform3D);
        tgpcCabeza.addChild(pcCabeza);
        tgCuello_Dragon.addChild(tgpcCabeza);
        tgCuello_Dragon.addChild(creaCabeza(transform3D)); //se ensambla la cabeza al cuello
        tgCuello_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((45 * Math.PI) / 180); //-25-135
        tgCuello_Dragon.setTransform(roz);
        cuello_dragon.addChild(tgCuello_Dragon);
        return cuello_dragon;
    }

    private TransformGroup creaCabeza(Transform3D transform3D) {
        TransformGroup cabeza_dragon;
        TransformGroup tgCabeza1 = new TransformGroup();
        cabeza_dragon = new TransformGroup(transform3D);
        tgCabeza_Dragon = new TransformGroup();
        float tamini = .25f; //ciclo para crear cabeza
        float tamfin = .6f;
        float k = tamfin;
        float radcont = (tamfin - tamini) / 12;
        for (float i = 0f; i > -1f; i = i - .1f) {
            Sphere cuerpo = new Sphere(k);
            transform3D.setTranslation(new Vector3f(i, 0f, 0f));
            tgCabeza1 = new TransformGroup(transform3D);
            tgCabeza1.addChild(cuerpo);
            tgCabeza_Dragon.addChild(tgCabeza1);
            k = k - radcont;
        }
        Appearance pintura1 = new Appearance();
        //pintura1.setTexture(this.getTexture("IBlanco.jpg"));
        Sphere ojoi = new Sphere(.1f); //esfera: ojo izquierdo
        //ojoi.setAppearance(pintura1);
        transform3D.setTranslation(new Vector3f(-.4f, .3f, -.3f));
        TransformGroup tgOjoi = new TransformGroup(transform3D);
        tgOjoi.addChild(ojoi);
        tgCabeza_Dragon.addChild(tgOjoi);
        Sphere ojod = new Sphere(.1f); //esfera: ojo izquierdo
        //ojod.setAppearance(pintura1);
        transform3D.setTranslation(new Vector3f(-.4f, .3f, .3f));
        TransformGroup tgOjod = new TransformGroup(transform3D);
        tgOjod.addChild(ojod);
        tgCabeza_Dragon.addChild(tgOjod);
        Appearance pintura2 = new Appearance();
        //pintura2.setTexture(this.getTexture("INegro.jpg"));
        Sphere fnai = new Sphere(.1f); //esfera: fosa nasal izquierda
        //fnai.setAppearance(pintura2);
        transform3D.setTranslation(new Vector3f(-1f, .1f, -.2f));
        TransformGroup tgFnai = new TransformGroup(transform3D);
        tgFnai.addChild(fnai);
        tgCabeza_Dragon.addChild(tgFnai);
        Sphere fnad = new Sphere(.1f); //esfera: fosa nasal derecha
        //fnad.setAppearance(pintura2);
        transform3D.setTranslation(new Vector3f(-1f, .1f, .2f));
        TransformGroup tgFnad = new TransformGroup(transform3D);
        tgFnad.addChild(fnad);
        tgCabeza_Dragon.addChild(tgFnad);

        TransformGroup tgC = new TransformGroup(transform3D);
        Transform3D pos = new Transform3D();
        pos.rotX((60 * Math.PI) / 180);
        Cone conoorejaI = new Cone(.25f, .5f); //cono: oreja izquierda
        pos.setTranslation(new Vector3f(1f, 0f, .45f));
        TransformGroup tgOrejaI = new TransformGroup(pos);
        tgOrejaI.addChild(conoorejaI);
        tgC.addChild(tgOrejaI);
        pos = new Transform3D();
        pos.rotX((300 * Math.PI) / 180);
        Cone conoorejaD = new Cone(.25f, .5f); //cono: oreja derecha
        pos.setTranslation(new Vector3f(1f, 0f, -.85f));
        TransformGroup tgOrejaD = new TransformGroup(pos);
        tgOrejaD.addChild(conoorejaD);
        tgC.addChild(tgOrejaD);

        pos = new Transform3D();
        pos.rotZ((-45 * Math.PI) / 180);
        Cone cCuernoI = new Cone(.15f, 1f); //cono: cuerno izquierdo
        pos.setTranslation(new Vector3f(1.6f, .55f, .1f));
        TransformGroup tgCuernoI = new TransformGroup(pos);
        tgCuernoI.addChild(cCuernoI);
        tgC.addChild(tgCuernoI);

        pos = new Transform3D();
        pos.rotZ((-45 * Math.PI) / 180);
        Cone cCuernoD = new Cone(.15f, 1f); //cono: cuerno derecho
        pos.setTranslation(new Vector3f(1.6f, .55f, -.5f));
        TransformGroup tgCuernoD = new TransformGroup(pos);
        tgCuernoD.addChild(cCuernoD);
        tgC.addChild(tgCuernoD);

        tgCabeza_Dragon.addChild(tgC);
        tgCabeza_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transform3D = new Transform3D();
        transform3D.rotZ((-15 * Math.PI) / 180);
        tgCabeza_Dragon.setTransform(transform3D);
        cabeza_dragon.addChild(tgCabeza_Dragon);
        return cabeza_dragon;
    }

    public TransformGroup creaColaS1(Transform3D transform3D) {
        TransformGroup cola_sec1_dragon;
        cola_sec1_dragon = new TransformGroup(transform3D);
        tgColaS1_Dragon = new TransformGroup();
        Cylinder colas1 = new Cylinder(.5f, 1f);
        transform3D.setTranslation(new Vector3f(0f, .7f, 0f));
        TransformGroup tgColaS1 = new TransformGroup(transform3D);
        tgColaS1.addChild(colas1);
        tgColaS1_Dragon.addChild(tgColaS1);
        Cylinder colas2 = new Cylinder(.45f, .8f);
        transform3D.setTranslation(new Vector3f(0f, 1.5f, 0f));
        TransformGroup tgColaS2 = new TransformGroup(transform3D);
        tgColaS2.addChild(colas2);
        tgColaS1_Dragon.addChild(tgColaS2);
        Sphere pcColaS2 = new Sphere(.4f); //esfera: punto control para cabeza
        transform3D.setTranslation(new Vector3f(0f, 1.9f, 0f));
        TransformGroup tgpcColaS2 = new TransformGroup(transform3D);
        tgpcColaS2.addChild(pcColaS2);
        tgColaS1_Dragon.addChild(tgpcColaS2);
        tgColaS1_Dragon.addChild(creaColaS2(tgpcColaS2)); //se ensambla la cabeza al cuello
        tgColaS1_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ(-Math.PI / 1.5); //1.3-3.5
        tgColaS1_Dragon.setTransform(roz);
        cola_sec1_dragon.addChild(tgColaS1_Dragon);
        return cola_sec1_dragon;
    }

    public TransformGroup creaColaS2(TransformGroup pc) {
        TransformGroup cola_sec2_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        cola_sec2_dragon = new TransformGroup(transform3D);
        tgColaS2_Dragon = new TransformGroup();
        Cylinder colas2 = new Cylinder(.35f, .7f);
        transform3D.setTranslation(new Vector3f(0f, .3f, 0f));
        TransformGroup tgColaS2 = new TransformGroup(transform3D);
        tgColaS2.addChild(colas2);
        tgColaS2_Dragon.addChild(tgColaS2);
        Cylinder colas3 = new Cylinder(.3f, .5f);
        transform3D.setTranslation(new Vector3f(0f, .75f, 0f));
        TransformGroup tgColaS3 = new TransformGroup(transform3D);
        tgColaS3.addChild(colas3);
        tgColaS2_Dragon.addChild(tgColaS3);
        Sphere pcColaS3 = new Sphere(.28f); //esfera: punto control para cola seccion 2
        transform3D.setTranslation(new Vector3f(0f, 1f, 0f));
        TransformGroup tgpcColaS3 = new TransformGroup(transform3D);
        tgpcColaS3.addChild(pcColaS3);
        tgColaS2_Dragon.addChild(tgpcColaS3);
        tgColaS2_Dragon.addChild(creaColaS3(tgpcColaS3));
        tgColaS2_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ(Math.PI / 10.0); //2.3-20.0
        tgColaS2_Dragon.setTransform(roz);
        cola_sec2_dragon.addChild(tgColaS2_Dragon);
        return cola_sec2_dragon;
    }

    public TransformGroup creaColaS3(TransformGroup pc) {
        TransformGroup cola_sec3_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        cola_sec3_dragon = new TransformGroup(transform3D);
        tgColaS3_Dragon = new TransformGroup();
        Cone pcColaS3 = new Cone(.28f, 1f); //cono, punta de cola
        transform3D.setTranslation(new Vector3f(0f, .5f, 0f));
        TransformGroup tgpcColaS3 = new TransformGroup(transform3D);
        tgpcColaS3.addChild(pcColaS3);
        tgColaS3_Dragon.addChild(tgpcColaS3);
        tgColaS3_Dragon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ(Math.PI / 9.0); //2.5-30.0
        tgColaS3_Dragon.setTransform(roz);
        cola_sec3_dragon.addChild(tgColaS3_Dragon);
        return cola_sec3_dragon;
    }

    private TransformGroup creaPataD(TransformGroup pc, String E) {
        TransformGroup pata_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        pata_dragon = new TransformGroup(transform3D);
        TransformGroup tgPataD = new TransformGroup();
        Cylinder pata = new Cylinder(.5f, 1.5f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .85f, 0f));
        TransformGroup tgPata = new TransformGroup(transform3D);
        tgPata.addChild(pata);
        tgPataD.addChild(tgPata);
        Sphere pcpata = new Sphere(.45f); //esfera: punto de control parta inferior de la pats
        transform3D.setTranslation(new Vector3f(0f, 1.5f, 0f));
        TransformGroup tgpcPata = new TransformGroup(transform3D);
        tgpcPata.addChild(pcpata);
        tgPataD.addChild(tgpcPata);
        if (E.equals("I")) {
            tgPataD.addChild(creaPataS2(tgpcPata, "I"));
        } else {
            tgPataD.addChild(creaPataS2(tgpcPata, "D"));
        }
        tgPataD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((-Math.PI) / 1.2); //.95-1.5
        tgPataD.setTransform(roz);
        if (E.equals("I")) {
            tgPataDelanteraI_Dragon = tgPataD;
            pata_dragon.addChild(tgPataDelanteraI_Dragon);
        } else {
            tgPataDelanteraD_Dragon = tgPataD;
            pata_dragon.addChild(tgPataDelanteraD_Dragon);
        }
        return pata_dragon;
    }

    private TransformGroup creaPataS2(TransformGroup pc, String E) {
        TransformGroup pata_s2_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        pata_s2_dragon = new TransformGroup(transform3D);
        TransformGroup tgPataD = new TransformGroup();
        Cylinder pata = new Cylinder(.4f, 1f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .7f, 0f));
        TransformGroup tgPata = new TransformGroup(transform3D);
        tgPata.addChild(pata);
        tgPataD.addChild(tgPata);
        Cone pico = new Cone(.3f, -.8f);
        transform3D.setTranslation(new Vector3f(-.1f, -.5f, 0f));
        TransformGroup tgPico = new TransformGroup(transform3D);
        tgPico.addChild(pico);
        tgPataD.addChild(tgPico);
        Sphere p = new Sphere(.33f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, 1.2f, 0f));
        TransformGroup tgP = new TransformGroup(transform3D);
        tgP.addChild(p);
        Transform3D pos = new Transform3D();
        pos.rotZ((Math.PI) / 5.5);
        Appearance pintura1 = new Appearance();
        pintura1.setMaterial(new Material());
        Box box = new Box(.5f, .2f, .25f, pintura1);
        pos.setTranslation(new Vector3f(.2f, .25f, 0f));
        TransformGroup tgBP = new TransformGroup(pos);
        tgBP.addChild(box);
        tgP.addChild(tgBP);
        Cone cpp = new Cone(.2f, .7f);
        pos = new Transform3D();
        pos.rotZ((Math.PI) / 1.5);
        pos.setTranslation(new Vector3f(-.5f, -.25f, 0f));
        TransformGroup tgCPP = new TransformGroup(pos);
        tgCPP.addChild(cpp);
        tgP.addChild(tgCPP);
        tgPataD.addChild(tgP);
        tgPataD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((-Math.PI) / 3.4);
        tgPataD.setTransform(roz);
        if (E.equals("I")) {
            tgRodillaDelanteraI_Dragon = tgPataD;
            pata_s2_dragon.addChild(tgRodillaDelanteraI_Dragon);
        } else {
            tgRodillaDelanteraD_Dragon = tgPataD;
            pata_s2_dragon.addChild(tgRodillaDelanteraD_Dragon);
        }
        return pata_s2_dragon;
    }

    private TransformGroup creaPataT(TransformGroup pc, String E) {
        TransformGroup patat_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        patat_dragon = new TransformGroup(transform3D);
        TransformGroup tgPataT = new TransformGroup();
        Cylinder pata = new Cylinder(.45f, 1.2f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .6f, 0f));
        TransformGroup tgPata = new TransformGroup(transform3D);
        tgPata.addChild(pata);
        tgPataT.addChild(tgPata);
        Sphere pcpata = new Sphere(.4f); //esfera: punto de control parte media de la pata
        transform3D.setTranslation(new Vector3f(0f, 1.2f, 0f));
        TransformGroup tgpcPata = new TransformGroup(transform3D);
        tgpcPata.addChild(pcpata);
        tgPataT.addChild(tgpcPata);
        if (E.equals("I")) {
            tgPataT.addChild(creaPataTS2(tgpcPata, "I"));
        } else {
            tgPataT.addChild(creaPataTS2(tgpcPata, "D"));
        }
        tgPataT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((Math.PI) / 1.2); //.95-1.5
        tgPataT.setTransform(roz);
        if (E.equals("I")) {
            tgPataTraseraI_Dragon = tgPataT;
            patat_dragon.addChild(tgPataTraseraI_Dragon);
        } else {
            tgPataTraseraD_Dragon = tgPataT;
            patat_dragon.addChild(tgPataTraseraD_Dragon);
        }
        return patat_dragon;
    }

    private TransformGroup creaPataTS2(TransformGroup pc, String E) {
        TransformGroup pata_s2_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        pata_s2_dragon = new TransformGroup(transform3D);
        TransformGroup tgPataT = new TransformGroup();
        Cylinder pata = new Cylinder(.4f, 1f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .4f, 0f));
        TransformGroup tgPata = new TransformGroup(transform3D);
        tgPata.addChild(pata);
        tgPataT.addChild(tgPata);
        Sphere patas3 = new Sphere(.35f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .9f, 0f));
        TransformGroup tgpcPataS3 = new TransformGroup(transform3D);
        tgpcPataS3.addChild(patas3);
        tgPataT.addChild(tgpcPataS3);
        tgPataT.addChild(creaPataTS3(tgpcPataS3));
        tgPataT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((Math.PI) / 2.0);
        tgPataT.setTransform(roz);
        if (E.equals("I")) {
            tgRodillaTraseraI_Dragon = tgPataT;
            pata_s2_dragon.addChild(tgRodillaTraseraI_Dragon);
        } else {
            tgRodillaTraseraD_Dragon = tgPataT;
            pata_s2_dragon.addChild(tgRodillaTraseraD_Dragon);
        }
        return pata_s2_dragon;
    }

    private TransformGroup creaPataTS3(TransformGroup pc) {
        TransformGroup patat_dragon;
        Transform3D transform3D = new Transform3D();
        pc.getTransform(transform3D);
        Vector3f vpc = new Vector3f();
        transform3D.get(vpc);
        transform3D.setTranslation(vpc);
        patat_dragon = new TransformGroup(transform3D);
        TransformGroup tgPataT = new TransformGroup();
        Cylinder pata = new Cylinder(.33f, .7f); //cilindro pata
        transform3D.setTranslation(new Vector3f(0f, .45f, 0f));
        TransformGroup tgPata = new TransformGroup(transform3D);
        tgPata.addChild(pata);
        tgPataT.addChild(tgPata);
        Sphere pcpata = new Sphere(.3f); //esfera: punto de control parte media de la pata
        transform3D.setTranslation(new Vector3f(0f, .75f, 0f));
        TransformGroup tgpcPata = new TransformGroup(transform3D);
        tgpcPata.addChild(pcpata);
        tgPataT.addChild(tgpcPata);
        Transform3D pos = new Transform3D();
        pos.rotZ((Math.PI) / 5.5);
        Appearance appearance = new Appearance();
        appearance.setMaterial(new Material());
        Box pie = new Box(.45f, .15f, .20f, appearance);
        pos.setTranslation(new Vector3f(.2f, .6f, 0f));
        TransformGroup tgPie = new TransformGroup(pos);
        tgPie.addChild(pie);
        tgPata.addChild(tgPie);
        Cone cpp = new Cone(.15f, .5f);
        pos = new Transform3D();
        pos.rotZ((Math.PI) / 1f);
        pos.setTranslation(new Vector3f(0f, -1f, 0f));
        TransformGroup tgCPP = new TransformGroup(pos);
        tgCPP.addChild(cpp);
        tgPata.addChild(tgCPP);
        Cone cppt = new Cone(.15f, .5f);
        pos = new Transform3D();
        pos.rotZ((Math.PI) / 1.5);
        pos.setTranslation(new Vector3f(-.4f, .25f, 0f));
        TransformGroup tgCPPT = new TransformGroup(pos);
        tgCPPT.addChild(cppt);
        tgPata.addChild(tgCPPT);
        tgPataT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D roz = new Transform3D();
        roz.rotZ((-Math.PI) / 2.2);
        tgPataT.setTransform(roz);
        patat_dragon.addChild(tgPataT);
        return patat_dragon;
    }

    private Background createBackground() {
        BoundingSphere bounds;
        Background background;
        bounds = new BoundingSphere();
        background = new Background(1.0f, 1.0f, 4.0f);
        background.setApplicationBounds(bounds);
        return background;
    }

    private AmbientLight createAmbientLight() {
        AmbientLight light;
        BoundingSphere bounds;
        bounds = new BoundingSphere();
        light = new AmbientLight(true, new Color3f(Color.BLUE));
        light.setInfluencingBounds(bounds);
        return light;
    }

    private PointLight createPointLight1() {
        PointLight light;
        BoundingSphere bounds;
        bounds = new BoundingSphere();
        light = new PointLight(new Color3f(Color.CYAN), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        light.setInfluencingBounds(bounds);
        return light;
    }

    private PointLight createPointLight2() {
        PointLight light;
        BoundingSphere bounds;
        bounds = new BoundingSphere();
        light = new PointLight(new Color3f(Color.BLACK), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        light.setInfluencingBounds(bounds);
        return light;
    }

    private Texture getTexture(String n) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(n));

        } catch (IOException e) {
        }
        TextureLoader loader = new TextureLoader(bufferedImage, "LUMINACE", new Container());
        Texture texture = loader.getTexture();
        return texture;
    }

    void setValores(int x, int y, int z) {
        Transform3D rotX = new Transform3D();
        Transform3D rotY = new Transform3D();
        Transform3D rotZ = new Transform3D();
        rotX.rotX((x * Math.PI) / 180);
        rotY.rotY((y * Math.PI) / 180);
        rotZ.rotZ((z * Math.PI) / 180);
        rotY.mul(rotZ);
        rotX.mul(rotY);
        switch (extremidad) {
        case CUERPO:
            rotX.setScale(escala);
            tgCuerpo_Dragon.setTransform(rotX);
            break;
        case CUELLO:
            tgCuello_Dragon.setTransform(rotX);
            break;
        case CABEZA:
            tgCabeza_Dragon.setTransform(rotX);
            break;
        case COLAS1:
            tgColaS1_Dragon.setTransform(rotX);
            break;
        case COLAS2:
            tgColaS2_Dragon.setTransform(rotX);
            break;
        case COLAS3:
            tgColaS3_Dragon.setTransform(rotX);
            break;
        case PATADIZQ:
            tgPataDelanteraI_Dragon.setTransform(rotX);
            break;
        case RODILLADIZQ:
            tgRodillaDelanteraI_Dragon.setTransform(rotX);
            break;
        case PATADDER:
            tgPataDelanteraD_Dragon.setTransform(rotX);
            break;
        case RODILLADDER:
            tgRodillaDelanteraD_Dragon.setTransform(rotX);
            break;
        case PATATIZQ:
            tgPataTraseraI_Dragon.setTransform(rotX);
            break;
        case RODILLATIZQ:
            tgRodillaTraseraI_Dragon.setTransform(rotX);
            break;
        case PATATDER:
            tgPataTraseraD_Dragon.setTransform(rotX);
            break;
        case RODILLATDER:
            tgRodillaTraseraD_Dragon.setTransform(rotX);
            break;
        case ALAS:
            tgAlaIzq.setTransform(rotX);
            rotX = new Transform3D();
            rotY = new Transform3D();
            rotZ = new Transform3D();
            rotX.rotX((-x * Math.PI) / 180);
            rotY.rotY((-y * Math.PI) / 180);
            rotZ.rotZ((z * Math.PI) / 180);
            rotY.mul(rotZ);
            rotX.mul(rotY);
            tgAlaDer.setTransform(rotX);
            break;
        }
    }

    void setAlpha(int Alpha) {
        System.out.println(Alpha);
        rotationAlpha = new Alpha(-1, Alpha);
    }

    void setEscala(double e) {
        Transform3D transform3D = new Transform3D();
        transform3D.rotZ(-Math.PI / 15.0);
        transform3D.setScale(escala);
        tgCuerpo_Dragon.setTransform(transform3D);
        escala = e;
    }

    void setExtremidad(int E) {
        extremidad = E;
        int[][] V = new int[3][3];
        switch (extremidad) {
        case CUERPO:
            V[0][0] = -90;
            V[0][1] = 90;
            V[0][2] = 0;
            V[1][0] = -90;
            V[1][1] = 90;
            V[1][2] = 0; /**/
            V[2][0] = -90;
            V[2][1] = 90;
            V[2][2] = -14; /**/
            pnlOpc.setValoresSld(V);
            break;
        case CUELLO:
            V[0][0] = -30;
            V[0][1] = 30;
            V[0][2] = 0;
            V[1][0] = -75;
            V[1][1] = 75;
            V[1][2] = 0; /**/
            V[2][0] = -4;
            V[2][1] = 135;
            V[2][2] = 45; /**/
            pnlOpc.setValoresSld(V);
            break;
        case CABEZA:
            V[0][0] = -32;
            V[0][1] = 32;
            V[0][2] = 0;
            V[1][0] = -100;
            V[1][1] = 100;
            V[1][2] = 0;
            V[2][0] = -60;
            V[2][1] = 30;
            V[2][2] = 0;
            pnlOpc.setValoresSld(V);
            break;
        case COLAS1:
            V[0][0] = -50;
            V[0][1] = 50;
            V[0][2] = 0;
            V[1][0] = -90;
            V[1][1] = 90;
            V[1][2] = 0;
            V[2][0] = -140;
            V[2][1] = -15;
            V[2][2] = -129;
            pnlOpc.setValoresSld(V);
            break;
        case COLAS2:
            V[0][0] = -50;
            V[0][1] = 50;
            V[0][2] = 0;
            V[1][0] = -100;
            V[1][1] = 100;
            V[1][2] = 0;
            V[2][0] = -80;
            V[2][1] = 80;
            V[2][2] = 22;
            pnlOpc.setValoresSld(V);
            break;
        case COLAS3:
            V[0][0] = -50;
            V[0][1] = 50;
            V[0][2] = 0;
            V[1][0] = -100;
            V[1][1] = 100;
            V[1][2] = 0;
            V[2][0] = -10;
            V[2][1] = 100;
            V[2][2] = 0;
            pnlOpc.setValoresSld(V);
            break;
        case PATADIZQ:
            V[0][0] = -84;
            V[0][1] = 15;
            V[0][2] = 0;
            V[1][0] = -48;
            V[1][1] = 32;
            V[1][2] = 0;
            V[2][0] = -240;
            V[2][1] = -130;
            V[2][2] = -149;
            pnlOpc.setValoresSld(V);
            break;
        case RODILLADIZQ:
            V[0][0] = -20;
            V[0][1] = 53;
            V[0][2] = 0;
            V[1][0] = -20;
            V[1][1] = 20;
            V[1][2] = 0;
            V[2][0] = -100;
            V[2][1] = -30;
            V[2][2] = -53;
            pnlOpc.setValoresSld(V);
            break;
        case PATADDER:
            V[0][0] = -15;
            V[0][1] = 85;
            V[0][2] = 0;
            V[1][0] = -32;
            V[1][1] = 48;
            V[1][2] = 0;
            V[2][0] = 130;
            V[2][1] = 240;
            V[2][2] = 209;
            pnlOpc.setValoresSld(V);
            break;
        case RODILLADDER:
            V[0][0] = -53;
            V[0][1] = 20;
            V[0][2] = 0;
            V[1][0] = -20;
            V[1][1] = 20;
            V[1][2] = 0;
            V[2][0] = -100;
            V[2][1] = 4;
            V[2][2] = -55;
            pnlOpc.setValoresSld(V);
            break;
        case PATATIZQ:
            V[0][0] = -69;
            V[0][1] = 15;
            V[0][2] = 0;
            V[1][0] = -48;
            V[1][1] = 20;
            V[1][2] = 0;
            V[2][0] = -240;
            V[2][1] = -155;
            V[2][2] = -209;
            pnlOpc.setValoresSld(V);
            break;
        case RODILLATIZQ:
            V[0][0] = -20;
            V[0][1] = 53;
            V[0][2] = 0;
            V[1][0] = -20;
            V[1][1] = 20;
            V[1][2] = 0;
            V[2][0] = 65;
            V[2][1] = 150;
            V[2][2] = 90;
            pnlOpc.setValoresSld(V);
            break;
        case PATATDER:
            V[0][0] = -15;
            V[0][1] = 85;
            V[0][2] = 0;
            V[1][0] = -32;
            V[1][1] = 48;
            V[1][2] = 0;
            V[2][0] = 120;
            V[2][1] = 205;
            V[2][2] = 149;
            pnlOpc.setValoresSld(V);
            break;
        case RODILLATDER:
            V[0][0] = -20;
            V[0][1] = 53;
            V[0][2] = 0;
            V[1][0] = -20;
            V[1][1] = 20;
            V[1][2] = 0;
            V[2][0] = 65;
            V[2][1] = 150;
            V[2][2] = 90;
            pnlOpc.setValoresSld(V);
            break;
        case ALAS:
            V[0][0] = 0;
            V[0][1] = 100;
            V[0][2] = 14;
            V[1][0] = 0;
            V[1][1] = 30;
            V[1][2] = 0;
            V[2][0] = -50;
            V[2][1] = 5;
            V[2][2] = -14;
            pnlOpc.setValoresSld(V);
            break;
        }
    }
}
