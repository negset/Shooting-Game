package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * プレイ画面の動作,描画を行うクラス.
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
	public static final int AREA_XCENTER = 280;
	public static final int AREA_YCENTER = 300;

	ObjectPool objectpool;
	Playdata playdata;

	public static int counter;
	private boolean isPause;
	private int cursor;

	/**
	 * コンストラクタ
	 */
	Play()
	{
		objectpool = new ObjectPool();
		playdata = new Playdata();
	}

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
	 * 動作を規定する.
	 * 1ループにつき1回呼ばれる.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		if (!isPause)
		{
			objectpool.updateAllObjects();
			objectpool.getColision();

			// 敵の出現(仮)
			if (counter % 100 == 50)
			{
				ObjectPool.newEnemy(AREA_LEFT + 50
						+ Random.nextInt(AREA_WIDTH - 100),
						AREA_TOP - 24);
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
	 * 描画処理を行う.
	 */
	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		objectpool.renderAllObjects();
		playdata.render(g);
		g.drawRect(AREA_LEFT, AREA_TOP,
				AREA_WIDTH, AREA_HEIGHT);

		if (Playdata.isGameover)
		{
			Text.drawString("GAME OVER", AREA_XCENTER, 240, 1);
			Text.drawString("Zキーでタイトルに戻る", AREA_XCENTER, 300, 1);
		}
		else if (isPause)
		{
			Text.drawString("タイトル画面に戻りますか？", AREA_XCENTER, 240, 1);
			if (cursor == 0) Text.setColor(0.9f, 0.9f, 0f);
			Text.drawString("はい", AREA_XCENTER - 50, 300, 1);
			Text.setDefaultColor();
			if (cursor == 1) Text.setColor(0.9f, 0.9f, 0f);
			Text.drawString("いいえ", AREA_XCENTER + 50, 300, 1);
			Text.setDefaultColor();
		}
	}
}