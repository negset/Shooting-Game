package shooting;

/**
 * 疑似乱数を生成するクラス.
 *
 * @author negset
 */
public class Random
{
	public static final long SEED = 18589;
	private static java.util.Random rnd;

	public static void init()
	{
		rnd = new java.util.Random(SEED);
	}

	/**
	 * 乱数を生成する.
	 * 0以上bound未満の整数を返す.
	 *
	 * @param bound 上限(含まない)
	 * @return 乱数
	 */
	public static int nextInt(int bound)
	{
		return rnd.nextInt(bound);
	}
}
