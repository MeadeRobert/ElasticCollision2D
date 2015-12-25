/**
 * The Vector2 class provides an object to represent 2 dimensional vector
 * quantity and changes to aspects of the object.
 * @author Robert James Meade
 * @version %I% %G%
 */
public class Vector2
{
	/** Magnitude of the vector **/
	private double magnitude = 0.0;
	/** Angle of the vector (direction) **/
	private double angle = 0.0;
	/** x component of the vector **/
	private double x;
	/** y component of the vector **/
	private double y;

	/**
	 * Vector2 copy constructor
	 * @param v Vector2 to copy
	 */
	public Vector2(Vector2 v)
	{
		setMagnitude(v.getMagnitude());
		setAngle(v.getAngle());
		setX(v.getX());
		setY(v.getY());
	}

	/**
	 * 2-Argument Vector2 Constructor
	 * @param x x component of vector
	 * @param y y component of vector
	 */
	public Vector2(double x, double y)
	{
		setAngle(Math.atan2(y, x));
		setMagnitude(Math.sqrt(x * x + y * y));
		setX(x);
		setY(y);
	}

	/**
	 * 3-Argument Vector2 Constructor
	 * @param magnitude magnitude of vector
	 * @param angle direction of vector as an angle from -pi to +pi
	 * @param isVector are the 2 doubles passed in a vector and not components?
	 */
	public Vector2(double magnitude, double angle, boolean isVector)
	{
		setMagnitude(magnitude);
		setAngle(angle);
		setY(Math.sin(angle) * magnitude);
		setX(Math.cos(angle) * magnitude);
	}

	// Overridden methods
	// ------------------------------------------------------------------
	
	@Override
	public String toString()
	{
		return magnitude + " | " + angle + " radians";
	}
	
	// Getters and Setters
	// ------------------------------------------------------------------

	/**
	 * The getX method gets the x component of the vector.
	 * @return x component of the vector
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * The setX method sets the x component of the vector.
	 * @param x x component of the vector
	 */
	public void setX(double x)
	{
		this.x = x;
		magnitude = Math.sqrt(x * x + y * y);
		angle = Math.atan2(y, x);
	}

	/**
	 * The getY method gets the y component of the vector.
	 * @return y component of the vector
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * The setY method sets the y component of the vector
	 * @param y y component of the vector
	 */
	public void setY(double y)
	{
		this.y = y;
		magnitude = Math.sqrt(y * y + x * x);
		angle = Math.atan2(y, x);
	}

	/**
	 * The getMagnitude method gets the magnitude of the vector
	 * @return magnitude of the vector
	 */
	public double getMagnitude()
	{
		return magnitude;
	}

	/**
	 * The setMagnitude method sets the magnitude of the vector
	 * @param magnitude magnitude of the vector
	 */
	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}

	/**
	 * The getAngle method gets the angle, or direction, of the vector.
	 * @return Direction, or angle, of the vector in radians
	 */
	public double getAngle()
	{
		return angle;
	}

	/**
	 * The setAngle method sets the angle of the vector in radians
	 * @param angle direction, or angle, of the vector in radians
	 */
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
}
