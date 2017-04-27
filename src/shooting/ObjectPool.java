package shooting;

import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの管理クラス.
 * オブジェクトのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
	/** 最大数の設定 */
	static final int BULLET_MAX = 1000;
	static final int MYBULLET_MAX = 20;
	static final int MYSUBBULLET_MAX = 40;
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

	/** ゲームオブジェクトのインスタンスをあらかじめ生成し,貯めておくための配列 */
	private static Bullet[] bullet;
	private static MyBullet[] myBullet;
	private static MySubBullet[] mySubBullet;
	private static Enemy[] enemy;
	private static Player player;
	private static Explosion[] explosion;
	private static Item[] item;
	private static Damage[] damage;
	private static Graze[] graze;

	private static int nearestEnemyIndex;

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
		myBullet = new MyBullet[MYBULLET_MAX];
		for(int i = 0; i < myBullet.length; i++)
		{
			myBullet[i] = new MyBullet();
		}

		mySubBullet = new MySubBullet[MYSUBBULLET_MAX];
		for(int i = 0; i < mySubBullet.length; i++)
		{
			mySubBullet[i] = new MySubBullet();
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
			item[i] = new Item();
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
		deactivateObjects(myBullet);
		deactivateObjects(mySubBullet);
		deactivateObjects(enemy);
		deactivateObjects(explosion);
		deactivateObjects(item);
		deactivateObjects(damage);
		deactivateObjects(graze);
		player.activate(Play.AREA_CENTER_X, 400);
	}

	/**
	 * ステップごとの更新.
	 */
	public void update()
	{
		if (player.active)
			player.update();
		updateEnemies();
		updateObjects(myBullet);
		updateObjects(mySubBullet);
		updateObjects(bullet);
		updateObjects(explosion);
		updateObjects(item);
		updateObjects(damage);
		updateObjects(graze);
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g)
	{
		renderObjects(item, g);
		if (player.active)
			player.render(g);
		renderObjects(enemy, g);
		renderObjects(myBullet, g);
		renderObjects(mySubBullet, g);
		renderObjects(bullet, g);
		renderObjects(explosion, g);
		renderObjects(damage, g);
		renderObjects(graze, g);
	}

	/**
	 * 配列内のすべてのインスタンスを無効にする.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void deactivateObjects(GameObject[] object)
	{
		for (GameObject obj : object)
		{
			obj.active = false;
		}
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを更新する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void updateObjects(GameObject[] object)
	{
		for (GameObject obj: object)
		{
			if (obj.active)
			{
				obj.update();
			}
		}
	}

	/**
	 * 敵機の配列内のインスタンスのうち,有効な物のみを更新する.
	 * 同時に,自機に最も近いものの距離とインデックスを調べる.
	 *
	 * @param enemy
	 */
	private void updateEnemies()
	{
		nearestEnemyIndex = -1;
		double dist = -1;
		for (int i = 0; i < enemy.length; i++)
		{
			if (enemy[i].active)
			{
				double d = getDistance(player, enemy[i]);
				if (d < dist || dist == -1)
				{
					dist = d;
					nearestEnemyIndex = i;
				}
				enemy[i].update();
			}
		}
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを描画する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void renderObjects(GameObject[] object, Graphics g)
	{
		for (GameObject obj : object)
		{
			if (obj.active)
			{
				obj.render(g);
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
			int score, int item, Shot shot)
	{
		for (int i = 0; i < enemy.length; i++)
		{
			if (!enemy[i].active)
			{
				enemy[i].activate(x, y, type, hp, motion, score, item, shot);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newMyBullet(float x, float y)
	{
		for (int i = 0; i < myBullet.length; i++)
		{
			if (!myBullet[i].active)
			{
				myBullet[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	public static int newMySubBullet(float x, float y, float angle)
	{
		for (int i = 0; i < mySubBullet.length; i++)
		{
			if (!mySubBullet[i].active)
			{
				mySubBullet[i].activate(x, y, angle);
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

	public static boolean hasActivePlayer()
	{
		return player.active;
	}

	public static boolean hasActiveEnemy()
	{
		if (nearestEnemyIndex == -1)
			return false;
		return true;
	}

	public static float getNearestEnemyX()
	{
		if (nearestEnemyIndex == -1)
			return -1;
		return enemy[nearestEnemyIndex].x;
	}

	public static float getNearestEnemyY()
	{
		if (nearestEnemyIndex == -1)
			return -1;
		return enemy[nearestEnemyIndex].y;
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
		double distX = Math.abs(o1.x - o2.x);
		double distY = Math.abs(o1.y - o2.y);
		return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
	}

	/**
	 * 衝突判定
	 */
	public void collisionDetection()
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
							if (Playdata.isGameOver())
							{
								newExplosion(player.x, player.y);
								player.active = false;
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
				for (int j = 0; j < myBullet.length; j++)
				{
					if (myBullet[j].active)
					{
						// あたり判定
						if (getDistance(enemy[i], myBullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							// 敵の体力を減らす.
							enemy[i].hit();
							// ダメージエフェクト
							newDamage(myBullet[j].x, myBullet[j].y);
							// 弾消滅
							myBullet[j].active = false;
						}
					}
				}

				for (int j = 0; j < mySubBullet.length; j++)
				{
					if (mySubBullet[j].active)
					{
						// あたり判定
						if (getDistance(enemy[i], mySubBullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							// 敵の体力を減らす.
							enemy[i].hit();
							// ダメージエフェクト
							newDamage(mySubBullet[j].x, mySubBullet[j].y);
							// 弾消滅
							mySubBullet[j].active = false;
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
						if (Playdata.isGameOver())
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