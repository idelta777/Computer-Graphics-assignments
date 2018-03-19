package E2GraphicsContents;

import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class Ex6 extends Applet {
  public static void main(String[] args) {
    new MainFrame(new Ex6(), 640, 480);
  }

  public void init() { 
    GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
    Canvas3D cv = new Canvas3D(gc);
    setLayout(new BorderLayout());
    add(cv, BorderLayout.CENTER);
    BranchGroup bg = createSceneGraph();
    bg.compile();
    SimpleUniverse su = new SimpleUniverse(cv);
    su.getViewingPlatform().setNominalViewingTransform();
    su.addBranchGraph(bg);
  }

  private BranchGroup createSceneGraph() {
    BranchGroup root = new BranchGroup();
    TransformGroup spin = new TransformGroup();
    spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    root.addChild(spin);

    //object
    Appearance ap = new Appearance();
    ap.setMaterial(new Material());
    Shape3D shape = new Shape3D(frustum(), ap);

    Transform3D tr = new Transform3D();
    tr.set(new Vector3f(0,-1.5f,0), 0.3f);
    TransformGroup tg = new TransformGroup(tr);
    spin.addChild(tg);
    tg.addChild(shape);

    Alpha alpha = new Alpha(-1, 4000);
    RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
    BoundingSphere bounds = new BoundingSphere();
    rotator.setSchedulingBounds(bounds);
    spin.addChild(rotator);

    //background and light
    Background background = new Background(1.0f, 1.0f, 1.0f);
    background.setApplicationBounds(bounds);
    root.addChild(background);
    AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
    light.setInfluencingBounds(bounds);
    root.addChild(light);
    PointLight ptlight = new PointLight(new Color3f(Color.green),
      new Point3f(3f,3f,3f), new Point3f(1f,0f,0f));
    ptlight.setInfluencingBounds(bounds);
    root.addChild(ptlight);
    PointLight ptlight2 = new PointLight(new Color3f(Color.orange),
      new Point3f(-2f,2f,2f), new Point3f(1f,0f,0f));
    ptlight2.setInfluencingBounds(bounds);
    root.addChild(ptlight2);
    return root;
  }

  private Geometry frustum() {
    IndexedQuadArray iqa = new IndexedQuadArray(8, GeometryArray.COORDINATES | GeometryArray.NORMALS, 4*6);
    Point3d[] coords = {new Point3d(-0.5, 1, -0.5), new Point3d(-0.5, 1, 0.5),
            new Point3d(0.5, 1, 0.5), new Point3d(0.5, 1, -0.5),
            new Point3d(-1, 0, -1), new Point3d(-1, 0, 1),
            new Point3d(1, 0, 1), new Point3d(1, 0, -1)
    };      
    iqa.setCoordinates(0, coords);
    int[] indices = {0,1,2,3,
            0,4,5,1, 1,5,6,2, 2,6,7,3, 3,7,4,0,
            4,7,6,5};
    iqa.setCoordinateIndices(0, indices);
    float c = 1/(float)Math.sqrt(5);
    float s = 2*c;
    Vector3f[] normals = {new Vector3f(0, 1, 0),
    new Vector3f(-c, s, 0),new Vector3f(0, s, c),new Vector3f(c, s, 0),new Vector3f(0, s, -c),
    new Vector3f(0, -1, 0)};
    iqa.setNormals(0, normals);
    int[] normIndices = {0,0,0,0,
            1,1,1,1, 2,2,2,2, 3,3,3,3, 4,4,4,4,
            5,5,5,5};
    iqa.setNormalIndices(0, normIndices);
    return iqa;
  }
}