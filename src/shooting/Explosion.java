package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * 爆発エフェクトのクラス.
 *
 * @author negset
 */
public class Explosion extends GameObject
{
	/** 画像 */
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[11];
			SpriteSheet ss = new SpriteSheet("res/explosion.png", 80, 80);
			for (int i = 0; i < 11; i++)
			{
				img[i] = ss.getSubImage(i % 5, i / 5);
			}
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** フレームカウンタ */
	int counter;

	/**
	 * コンストラクタ
	 */
	Explosion()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		if (Play.counter % 3 == 0)
		{
			counter++;
		}
		if (counter >= 10)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		img[counter%11].drawCentered(x, y);
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
		counter = 0;
	}
}