package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * 敵がプレイヤーの弾を受けた時のダメージエフェクト.
 *
 * @author negset
 */
public class Damage extends GameObject
{
	private static Image img;
	static
	{
		try
		{
			img = new Image("res/damage.png");
		}
		catch (SlickException e) {}
	}

	private int counter;
	private float scale;

	Damage()
	{
		width = img.getWidth();
		height = img.getHeight();
	}

	public void update()
	{
		y -= 2.5;
		counter++;
		if (counter > 50)
		{
			active = false;
		}
	}

	public void render(Graphics g)
	{
		scale = 2.0f - (float)counter/25;
		img.setAlpha(1-(float)counter/50);
		img.draw(x - 8 * scale, y - 8 * scale, scale);
	}

	public void activate(float x, float y)
	{
		active = true;
		this.x = x;
		this.y = y;
		counter = 0;
	}
}
