package shooting;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Explosion extends GameObject
{
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[11];
			SpriteSheet ss = new SpriteSheet("res/explosion.png", 80, 80);
			for (int i = 0; i < 11; i++)
			{
				img[i] = ss.getSubImage(i%5, i/5);
			}
		}
		catch (SlickException e) {}
	}

	int counter;

	Explosion()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
	}

	public void update()
	{
		if (Play.counter % 3 == 0)
		{
			counter++;
		}
		if (counter >= 10)
		{
			active = false;
		}
	}

	public void render()
	{
		img[counter%11].drawCentered(x, y);
	}

	public void activate(float x, float y)
	{
		this.x = x;
		this.y = y;
		active = true;
		counter = 0;
	}
}