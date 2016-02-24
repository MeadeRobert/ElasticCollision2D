import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.*;

import CustomAlgorithms.CustomMath;

/**
 * The Main class is the java applet as an object.
 * @author Robert James Meade
 * @version 0.2
 */
public class ElasticCollisionCircleSimulation extends Applet implements Runnable
{
	/** serial version ID for serialization **/
	private static final long serialVersionUID = 1118260195664301015L;

	// Applet parameters
	// -------------------------------------------------------------

	/** frame rate of applet set to 120 fps **/
	public static final long frameRate = 1000 / 120;
	/** scale of time as pertinent to frameRate **/
	public static final double timeScale = 1.0 / 12.0;
	/** width of applet **/
	private int width = 800;
	/** height of applet **/
	private int height = 600;
	/** color of background **/
	private Color backgroundColor = Color.BLACK;
	/** instance of self **/
	public static ElasticCollisionCircleSimulation main;
	/** Thread to run on **/
	private Thread thread = new Thread(this);
	/** Graphics to double buffer with **/
	private Graphics gg;
	/** Image to double buffer with **/
	private Image ii;

	// Simulation Objects
	// ----------------------------------------------------------------

	/** balls in simulation **/
	private ArrayList<Circle> circles = new ArrayList<Circle>();

	// Simulation Parameters
	// -------------------------------------------------------------------

	/** The energy that the system starts with **/
	double energyStart = 0;
	/** The energy that the system currently has **/
	double energyCurrent = 0;
	/** whether or not to correct for incidental energy loss **/
	boolean correctEnergyLoss = true;
	/** tolerance of energy correction **/
	double energyCorrectionTolerance = .05;

	// maximum ball generation values
	// ---------------------------------------------------------------------

	/** maximum random velocity of balls **/
	double maximumRandomComponentVelocity = 10.0;
	/** maximum random mass of balls **/
	double maximumRandomMass = 2.0;
	/** maximum random red color value for balls **/
	int maximumRandomRed = 255;
	/** maximum random green color value for balls **/
	int maximumRandomGreen = 255;
	/** maximum random blue color value for balls **/
	int maximumRandomBlue = 255;

	// minimum ball generation values
	// ----------------------------------------------------------------------

	/** minimum random velocity of balls **/
	double minimumRandomComponentVelocity = 0.0;
	/** minimum random mass of balls **/
	double minimumRandomMass = .5;
	/** minimum random red color value for balls **/
	int minimumRandomRed = 10;
	/** minimum random green color value for balls **/
	int minimumRandomGreen = 10;
	/** minimum random blue color value for balls **/
	int minimumRandomBlue = 10;

	// other ball generation values
	// --------------------------------------------------------------------

	/** chance that a generated ball is filled in or not **/
	double chanceFilled = 0;

	// Functional methods
	// ---------------------------------------------------------------------

	/**
	 * The distance method uses CustomMath's distance formula with two balls to
	 * compute the distance between their centers.
	 * @param b1 ball 1
	 * @param b2 ball 2
	 * @return Distance between balls' centers
	 */
	public double distance(Circle b1, Circle b2)
	{
		return CustomMath.distance(b1.getX(), b1.getY(), b2.getX(), b2.getY());
	}

	// Overridden methods
	// ----------------------------------------------------------------------

