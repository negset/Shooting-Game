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
}