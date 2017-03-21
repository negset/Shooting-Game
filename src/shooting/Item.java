package shooting;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * アイテムクラス.
 *
 * @author negset
 */
public class Item extends GameObject
{
	/** 画像 */
	private static Image[] img;
	static
	{
		try
		{
			img = new Image[13];
			SpriteSheet ss = new SpriteSheet("res/img/item.png", 16, 16);
			for (int i = 0; i < 13; i++)
			{
				img[i] = ss.getSubImage(i, 0);
			}
		}
		catch (SlickException e) {}
	}

	/** 回収先の自機 */
	Player player;
	/** 種類 */
	int type;
	/** 速度のY成分 */
	float speedY;
	/** フレームカウンタ */
	int counter;
	/** 自動回収フラグ */
	boolean autoCollect;
	/** 自動接近フラグ */
	boolean autoFollow;

	/**
	 * コンストラクタ
	 *
	 * @param player アイテム回収先
	 */
	Item(Player player)
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
		this.player = player;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		if (player.active)
		{
			float distX = player.x - x;
			float distY = player.y - y;
			double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

			// アイテム獲得
			if (dist < 10)
			{
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
			// 自動回収時の動作
			else if (autoCollect)
			{
				x += (float) (distX * 10 / dist);
				y += (float) (distY * 10 / dist);
			}
			else
			{
				// 自機が画面上部に来たら,自動回収フラグを立てる.
				if (player.y < Play.AREA_TOP + 150)
				{
					autoCollect = true;
				}
				// 自動接近時の動作
				else if (autoFollow)
				{
					double pv = Math.sqrt(Math.pow(player.deltaX, 2) + Math.pow(player.deltaY, 2));
					double v = pv > 0 ? pv + 0.25 : 2.5;
					x += (float) (distX * v / dist);
					y += (float) (distY * v / dist);
				}
				// 自機が近くに来たら,自動接近フラグを立てる.
				else if (dist < 50)
				{
					autoFollow = true;
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
		if (counter > 60 && counter < 60 + 40)
		{
			speedY += 0.1;
		}
		// 画面外に出たら非アクティブにする.
		if (y > Play.AREA_BOTTOM + 8)
		{
			active = false;
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		if (y > Play.AREA_TOP - 8)
		{
			img[type * 2].setRotation(Math.min(counter * 30, 1080));
			img[type * 2].drawCentered(x, y);
		}
		else
		{
			img[type * 2 + 1].drawCentered(x, Play.AREA_TOP + 8);
		}
	}

	/**
	 * 初期化処理.
	 *
	 * @param x X座標
	 * @param y Y座標
	 * @param type 種類
	 */
	public void activate(float x, float y, int type)
	{
		active = true;
		this.x = x;
		this.y = y;
		this.type = type;
		speedY = -2;
		counter = 0;
		autoCollect = false;
		autoFollow = false;
	}
}