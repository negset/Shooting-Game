package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

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
	private static Image frame0, frame1, frame2, frame3;
	/** 決定 SE */
	private static Sound decide;
	/** 選択 SE */
	private static Sound select;
	static
	{
		try
		{
			frame0 = new Image("res/img/frame0.jpg");
			frame1 = new Image("res/img/frame1.jpg");
			frame2 = new Image("res/img/frame2.jpg");
			frame3 = new Image("res/img/frame3.jpg");
			decide = new Sound("res/snd/se_decide.wav");
			select = new Sound("res/snd/se_select.wav");
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
	private boolean isPausing;
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
		Random.init();
		counter = 0;
		isPausing = false;
		cursor = 1;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		if (!isPausing)
		{
			objectpool.update();
			objectpool.collisionDetection();

			// 敵の出現(仮)
			if (counter % 180 == 90)
			{
				Shot shot = new Shot(counter/180, 6, 6, 0, 90, 0, 20, 12, 0, 0, 1, 2f, 0f);
				ObjectPool.newEnemy(300, 0, 0, 20, 0, 1500, 0, shot);
			}

			if (Playdata.isGameOver())
			{
				if (KeyInput.getZ() == 1)
				{
					nextState = true;
				}
			}
			else if (KeyInput.getEscape() == 1)
			{
				isPausing = true;
				decide.play();
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
						isPausing = false;
						break;
				}
				decide.play();
			}
			else if (KeyInput.getLeft() == 1)
			{
				cursor = 0;
				select.play();
			}
			else if (KeyInput.getRight() == 1)
			{
				cursor = 1;
				select.play();
			}
			else if (KeyInput.getEscape() == 1)
			{
				cursor = 1;
				isPausing = false;
				decide.play();
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

		objectpool.render(g);
		playdata.render(g);

		if (Playdata.isGameOver())
		{
			Text.drawString("GAME OVER", AREA_CENTER_X, 240, 1);
			Text.drawString("Zキーでタイトルに戻る", AREA_CENTER_X, 300, 1);
		}
		else if (isPausing)
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