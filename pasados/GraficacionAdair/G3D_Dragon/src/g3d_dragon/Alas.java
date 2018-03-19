package g3d_dragon;
import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;

public class Alas extends Shape3D{
   public Alas(double d1,double d2) {
    GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
    Point3d[] vertices = {new Point3d(0,0,0),new Point3d(1,-1,0),new Point3d(3.5,1.5,0),new Point3d(1.4,1.3,0),
                          new Point3d(.6,2.4,d1),new Point3d(4.5,2.7,d1),
                          new Point3d(3,5,d2)};
    int[] indices = { 0,1,2,3, 3,2,5,4, 4,5,6 };
    gi.setCoordinates(vertices);
    gi.setCoordinateIndices(indices);
    int[] stripCounts = {4,4,3};
    gi.setStripCounts(stripCounts);
    NormalGenerator ng = new NormalGenerator();
    ng.generateNormals(gi);
    this.setGeometry(gi.getGeometryArray());
  }
}
