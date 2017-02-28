package shooting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.newdawn.slick.Graphics;

public class Playdata
{
	static int score;
	static int hiscore;
	static int life;
	static int bomb;
	static int power;
	static int graze;
	static boolean isFullpower;
	static boolean isGameover;

	public void init()
	{
		score = 0;
		loadScore();
		life = 2;
		bomb = 3;
		power = 0;
		graze = 0;
		isFullpower = false;
		isGameover = false;
	}

	public void render(Graphics g)
	{
		Text.drawString("最高得点", 545, 80);
		Text.drawString(String.format("%09d", hiscore), 650, 80);
		Text.drawString("得点", 545, 110);
		Text.drawString(String.format("%09d", score), 650, 110);
		Text.drawString("残機", 545, 160);
		String s = "";
		for (int i = 0; i < life; i++)
			s += "★";
		Text.drawString(s, 650, 160);
		Text.drawString("ボム", 545, 190);
		s = "";
		for (int i = 0; i < bomb; i++)
			s += "★";
		Text.drawString(s, 650, 190);
		Text.drawString("パワー", 545, 250);
		Text.drawString(power + " / 128", 650, 250);
		Text.drawString("グレイズ", 545, 280);
		Text.drawString(String.valueOf(graze), 650, 280);
	}

	public static void addScore(int gain)
	{
		score += gain;
		if (score > hiscore)
		{
			hiscore = score;
		}
	}

	public static void addLife(int gain)
	{
		life += gain;
		if (life < 0)
		{
			isGameover = true;
		}
	}

	public static void addBomb(int gain)
	{
		bomb += gain;
	}

	public static void addPower(int gain)
	{
		if (!isFullpower)
		{
			power += gain;
			if (power >= 128)
			{
				power = 128;
				isFullpower = true;
			}
		}
	}

	public static void addGraze()
	{
		graze++;
	}

	static void saveScore()
	{
		DataOutputStream dout;
		try
		{
			dout = new DataOutputStream(new FileOutputStream("data.dat"));
			dout.writeInt(hiscore);
			dout.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static void loadScore()
	{
		DataInputStream din;
		try
		{
			din = new DataInputStream(new FileInputStream("data.dat"));
			hiscore = din.readInt();
			din.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}