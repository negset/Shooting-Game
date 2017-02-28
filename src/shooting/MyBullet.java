package shooting;

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
		// 生成時は非アクティブ
		active = false;
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
	public void render()
	{
		img.drawCentered(x, y);
	}

	public void activate(float x, float y)
	{
		this.x = x;
		this.y = y;
		active = true;
	}
}