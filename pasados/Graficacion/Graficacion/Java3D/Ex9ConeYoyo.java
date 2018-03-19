/**
 * EXAMPLE 9 CONE YOYO CLASS
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

public class Ex9ConeYoyo {
    //  FIELDS
    private BranchGroup yoyoBranchGroup;
    private Appearance appearance;
    Transform3D rotate, translate;
    TransformGroup transformGroupTraslateCone1, transformGroupRotateCone1;
    TransformGroup transformGroupTraslateCone2, transformGroupRotateCone2;
    Cone cone1, cone2;

    //  CONSTRUCTOR
    Ex9ConeYoyo() {
        yoyoBranchGroup = new BranchGroup();
        appearance = new Appearance();

        rotate = new Transform3D();
        translate = new Transform3D();

        translate.set(new Vector3f(0.1f, 0.0f, 0.0f));
        rotate.rotZ(Math.PI / 2.0d);
        transformGroupTraslateCone1 = new TransformGroup(translate);
        transformGroupRotateCone1 = new TransformGroup(rotate);

        translate.set(new Vector3f(-0.1f, 0.0f, 0.0f));
        rotate.rotZ(-Math.PI / 2.0d);
        transformGroupTraslateCone2 = new TransformGroup(translate);
        transformGroupRotateCone2 = new TransformGroup(rotate);

        cone1 = new Cone(0.6f, 0.2f);
        cone2 = new Cone(0.6f, 0.2f);

        cone1.setAppearance(appearance);
        cone2.setAppearance(appearance);

        transformGroupRotateCone1.addChild(cone1);
        transformGroupRotateCone2.addChild(cone2);
        transformGroupTraslateCone1.addChild(transformGroupRotateCone1);
        transformGroupTraslateCone2.addChild(transformGroupRotateCone2);
        yoyoBranchGroup.addChild(transformGroupTraslateCone1);
        yoyoBranchGroup.addChild(transformGroupTraslateCone2);

        yoyoBranchGroup.compile();
    }   //  END ONSTRUCTOR

    //  GET BRANCH GROUP
    public BranchGroup getBranchGroup() {
      return yoyoBranchGroup;
    }   //  END GET BRANCH GROUP

  } // end of class ConeYoyo
