package shooting;

import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの抽象クラス.
 */
public abstract class GameObject
{
	/**
	 * インスタンス有効フラグ(falseならインスタンスは処理されない)
	 */
	public boolean active;

	/**
	 * 座標のx成分
	 */
	public float x;

	/**
	 * 座標のy成分
	 */
	public float y;

	/**
	 * 画像の横幅
	 */
	public int width;

	/**
	 * 画像の縦幅
	 */
	public int height;

	/**
	 * ステップごとの更新.
	 */
	public abstract void update();

	/**
	 * ステップごとの描画処理.
	 */
	public abstract void render(Graphics g);

	/**
	 * オブジェクトがプレイ領域内にいるかどうかを確認し,
	 * 領域外に出ている場合は,インスタンスを無効にする.
	 *
	 * @param mergin
	 */
	void checkLeaving(int mergin)
	{
		if (x < Play.AREA_LEFT - width / 2 - mergin
				|| x > Play.AREA_RIGHT + width / 2 + mergin
				|| y < Play.AREA_TOP - height / 2 - mergin
				|| y > Play.AREA_BOTTOM + height / 2 + mergin)
		{
			active = false;
		}
	}
}