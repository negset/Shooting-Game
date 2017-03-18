package shooting;

/**
 * 弾幕の生成を行うクラス.
 *
 * @author negset
 */
public class Shot
{
	public static void shoot(float x, float y,int type, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		switch (type)
		{
			case 0:
				nWay(x, y, angle, range, ways, bType, bColor, bMotion, bSpeed);
				break;

			default:
		}
	}

	/**
	 * nWayショット
	 * 扇型に複数列の弾を展開する.
	 */
	private static void nWay(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		float bAngle;
		for (int i = 0; i < ways; i++)
		{
			bAngle = angle - range / 2 + i * range / (ways - 1);
			ObjectPool.newBullet(x, y, bAngle, bSpeed, bMotion, bType, bColor);
		}
	}
}
