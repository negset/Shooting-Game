package shooting;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * メインクラス.
 * ウィンドウの生成及びゲームシーンの管理を行う.
 *
 * @author negset
 */
public class Main extends BasicGame
{
	/** タイトル画面 */
	Title title;
	/** プレイ画面 */
	Play play;
	/** キー入力 */
	KeyInput keyinput;
	/** ゲームシーン */
	private int state;
	/** フレームレート */
	private float fps;
	/** フレームカウンタ */
	private int frameCnt;
	/** 経過時間換算用のタイマー */
	private int timer;

	/**
	 * コンストラクタ
	 *
	 * @param name ゲーム名
	 * @throws SlickException
	 */
	public Main(String name)
	{
		super(name);
	}

	/**
	 * 初期化処理.
	 */
	public void init(GameContainer gc)
			throws SlickException
	{
		title = new Title();
		play = new Play();
		keyinput = new KeyInput();
		state = 0;
		frameCnt = 0;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		// 非フォーカス時は処理を行わない.
		if (!gc.hasFocus()) return;

		keyinput.update(gc);

		switch(state)
		{
			case 0:
				title.update(gc, delta);
				if (title.nextState)
				{
					play.init(gc);
					state = 1;
				}
				break;

			case 1:
				play.update(gc, delta);
				if (play.nextState)
				{
					title.init(gc);
					state = 0;
				}
				break;

			default:
		}

		calcFPS(delta);
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		switch(state)
		{
			case 0:
				title.render(gc, g);
				break;

			case 1:
				play.render(gc, g);
				break;

			default:
		}

		Text.drawString("FPS: " + String.format("%.2f", fps), 680, 580);
	}

	/**
	 * FPSを計算する.
	 *
	 * @param delta 前フレームにかかった時間(ミリ秒)
	 */
	private void calcFPS(int delta)
	{
		frameCnt++;
		timer += delta;
		if (frameCnt % 60 == 0)
		{
			fps = (float) 60000 / timer;
			timer = 0;
		}
	}

	/**
	 * メインメソッド.
	 * ウィンドウの生成を行う.
	 */
	public static void main(String[] args)
	{
		Main main = new Main("Shooting Game");
		try
		{
			AppGameContainer agc = new AppGameContainer(main);
			agc.setDisplayMode(800, 600, false);
			agc.setTargetFrameRate(60);
			agc.setShowFPS(false);
			agc.setAlwaysRender(true);
			agc.setIcon("res/img/bullet0.png");
			agc.start();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}