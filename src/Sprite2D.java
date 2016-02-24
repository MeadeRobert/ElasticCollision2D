import java.awt.Graphics;
import java.io.Serializable;

/**
 * The abstract Object2D class provides parameters for objects that will exist in a 2D world.
 * @author Robert James MEade
 * @version 0.1
 * @see World2D
 */
public abstract class Sprite2D implements Serializable
{
	/** serial version id **/
	private static final long serialVersionUID = 7307504985193527867L;
	/** x position of object **/
	private double x = 0.0;
	/** y position of object **/
	private double y = 0.0;
	
	/** whether or not gravity affects the object **/
	private boolean gravity = false;
	/** whether or not the object may move **/
	private boolean movable = false;

	// Constructors
	// --------------------------------------------------------------
	
	/**
	 * No-Argument Object2D Constructor
	 */
	public Sprite2D()
	{
	}
	
	/**
	 * Copy Object2D Constructor
	 * @param o Object2D to copy
	 */
	public Sprite2D(Sprite2D o)
	{
		setX(o.getX());
		setY(o.getY());
		setGravity(o.isGravity());
		setMovable(o.isMovable());
	}
	
	/**
	 * 2-Argument Object2D Constructor
	 * @param x x position of object
	 * @param y y position of object
	 */
	public Sprite2D(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	/**
	 * 2-Argument Object2D Constructor
	 * @param gravity is affected by gravity?
	 * @param movable is movable?
	 */
	public Sprite2D(boolean gravity, boolean movable)
	{
		setGravity(gravity);
		setMovable(movable);
	}
	
	/**
	 * 4-Argument Object2D Constructor
	 * @param x x position of object
	 * @param y y position of object
	 * @param gravity is affected by gravity?
	 * @param movable is movable?
	 */
	public Sprite2D(double x, double y, boolean gravity, boolean movable)
	{
		setGravity(gravity);
		setMovable(movable);
		setX(x);
		setY(y);
	}
	
	// abstract methods
	// -----------------------------------------------------------
	
	/**
	 * The update method is used to update the object in accordance with time.
	 * @param time time passed between update
	 */
	public abstract void update(double time);
	
	/**
	 * The paint method is used by an applet to draw the object.
	 * @param g Graphics to paint on
	 */
	public abstract void paint(Graphics g);
	
	// Getters and Setters
	// -----------------------------------------------------------

	/**
	 * The isGravity method returns whether or not the object is affected by gravity.
	 * @return is affected by gravity?
	 */
	public boolean isGravity()
	{
		return gravity;
	}

	/**
	 * The setGRavity method sets whether or not the object is affected  by gravity.
	 * @param gravity is affected by gravity?
	 */
	public void setGravity(boolean gravity)
	{
		this.gravity = gravity;
	}

	/**
	 * The isMovable method returns whether or not the object is movable.
	 * @return is movable?
	 */
	public boolean isMovable()
	{
		return movable;
	}

	/**
	 * The setMovable method sets whether or not the object is movable.
	 * @param movable is movable?
	 */
	public void setMovable(boolean movable)
	{
		this.movable = movable;
	}

	/**
	 * The getX method gets the x position of the object.
	 * @return x position
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * The setX method sets the x position of the object.
	 * @param x x position
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * The getY method gets the y position of the object.
	 * @return y position
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * The setY method sets the y position of the object.
	 * @param y y position
	 */
	public void setY(double y)
	{
		this.y = y;
	}
	
}
