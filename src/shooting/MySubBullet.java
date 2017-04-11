package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MySubBullet extends GameObject
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

	private float speed;
	private float angle;
	private int count;

	MySubBullet()
	{
		width = img.getWidth();
		height = img.getHeight();
		speed = 10;
	}

	public void update()
	{
		if (ObjectPool.hasActiveEnemy())
		{
			double idealAngle = getAngle();

			if ((idealAngle - angle + 360) % 360 < 180)
			{
				angle += count + 5;
				if (angle > 180)
					angle -= 360;

				if ((idealAngle - angle + 360) % 360 > 180)
					angle = (float) idealAngle;
			}
			else
			{
				angle -= count + 5;
				if (angle < -180)
					angle += 360;

				if ((idealAngle - angle + 360) % 360 < 180)
					angle = (float) idealAngle;
			}
		}

		double radian = Math.toRadians(angle);
		x += speed * Math.cos(radian);
		y += speed * Math.sin(radian);

		checkLeaving(50);

		count++;
	}

	public void render(Graphics g)
	{
		img.draw(x - width / 2, y - height / 2);
	}

	private double getAngle()
	{
		float ex = ObjectPool.getNearestEnemyX();
		float ey = ObjectPool.getNearestEnemyY();
		return Math.toDegrees(Math.atan2(ey - y, ex - x));
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