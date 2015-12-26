import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;

import CustomAlgorithms.CustomMath;

/**
 * The Ball class provides an instantiable 2D object that possesses a mass,
 * radius, color, restitution value, and velocity vector.
 * @author Robert James Meade
 * @version %I% %G%
 * @see Object2D Vector
 */
public class Ball extends Object2D
{

	/** serial version id **/
	private static final long serialVersionUID = -570084700155576287L;

	/** ratio of mass to area for the ball **/
	private static double massToArea = 1 / (25 * 25 * Math.PI);

	/**
	 * The balls's velocity as a vector referenced to the horizontal with x and
	 * y components
	 **/
	private Vector2 velocity = new Vector2(0, 0);
	/** Color of the ball **/
	private Color color = Color.BLACK;
	/** radius of the ball (proportional to mass) **/
	private double radius = 0;
	/** mass of the ball (proportional to radius) **/
	private double mass = 1;
	/**
	 * coefficient of restitution for ball's collisions with objects of very
	 * high mass
	 **/
	private double restitution = 1;

	// Constructors
	// -----------------------------------------------------------------------

	/**
	 * Ball Copy Constructor
	 * @param b Ball to copy
	 */
	public Ball(Ball b)
	{
		super(b.isGravity(), b.isMovable());
		setRadius(b.getRadius());
		// mass is preserved when setting radius
		setColor(b.getColor());
		setVelocity(b.getVelocity());
		setRestitution(b.getRestitution());
	}

	/**
	 * 2-Argument Ball Constructor
	 * @param radius radius of ball
	 * @param color color of ball
	 */
	public Ball(int radius, Color color)
	{
		super(true, true);
		setRadius(radius);
		setColor(color);
	}

	/**
	 * 3-Argument Ball Constructor
	 * @param x x position of ball
	 * @param y y position of ball
	 * @param velocity velocity of ball
	 */
	public Ball(double x, double y, Vector2 velocity)
	{
		super(x, y, true, true);
		setVelocity(velocity);
	}

	/**
	 * 4-Argument Ball Constructor
	 * @param radius radius of ball
	 * @param color color of ball
	 * @param vx velocity x component
	 * @param vy velocity y component
	 */
	public Ball(int radius, Color color, double vx, double vy)
	{
		super(true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(new Vector2(vx, vy));
	}

	/**
	 * 5-Argument Ball Constructor
	 * @param x x position of ball
	 * @param y y position of ball
	 * @param radius radius of ball
	 * @param color color of ball
	 * @param velocity velocity of ball (as Vector)
	 */
	public Ball(double x, double y, int radius, Color color, Vector2 velocity)
	{
		super(x, y, true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(velocity);
	}

	/**
	 * 5-Argument Ball Constructor
	 * @param x x position of ball
	 * @param y y position of ball
	 * @param mass mass of ball
	 * @param color color of ball
	 * @param velocity velocity of ball (as Vector)
	 */
	public Ball(double x, double y, double mass, Color color, Vector2 velocity)
	{
		super(x, y, true, true);
		setMass(mass);
		setColor(color);
		setVelocity(velocity);
	}

	/**
	 * 6-Argument Ball Constructor
	 * @param x x position of ball
	 * @param y y position of ball
	 * @param radius radius of ball
	 * @param color color of ball
	 * @param vx velocity x component
	 * @param vy velocity y component
	 */
	public Ball(double x, double y, int radius, Color color, double vx, double vy)
	{
		super(x, y, true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(new Vector2(vx, vy));
	}

	/**
	 * 6-Argument Ball Constructor
	 * @param x x position of ball
	 * @param y y position of ball
	 * @param mass mass of ball
	 * @param color color of ball
	 * @param vx velocity x component
	 * @param vy velocity y component
	 */
	public Ball(double x, double y, double mass, Color color, double vx, double vy)
	{
		super(x, y, true, true);
		setMass(mass);
		setColor(color);
		setVelocity(new Vector2(vx, vy));
	}

	// Overridden Methods
	// ---------------------------------------------------------------------

	@Override
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int) (getX() - radius), (int) (getY() - radius), (int) radius * 2, (int) radius * 2);
	}

