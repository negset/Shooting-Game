package shooting;

/**
 * 文字列の描画を行うクラス.
 *
 * @author negset
 */
public class Text
{
	/** 左揃えを表す定数 */
	public static final int LEFT_ALIGN = 0;
	/** 中央揃えを表す定数 */
	public static final int CENTER_ALIGN = 1;
	/** 右揃えを表す定数 */
	public static final int RIGHT_ALIGN = 2;

	/** 描画用フォント */
	private static Font font;
	static
	{
		font = new Font("res/font");
		setDefaultColor();
	}

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
		font.setColor(r, g, b, a);
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
		font.setColor(r, g, b, 1.0f);
	}

	/**
	 * 描画する際の透明度を設定する.
	 *
	 * @param a 透明度
	 */
	public static void setAlpha(float a)
	{
		font.setAlpha(a);
	}

	/**
	 * デフォルトの色を設定する.
	 */
	public static void setDefaultColor()
	{
		setColor(0.9f, 0.9f, 0.9f, 1.0f);
	}

	/**
	 * 文字列を描画する.
	 *
	 * @param str 描画する文字列
	 * @param x 描画する位置のX座標
	 * @param y 描画する位置のY座標
	 */
	public static void drawString(String str, float x, float y)
	{
		font.drawString(str, x, y);
	}

	/**
	 * 文字列を描画する.
	 *
	 * @param str 描画する文字列
	 * @param x 描画する位置のX座標
	 * @param y 描画する位置のY座標
	 * @param align 揃え位置
	 */
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