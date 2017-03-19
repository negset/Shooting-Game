package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * プレイ画面の更新,描画を行うクラス.
 *
 * @author negset
 */
public class Play extends GameState
{
	/**
	 * プレイ領域を表す定数
	 */
	public static final int AREA_LEFT = 40;
	public static final int AREA_RIGHT = 520;
	public static final int AREA_TOP = 20;
	public static final int AREA_BOTTOM = 580;
	public static final int AREA_WIDTH = 480;
	public static final int AREA_HEIGHT = 560;
	public static final int AREA_CENTER_X = 280;
	public static final int AREA_CENTER_Y = 300;

	/** フレーム画像 */
	static Image frame0, frame1, frame2, frame3;
	static
	{
		try
		{
			frame0 = new Image("res/frame0.jpg");
			frame1 = new Image("res/frame1.jpg");
			frame2 = new Image("res/frame2.jpg");
			frame3 = new Image("res/frame3.jpg");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	/** オブジェクトプール */
	ObjectPool objectpool;
	/** プレイデータ */
	Playdata playdata;

	/** フレームカウンタ */
	public static int counter;
	/** ポーズフラグ */
	private boolean isPause;
	/** ポーズ時のカーソル位置 */
	private int cursor;

	/**
	 * コンストラクタ
	 */
	Play()
	{
		objectpool = new ObjectPool();
		playdata = new Playdata();
	}

	/**
	 * 初期化処理.
	 */
	public void init(GameContainer gc)
			throws SlickException
	{
		nextState = false;
		objectpool.init();
		playdata.init();
		counter = 0;
		isPause = false;
		cursor = 1;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		if (!isPause)
		{
			objectpool.updateAllObjects();
			objectpool.getColision();

			// 敵の出現(仮)
			if (counter % 180 == 90)
			{
				ObjectPool.newEnemy(300, 0, 0, 2000, 0, 1500, 0, (counter-90)/180, 3, 10, 2,
						90, 45, 4, 0, 0, 1, 2);
			}

			if (Playdata.isGameover)
			{
				if (KeyInput.getZ() == 1)
				{
					nextState = true;
				}
			}
			else if (KeyInput.getEscape() == 1)
			{
				isPause = true;
			}

			counter++;
		}
		// ポーズ時の処理
		else
		{
			if (KeyInput.getZ() == 1)
			{
				switch (cursor)
				{
					case 0:
						nextState = true;
						break;

					case 1:
						cursor = 1;
						isPause = false;
						break;
				}
			}
			else if (KeyInput.getLeft() == 1)
			{
				cursor = 0;
			}
			else if (KeyInput.getRight() == 1)
			{
				cursor = 1;
			}
			else if (KeyInput.getEscape() == 1)
			{
				cursor = 1;
				isPause = false;
			}
		}
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		frame0.draw(0, 0);
		frame1.draw(AREA_LEFT, 0);
		frame2.draw(AREA_LEFT, AREA_BOTTOM);
		frame3.draw(AREA_RIGHT, 0);

		objectpool.renderAllObjects(g);
		playdata.render(g);

		if (Playdata.isGameover)
		{
			Text.drawString("GAME OVER", AREA_CENTER_X, 240, 1);
			Text.drawString("Zキーでタイトルに戻る", AREA_CENTER_X, 300, 1);
		}
		else if (isPause)
		{
			Text.drawString("タイトル画面に戻りますか？", AREA_CENTER_X, 240, 1);
			if (cursor == 0) Text.setColor(0.9f, 0.9f, 0f);
			Text.drawString("はい", AREA_CENTER_X - 50, 300, 1);
			Text.setDefaultColor();
			if (cursor == 1) Text.setColor(0.9f, 0.9f, 0f);
			Text.drawString("いいえ", AREA_CENTER_X + 50, 300, 1);
			Text.setDefaultColor();
		}
	}
}