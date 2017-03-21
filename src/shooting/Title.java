package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * タイトル画面の更新,描画を行うクラス.
 *
 * @author negset
 */
public class Title extends GameState
{
	/** フレームカウンタ */
	int counter;

	/**
	 * 初期化処理.
	 */
	public void init(GameContainer gc)
			throws SlickException
	{
		nextState = false;
		counter = 0;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		counter++;

		if (KeyInput.getZ() == 1)
		{
			nextState = true;
		}
		else if (KeyInput.getEscape() == 1)
		{
			gc.exit();
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		Text.drawString("弾幕シューティングゲーム", 400, 200, 1);
		Text.setAlpha(1-(float)counter%50/50);
		Text.drawString("Ｚキーで始める", 400, 300, 1);
		Text.setAlpha(1f);
		Text.drawString("©2017 negset all right reserved", 400, 520, 1);
	}
}