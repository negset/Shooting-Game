package shooting;

import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクト抽象クラス
 * プレイヤー,弾,敵などのスーパークラス
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
	 * 動作を規定する.
	 */
	public abstract void update();

	/**
	 * 描画処理を行う.
	 */
	public abstract void render(Graphics g);
}