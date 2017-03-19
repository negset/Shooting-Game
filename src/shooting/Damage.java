package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * 敵機のダメージエフェクトのクラス.
 *
 * @author negset
 */
public class Damage extends GameObject
{
	/** 画像 */
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/img/damage.png");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** フレームカウンタ */
	private int counter;
	/** 画像の描画倍率 */
	private float scale;

	/**
	 * コンストラクタ
	 */
	Damage()
	{
		width = img.getWidth();
		height = img.getHeight();
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		y -= 2.5;
		scale = 2.0f - (float)counter/25;

		counter++;
		if (counter > 50)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		// 加算合成
		g.setDrawMode(Graphics.MODE_ADD);
		img.draw(x - width * scale / 2, y - width * scale / 2, scale);
		g.setDrawMode(Graphics.MODE_NORMAL);
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
