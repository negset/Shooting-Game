package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet extends GameObject
{
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
		catch (SlickException e) {}
	}

	float angle;	// X軸正の向きが0°,時計回り
	float speed;
	int motion;
	int type;
	int color;
	boolean isGrazed;
	private float speedX;
	private float speedY;
	private int counter;

	/**
	 * コンストラクタ
	 */
	Bullet()
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

			default:
		}
		counter++;
	}

	/**
	 * 等速で直進移動する.
	 */
	private void motion0()
	{
		x += speedX;
		y += speedY;

		if (x < Play.AREA_LEFT || x > Play.AREA_RIGHT
				|| y < Play.AREA_TOP || y > Play.AREA_BOTTOM)
		{
			active = false;
		}
	}

	/**
	 * 射出直後のみ高速で直進移動する.
	 */
	private void motion1()
	{
		float f;
		if (counter < 10)
		{
			f = 2.0f;
		}
		else
		{
			f = 1.0f;
		}
		x += speedX * f;
		y += speedY * f;

		if (x < Play.AREA_LEFT || x > Play.AREA_RIGHT
				|| y < Play.AREA_TOP || y > Play.AREA_BOTTOM)
		{
			active = false;
		}
	}

	public void render(Graphics g)
	{
		img[color].setRotation(angle + 90);
		img[color].drawCentered(x, y);
	}

	/**
	 * 弾の初期化を行う.
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