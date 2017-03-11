package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * 敵機が射出する弾のクラス.
 *
 * @author negset
 */
public class Bullet extends GameObject
{
	/** 画像 */
	private static Image[] img;
	static
	{
		img = new Image[7];
		try
		{
			for (int i = 0; i < 7; i++)
			{
				img[i] = new Image("res/bullet" + i + ".png");
			}
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** 角度 */
	float angle;		// X軸正の向きが0°で時計回り
	/** 速さ */
	float speed;
	/** 動き */
	int motion;
	/** 種類 */
	int type;
	/** 色 */
	int color;
	/** グレイズ判定済みフラグ */
	boolean isGrazed;
	/** 速度のX成分 */
	private float speedX;
	/** 速度のY成分 */
	private float speedY;
	/** フレームカウンタ */
	private int counter;

	/**
	 * コンストラクタ
	 */
	Bullet()
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

			default:
		}
		counter++;
	}

	/**
	 * 等速で直線移動する.
	 */
	private void motion0()
	{
		x += speedX;
		y += speedY;

		if (x < Play.AREA_LEFT - width / 2 || x > Play.AREA_RIGHT + width / 2
				|| y < Play.AREA_TOP - height / 2|| y > Play.AREA_BOTTOM + height / 2)
		{
			active = false;
		}
	}

	/**
	 * 射出直後のみ高速で直線移動する.
	 */
	private void motion1()
	{
		float f;
		if (counter < 15)
		{
			f = 2.5f;
		}
		else
		{
			f = 1.0f;
		}
		x += speedX * f;
		y += speedY * f;

		if (x < Play.AREA_LEFT - width / 2 || x > Play.AREA_RIGHT + width / 2
				|| y < Play.AREA_TOP - height / 2|| y > Play.AREA_BOTTOM + height / 2)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		img[color].setRotation(angle + 90);
		img[color].drawCentered(x, y);
	}

	/**
	 * 初期化処理.
	 *
	 * @param x 開始X座標
	 * @param y 開始Y座標
	 * @param angle 弾の向き
	 * @param speed 弾速
	 * @param motion 弾の動き
	 * @param type 弾の種類
	 */
	public void activate(float x, float y, float angle, float speed, int motion, int type, int color)
	{
		active = true;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		this.motion = motion;
		this.type = type;
		this.color = color;
		counter = 0;
		isGrazed = false;

		// 高速化のため、極座標からXY速度に変換しておく
		double radian = Math.toRadians(angle);
		speedX = (float) (speed * Math.cos(radian));
		speedY = (float) (speed * Math.sin(radian));
	}
}