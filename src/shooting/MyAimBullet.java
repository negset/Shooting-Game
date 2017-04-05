package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyAimBullet extends GameObject
{
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/img/bullet7.png");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	float speed;
	float angle;
	int count;

	MyAimBullet()
	{
		width = img.getWidth();
		height = img.getHeight();
		speed = 10;
	}

	public void update()
	{
		if (ObjectPool.hasNearestEnemy())
		{
			float ex = ObjectPool.getNearestEnemyX();
			float ey = ObjectPool.getNearestEnemyY();
			angle = (float) Math.toDegrees(Math.atan2(ey - y, ex - x));
		}

		double radian = Math.toRadians(angle);
		x += speed * Math.cos(radian);
		y += speed * Math.sin(radian);

		active = y > Play.AREA_TOP - height;

		count++;
	}

	public void render(Graphics g)
	{
		img.draw(x - width / 2, y - height / 2);
	}

	public void activate(float x, float y, float angle)
	{
		active = true;
		this.x = x;
		this.y = y;
		this.angle = angle;

		count = 0;
	}
}