	@Override
	public void update(double time)
	{
		// y = y0 + v0t + .5at^2
		// x = x0 + v0t
		// update position
		double previousY = getY();
		double velocityPreviousY = velocity.getY();
		setY(getY() + velocity.getY() * time + .5 * World2D.gravityAcceleration * time * time);
		setX(getX() + velocity.getX() * time);

		// check collision with left or right
		if (getX() + radius > Main.main.getWidth())
		{
			setX(Main.main.getWidth() - radius - 1);
			velocity.setX(-velocity.getX() * restitution);
		}
		else if (getX() - radius < 0)
		{
			setX(radius + 1);
			velocity.setX(-velocity.getX() * restitution);
		}

		// check collision with top or bottom
		if (getY() + radius > Main.main.getHeight())
		{
			setY(Main.main.getHeight() - radius - 1);
			// correct velocity with work energy theorem
			// v =( v0 ^ 2 + 2 a deltaX) ^ (1/2)
			velocity.setY(-Math.sqrt((velocity.getY() * velocity.getY()
					+ 2 * World2D.gravityAcceleration * (Main.main.getHeight() - previousY - radius - 1)))
					* restitution);
			
			// correct non-real result
			if(Double.isNaN(velocity.getY()))
			{
				velocity.setY(-velocityPreviousY);
			}
		}
		else if (getY() - radius < 0)
		{
			setY(radius + 1);
			// correct velocity with work energy theorem
			// v =( v0 ^ 2 + 2 a deltaX) ^ (1/2)
			velocity.setY(Math.sqrt((velocity.getY() * velocity.getY()
					+ 2 * World2D.gravityAcceleration * (radius + 1 - previousY) * restitution)));
		}
		else
		{
			// update velocity as pertinent to gravity and time
			// v = v0 + at
			velocity.setY(velocity.getY() + World2D.gravityAcceleration * time);
		}
	}

	// Functional Methods
	// ---------------------------------------------------------------------

	/**
	 * The calcRadius method updates the ball's radius as pertinent to its mass
	 * and massToArea ratio.
	 */
	public void calcRadius()
	{
		radius = (int) (Math.sqrt(mass / (massToArea * Math.PI)));
	}

	/**
	 * The calcMass method updates the ball's mass as pertinent to its radius
	 * and massToArea ratio.
	 */
	public void calcMass()
	{
		mass = Math.PI * radius * radius * massToArea;
	}

