
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * A TurlePanel is a drawing area in which a virtual "turtle" moves around,
 * possible leaving a trail as it goes. The turtle has a pen that can be raised
 * and lowered; when the pen is down, it leaves a trail. There are commands for
 * moving the turtle and for telling it which direction to face. The turtle is
 * shown as a black triangle pointing in the direction that the turtle is facing
 * (but the display of the turtle can be turned off). Ordinarily, any change to
 * the picture produces an automatic call to repaint (but this automatic
 * repainting can be turned off). The color of the trail left by the turtle can
 * be changed. For the purposes of the turtle, the coordinate system on the
 * drawing area extends from -10 at the left to 10 at the right and from -10 at
 * the bottom to 10 at the top. (If the panel is not square, then the unit of
 * measure in the horizontal direction is different from the unit in the
 * vertical direction.)
 */
public class TurtlePanel extends JPanel {

	private double turtleX, turtleY; // Location of the turtle.
	private double facing; // What direction the turtle is facing, given in
							// degrees.
	private boolean penIsUp; // Tells whether the pen is currently raised.

	private boolean turtleIsVisible = true; // Tells whether turtle should be
											// displayed.
	private boolean autoRepaint = true; // Tells whether repaint should be
										// called automatically.

	private BufferedImage canvas; // The offscreen, official copy of the
									// picture, without the turtle.
	private Graphics canvasGraphics; // A graphics context for drawing on the
										// canvas.

	private ArrayList<Double> x = new ArrayList<Double>();
	private ArrayList<Double> y = new ArrayList<Double>();
	private ArrayList<Double> xTemp = new ArrayList<Double>();
	ArrayList<Double> yTemp = new ArrayList<Double>();

	/**
	 * Create a TurtlePanel with a preferred size of 600-by-600. The turtle is
	 * places at (0,0), in the center of the panel, facing right.
	 */
	public TurtlePanel() {
		this(800);
	}
	
	
	public void triangle() {
		// get to start
		turn(90);
		penUp();
		forward(10);
		turn(150);
		penDown();

		/*
		 * p.drawTriangle(10); p.turn(120); p.forward(10);
		 * 
		 * p.drawTriangle(5); p.turn(240); p.forward(10);
		 * 
		 * p.turn(240); p.drawTriangle(5);
		 */

		int x = 5;
		while (x > 1) {
			int smCircle = x / 3;
			drawTriangle(x);
			turn(120);
			forward(x);

			drawTriangle(smCircle);
			turn(240);
			forward(x);

			turn(240);
			drawTriangle(smCircle);

			turn(120);
			forward(smCircle);
			--x;
		}
	}
	
	public void drawTriangle(int distance) {
		forward(distance);
		turn(120);
		forward(distance);
		turn(120);
		forward(distance);
	}

	
	

	
	
	public void treeQ5(int len) {	
		face(90);
		back(len);
		forward(len);

		x.add(getTurtleX());
		y.add(getTurtleY());

		while (len > 0) {

			while (!x.isEmpty()) {
				
				penUp();
				moveTo(x.get(0), y.get(0));
				x.remove(0);
				y.remove(0);
				penDown();

				
				Random ran = new Random();
				int r = ran.nextInt(19);
				System.out.println(r);
				while(r > 0) {
					if(len<3) {
						color(Color.green);
					}
					System.out.println(r);
					int r2 = ran.nextInt(180);
					r2= r2;
					face(r2);
					
					forward(len);
					xTemp.add(getTurtleX());
					yTemp.add(getTurtleY());
					back(len);
					r--;
					face(90);
				}

			}

			len--;
			x = xTemp;
			y = yTemp;
			xTemp = new ArrayList<Double>();
			yTemp = new ArrayList<Double>();

		}
		
	}

	public void tree(int len) {

		face(90);
		back(len);
		forward(len);

		x.add(getTurtleX());
		y.add(getTurtleY());

		while (len > 0) {

			while (!x.isEmpty()) {

				penUp();
				moveTo(x.get(0), y.get(0));
				x.remove(0);
				y.remove(0);
				penDown();

				face(45);
				forward(len);
				xTemp.add(getTurtleX());
				yTemp.add(getTurtleY());
				back(len);

				face(135);
				forward(len);
				xTemp.add(getTurtleX());
				yTemp.add(getTurtleY());
				back(len);

			}

			len--;
			x = xTemp;
			y = yTemp;
			xTemp = new ArrayList<Double>();
			yTemp = new ArrayList<Double>();

		}

	}

	/**
	 * Create a TurtlePanel with a given preferred size. The preferred size is a
	 * square. The turtle is places at (0,0), in the center of the panel, facing
	 * right.
	 */
	public TurtlePanel(int preferredSize) {

	}

