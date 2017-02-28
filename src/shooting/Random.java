package shooting;

public class Random
{
	public static int nextInt(int i)
	{
		java.util.Random rnd = new java.util.Random();
		int r = rnd.nextInt(i);
		return r;
	}
}
