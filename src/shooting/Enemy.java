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

	private boolean startshoot;
	private int shootnum;

	/**
	 * コンストラクタ
	 */
	Enemy()
	{
		active = false;
	}

	/**
	 * プレイヤーの弾と衝突したときの動作.
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
				move_enemy2();
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
				Barrage.nWayShot(x, y, 90, 1.4f, 1, 0, 0, 3, 45);
			speedY = 0;
		}
		else if (counter < c + 100)
		{
			speedX += 0.1f;
		}
		else
		{
			speedX = 2.5f;
		}

		active = x < Play.AREA_RIGHT + 24;
	}

	/**
	 * 敵その１の動作
	 */
	void move_enemy0()
	{
		y++;
		// ゆらゆら
		x += Math.sin(y / 20);

		// 画面外に出たら消去
		if (y > Play.AREA_BOTTOM + 20)
		{
			active = false;
		}

		// 一定間隔で弾を撃つ
		if ((counter % 100) == 0)
		{
			Barrage.nWayShot(x, y, 90, 1.5f, 1, 0, Random.nextInt(7), 6, 300);
		}
	}

	/**
	 * 敵その２の動作
	 */
	void move_enemy1()
	{
		double p = 100; // 静止までの時間
		double q = 200; // 画面のどこで静止するか
		// 二次関数で動きを表現
		y = (float) (-q / Math.pow(p, 2) * Math.pow((counter - p), 2) + q);

		// 画面外に出たら消去
		if ((-30 > y))
		{
			active = false;
		}

		if (counter > 100)
		{
			// 撃ち始め
			startshoot = true;
		}

		// 撃ち始めフラグが立っていたら、レベルと等しい回数、弾を撃つ
		if (startshoot)
		{
			if (((counter % 10) == 0) && (shootnum > 0))
			{
				Barrage.nWayAimShot(x, y, 1.5f, 1, 0, Random.nextInt(7), 3, 45);
				shootnum--;
			}
		}
	}

	/**
	 * 敵その３の動作
	 */
	void move_enemy2()
	{
		y += 2 * Math.cos(Math.toRadians(Math.min(180, counter / 2)));

		// 画面外に出たら消去
		if (-30 > y)
		{
			active = false;
		}

		if (counter > 100 && counter < 200)
		{
			if (counter % 4 == 0)
			{
				ObjectPool.newBullet(x, y, counter * 3.6f + 90, 1.5f, 0, 0, Random.nextInt(7));
			}
		}
	}

	/**
	 * 描画を行う.
	 */
	public void render()
	{
		img[counter / 2 % 4].drawCentered(x, y);
	}

	/**
	 * インスタンスを初期化する.
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	public void activate(float x, float y)
	{
		this.x = x;
		this.y = y;
		active = true;
		motion = 0;
		type = 0;
		hitpoint = 3;
		item = 0;
		counter = 0;
		speedX = 0;
		speedY = 0;
		shootnum = 5;
		startshoot = false;
	}
}