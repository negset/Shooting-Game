package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * 自機が射出する弾のクラス.
 *
 * @author negset
 */
public class MyBullet extends GameObject
{
	/** 画像 */
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/mybullet.png");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ
	 */
	MyBullet()
	{
		width = img.getWidth();
		height = img.getHeight();
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		y -= 8;

		// 画面の外に出たら消去
		if (y < Play.AREA_TOP - 5)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		img.drawCentered(x, y);
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
	}
}