package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * ゲームシーンの抽象クラス.
 *
 * @author negset
 */
public abstract class GameState
{
	/** シーン移行フラグ */
	public boolean nextState;

	/**
	 * コンストラクタ
	 */
	GameState()
	{
		nextState = false;
	}

	/**
	 * 初期化処理.
	 */
	public abstract void init(GameContainer gc)
			throws SlickException;

	/**
	 * ステップごとの更新.
	 */
	public abstract void update(GameContainer gc, int delta)
			throws SlickException;

	/**
	 * ステップごとの描画処理.
	 */
	public abstract void render(GameContainer gc, Graphics g)
			throws SlickException;
}
