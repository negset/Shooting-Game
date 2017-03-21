package shooting;

import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの管理クラス.
 * オブジェクトのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
	/** 敵機の弾 */
	private static Bullet[] bullet;
	/** 自機の弾 */
	private static MyBullet[] mybullet;
	/** 敵機 */
	private static Enemy[] enemy;
	/** 自機 */
	private static Player player;
	/** 爆発エフェクト */
	private static Explosion[] explosion;
	/** アイテム */
	private static Item[] item;
	/** ダメージエフェクト */
	private static Damage[] damage;
	/** グレイズエフェクト */
	private static Graze[] graze;

	/** 最大数の設定 */
	static final int BULLET_MAX = 1000;
	static final int MYBULLET_MAX = 20;
	static final int ENEMY_MAX = 50;
	static final int EXPLOSION_MAX = 50;
	static final int ITEM_MAX = 50;
	static final int DAMAGE_MAX = 20;
	static final int GRAZE_MAX = 10;

	/** 衝突判定に用いる距離定数 */
	static final int DIST_PLAYER_TO_BULLET = 10;
	static final int DIST_GRAZE = 30;
	static final int DIST_ENEMY_TO_MYBULLET = 16;
	static final int DIST_PLAYER_TO_ENEMY = 20;

	/**
	 * コンストラクタ
	 */
	ObjectPool()
	{
		// 自機のインスタンスを作る.
		player = new Player();
		player.activate(Play.AREA_CENTER_X, 400);

		// 敵機の弾の配列を確保し,配列の要素分インスタンスを作る.
		bullet = new Bullet[BULLET_MAX];
		for(int i = 0; i < bullet.length; i++)
		{
			bullet[i] = new Bullet();
		}

		// 自機の弾の配列を確保し,配列の要素分インスタンスを作る.
		mybullet = new MyBullet[MYBULLET_MAX];
		for(int i = 0; i < mybullet.length; i++)
		{
			mybullet[i] = new MyBullet();
		}

		// 敵機の配列を確保し,配列の要素分インスタンスを作る.
		enemy = new Enemy[ENEMY_MAX];
		for(int i = 0; i < enemy.length; i++)
		{
			enemy[i] = new Enemy();
		}

		// 爆発エフェクトの配列を確保し,配列の要素分インスタンスを作る.
		explosion = new Explosion[EXPLOSION_MAX];
		for (int i = 0; i < explosion.length; i++)
		{
			explosion[i] = new Explosion();
		}

		// アイテムの配列を確保し,配列の要素分インスタンスを作る.
		item = new Item[ITEM_MAX];
		for (int i = 0; i < item.length; i++)
		{
			item[i] = new Item(player);
		}

		// ダメージエフェクトの配列を確保し,配列の要素分インスタンスを作る.
		damage = new Damage[DAMAGE_MAX];
		for (int i = 0; i < damage.length; i++)
		{
			damage[i] = new Damage();
		}

		// グレイズエフェクトの配列を確保し,配列の要素分インスタンスを作る.
		graze = new Graze[GRAZE_MAX];
		for (int i = 0; i < graze.length; i++)
		{
			graze[i] = new Graze();
		}
	}

	/**
	 * 初期化処理.
	 */
	public void init()
	{
		// 全てのインスタンスを無効にし,自機のみ有効にする.
		deactivateObjects(bullet);
		deactivateObjects(mybullet);
		deactivateObjects(enemy);
		deactivateObjects(explosion);
		deactivateObjects(item);
		deactivateObjects(damage);
		deactivateObjects(graze);
		player.activate(Play.AREA_CENTER_X, 400);
	}

	/**
	 * 配列内のすべてのインスタンスを無効にする.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void deactivateObjects(GameObject[] object)
	{
		for (int i = 0; i < object.length; i++)
		{
			object[i].active = false;
		}
	}

	/**
	 * 全てのオブジェクトのステップごとの更新を行う.
	 */
	public void updateAllObjects()
	{
		if (player.active)
		{
			player.update();
		}
		updateObjects(enemy);
		updateObjects(mybullet);
		updateObjects(bullet);
		updateObjects(explosion);
		updateObjects(item);
		updateObjects(damage);
		updateObjects(graze);
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを更新する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	public void updateObjects(GameObject[] object)
	{
		for (int i = 0; i < object.length; i++)
		{
			if (object[i].active)
			{
				object[i].update();
			}
		}
	}

	/**
	 * 全てのオブジェクトの描画処理を行う.
	 */
	public void renderAllObjects(Graphics g)
	{
		renderObjects(item, g);
		if (player.active)
		{
			player.render(g);
		}
		renderObjects(enemy, g);
		renderObjects(mybullet, g);
		renderObjects(bullet, g);
		renderObjects(explosion, g);
		renderObjects(damage, g);
		renderObjects(graze, g);
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを描画する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void renderObjects(GameObject[] object, Graphics g)
	{
		for (int i = 0; i < object.length; i++)
		{
			if (object[i].active)
			{
				object[i].render(g);
			}
		}
	}

	public static int newBullet(float x, float y, int type, int color,
			int motion, float speed, float angle)
	{
		for (int i = 0; i < bullet.length; i++)
		{
			if (!bullet[i].active)
			{
				bullet[i].activate(x, y, type, color, motion, speed, angle);
				return i;
			}
		}
		return -1;		// 見つからなかった
	}

	public static int newEnemy(float x, float y, int type, int hp, int motion,
			int score, int item, int sType, int sTimes, int sInterval,
			int sAimType, float sAngle1, float sAngle2, int sRange, int sWays,
			int bType, int bColor, int bMotion, float bSpeed1, float bSpeed2)
	{
		for (int i = 0; i < enemy.length; i++)
		{
			if (!enemy[i].active)
			{
				enemy[i].activate(x, y, type, hp, motion, score, item, sType,
						sTimes, sInterval, sAimType, sAngle1, sAngle2, sRange, sWays,
						bType, bColor, bMotion, bSpeed1, bSpeed2);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newMyBullet(float x, float y)
	{
		for (int i = 0; i < mybullet.length; i++)
		{
			if (!mybullet[i].active)
			{
				mybullet[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newExplosion(float x, float y)
	{
		for (int i = 0; i < explosion.length; i++)
		{
			if (!explosion[i].active)
			{
				explosion[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newItem(float x, float y, int type)
	{
		for (int i = 0; i < item.length; i++)
		{
			if (!item[i].active)
			{
				item[i].activate(x, y, type);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newDamage(float x, float y)
	{
		for (int i = 0; i < damage.length; i++)
		{
			if (!damage[i].active)
			{
				damage[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newGraze(float x, float y)
	{
		for (int i = 0; i < graze.length; i++)
		{
			if (!graze[i].active)
			{
				graze[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static float getPlayerX()
	{
		return player.x;
	}

	public static float getPlayerY()
	{
		return player.y;
	}

	/**
	 * オブジェクト間の距離を返す.
	 *
	 * @param o1 ゲームオブジェクト
	 * @param o2 比較先ゲームオブジェクト
	 * @return 距離
	 */
	private double getDistance(GameObject o1, GameObject o2)
	{
		// 三平方の定理
		double distX = Math.abs(o1.x - o2.x);
		double distY = Math.abs(o1.y - o2.y);
		return Math.sqrt(Math.pow(distX,2) + Math.pow(distY,2));
	}

	/**
	 * 衝突判定
	 */
	public void getColision()
	{
		// 敵機の弾と自機の衝突
		if (player.active)
		{
			for (int i = 0; i < bullet.length; i++)
			{
				if (bullet[i].active)
				{
					double dist = getDistance(player, bullet[i]);
					// グレイズ判定
					if (!bullet[i].isGrazed && dist < DIST_GRAZE)
					{
						Playdata.addGraze();
						newGraze(player.x, player.y);
						bullet[i].isGrazed = true;
					}
					// あたり判定
					else if (dist < DIST_PLAYER_TO_BULLET)
					{
						if (!player.isInvincible)
						{
							// 残機を減らす.
							Playdata.addLife(-1);
							if (Playdata.isGameover)
							{
								newExplosion(player.x, player.y);
								player.active = false;

								// スコア保存
								Playdata.saveScore();
							}
							player.isInvincible = true;
						}
						// 弾消滅
						bullet[i].active = false;
					}
				}
			}
		}

		// 自機の弾と敵機の衝突
		for (int i = 0; i < enemy.length; i++)
		{
			if (enemy[i].active)
			{
				for (int j = 0; j < mybullet.length; j++)
				{
					if (mybullet[j].active)
					{
						// あたり判定
						if (getDistance(enemy[i], mybullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							// 敵の体力を減らす.
							enemy[i].hit();
							// ダメージエフェクト
							newDamage(mybullet[j].x, mybullet[j].y);
							// 弾消滅
							mybullet[j].active = false;
						}
					}
				}
			}
		}

		// 敵機と自機の衝突
		if (player.active && !player.isInvincible)
		{
			for (int i = 0; i < enemy.length; i++)
			{
				if (enemy[i].active)
				{
					if (getDistance(player, enemy[i]) < DIST_PLAYER_TO_ENEMY)
					{
						// 残機を減らす.
						Playdata.addLife(-1);
						player.isInvincible = true;
						if (Playdata.isGameover)
						{
							newExplosion(player.x, player.y);
							player.active = false;
						}
					}
				}
			}
		}
	}
}