package shooting;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * キーボード入力を受け取るクラス.
 *
 * @author negset
 */
public class KeyInput
{
	/**
	 * キーの状態を保持する.
	 * 0: 押されていない
	 * 1: 押された瞬間
	 * 2: 押されて続けている
	 */
	private static int z;
	private static int left;
	private static int right;
	private static int up;
	private static int down;
	private static int shift;
	private static int escape;

	/**
	 * ステップごとの更新.
	 *
	 * @param gc ゲームコンテナ
	 */
	public void update(GameContainer gc)
	{
		z = getInput(gc, Input.KEY_Z);
		left = getInput(gc, Input.KEY_LEFT);
		right = getInput(gc, Input.KEY_RIGHT);
		up = getInput(gc, Input.KEY_UP);
		down = getInput(gc, Input.KEY_DOWN);
		shift = Math.max(getInput(gc, Input.KEY_LSHIFT),
				getInput(gc, Input.KEY_RSHIFT));
		escape = getInput(gc, Input.KEY_ESCAPE);
	}

	/**
	 * キーの入力状態を取得する.
	 *
	 * @param gc ゲームコンテナ
	 * @param key 状態を調べるキー
	 * @return キーの状態
	 */
	private int getInput(GameContainer gc, int key)
	{
		if (gc.getInput().isKeyPressed(key))
		{
			return 1;
		}
		if (gc.getInput().isKeyDown(key))
		{
			return 2;
		}
		return 0;
	}

	public static int getZ()
	{
		return z;
	}

	public static int getLeft()
	{
		return left;
	}

	public static int getRight()
	{
		return right;
	}

	public static int getUp()
	{
		return up;
	}

	public static int getDown()
	{
		return down;
	}

	public static int getShift()
	{
		return shift;
	}

	public static int getEscape()
	{
		return escape;
	}
}
