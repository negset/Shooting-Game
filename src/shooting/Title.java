package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * タイトル画面の動作・描画を行うクラス.
 *
 * @author negset
 */
public class Title extends GameState
{
	int counter;

	/**
	 * コンストラクタ
	 */
	Title()
	{
	}

	/**
	 * ゲーム画面の初期化.
	 * リソースファイルの読み込み等を行う.
	 * 起動時に1度だけ呼ばれる.
	 */
	public void init(GameContainer gc)
			throws SlickException
	{
		nextState = false;
		counter = 0;
	}

	/**
	 * 動作を規定する.
	 * 1ループにつき1回呼ばれる.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		counter++;

		if (KeyInput.getZ() == 1)
		{
			nextState = true;
		}
	}

	/**
	 * 描画処理を行う.
	 * 1ループにつき1回呼ばれる.
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