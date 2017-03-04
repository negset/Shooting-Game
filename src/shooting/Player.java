package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends GameObject
{
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[8];
			SpriteSheet ss = new SpriteSheet("res/player.png", 48, 48);
			for (int i = 0; i < 8; i++)
			{
				img[i] = ss.getSubImage(i%4, i/4);
			}
		}
		catch (SlickException e) {}
	}

	float postx, posty;
	float speed;
	boolean invincible;
	int invincibleCnt;

	Player()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
		speed = 6;
	}

	public void update()
	{
		// 移動
		float mx = 0, my = 0;
		if (KeyInput.getLeft() >= 1)
		{
			mx -= speed;
		}
		if (KeyInput.getRight() >= 1)
		{
			mx += speed;
		}
		if (KeyInput.getUp() >= 1)
		{
			my -= speed;
		}
		if (KeyInput.getDown() >= 1)
		{
			my += speed;
		}
		// 斜め移動
		if (mx * my != 0)
		{
			mx /= 1.414;
			my /= 1.414;
		}
		// 低速移動
		if (KeyInput.getShift() >= 1)
		{
			mx *= 0.4;
			my *= 0.4;
		}
		postx = x + mx;
		posty = y + my;
		if (postx > Play.AREA_LEFT && postx < Play.AREA_RIGHT)
		{
			x = postx;
		}
		if (posty > Play.AREA_TOP && posty < Play.AREA_BOTTOM)
		{
			y = posty;
		}

		// 弾丸の射出
		if (KeyInput.getZ() >= 1 && Play.counter % 6 == 0)
		{
			ObjectPool.newMyBullet(x, y);
		}

		if (invincible)
		{
			if (++invincibleCnt > 240)
			{
				invincibleCnt = 0;
				invincible = false;
			}
		}
	}

	public void render(Graphics g)
	{
		if (invincible)
		{
			if (Play.counter%10<5)
			{
				img[Play.counter/2%4].drawCentered(x, y);
			}
		}
		else
		{
			img[Play.counter/2%4].drawCentered(x, y);
		}
	}

	public void activate(float x, float y)
	{
		active = true;
		this.x = x;
		this.y = y;
		invincible = false;
		invincibleCnt = 0;
	}
}