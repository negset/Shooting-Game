package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * グレイズエフェクトのクラス.
 *
 * @author negset
 */
public class Graze extends GameObject
{
	/** 画像 */
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/graze.png");
		}
		catch (SlickException e) {}
	}

	/** フレームカウンタ */
	int counter;
	/** 各パーティクルのX座標 */
	float[] x;
	/** 各パーティクルのY座標 */
	float[] y;
	/** 各パーティクルの飛ぶ方向 */
	int[] angle;
	/** 各パーティクルの速度のX成分 */
	float[] speedX;
	/** 各パーティクルの速度のY成分 */
	float[] speedY;

	/**
	 * コンストラクタ
	 */
	Graze()
	{
		width = img.getWidth();
		height = img.getHeight();
		x = new float[3];
		y = new float[3];
		angle = new int[3];
		speedX = new float[3];
		speedY = new float[3];
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		for (int i = 0; i < 3; i++)
		{
			x[i] += speedX[i];
			y[i] += speedY[i];
		}

		counter++;
		if (counter > 20)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		for (int i = 0; i < 3; i++)
		{
			img.setRotation(angle[i]);
			img.draw(x[i] - img.getWidth() / 2, y[i] - img.getHeight() / 2);
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
		counter = 0;
		for (int i = 0; i < 3; i++)
		{
			this.x[i] = x;
			this.y[i] = y;
			angle[i] = i * 120  + Random.nextInt(120);
			float speed = 2 + Random.nextInt(2);
			speedX[i] = speed * (float) Math.cos(Math.toRadians(angle[i]));
			speedY[i] = speed * (float) Math.sin(Math.toRadians(angle[i]));
		}
	}
}
