package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * 自機クラス.
 *
 * @author negset
 */
public class Player extends GameObject
{
	/** 画像 */
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[8];
			SpriteSheet ss = new SpriteSheet("res/player.png", 48, 48);
			for (int i = 0; i < 8; i++)
			{
				img[i] = ss.getSubImage(i % 4, i / 4);
			}
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** 移動の速さ */
	float speed;
	/** 無敵フラグ */
	boolean isInvincible;
	/** 無敵時間計測用のカウンタ */
	int invincibleCnt;

	/**
	 * コンストラクタ
	 */
	Player()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
		speed = 6;
	}

	/**
	 * ステップごとのステップごとの更新.
	 */
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
			mx *= 0.44;
			my *= 0.44;
		}
		float postX = x + mx;
		float postY = y + my;
		if (postX > Play.AREA_LEFT && postX < Play.AREA_RIGHT)
		{
			x = postX;
		}
		if (postY > Play.AREA_TOP && postY < Play.AREA_BOTTOM)
		{
			y = postY;
		}

		// 弾丸の射出
		if (KeyInput.getZ() >= 1 && Play.counter % 6 == 0)
		{
			ObjectPool.newMyBullet(x, y);
		}

		if (isInvincible)
		{
			if (++invincibleCnt > 240)
			{
				invincibleCnt = 0;
				isInvincible = false;
			}
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		if (isInvincible)
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

	/**
	 * 初期化処理.
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	public void activate(float x, float y)
	{
		active = true;
		this.x = x;
		this.y = y;
		isInvincible = false;
		invincibleCnt = 0;
	}
}