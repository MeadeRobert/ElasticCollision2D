import java.awt.Graphics;

public abstract class Object2D extends World
{
	// position
	private double x = 0;
	private double y = 0;
	
	private boolean gravity = false;
	private boolean moveable = false;

	// Constructors
	// --------------------------------------------------------------
	
	public Object2D()
	{
	}
	
	public Object2D(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public Object2D(boolean gravity, boolean moveable)
	{
		setGravity(gravity);
		setMoveable(moveable);
	}
	
	public Object2D(double x, double y, boolean gravity, boolean moveable)
	{
		setGravity(gravity);
		setMoveable(moveable);
		setX(x);
		setY(y);
	}
	
	// abstract methods
	// -----------------------------------------------------------
	
	public abstract void update(double time);
	public abstract void paint(Graphics g);
	
	// Getters and Setters
	// -----------------------------------------------------------

	public boolean isGravity()
	{
		return gravity;
	}

	public void setGravity(boolean gravity)
	{
		this.gravity = gravity;
	}

	public boolean isMoveable()
	{
		return moveable;
	}

	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
}
