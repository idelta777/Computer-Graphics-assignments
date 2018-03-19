/**
 * EXAMPLE 8 SIMPLE ANIMATED CYLINDER
 *
 * @author          Cavees from java 3d tutorial
 * @version         1.0, 11.05.2012
 *
 */

package Graficos2DCh2;
import java.applet.Applet;
import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;

public class Ex9ConeYoyoApp extends Applet{
    //  FIELDS
    GraphicsConfiguration graphicsConfiguration;
    Canvas3D canvas3D;
    SimpleUniverse simpleUniverse;
    BranchGroup contentBranchGraph;

    //  CONSTRUCTOR
    Ex9ConeYoyoApp(){
        setLayout(new BorderLayout());

        graphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(graphicsConfiguration);

        contentBranchGraph = createContentBranchGraph();
        contentBranchGraph.compile();

        simpleUniverse = new SimpleUniverse(canvas3D);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        simpleUniverse.addBranchGraph(contentBranchGraph);
        add("Center", canvas3D);
    }   //  END CONSTRUCTOR


    //  CREATE SCENE GRAPH
    public BranchGroup createContentBranchGraph() {
        //  FIELDS
        BranchGroup branchGraph;
        TransformGroup objectSpin;
        Alpha rotationAlpha;
        RotationInterpolator interpolator;
        BoundingSphere bounds,bouds2;

        branchGraph = new BranchGroup();
        objectSpin = new TransformGroup();
        /*Cone cono3 = new Cone();
        Appearance apariencia = new Appearance();
        cono3.setAppearance(apariencia);
        objectSpin.addChild(cono3);*/
        bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        rotationAlpha = new Alpha(-1, 4000);
        //Transform3D yAxis = new Transform3D();
        interpolator = new RotationInterpolator(rotationAlpha, objectSpin);
                //yAxis,0.0f,-3*3.0f);

        objectSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        interpolator.setSchedulingBounds(bounds);

        objectSpin.addChild(interpolator);
        //objectSpin.addChild(new Ex9ConeYoyo().getBranchGroup());
        
        objectSpin.addChild(new Ex9ConeYoyo().getBranchGroup());
        
        branchGraph.addChild(objectSpin);

        branchGraph.compile();
	return branchGraph;
    }   //  END CREATE SCENE GRAPH

    public static void main(String[] args) {
        Frame frame = new MainFrame(new Ex9ConeYoyoApp(), 256, 256);
    }
}

