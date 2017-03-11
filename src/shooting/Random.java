package shooting;

/**
 * 疑似乱数を生成するクラス.
 *
 * @author negset
 */
public class Random
{
	/**
	 * 乱数を生成する.
	 * 0以上bound未満の整数を返す.
	 *
	 * @param bound 上限(含まない)
	 * @return 乱数
	 */
	public static int nextInt(int bound)
	{
		java.util.Random rnd = new java.util.Random();
		int r = rnd.nextInt(bound);
		return r;
	}
}
