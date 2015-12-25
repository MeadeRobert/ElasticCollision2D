import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import CustomAlgorithms.CustomMath;

public class Main extends Applet implements Runnable
{
	private static final long serialVersionUID = 1118260195664301015L;

	public static final double timeScale = 1.0/12.0;

	public static Main main;
	private Thread thread = new Thread(this);
	private Graphics gg;
	private Image ii;

	private int width = 1000;
	private int height = 800;

	ArrayList<Ball> balls = new ArrayList<Ball>();

	// Functional methods
	// ---------------------------------------------------------------------
	public double distance(Ball b1, Ball b2)
	{
		return CustomMath.distance(b1.getX(), b1.getY(), b2.getX(), b2.getY());
	}

	// Overridden methods
	// ----------------------------------------------------------------------

	@Override
	public void init()
	{
		 balls.add(new Ball(50, height / 2, 25, Color.RED, 5, 20));
		 balls.add(new Ball(200, 300, 25, Color.GREEN, 5, -5));
		 balls.add(new Ball(400, 0, 35, new Color(255, 100, 0), 75, -75));
		 balls.add(new Ball(0, height / 4, 25, Color.CYAN, 50, -50));
		 balls.add(new Ball(70, height / 3, 25, Color.MAGENTA, 80, 50));
		 balls.add(new Ball(60, 25, 1.5, new Color(90, 0, 130), 80, 50));
		 balls.add(new Ball(60, 25, 1.5, new Color(255, 0, 170), -80, -50));
		 balls.add(new Ball(width / 2, height / 2, 1.5, new Color(0xD1111A),
		 0, 0));
		 balls.add(new Ball(width / 2, height / 2, 1.5, Color.BLUE, 0, 0));
		 balls.add(new Ball(500, 200, .75, new Color(0xFFFF00), 10, 10));
		 balls.add(new Ball(100, 300, 1.25, Color.ORANGE, 20, 10));
		 balls.add(new Ball(400, 50, 2.0, new Color(0x6666FF), -20, -10));
		 balls.add(new Ball(200, 100, 3.0, new Color(0x008000), new
		 Vector(100, Math.acos(Math.sqrt(3) / 2.0))));

//		balls = new ArrayList<Ball>();
//		double mass = 1;
//		double maxVelocityXY = 10;
//		for (int i = 0; i < 20; i++)
//		{
//			balls.add(new Ball(width / 2, height / 2, mass, Color.BLUE, Math.random() * maxVelocityXY - maxVelocityXY,
//					Math.random() * maxVelocityXY - maxVelocityXY));
//			if (i % 5 == 0)
//			{
//				balls.get(i).setColor(Color.RED);
//			}
//		}

		World.gravityAcceleration = 0;

		if (main == null)
		{
			main = this;
		}
		setSize(width, height);
		setBackground(new Color(0x333333));
		setBackground(Color.BLACK);
	}

	@Override
	public void start()
	{
		thread.start();
	}

	@Override
	public void run()
	{
		double energyPrevious = 0;
		double energy = 0;
		int propagation = 0;
		boolean calcEnergy = true;
		while (true)
		{
			repaint();

		
			
			energy = 0;
			for (int i = 0; i < balls.size(); i++)
			{
				energy += .5 * balls.get(i).getMass() * balls.get(i).getVelocity().getMagnitude()
						* balls.get(i).getVelocity().getMagnitude()
						+ balls.get(i).getMass() * (height - balls.get(i).getY()) * World.gravityAcceleration;
				balls.get(i).update(timeScale);
				for (int j = i + 1; j < balls.size(); j++)
				{
					if (distance(balls.get(i), balls.get(j)) <= balls.get(i).getRadius() + balls.get(j).getRadius())
					{
						balls.get(i).collision(balls.get(j));
					}
				}
			}
			
			// keep track of energy and if it changes by greater than 20% from the original alert the user
			if(calcEnergy)
			{
				energyPrevious = energy;
				calcEnergy = false;
			}
			
			if ((double) Math.abs(energy - energyPrevious) > energy * .2)
			{
				if (propagation > 10)
				{
					System.out.println("violation: LAW of conservation of energy");
					System.out.println(energy);
					System.out.println(energyPrevious);
				}
				else
				{
				 propagation++;
				}
			}
			else
			{
				propagation--;
			}

			try
			{
				Thread.sleep(1000 / 120);
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
		for (Ball b : balls)
		{
			b.paint(g);
		}
	}

	public void restart()
	{

	}

	// Getters and Setters
	// ------------------------------------------------------------------------------

	public Thread getThread()
	{
		return thread;
	}

	public void setThread(Thread thread)
	{
		this.thread = thread;
	}

	public Graphics getGg()
	{
		return gg;
	}

	public void setGg(Graphics gg)
	{
		this.gg = gg;
	}

	public Image getIi()
	{
		return ii;
	}

	public void setIi(Image ii)
	{
		this.ii = ii;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
