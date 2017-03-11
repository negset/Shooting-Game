package shooting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.newdawn.slick.Graphics;

/**
 * プレイデータの管理や描画を行うクラス.
 *
 * @author negset
 */
public class Playdata
{
	/** 得点 */
	static int score;
	/** 最高得点 */
	static int hiscore;
	/** 残機 */
	static int life;
	/** 残りボム */
	static int bomb;
	/** パワー */
	static int power;
	/** グレイズ回数 */
	static int graze;
	/** ゲームオーバーフラグ */
	static boolean isGameover;

	/**
	 * 初期化処理.
	 */
	public void init()
	{
		score = 0;
		loadScore();
		life = 2;
		bomb = 3;
		power = 0;
		graze = 0;
		isGameover = false;
	}

	/**
	 * ステップごとの描画処理.
	 *
	 * @param g 描画先
	 */
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

	/**
	 * 得点を増やす.
	 *
	 * @param gain 増やす値
	 */
	public static void addScore(int gain)
	{
		score += gain;
		if (score > hiscore)
		{
			hiscore = score;
		}
	}

	/**
	 * 残機を増やす.
	 *
	 * @param gain 増やす値
	 */
	public static void addLife(int gain)
	{
		life += gain;
		if (life < 0)
		{
			isGameover = true;
		}
	}

	/**
	 * 残りボムを増やす.
	 *
	 * @param gain 増やす値
	 */
	public static void addBomb(int gain)
	{
		bomb += gain;
	}

	/**
	 * パワーを増やす.
	 *
	 * @param gain 増やす値
	 */
	public static void addPower(int gain)
	{
		if (power < 128)
		{
			power += gain;
			if (power >= 128)
			{
				power = 128;
			}
		}
	}

	/**
	 * グレイズ回数を増やす.
	 */
	public static void addGraze()
	{
		graze++;
	}

	/**
	 * 得点を保存する.
	 */
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

	/**
	 * 得点を読み込む.
	 */
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