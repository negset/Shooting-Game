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
	/** アイテム獲得となる自機との距離 */
	public static final int DIST_CATCH = 10;
	/** 自動接近が有効になる自機との距離 */
	public static final int DIST_FOLLOW = 50;
	/** 自動回収時の移動の速さ */
	public static final int SPEED_GATHER = 10;
	/** 自動接近時の移動の速さ */
	public static final int SPEED_FOLLOW = 3;

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
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** 種類 */
	private int type;
	/** 落下時の速度 */
	private float fallSpeed;
	/** フレームカウンタ */
	private int counter;
	/** 自動回収フラグ */
	private boolean isGathering;
	/** 自動接近フラグ */
	private boolean isFollowing;
	/***/
	private int idealDist;

	/**
	 * コンストラクタ
	 */
	Item()
	{
		width = img[0].getWidth();
		height = img[0].getHeight();
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		if (ObjectPool.hasActivePlayer())
		{
			float diffX = ObjectPool.getPlayerX() - x;
			float diffY = ObjectPool.getPlayerY() - y;
			double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

			// アイテム獲得
			if (dist <= DIST_CATCH)
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
			else if (isGathering)
			{
				x += diffX * SPEED_GATHER / dist;
				y += diffY * SPEED_GATHER / dist;
			}
			else
			{
				// 自機が画面上部に来たら,自動回収フラグを立てる.
				if (ObjectPool.getPlayerY() <= Play.AREA_TOP + 150)
				{
					isGathering = true;
				}
				// 自動接近時の動作
				else if (isFollowing)
				{
					// 実際の距離と目標距離の差を計算する.
					double diff = dist - idealDist;
					// 差が0以下であれば通常速度,正であれば目標までの距離を詰める.
					double v = (diff <= 0) ? SPEED_FOLLOW : diff;
					x += diffX * v / dist;
					y += diffY * v / dist;
					idealDist -= SPEED_FOLLOW;
				}
				// 自機が近くに来たら,自動接近フラグを立てる.
				else if (dist <= 50)
				{
					isFollowing = true;
				}
				else
				{
					fall();
				}
			}
		}
		else
		{
			fall();
		}

		counter++;
	}

	/**
	 * 落下する際の動作.
	 */
	private void fall()
	{
		y += fallSpeed;
		// 方向転換
		if (counter > 60 && counter < 60 + 40)
		{
			fallSpeed += 0.1;
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
		fallSpeed = -2;
		counter = 0;
		isGathering = false;
		isFollowing = false;
		idealDist = DIST_FOLLOW;
	}
}