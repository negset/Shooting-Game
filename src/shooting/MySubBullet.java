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
	private double speedX, speedY;
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
			float tx = ObjectPool.getNearestEnemyX() - x;
			float ty = ObjectPool.getNearestEnemyY() - y;
			double radian = 0;
			// 進行方向より左
			if (speedX * ty - speedY * tx < 0)
			{
				radian = -Math.toRadians(500 / getDistance());
			}
			// 進行方向より右
			else if (speedX * ty - speedY * tx > 0)
			{
				radian = Math.toRadians(500 / getDistance());
			}
			speedX = (float) (speedX * Math.cos(radian) - speedY * Math.sin(radian));
			speedY = (float) (speedX * Math.sin(radian) + speedY * Math.cos(radian));
		}

		x += speedX * speed;
		y += speedY * speed;

		checkLeaving(50);

		count++;
	}

	public void render(Graphics g)
	{
		img.draw(x - width / 2, y - height / 2);
	}

	private double getDistance()
	{
		double vx = Math.abs(ObjectPool.getNearestEnemyX() - x);
		double vy = Math.abs(ObjectPool.getNearestEnemyY() - y);
		return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
	}

	public void activate(float x, float y, float angle)
	{
		active = true;
		this.x = x;
		this.y = y;

		speedX = Math.cos(Math.toRadians(angle));
		speedY = Math.sin(Math.toRadians(angle));

		count = 0;
	}
}