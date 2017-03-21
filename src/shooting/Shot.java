package shooting;

/**
 * ショットクラス.
 *
 * @author negset
 */
public class Shot
{
	private int type;
	private int times;
	private int interval;
	private int aimType;
	private float angle1;
	private float angle2;
	private int range;
	private int ways;
	private int bType;
	private int bColor;
	private int bMotion;
	private float bSpeed1;
	private float bSpeed2;

	private int counter;
	private int nextShoot;
	private int shootCnt;
	boolean isEnd;

	Shot(int type, int times, int interval, int aimType,
			float angle1, float angle2, int range, int ways,
			int bType, int bColor, int bMotion, float bSpeed1, float bSpeed2)
	{
		this.type = type;
		this.times = times;
		this.interval = interval;
		this.aimType = aimType;
		this.angle1 = angle1;
		this.angle2 = angle2;
		this.range = range;
		this.ways = ways;
		this.bType = bType;
		this.bColor = bColor;
		this.bMotion = bMotion;
		this.bSpeed1 = bSpeed1;
		this.bSpeed2 = bSpeed2;

		counter = 0;
		nextShoot = 0;
		shootCnt = 0;
	}

	public void fire(float x, float y)
	{
		if (++counter > nextShoot)
		{
			if (aimType == 2 || (shootCnt == 0 && aimType == 1))
			{
				angle1 = (float) getAngleToPlayer(x, y);
			}

			switch (type)
			{
				case 0:
					single(x, y, angle1, bType, bColor, bMotion, bSpeed1);
					break;

				case 1:
					nWay(x, y, angle1, range, ways, bType, bColor, bMotion, bSpeed1);
					break;

				case 2:
					round(x, y, angle1, ways, bType, bColor, bMotion, bSpeed1);
					break;

				case 3:
					starRound(x, y, angle1, range, ways, bType, bColor, bMotion, bSpeed1);
					break;

				case 4:
					massedRound(x, y, angle1, range, ways, bType, bColor, bMotion, bSpeed1);
					break;
				default:
			}

			if (++shootCnt == times)
			{
				isEnd = true;
				return;
			}

			angle1 += angle2;
			bSpeed1 += bSpeed2;

			nextShoot += interval;
		}
	}

	private double getAngleToPlayer(float x, float y)
	{
		float px = ObjectPool.getPlayerX();
		float py = ObjectPool.getPlayerY();
		return Math.toDegrees(Math.atan2(py - y, px - x));
	}

	private void single(float x, float y, float angle,
			int bType, int bColor, int bMotion, float bSpeed)
	{
		ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, angle);
	}

	private void nWay(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		float bAngle;
		for (int i = 0; i < ways; i++)
		{
			bAngle = angle - (float) range / 2 + (float) i * range / (ways - 1);
			ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle);
		}
	}

	private void round(float x, float y, float angle,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		float bAngle;
		for (int i = 0; i < ways; i++)
		{
			bAngle = angle + (float) i * 360 / ways;
			ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle);
		}
	}

	private void starRound(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		if (shootCnt == 0)
			round(x, y, angle, ways, bType, bColor, bMotion, bSpeed);
		else
		{
			float bAngle1, bAngle2;
			for (int i = 0; i < ways; i++)
			{
				bAngle1 = angle + (float) i * 360 / ways - (float) range / 2 * shootCnt / times;
				bAngle2 = angle + (float) i * 360 / ways + (float) range / 2 * shootCnt / times;
				ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle1);
				ObjectPool.newBullet(x, y, bType, bColor, bMotion, bSpeed, bAngle2);
			}
		}
	}

	private void massedRound(float x, float y, float angle, int range,
			int ways, int bType, int bColor, int bMotion, float bSpeed)
	{
		for (int i = 0; i < range; i += 5)
		{
			round(x, y, angle + i, ways, bType, bColor, bMotion, bSpeed);
		}
	}
}