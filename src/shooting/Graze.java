package shooting;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Graze extends GameObject
{
	static Image img;
	static
	{
		try
		{
			img = new Image("res/graze.png");
		}
		catch (SlickException e) {}
	}

	int counter;
	float[] x, y;
	int[] angle;
	float[] speedX, speedY;

	Graze()
	{
		active = false;
		x = new float[3];
		y = new float[3];
		angle = new int[3];
		speedX = new float[3];
		speedY = new float[3];
	}

	public void update()
	{
		for (int i = 0; i < 3; i++)
		{
			x[i] += speedX[i];
			y[i] += speedY[i];
		}

		counter++;
		if (counter > 20)
		{
			active = false;
		}
	}

	public void render()
	{
		for (int i = 0; i < 3; i++)
		{
			img.setRotation(angle[i]);
			img.draw(x[i] - img.getWidth() / 2, y[i] - img.getHeight() / 2);
		}
	}

	public void activate(float x, float y)
	{
		active = true;
		counter = 0;
		for (int i = 0; i < 3; i++)
		{
			this.x[i] = x;
			this.y[i] = y;
			angle[i] = i * 120  + Random.nextInt(120);
			float speed = 2 + Random.nextInt(2);
			speedX[i] = speed * (float) Math.cos(Math.toRadians(angle[i]));
			speedY[i] = speed * (float) Math.sin(Math.toRadians(angle[i]));
		}
	}
}
