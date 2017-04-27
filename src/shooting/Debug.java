package shooting;

public class Debug
{
	private static long time;
	private static String name;

	public static void startTimer(String name)
	{
		Debug.name = name;
		time = System.nanoTime();
	}

	public static void stopTimer(String name)
	{
		if (name.equals(Debug.name))
		{
			long t = System.nanoTime() - time;
			System.out.print(name + ": " + String.format("%08d", t) + " ns");
			System.out.println(" (" + String.format("%08f", t / 1000000f) + " ms)");
		}
		else
			System.err.println("タイマーエラー");
	}
}