	@Override
	public void init()
	{

		// some hard-coded instantiations
		 circles.add(new Circle(50, height / 2, 25, Color.RED, 0, 0));
		 circles.add(new Circle(200, 300, 25, Color.GREEN, 5, -5));
		 circles.add(new Circle(400, 0, 35, new Color(255, 100, 0), 75, -75));
		 circles.add(new Circle(0, height / 4, 25, Color.CYAN, 50, -50));
		 circles.add(new Circle(70, height / 3, 25, Color.MAGENTA, 80, 50));
		 circles.add(new Circle(60, 25, 1.5, new Color(90, 0, 130), 80, 50));
		 circles.add(new Circle(60, 25, 1.5, new Color(255, 0, 170), -80, -50));
		 circles.add(new Circle(width / 2, height / 2, 1.5, new Color(0xD1111A),
		 0, 0));
		 circles.add(new Circle(width / 2, height / 2, 1.5, Color.BLUE, 0, 0));
		 circles.add(new Circle(500, 200, .75, new Color(0xFFFF00), 10, 10));
		 circles.add(new Circle(100, 300, 1.25, Color.ORANGE, 20, 10));
		 circles.add(new Circle(400, 50, 2.0, new Color(0x6666FF), -20, -10));
		 circles.add(new Circle(200, 100, 3.0, new Color(0x008000), new
		 Vector2(100, Math.acos(Math.sqrt(3) / 2.0))));

//		// random generation
//		for (int i = 0; i < 10; i++)
//		{
//			generateRandomBall();
//		}

		// simulation of earth atmosphere
//		circles = new ArrayList<Circle>();
//		double mass = .01;
//		double maxVelocityXY = 50;
//		int numBalls = 200;
//		for (int i = 0; i < numBalls; i++)
//		{
//			if (i < .78 * numBalls)
//			{
//				circles.add(new Circle(0, 0, mass, Color.BLUE, Math.random() * maxVelocityXY - maxVelocityXY,
//						Math.random() * maxVelocityXY - maxVelocityXY));
//			}
//			else if (i < (.78 + .21) * numBalls)
//			{
//				circles.add(new Circle(width / 2, height / 2, mass, Color.RED,
//						Math.random() * maxVelocityXY - maxVelocityXY, Math.random() * maxVelocityXY - maxVelocityXY));
//			}
//			else
//			{
//				circles.add(new Circle(width, height, mass, Color.GREEN, Math.random() * maxVelocityXY - maxVelocityXY,
//						Math.random() * maxVelocityXY - maxVelocityXY));
//			}
//		}

		// change the default gravity value
		World2D.gravityAcceleration = 0;//9.81;

		// setup correction for energy leakage due to floating point math issues
		for (int i = 0; i < circles.size(); i++)
		{
			energyStart += .5 * circles.get(i).getMass() * circles.get(i).getVelocity().getMagnitude()
					* circles.get(i).getVelocity().getMagnitude()
					+ circles.get(i).getMass() * (height - circles.get(i).getY()) * World2D.gravityAcceleration;
			if (circles.get(i).getRestitution() != 1)
			{
				correctEnergyLoss = false;
			}
		}

		// setup the window
		if (main == null)
		{
			main = this;
		}
		setSize(width, height);
		setBackground(backgroundColor);
	}

	@Override
	public void start()
	{
		thread.start();
	}

