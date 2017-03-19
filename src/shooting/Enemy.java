package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * 敵機のクラス.
 *
 * @author negset
 */
public class Enemy extends GameObject
{
	/** 画像 */
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

	/** 種類 */
	int type;
	/** 体力 */
	int hp;
	/** 動き */
	int motion;
	/** 倒したときの得点 */
	int score;
	/** 倒したときに得られるアイテム */
	int item;
	/** ショットの種類 */
	int sType;
	/** ショットの回数 */
	int sTimes;
	/** ショットの間隔 */
	int sInterval;
	/**
	 * ショットの狙い方
	 * 0:狙わない 1:自機狙い(固定) 2:自機狙い(常時)
	 */
	int sAimType;
	/** ショットの方向 */
	int sAngle1, sAngle2;
	/** ショットの広がり */
	int sRange;
	/** ショットの列数 */
	int sWays;
	/** 弾の種類 */
	int bType;
	/** 弾の色 */
	int bColor;
	/** 弾の動き */
	int bMotion;
	/** 弾の速さ */
	float bSpeed1, bSpeed2;

	/** フレームカウンタ */
	private int counter;
	/** 速度のX成分 */
	private float speedX;
	/** 速度のY成分 */
	private float speedY;

	private boolean startShoot;
	private int nextShoot;
	private int shootCnt;

	/**
	 * コンストラクタ
	 */
	Enemy()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
	}

	/**
	 * ステップごとの更新.
	 */
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

		if (startShoot && counter >= nextShoot)
		{
			shoot();
		}

		counter++;
	}

	/**
	 * 動きその1
	 * 真下に降下し,射出後に真上に上昇する.
	 */
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
				startShoot = true;
			speedY = 0;
		}
		else if (counter < c + 100)
			speedY -= 0.1f;
		else
			speedY = -2.5f;

		active = y > -10;
	}

	/**
	 * 動きその2
	 * 真下に降下し,射出後に右斜め下に降下する.
	 */
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
				startShoot = true;
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

	/**
	 * 動きその3
	 * 真下に降下し,射出後に左斜め下に降下する.
	 */
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
				startShoot = true;
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

	private void shoot()
	{
		if (sAimType == 2 || (shootCnt == 0 && sAimType == 1))
		{
			sAngle1 += ObjectPool.getAngleToPlayer(this);
		}

		switch (sType)
		{
			case 0:
				Shot.single(x, y, sAngle1, bType, bColor, bMotion, bSpeed1);
				break;

			case 1:
				Shot.nWay(x, y, sAngle1, sRange, sWays, bType, bColor, bMotion, bSpeed1);
				break;

			case 2:
				Shot.round(x, y, sAngle1, sRange, sWays, bType, bColor, bMotion, bSpeed1);
				break;

			default:
		}

		if (++shootCnt == sTimes)
		{
			startShoot = false;
			return;
		}

		sAngle1 += sAngle2;
		bSpeed1 += bSpeed2;

		nextShoot = counter + sInterval;
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		img[counter / 2 % 4].drawCentered(x, y);
	}

	/**
	 * 自機の弾と衝突したときの動作.
	 */
	public void hit()
	{
		hp--;

		if (hp <= 0)
		{
			Playdata.addScore(score);
			ObjectPool.newExplosion(x, y);
			ObjectPool.newItem(x, y, item);
			active = false;
		}
	}

	/**
	 * 初期化処理.
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	public void activate(float x, float y, int type, int hp, int motion,
			int score, int item, int sType, int sTimes, int sInterval,
			int sAimType, int sAngle1, int sAngle2, int sRange, int sWays,
			int bType, int bColor, int bMotion, float bSpeed1, float bSpeed2)
	{
		active = true;
		this.x = x;
		this.y = y;
		this.type = type;
		this.hp = hp;
		this.motion = motion;
		this.score = score;
		this.item = item;
		this.sType = sType;
		this.sTimes = sTimes;
		this.sInterval = sInterval;
		this.sAimType = sAimType;
		this.sAngle1 = sAngle1;
		this.sAngle2 = sAngle2;
		this.sRange = sRange;
		this.sWays = sWays;
		this.bType = bType;
		this.bColor = bColor;
		this.bMotion = bMotion;
		this.bSpeed1 = bSpeed1;
		this.bSpeed2 = bSpeed2;

		counter = 0;
		speedX = 0;
		speedY = 0;
		startShoot = false;
		nextShoot = 0;
		shootCnt = 0;
	}
}