	/**
	 * The collision method manages the change in momentum and energy for the
	 * collision of two balls. Both ball's velocities are updated as a result
	 * (You do not need to call the method of each of the colliding balls, only
	 * one). The collision is considered to be perfectly elastic.
	 * @param b Ball this is colliding with
	 */
	public void collision(Ball b)
	{
		// contact angle
		double phi = Math.atan2(b.getY() - getY(), b.getX() - getX());

		// vector 1 scalar speed, mass, and velocity respectively
		double v1 = velocity.getMagnitude();
		double m1 = mass;
		double theta1 = velocity.getAngle();

		// vector 2 scalar speed, mass, and velocity respectively
		double v2 = b.getVelocity().getMagnitude();
		double m2 = b.getMass();
		double theta2 = b.getVelocity().getAngle();

		// See "Two-dimensional collision with two moving objects" on elastic
		// collision wikipedia
		// https://en.wikipedia.org/wiki/Elastic_collision

		// calculate the x and y components of resultant velocities
		double v1x = (((v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi)) / (m1 + m2))
				* Math.cos(phi)) + v1 * Math.sin(theta1 - phi) * Math.cos(phi + Math.PI / 2.0);
		double v1y = (((v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi)) / (m1 + m2))
				* Math.sin(phi)) + v1 * Math.sin(theta1 - phi) * Math.sin(phi + Math.PI / 2.0);

		double v2x = (((v2 * Math.cos(theta2 - phi) * (m2 - m1) + 2 * m1 * v1 * Math.cos(theta1 - phi)) / (m2 + m1))
				* Math.cos(phi)) + v2 * Math.sin(theta2 - phi) * Math.cos(phi + Math.PI / 2.0);
		double v2y = (((v2 * Math.cos(theta2 - phi) * (m2 - m1) + 2 * m1 * v1 * Math.cos(theta1 - phi)) / (m2 + m1))
				* Math.sin(phi)) + v2 * Math.sin(theta2 - phi) * Math.sin(phi + Math.PI / 2.0);

		if (Double.isNaN(v1x) || Double.isNaN(v1y) || Double.isNaN(v2x) || Double.isNaN(v2y))
		{
			System.out.println("collision gave undefined result");
		}

		// set new velocities
		velocity = new Vector2(v1x, v1y);
		b.setVelocity(new Vector2(v2x, v2y));

		// move balls to new positions at reverse of old velocities
		boolean zeroVelocity = false;
		while (CustomMath.distance(getX(), getY(), b.getX(), b.getY()) <= radius + b.getRadius())
		{
			// give balls velocity in opposing directions to move them apart
			if (Math.abs(velocity.getMagnitude() - b.getVelocity().getMagnitude()) < 1
					&& Math.abs(velocity.getAngle() - b.getVelocity().getAngle()) < 1)
			{

				velocity = new Vector2(1, 3 * Math.PI / 4, true);
				b.setVelocity(new Vector2(1, -Math.PI / 4, true));
				zeroVelocity = true;
			}
			update(Main.timeScale / 100);
			b.update(Main.timeScale / 100);
		}

		// reset velocity if it was zero
		if (zeroVelocity)
		{
			b.setVelocity(new Vector2(0, 0));
			setVelocity(new Vector2(0, 0));
		}

	}

	private static int count = 0;
	private File file = new File(".." + File.separator + "res" + File.separator + "ball" + (count++) + ".log");
	private boolean createNew = true;

	public void log(int interval)
	{
		try
		{
			if (file.exists())
			{
				file.createNewFile();
			}
			if (createNew)
			{
				file.delete();
				file.createNewFile();
				createNew = false;
			}
			FileWriter writer = (new FileWriter(file, true));
			writer.write(interval + "\n" + velocity + "\n" + "{" + getX() + " , " + getY() + "}\n\n");
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			System.out.println(".." + File.separator + "res" + File.separator + "ball" + (count) + ".log");
		}
	}

	// Getters and Setters
	// ----------------------------------------------------------------------

	/**
	 * The getRadius method gets the radius of the ball.
	 * @return radius of ball
	 */
	public double getRadius()
	{
		return radius;
	}

	/**
	 * The setRadius method sets the radius of the ball and updates the ball's
	 * mass accordingly.
	 * @param radius radius of ball
	 */
	public void setRadius(double radius)
	{
		this.radius = radius;
		calcMass();
	}

	/**
	 * The getMass method gets the mass of the ball.
	 * @return mass of ball
	 */
	public double getMass()
	{
		return mass;
	}

	/**
	 * The setMass method sets the mass of the ball and updates its radius
	 * accordingly.
	 * @param mass mass of ball
	 */
	public void setMass(double mass)
	{
		this.mass = mass;
		calcRadius();
	}

	/**
	 * The getColor method gets the color of the ball.
	 * @return color of ball
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * The setColor method sets the color of the ball.
	 * @param color color of ball
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	/**
	 * The getVelocity method gets the velocity of the ball as a deep copy of
	 * the ball's velocity vector.
	 * @return velocity of ball
	 */
	public Vector2 getVelocity()
	{
		return new Vector2(velocity);
	}

	/**
	 * The setVelocity method sets the ball's velocity as a deep copy of the
	 * passed in vector.
	 * @param velocity velocity of ball
	 */
	public void setVelocity(Vector2 velocity)
	{
		this.velocity = new Vector2(velocity);
	}

	/**
	 * The getRestitution method gets the value of restitution for the ball.
	 * @return restitution of ball
	 */
	public double getRestitution()
	{
		return restitution;
	}

	/**
	 * The setRestitution method sets the value of restitution for the ball.
	 * @param e restitution of ball
	 */
	public void setRestitution(double e)
	{
		this.restitution = e;
	}

}
