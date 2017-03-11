package shooting;

/**
 * 文字列の描画を行うクラス.
 *
 * @author negset
 */
public class Text
{
	/** 揃え位置を表す定数 */
	public static final int LEFT_ALIGN = 0;
	public static final int CENTER_ALIGN = 1;
	public static final int RIGHT_ALIGN = 2;

	/** 描画用フォント */
	private static Font font;
	static
	{
		font = new Font("res/font");
		setDefaultColor();
	}
	/** 描画時の文字色 */
	private static float r, g, b, a;

	/**
	 * 描画する際の色を設定する.
	 *
	 * @param r 赤
	 * @param g 緑
	 * @param b 青
	 * @param a 透明度
	 */
	public static void setColor(float r, float g, float b, float a)
	{
		Text.r = r;
		Text.g = g;
		Text.b = b;
		Text.a = a;
	}

	/**
	 * 描画する際の色を設定する.
	 *
	 * @param r 赤
	 * @param g 緑
	 * @param b 青
	 */
	public static void setColor(float r, float g, float b)
	{
		setColor(r, g, b, 1.0f);
	}

	/**
	 * 描画する際の透明度を設定する.
	 *
	 * @param a 透明度
	 */
	public static void setAlpha(float a)
	{
		Text.a = a;
	}

	/**
	 * デフォルトの色を設定する.
	 */
	public static void setDefaultColor()
	{
		setColor(0.9f, 0.9f, 0.9f);
	}

	public static void drawString(String str, float x, float y)
	{
		font.setColor(r, g, b, a);
		font.drawString(str, x, y);
	}

	public static void drawString(String str, float x, float y, int align)
	{
		switch (align)
		{
			case LEFT_ALIGN:
				drawString(str, x, y);
				break;

			case CENTER_ALIGN:
				drawString(str, x - font.getWidth(str) / 2, y);
				break;

			case RIGHT_ALIGN:
				drawString(str, x - font.getWidth(str), y);
				break;
		}
	}
}