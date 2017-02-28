package shooting;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * メインクラス.
 * ウィンドウの生成及びゲーム画面の管理を行う.
 *
 * @author negset
 */
public class Main extends BasicGame
{
	Title title;
	Play play;
	KeyInput keyinput;
	private int state;
	private float fps;
	private int framecnt;
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

	public void init(GameContainer gc)
			throws SlickException
	{
		title = new Title();
		play = new Play();
		keyinput = new KeyInput();
		state = 0;
		framecnt = 0;
	}

	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		// 非フォーカス時は処理を行わない.
		if (!gc.hasFocus()) return;

		keyinput.update(gc);
		calcFPS(delta);

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
	}

	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		Text.drawString("FPS: " + String.format("%.2f", fps), 650, 580);

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
	}

	/**
	 * FPSを計算する.
	 *
	 * @param delta 前フレームにかかった時間(ミリ秒)
	 */
	private void calcFPS(int delta)
	{
		framecnt++;
		timer += delta;
		if (framecnt % 60 == 0)
		{
			fps = 60000 / (float) timer;
			timer = 0;
		}
	}

	/**
	 * ウィンドウの生成を行う.
	 */
	public static void main(String[] args)
	{
		Main main = new Main("Shooting Game");
		AppGameContainer app;
		try
		{
			app = new AppGameContainer(main);
			app.setDisplayMode(800, 600, false);
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.setAlwaysRender(true);
			app.setIcon("res/bullet0.png");
			app.start();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}