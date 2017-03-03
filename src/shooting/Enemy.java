package shooting;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends GameObject
{
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[8];
			SpriteSheet ss = new SpriteSheet("res/enemy.png", 48, 48);
			for (int i = 0; i < 8; i++)
			{
				img[i] = ss.getSubImage(i % 4, i / 4);
			}
		}
		catch (SlickException e) {}
	}

	int motion;
	int type;
	int hitpoint;
	int item;

	private int counter;
	private float speedX;
	private float speedY;

	/**
	 * コンストラクタ
	 */
	Enemy()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
	}

	public void update()
	{
		switch (motion)
		{
			case 0:
				motion0();
				break;

			case 1:
				motion1();
				break;

			case 2:
				motion2();
				break;

			default:
		}

		x += speedX;
		y += speedY;

		counter++;
	}

	private void motion0()
	{
		int c = 50;
		if (counter < c)
			speedY = 2.5f;
		else if (counter < c + 25)
			speedY -= 0.1f;
		else if (counter < c + 75)
		{
			if (counter == c + 50)
				Barrage.nWayAimShot(x, y, 1.4f, 1, 0, 0, 3, 45);
			speedY = 0;
		}
		else if (counter < c + 100)
			speedY -= 0.1f;
		else
			speedY = -2.5f;

		active = y > -10;
	}

	private void motion1()
	{
		int c = 50;
		if (counter < c)
			speedY = 2.5f;
		else if (counter < c + 25)
			speedY -= 0.1f;
		else if (counter < c + 75)
		{
			if (counter == c + 50)
				Barrage.nWayAimShot(x, y, 1.4f, 1, 0, 0, 3, 45);
			speedY = 0;
		}
		else if (counter < c + 100)
		{
			speedX += 0.01f;
			speedY += 0.1f;
		}
		else
		{
			speedX += 0.01f;
			speedY = 2.5f;
		}
		active = y < Play.AREA_BOTTOM + 10;
	}

	private void motion2()
	{
		int c = 50;
		if (counter < c)
			speedY = 2.5f;
		else if (counter < c + 25)
			speedY -= 0.1f;
		else if (counter < c + 75)
		{
			if (counter == c + 50)
				Barrage.nWayAimShot(x, y, 1.4f, 1, 0, 0, 3, 45);
			speedY = 0;
		}
		else if (counter < c + 100)
		{
			speedX -= 0.01f;
			speedY += 0.1f;
		}
		else
		{
			speedX -= 0.01f;
			speedY = 2.5f;
		}
		active = y < Play.AREA_BOTTOM + 10;
	}

	/**
	 * 描画を行う.
	 */
	public void render()
	{
		img[counter / 2 % 4].drawCentered(x, y);
	}

	/**
	 * 自機の弾と衝突したときの動作
	 */
	public void hit()
	{
		hitpoint--;

		if (hitpoint <= 0)
		{
			Playdata.addScore(300);
			ObjectPool.newExplosion(x, y);
			ObjectPool.newItem(x, y);
			active = false;
		}
	}

	/**
	 * インスタンスを初期化する.
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	public void activate(float x, float y)
	{
		active = true;
		this.x = x;
		this.y = y;
		motion = Random.nextInt(3);
		type = 0;
		hitpoint = 3;
		item = 0;
		counter = 0;
		speedX = 0;
		speedY = 0;
	}
}