	/**
	 * Draw the picture by copying the off-screen canvas onto the panel. If the
	 * turtle is visible, draw it on top of the picture from the panel.
	 */
	synchronized protected void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
		if (turtleIsVisible) { // Draw the turtle.
			Graphics2D g2 = (Graphics2D) g;
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			Path2D path = new Path2D.Double();
			double rad = facing / 180 * Math.PI;
			double dx = Math.cos(rad);
			double dy = Math.sin(rad);
			path.moveTo(getWidth() * (0.5 + (turtleX + dx) / 20), getHeight()
					* (0.5 - (turtleY + dy) / 20));
			path.lineTo(getWidth() * (0.5 + (turtleX - dy / 3) / 20),
					getHeight() * (0.5 - (turtleY + dx / 3) / 20));
			path.lineTo(getWidth() * (0.5 + (turtleX + dy / 3) / 20),
					getHeight() * (0.5 - (turtleY - dx / 3) / 20));
			path.closePath();
			g2.fill(path);
		}
	}

	/**
	 * Method used internally to move the turtle from its current position to
	 * (x,y). If the pen is down, a line is drawn between the two points.
	 */
	synchronized private void go(double x, double y) {
		if (!penIsUp) {
			double x1 = (0.5 + turtleX / 20.0) * canvas.getWidth();
			double y1 = (0.5 - turtleY / 20.0) * canvas.getHeight();
			double x2 = (0.5 + x / 20.0) * canvas.getWidth();
			double y2 = (0.5 - y / 20.0) * canvas.getHeight();
			canvasGraphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
			if (autoRepaint) {
				repaint();
			}
		}
		turtleX = x;
		turtleY = y;
	}

	/**
	 * Move the turtle forward in the direction it is facing for a given
	 * distance. (Note: negative distance will make the turtle back up.)
	 */
	public void forward(double distance) {
		double rad = facing / 180 * Math.PI;
		double dx = Math.cos(rad) * distance;
		double dy = Math.sin(rad) * distance;
		go(turtleX + dx, turtleY + dy);
	}

	/**
	 * Move the turtle in the direction opposite from the direction it is facing
	 * for a given distance. This is the same as forward(-distance). (Note:
	 * negative distance will make the turtle move forward.)
	 */
	public void back(double distance) {
		forward(-distance);
	}

	/**
	 * Move the turtle dx units horizontally and dy units vertically from its
	 * current position. The turtle's direction is not taken into consideration
	 * and does not change.
	 */
	public void move(double dx, double dy) {
		go(turtleX + dx, turtleY + dy);
	}

	/**
	 * Move the turtle to the point (x,y). The turtle's direction is not taken
	 * into consideration and does not change.
	 */
	public void moveTo(double x, double y) {
		go(x, y);
	}

	/**
	 * Turn the turtle through a given angle, specified in degrees. The angle is
	 * just added to the angle that gives the turtle's direction. For example,
	 * turn(90) is a left turn, and turn(180) reverses the direction of the
	 * turtle.
	 */
	public void turn(double angle) {
		facing += angle;
		if (turtleIsVisible && autoRepaint) {
			repaint();
		}
	}

	/**
	 * Turn the turtle so that it faces is in given direction. The angle is
	 * given in degrees. Zero degrees means facing right. Positive angles are
	 * counterclockwise from that orientation.
	 */
	public void face(double angle) {
		facing = angle;
		if (turtleIsVisible && autoRepaint) {
			repaint();
		}
	}

	/**
	 * Move the turtle to (0,0), and set its direction angle to 0 (facing
	 * right). This method also resets the line color and width to their default
	 * values.
	 */
	public void home() {
		go(0, 0);
		facing = 0;
		color(Color.RED);
		lineWidth(1);
	}

	/**
	 * Raise the turtle's pen, so it doesn't leave a trail when it moves.
	 */
	public void penUp() {
		penIsUp = true;
	}

	/**
	 * Lower the turtle's pen, so it will leave a trail when it moves.
	 */
	public void penDown() {
		penIsUp = false;
	}

	/**
	 * Set the color that will be used for the turtle's trail.
	 *
	 * @param c
	 *            the color for the trail. A null value is treated as black.
	 */
	public void color(Color c) {
		if (c == null) {
			c = Color.BLACK;
		}
		canvasGraphics.setColor(c);
	}

	/**
	 * Set the turtle's color to a random value. An HSB color is created with
	 * random hue and with brightness and saturation set to 1. This is a random
	 * "spectral" color.
	 */
	public void randomColor() {
		float hue = (float) Math.random();
		Color c = Color.getHSBColor(hue, 1, 1);
		color(c);
	}

	/**
	 * Set the width of the line, in pixels, that will be used for drawing the
	 * trail left by the turtle as it moves.
	 *
	 * @param width
	 *            The width of the line, in pixels. Non-integer values are
	 *            allowed. If this parameter is less than 1, a line width of 1
	 *            is used.
	 */
	public void lineWidth(double width) {
		if (width < 1) {
			width = 1;
		}
		if (width < 2) {
			((Graphics2D) canvasGraphics).setStroke(new BasicStroke(
					(float) width));
		} else {
			((Graphics2D) canvasGraphics).setStroke(new BasicStroke(
					(float) width, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
		}
	}

	/**
	 * Erase the picture by filling it with white. The turtle does not move or
	 * change direction.
	 */
	public void clear() {
		Color c = canvasGraphics.getColor();
		canvasGraphics.setColor(Color.WHITE);
		canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		canvasGraphics.setColor(c);
		if (autoRepaint) {
			repaint();
		}
	}

	/**
	 * Determines whether the turtle should be displayed. The default is true.
	 * If the value is set to false, the turtle is not shown.
	 */
	public void setTurtleIsVisible(boolean visible) {
		if (visible != turtleIsVisible) {
			turtleIsVisible = visible;
			if (autoRepaint) {
				repaint();
			}
		}
	}

	/**
	 * Determines whether repaint should be called automatically whenever the
	 * picture changes. The default is true. If the value is set to false,
	 * repaint() is not called automatically, and you are responsible for
	 * calling this panel's repaint() method when you want the picture on the
	 * screen to be updated. Turning off autorepaint can speed up long redraw
	 * operations and will prevent the user from seeing partially drawn pictures
	 * in the middle of the operation.
	 */
	public void setAutoRepaint(boolean autoRepaint) {
		this.autoRepaint = autoRepaint;
	}

	public double getTurtleX() {
		return turtleX;
	}

	public double getTurtleY() {
		return turtleY;
	}

	public void resetFacing() {
		facing = 0;
	}

}