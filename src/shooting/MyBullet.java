package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyBullet extends GameObject
{
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/mybullet.png");
		}
		catch (SlickException e) {}
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
	 * インスタンスを更新する.
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
	 * 描画を行う.
	 */
	public void render(Graphics g)
	{
		img.drawCentered(x, y);
	}

	public void activate(float x, float y)
	{
		active = true;
		this.x = x;
		this.y = y;
	}
}