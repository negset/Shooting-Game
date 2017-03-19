package shooting;

/**
 * 弾幕の生成を行うクラス.
 *
 * @author negset
 */
public class Shot
{
	public static void single(float x, float y, float angle,
			int bType, int bColor, int bMotion, float bSpeed)
	{
		ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, angle);
	}

	/**
	 * nWayショット.
	 * 扇状に複数列の弾を展開する.
	 */
	public static void nWay(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		if (ways < 2)
		{
			single(x, y, angle, bType, bColor, bMotion, bSpeed);
			return;
		}

		float bAngle;
		for (int i = 0; i < ways; i++)
		{
			bAngle = angle - range / 2 + i * range / (ways - 1);
			ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle);
		}
	}

	public static void round(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		float bAngle;
		for (int i = 0; i < ways; i++)
		{
			bAngle = angle + i * 360 / ways;
			ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle);
		}
	}
}
