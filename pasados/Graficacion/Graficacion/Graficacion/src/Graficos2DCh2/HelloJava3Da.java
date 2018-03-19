/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos2DCh2;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.vecmath.Vector3f;

public class HelloJava3Da extends Applet {
    public HelloJava3Da() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
        SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        BranchGroup scene5 = createSceneGraph5();
        BranchGroup scene4 = createSceneGraph4();
        BranchGroup scene3 = createSceneGraph3();
        scene5.compile();
        scene4.compile();
        scene3.compile();
        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        // This moves the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene4);
        simpleU.addBranchGraph(scene5);
        //simpleU.addBranchGraph(scene3);
    } // end of HelloJava3Da (constructor)
    
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();
        // Create a simple shape leaf node, add it to the scene graph.
        // ColorCube is a Convenience Utility class
        objRoot.addChild(new ColorCube(0.4));
        return objRoot;
    } // end of createSceneGraph method of HelloJava3Da
    public BranchGroup createSceneGraph2() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();
        // rotate object has composite transformation matrix
        Transform3D rotate = new Transform3D();
        rotate.rotX(Math.PI/5.0d);
        TransformGroup objRotate = new TransformGroup(rotate);
        objRotate.addChild(new ColorCube(0.4));
        objRoot.addChild(objRotate);
        return objRoot;
    } // end of createSceneGraph method
    public BranchGroup createSceneGraph3() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();
        // rotate object has composite transformation matrix
        Transform3D rotate = new Transform3D();
        Transform3D tempRotate = new Transform3D();
        rotate.rotX(Math.PI/4.0d);
        tempRotate.rotY(Math.PI/5.0d);
        rotate.mul(tempRotate);
        TransformGroup objRotate = new TransformGroup(rotate);
        objRotate.addChild(new ColorCube(0.4));
        objRoot.addChild(objRotate);
        return objRoot;
    }
    public BranchGroup createSceneGraph4() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();
        // Create the transform group node and initialize it to the
        // identity. Add it to the root of the subgraph.
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objSpin);
        // Create a simple shape leaf node, add it to the scene graph.
        // ColorCube is a Convenience Utility class
        objSpin.addChild(new ColorCube(0.4));
        // create time varying function to drive the animation
        Alpha rotationAlpha = new Alpha(-1, 4000);
        // Create a new Behavior object that performs the desired
        // operation on the specified transform object and add it into
        // the scene graph.
        RotationInterpolator rotator =
        new RotationInterpolator(rotationAlpha, objSpin);
        // a bounding sphere specifies a region a behavior is active
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);
        return objRoot;
        //end of createSceneGraph method
    }
    public BranchGroup createSceneGraph5() {
        BranchGroup objRoot = new BranchGroup();
        // rotate object has composite transformation matrix
        Transform3D rotate = new Transform3D();
        Transform3D tempRotate = new Transform3D();
        rotate.rotX(Math.PI/4.0d);
        tempRotate.rotY(Math.PI/5.0d);
        TransformGroup objRotate = new TransformGroup(rotate);
        // Create the transform group node and initialize it to the
        // identity. Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime. Add it to the
        // root of the subgraph.
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objRotate);
        objRotate.addChild(objSpin);
        // Create a simple shape leaf node, add it to the scene graph.
        // ColorCube is a Convenience Utility class
        objSpin.addChild(new ColorCube(0.4));
        // Create a new Behavior object that performs the desired
        // operation on the specified transform object and add it into
        // the scene graph.

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, 4000);
        RotationInterpolator rotator =
        new RotationInterpolator(rotationAlpha, objSpin, yAxis,
        0.0f, (float) Math.PI*2.0f);
        // a bounding sphere specifies a region a behavior is active
        // create a sphere centered at the origin with radius of 1
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);
        return objRoot;
    } // end of createSceneGraph method of HelloJava3Dd


    public static void main(String[] args) {
        Frame frame = new MainFrame(new HelloJava3Da(), 356, 356);
    } // end of main (method of HelloJava3Da)

} // end of class HelloJava3Da
