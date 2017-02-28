package shooting;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Item extends GameObject
{
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[13];
			SpriteSheet ss = new SpriteSheet("res/item.png", 16, 16);
			for (int i = 0; i < 13; i++)
			{
				img[i] = ss.getSubImage(i, 0);
			}
		}
		catch (SlickException e) {}
	}

	Player player;
	int type;
	double distance;
	float speedY;
	int counter;
	boolean autocollect;

	Item(Player player)
	{
		active = false;
		this.player = player;
	}

	public void update()
	{
		if (player.active)
		{
			float xdis = player.x - x;
			float ydis = player.y - y;
			distance = Math.sqrt(Math.pow(xdis, 2) + Math.pow(ydis, 2));

			if (distance < 20)
			{
				// アイテム獲得
				switch (type)
				{
					case 0:
						Playdata.addPower(1);
						break;

					case 1:
						Playdata.addScore(1000);
						break;

					case 2:
						Playdata.addPower(10);
						break;

					case 3:
						Playdata.addBomb(1);
						break;

					case 4:
						Playdata.addLife(1);
						break;

					case 5:
						Playdata.addPower(128);
						break;

					default:
				}
				active = false;
			}
			else if (autocollect)
			{
				// アイテムを自動で回収
				x += (float) (xdis * 10 / distance);
				y += (float) (ydis * 10 / distance);
			}
			else
			{
				if (player.y < 180 && Playdata.isFullpower)
				{
					autocollect = true;
				}
				else if (distance < 45)
				{
					// アイテムが近寄る
					x += (float) (xdis * 3 / distance);
					y += (float) (ydis * 3 / distance);
				}
				else
				{
					// アイテム落下
					fall();
				}
			}
		}
		else
		{
			// アイテム落下
			fall();
		}

		counter++;
	}

	/**
	 * アイテムが落下する際の動作.
	 */
	private void fall()
	{
		y += speedY;
		// 方向転換
		if (counter > 60 && counter < 100)
		{
			speedY += 0.1;
		}
		// 画面外に出たら非アクティブにする
		if (y > Play.AREA_BOTTOM + 8)
		{
			active = false;
		}
	}

	public void render()
	{
		if (y > Play.AREA_TOP - 8)
		{
			img[type * 2].drawCentered(x, y);
		}
		else
		{
			img[type * 2 + 1].drawCentered(x, Play.AREA_TOP + 8);
		}
	}

	public void activate(float x, float y, int type)
	{
		this.x = x;
		this.y = y;
		active = true;
		this.type = type;
		speedY = -2;
		counter = 0;
		autocollect = false;
	}
}