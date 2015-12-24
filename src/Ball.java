import java.awt.Color;
import java.awt.Graphics;
import CustomAlgorithms.CustomMath;

public class Ball extends Object2D
{

	// velocity
	private Vector velocity = new Vector(0, 0);

	// physical characteristics
	private Color color = Color.BLACK;
	private int radius = 0;
	private double mass = 1;
	private double massToArea = 1 / (25 * 25 * Math.PI);
	private double coefficientRestitution = 1;

	// Constructors
	// -----------------------------------------------------------------------

	public Ball(int radius, Color color)
	{
		super(true, true);
		setRadius(radius);
		setColor(color);
	}

	public Ball(int radius, Color color, double vx, double vy)
	{
		super(true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(new Vector(vx, vy));
	}

	public Ball(double x, double y, int radius, Color color, Vector velocity)
	{
		super(x, y, true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(new Vector(velocity));
	}

	public Ball(double x, double y, int radius, Color color, double vx, double vy)
	{
		super(x, y, true, true);
		setRadius(radius);
		setColor(color);
		setVelocity(new Vector(vx, vy));
	}

	public Ball(double x, double y, double mass, Color color, double vx, double vy)
	{
		super(x, y, true, true);
		setMass(mass);
		setColor(color);
		setVelocity(new Vector(vx, vy));
	}

	public Ball(double x,double y, double mass, Color color, Vector velocity)
	{
		super(x, y, true, true);
		setMass(mass);
		setColor(color);
		setVelocity(velocity);
	}

	public Ball(double x, double y, Vector velocity)
	{
		super(x, y, true, true);
		setVelocity(velocity);
	}

	// Overridden Methods
	// ---------------------------------------------------------------------

	@Override
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int) getX()- radius, (int) getY() - radius, radius * 2, radius * 2);
	}
	
	@Override
	public void update(double time)
	{
		// manage collisions with walls
		if (getX() + radius > Main.main.getWidth())
		{
			setX(Main.main.getWidth() - radius - 1);
			velocity.setX(-velocity.getX() * coefficientRestitution);
		}
		else if (getX() - radius < 0)
		{
			setX(radius + 1);
			velocity.setX(-velocity.getX() * coefficientRestitution);
		}

		if (getY() + radius > Main.main.getHeight())
		{
			setY(Main.main.getHeight() - radius - 1);
			velocity.setY(-velocity.getY() * coefficientRestitution);
		}
		else if (getY() - radius < 0)
		{
			setY(radius + 1);
			velocity.setY(-velocity.getY() * coefficientRestitution);
		}

		// update velocity as pertinent to gravity and time
		velocity.setY(velocity.getY() + gravityAcceleration * time);

		// update position
		setY(getY() + velocity.getY() * time);
		setX(getX() + velocity.getX() * time);
	}

	// Functional Methods
	// ---------------------------------------------------------------------

	public void calcRadius()
	{
		radius = (int) (Math.sqrt(mass / (massToArea * Math.PI)));
	}

	public void calcMass()
	{
		mass = Math.PI * radius * radius * massToArea;
	}

	public void collision(Ball b)
	{
		
		// store previous velocities; if 0 set them so that the balls will be
		// moved to adjacent at an angle 135 degrees in reference to the
		// horizontal
		double velocityPreviousX1 = velocity.getX() != 0 ? velocity.getX() : 1;
		double velocityPreviousY1 = velocity.getY() != 0 ? velocity.getY() : 1;
		double velocityPreviousX2 = b.getVelocity().getX() != 0 ? b.getVelocity().getX() : -1;
		double velocityPreviousY2 = b.getVelocity().getY() != 0 ? b.getVelocity().getY() : -1;

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
		// collision wikipedia like WTF?!?!?
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

		// set new velocities
		velocity = new Vector(v1x, v1y);
		b.setVelocity(new Vector(v2x, v2y));

		// move balls to new positions at reverse of old velocities
		double step = .1;
		while (CustomMath.distance(getX(), getY(), b.getX(), b.getY()) <= radius + b.getRadius())
		{
			setX(getX() - velocityPreviousX1 * step);
			setY(getY() - velocityPreviousY1 * step);
			b.setX(b.getX() - velocityPreviousX2 * step);
			b.setY(b.getY() - velocityPreviousY2 * step);
		}
		
	}

	// Getters and Setters
	// ----------------------------------------------------------------------

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
		calcMass();
	}

	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
		calcRadius();
	}
	
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Vector getVelocity()
	{
		return new Vector(velocity);
	}

	public void setVelocity(Vector velocity)
	{
		this.velocity = new Vector(velocity);
	}

	public double getCoefficientRestitution()
	{
		return coefficientRestitution;
	}

	public void setCoefficientRestitution(double e)
	{
		this.coefficientRestitution = e;
	}

}
