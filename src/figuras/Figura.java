package figuras;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Figura implements Shape {
	protected int x, y;
	protected int width = 0, height = 0;
	protected GeneralPath path;
	protected Rectangle rectangulo = new Rectangle();
	protected int stroke = 1;
	protected Color color = Color.BLACK;

	public int escalaWidth;
	public int escalaHeight;
	
	public Figura() {
		path = new GeneralPath();
	}
	
	public void draw(Graphics2D g, boolean cambiandoTam) {
	}
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		rectangulo.setSize(w, h);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		rectangulo.setLocation(x, y);
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public Rectangle getRectangle() {
		return rectangulo;
	}
	
	public void setRectangleValues(int x, int y, int width, int height) {
		rectangulo.setLocation(x, y);
		rectangulo.setSize(width, height);
	}
	
	public int getY() {
		return y;
	}
	
	public void forma() {
	}

	@Override
	public boolean contains(Point2D arg0) {
		return path.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return path.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return path.contains(arg0, arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return path.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getBounds() {
		return path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return path.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return path.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return path.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return path.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return path.intersects(arg0, arg1, arg2, arg3);
	}
}
