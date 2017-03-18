package shooting;

/**
 * 弾幕の生成を行うクラス.
 *
 * @author negset
 */
public class Barrage
{
	/** 狙い先 */
	private static Player player;

	/** 狙い先を設定する */
	public static void setPlayer(Player player)
	{
		Barrage.player = player;
	}

	/**
	 * nWayショット
	 * 扇型に複数列の弾を展開する.
	 *
	 * @param x 開始X座標
	 * @param y 開始Y座標
	 * @param angle 扇の向き
	 * @param speed 弾の速さ
	 * @param motion 弾の動き
	 * @param type 弾の種類
	 * @param n 列数
	 * @param degree 扇の角度
	 */
	public static void nWayShot(float x, float y, float angle, float speed,
			int motion, int type, int color, int n, float range)
	{
		float bAngle;
		for (int i = 0; i < n; i++)
		{
			bAngle = angle - range / 2 + i * range / (n - 1);
			ObjectPool.newBullet(x, y, bAngle, speed, motion, type, color);
		}
	}

	/**
	 * 自機狙いのnWayショット
	 *
	 * @param x 開始X座標
	 * @param y 開始Y座標
	 * @param speed 弾の速さ
	 * @param motion 弾の動き
	 * @param type 弾の種類
	 * @param n 列数
	 * @param degree 扇の角度
	 */
	public static void nWayAimShot(float x, float y, float speed,
			int motion, int type, int color, int n, float range)
	{
		float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		nWayShot(x, y, angle, speed, motion, type, color, n, range);
	}
}
