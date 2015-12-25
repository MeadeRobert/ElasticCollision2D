package CustomAlgorithms;

/**
 * A custom math class for miscellaneous purposes.
 * @author Robert James Meade
 * @version 0.1
 */
public class CustomMath
{

	/**
	 * A for-loop implementation of the isPrime method.
	 * @param n number
	 * @return isPrime
	 */
	public static boolean isPrime(int n)
	{
		if (n < 2)
		{
			return false;
		}
		else if (n == 2)
		{
			return true;
		}
		else
		{
			for (int i = 2; i <= Math.sqrt(n); i++)
			{
				if (n % i == 0)
				{
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * A recursive implementation of the isPrime method.
	 * @param n number
	 * @param i Counter; must start at a value of 2 to function properly
	 * @return isPrime
	 */
	public static boolean isPrime(int n, int i)
	{
		if (n < 2)
		{
			return false;
		}
		else if (n == 2)
		{
			return true;
		}
		else if (i <= Math.sqrt(n))
		{
			if (n % i == 0)
			{
				return false;
			}
			return isPrime(n, i + 1);
		}
		return true;
	}

	/**
	 * The distance method returns the scalar distance between 2 points.
	 * @param x1 x coordinate 1
	 * @param y1 y coordinate 1
	 * @param x2 x coordinate 2
	 * @param y2 y coordinate 2
	 * @return Distance between two points
	 */
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
	}

}
