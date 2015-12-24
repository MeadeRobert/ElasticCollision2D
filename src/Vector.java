
public class Vector
{
	private double magnitude = 0.0;
	private double angle = 0.0;
	private double x;
	private double y;

	public Vector(Vector v)
	{
		setMagnitude(v.getMagnitude());
		setAngle(v.getAngle());
		setX(v.getX());
		setY(v.getY());
	}
	
	public Vector(double x, double y)
	{
		this.angle = Math.atan2(y, x);
		this.magnitude = Math.sqrt(x * x + y * y);
		this.x = x;
		this.y = y;
	}

	public Vector(double magnitude, double angle, boolean isVector)
	{
		this.magnitude = magnitude;
		this.angle = angle;
		this.y = Math.sin(angle) * magnitude;
		this.x = Math.cos(angle) * magnitude;
	}

	// Getters and Setters
	// ------------------------------------------------------------------

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
		magnitude = Math.sqrt(x * x + y * y);
		angle = Math.atan2(y , x);
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
		magnitude = Math.sqrt(y * y + x * x);
		angle = Math.atan2(y , x);
	}
	
	public double getMagnitude()
	{
		return magnitude;
	}

	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}

	public double getAngle()
	{
		return angle;
	}

	public void setAngle(double angle)
	{
		this.angle = angle;
	}
}