	@Override
	public void run()
	{
		// update objects
		while (true)
		{
			repaint();

			energyCurrent = 0;
			for (int i = 0; i < circles.size(); i++)
			{
				energyCurrent += .5 * circles.get(i).getMass() * circles.get(i).getVelocity().getMagnitude()
						* circles.get(i).getVelocity().getMagnitude()
						+ circles.get(i).getMass() * (height - circles.get(i).getY()) * World2D.gravityAcceleration;
				circles.get(i).update(timeScale);

				for (int j = i + 1; j < circles.size(); j++)
				{
					if (distance(circles.get(i), circles.get(j)) < circles.get(i).getRadius() + circles.get(j).getRadius())
					{
						circles.get(i).collision(circles.get(j));
					}
				}
			}

			// correct energy in system if applicable
			if (correctEnergyLoss && energyStart - energyCurrent > energyStart * energyCorrectionTolerance)
			{
				adjustUpEnergy();
			}
			else if (correctEnergyLoss && energyStart - energyCurrent < energyStart * (1.0 - energyCorrectionTolerance))
			{
				adjustDownEnergy();
			}

			// sleep for rest of frame time
			try
			{
				Thread.sleep(frameRate);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop()
	{

	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void update(Graphics g)
	{
		if (ii == null)
		{
			ii = createImage(this.getWidth(), this.getHeight());
			gg = ii.getGraphics();
		}

		gg.setColor(getBackground());
		gg.fillRect(0, 0, this.getWidth(), this.getHeight());

		gg.setColor(getForeground());
		paint(gg);

		g.drawImage(ii, 0, 0, this);
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		for (Circle b : circles)
		{
			b.paint(g);
		}
	}

	// Functional Methods
	// ----------------------------------------------------------------------------

	/**
	 * The generateRandomBall method generates a random ball according to fields
	 * that set its limits for generation.
	 */
	public void generateRandomBall()
	{
		//@formatter:off
		// x, y, mass, color, vx, vy, filled
		circles.add
				(
				new Circle
						(
						Math.random() * 2 * width - width,
						Math.random() * 2 * height - height,
						Math.random() * (maximumRandomMass - minimumRandomMass) + minimumRandomMass,
						new Color
								(
								(int) (Math.random() * (maximumRandomRed - minimumRandomRed) + minimumRandomRed),
								(int) (Math.random() * (maximumRandomGreen - minimumRandomGreen) + minimumRandomGreen),
								(int) (Math.random() * (maximumRandomBlue - minimumRandomBlue) + minimumRandomBlue)
								),
						Math.random() * (maximumRandomComponentVelocity - minimumRandomComponentVelocity) + minimumRandomComponentVelocity,
						Math.random() * (maximumRandomComponentVelocity - minimumRandomComponentVelocity) + minimumRandomComponentVelocity,
						Math.random() > chanceFilled
						)
				);
		//@formatter:on
	}

	/**
	 * The adjustUpEnergy method maintains conservation of energy in the face of
	 * energy loss resulting from rounding errors and faulty collision
	 * detection.
	 */
	public void adjustUpEnergy()
	{
		double multiplier = 1.001;
		while (energyStart > energyCurrent)
		{
			energyCurrent = 0;
			for (int i = 0; i < circles.size(); i++)
			{
				// update velocity
				circles.get(i).setVelocity(new Vector2(circles.get(i).getVelocity().getX() * multiplier,
						circles.get(i).getVelocity().getY() * multiplier));

				// calculate new energy
				energyCurrent += .5 * circles.get(i).getMass() * circles.get(i).getVelocity().getMagnitude()
						* circles.get(i).getVelocity().getMagnitude()
						+ circles.get(i).getMass() * (height - circles.get(i).getY()) * World2D.gravityAcceleration;
			}
		}
	}

	/**
	 * The adjustDownEnergy method maintains conservation of energy in the face
	 * of energy gain resulting from rounding errors and faulty collision
	 * detection.
	 */
	public void adjustDownEnergy()
	{
		double multiplier = .999;
		while (energyStart < energyCurrent)
		{
			energyCurrent = 0;
			for (int i = 0; i < circles.size(); i++)
			{
				// update velocity
				circles.get(i).setVelocity(new Vector2(circles.get(i).getVelocity().getX() * multiplier,
						circles.get(i).getVelocity().getY() * multiplier));

				// calculate new energy
				energyCurrent += .5 * circles.get(i).getMass() * circles.get(i).getVelocity().getMagnitude()
						* circles.get(i).getVelocity().getMagnitude()
						+ circles.get(i).getMass() * (height - circles.get(i).getY()) * World2D.gravityAcceleration;
			}
		}
	}

	/**
	 * The restart method restarts the applet.
	 */
	public void restart()
	{
		// not yet implemented
	}

	// Getters and Setters
	// ------------------------------------------------------------------------------

	/**
	 * The getThread method gets the thread that the applet is running on.
	 * (Shallow copy)
	 * @return running thread
	 */
	public Thread getThread()
	{
		return thread;
	}

	/**
	 * The setThread method sets the thread that the applet is running on.
	 * (thread is not cloned; direct set by reference)
	 * @param thread running thread
	 */
	public void setThread(Thread thread)
	{
		this.thread = thread;
	}

	/**
	 * The getGg method gets the graphics for double buffering. (shallow copy)
	 * @return double buffering graphics
	 */
	public Graphics getGg()
	{
		return gg;
	}

	/**
	 * The setGg method sets the graphics for double buffering. (object not
	 * cloned; direct set by reference)
	 * @param gg double buffering graphics
	 */
	public void setGg(Graphics gg)
	{
		this.gg = gg;
	}

	/**
	 * The getIi method gets the image used for double buffering. (shallow copy)
	 * @return double buffering image
	 */
	public Image getIi()
	{
		return ii;
	}

	/**
	 * The setIi method sets the image used for double buffering. (object not
	 * cloned; direct set by reference)
	 * @param ii double buffering image
	 */
	public void setIi(Image ii)
	{
		this.ii = ii;
	}

	/**
	 * The getWidth method gets the width of the applet.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * The setWidth method sets the width of the applet.
	 * @param width width of applet
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * The getHeight method gets the height of the applet.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * The setHeight method sets the height of the applet
	 * @param height height of applet
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
}
