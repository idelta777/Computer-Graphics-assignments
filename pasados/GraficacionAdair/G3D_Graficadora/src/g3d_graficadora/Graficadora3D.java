package g3d_graficadora;

import java.awt.GraphicsConfiguration;
import java.awt.event.ActionListener;

import javax.media.j3d.BranchGroup;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;

import javax.media.j3d.Canvas3D;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;

import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.media.j3d.TransparencyAttributes;

import javax.swing.UIManager;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class Graficadora3D extends JFrame implements ActionListener {
    private JPanel panel = new JPanel();
    PnlOpciones pnlOpc = new PnlOpciones(this);
    TransformGroup tgGrafica, tgcolor;
    Shape3D shape;
    PointLight ptlight;
    BoundingSphere bounds;
    double escala = 0.1;
    int largo = 150, grosor = 10;
    String ecuacion = "sin(x)", expresion = "";
    Color C = Color.CYAN;
    BranchGroup bg;
    JTextField jtxtEcuacion = new JTextField(15);
    JButton jbtnGraficar = new JButton("Graficar");

    SimpleUniverse su;
    int m = 80;
    Canvas3D cv;
    GraphicsConfiguration gc;

    public Graficadora3D() {
        try {
            jbinit();
            establecer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jbinit() throws Exception {
        int dspBtns = 60;
        this.getContentPane().setLayout(null);
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBounds(new Rectangle(10, 10, 700, 630));
        jbtnGraficar.setBounds(dspBtns + 55, 230, 100, 25);
        jbtnGraficar.setActionCommand("graficar");
        jbtnGraficar.addActionListener((ActionListener)this);
        pnlOpc.setBounds(720, 30, 220, 400);
        this.getContentPane().add(pnlOpc, null);
        this.getContentPane().add(panel);

    }

    public void establecer() {
        setLayout(new BorderLayout());
        gc = SimpleUniverse.getPreferredConfiguration();
        cv = new Canvas3D(gc);
        bg = createSceneGraph();
        su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        cv.setSize(panel.getSize());
        panel.add(cv, BorderLayout.EAST);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        Appearance ap = new Appearance();
        bounds = new BoundingSphere();

        ap = new Appearance();
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        ap.setPolygonAttributes(polyAttrib);
        //TransparencyAttributes transp = new TransparencyAttributes();
        //transp.setTransparency(5);
        //ap.setTransparencyAttributes(transp);
        ap.setMaterial(new Material());
        //Material M = new Material();
        shape = new Shape3D(createGeometry(), ap);
        Transform3D tr = new Transform3D();
        tr.setScale(escala);
        tgGrafica = new TransformGroup(tr);
        tgGrafica.addChild(shape);

        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        AmbientLight light = new AmbientLight(true, new Color3f(Color.BLACK));
        light.setInfluencingBounds(bounds);
        ptlight = new PointLight(new Color3f(C), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);

        tgGrafica.addChild(background);
        tgGrafica.addChild(light);
        tgGrafica.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgcolor = new TransformGroup();
        tgcolor.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgcolor.addChild(ptlight);
        tgGrafica.addChild(tgcolor);

        TransformGroup spin = new TransformGroup();
        Alpha alpha = new Alpha(-1, 12000);
        RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
        rotator.setSchedulingBounds(bounds);
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        spin.addChild(rotator); //se agrega la rotacion
        spin.addChild(tgGrafica);

        root.addChild(spin);
        return root;
    }


    private Geometry createGeometry() {
        Parse parser;
        parser = new Parse();
        double valy = 0.0;
        parser.evaluador.parseExpression(ecuacion);
        parser.errorExpr = parser.evaluador.hasError();
        int n = 80;
        Point3f[] pts = new Point3f[m * n];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                float x = (i - m / 2) * 0.1f;
                float z = (j - n / 2) * 0.1f;
                parser.evaluador.addVariable("x", x);
                parser.evaluador.addVariable("z", z);
                valy = parser.evaluador.getValue();
                float y = (float)valy;
                pts[idx++] = new Point3f(x, y * .75f, z);
            }
        }
        int[] coords = new int[2 * n * (m - 1)];
        idx = 0;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                coords[idx++] = i * n + j;
                coords[idx++] = (i - 1) * n + j;
            }
        }
        int[] stripCounts = new int[m - 1];
        for (int i = 0; i < m - 1; i++)
            stripCounts[i] = 2 * n;
        GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_STRIP_ARRAY);
        gi.setCoordinates(pts);
        gi.setCoordinateIndices(coords);
        gi.setStripCounts(stripCounts);
        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        return gi.getGeometryArray();
    }

    public void actionPerformed(ActionEvent e) {
        this.ecuacion = jtxtEcuacion.getText();
        panel.removeAll();
        gc = SimpleUniverse.getPreferredConfiguration();
        cv = new Canvas3D(gc);
        bg = createSceneGraph();
        su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        cv.setSize(panel.getSize());
        panel.add(cv, BorderLayout.CENTER);
        panel.updateUI();
        this.repaint();
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(laf.getName()))
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (Exception ex) {
                }
        }
        Graficadora3D p = new Graficadora3D();
        p.setLocationRelativeTo(null);
        p.setResizable(false);
        p.setVisible(true);
    }


    void setColor(Color color) {
        C = color;
        panel.removeAll();
        gc = SimpleUniverse.getPreferredConfiguration();
        cv = new Canvas3D(gc);
        bg = createSceneGraph();
        su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        cv.setSize(panel.getSize());
        panel.add(cv, BorderLayout.CENTER);
        panel.updateUI();
        this.repaint();
    }

    void setTransparencia(int i) {
        m = i;
        panel.removeAll();
        gc = SimpleUniverse.getPreferredConfiguration();
        cv = new Canvas3D(gc);
        bg = createSceneGraph();
        su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        cv.setSize(panel.getSize());
        panel.add(cv, BorderLayout.CENTER);
        panel.updateUI();
        this.repaint();
    }

    void setEscala(double e) {
        Transform3D transform3D = new Transform3D();
        transform3D.setScale(e);
        tgGrafica.setTransform(transform3D);
        escala = e;
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
        rotX.setScale(escala);
        tgGrafica.setTransform(rotX);
    }

    void setEcuacion(String ec) {
        this.ecuacion = ec;
        panel.removeAll();
        gc = SimpleUniverse.getPreferredConfiguration();
        cv = new Canvas3D(gc);
        bg = createSceneGraph();
        su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        cv.setSize(panel.getSize());
        panel.add(cv, BorderLayout.CENTER);
        panel.updateUI();
        this.repaint();
    }
}
