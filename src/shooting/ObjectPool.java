package shooting;

/**
 * ゲームオブジェクトの管理クラス.
 * プレイヤーや弾,敵などのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
	private static Bullet[] bullet;
	private static MyBullet[] mybullet;
	private static Enemy[] enemy;
	private static Player[] player;
	private static Explosion[] explosion;
	private static Item[] item;
	private static Damage[] damage;
	private static Graze[] graze;

	// 衝突判定に用いる距離定数
	static final int DIST_PLAYER_TO_BULLET = 10;
	static final int DIST_GRAZE = 30;
	static final int DIST_ENEMY_TO_MYBULLET = 16;
	static final int DIST_PLAYER_TO_ENEMY = 20;

	// 最大数の設定
	static final int BULLET_MAX = 100;
	static final int MYBULLET_MAX = 20;
	static final int ENEMY_MAX = 50;
	static final int EXPLOSION_MAX = 50;
	static final int ITEM_MAX = 50;
	static final int DAMAGE_MAX = 20;
	static final int GRAZE_MAX = 10;

	/**
	 * コンストラクタ
	 */
	ObjectPool()
	{
		// プレイヤーを作る
		player = new Player[1];
		player[0] = new Player();
		player[0].activate(Play.AREA_XCENTER, 400);

		// 弾の配列を確保し,配列の要素分インスタンスを作る
		bullet = new Bullet[BULLET_MAX];
		for(int i = 0; i < bullet.length; i++)
		{
			bullet[i] = new Bullet();
		}

		// プレイヤーの弾の配列を確保し,配列の要素分インスタンスを作る
		mybullet = new MyBullet[MYBULLET_MAX];
		for(int i = 0; i < mybullet.length; i++)
		{
			mybullet[i] = new MyBullet();
		}

		// 敵の配列を確保し,配列の要素分インスタンスを作る
		enemy = new Enemy[ENEMY_MAX];
		for(int i = 0; i < enemy.length; i++)
		{
			enemy[i] = new Enemy();
		}

		explosion = new Explosion[EXPLOSION_MAX];
		for (int i = 0; i < explosion.length; i++)
		{
			explosion[i] = new Explosion();
		}

		item = new Item[ITEM_MAX];
		for (int i = 0; i < item.length; i++)
		{
			item[i] = new Item(player[0]);
		}

		damage = new Damage[DAMAGE_MAX];
		for (int i = 0; i < damage.length; i++)
		{
			damage[i] = new Damage();
		}

		graze = new Graze[GRAZE_MAX];
		for (int i = 0; i < graze.length; i++)
		{
			graze[i] = new Graze();
		}
	}

	public void init()
	{
		initObjects(bullet);
		initObjects(mybullet);
		initObjects(enemy);
		initObjects(explosion);
		initObjects(item);
		initObjects(damage);
		initObjects(graze);

		player[0].activate(Play.AREA_XCENTER, 400);
		Barrage.setPlayer(player[0]);
	}

	private void initObjects(GameObject[] object)
	{
		for (int i = 0; i < object.length; i++)
		{
			if (object[i].active)
			{
				object[i].active = false;
			}
		}
	}

	public void updateAllObjects()
	{
		updateObjects(player);
		updateObjects(enemy);
		updateObjects(mybullet);
		updateObjects(bullet);
		updateObjects(explosion);
		updateObjects(item);
		updateObjects(damage);
		updateObjects(graze);
	}

	public void updateObjects(GameObject[] object)
	{
		for (int i = 0; i < object.length; i++)
		{
			if (object[i].active)
			{
				object[i].update();
				object[i].render();
			}
		}
	}

	public void renderAllObjects()
	{
		renderObjects(player);
		renderObjects(enemy);
		renderObjects(mybullet);
		renderObjects(bullet);
		renderObjects(explosion);
		renderObjects(item);
		renderObjects(damage);
		renderObjects(graze);
	}

	private void renderObjects(GameObject[] object)
	{
		for (int i = 0; i < object.length; i++)
		{
			if (object[i].active)
			{
				object[i].render();
			}
		}
	}

	/**
	 * 弾の生成・初期化（実際は配列のインスタンスを使い回す）
	 */
	public static int newBullet(float x, float y, float angle, float speed, int motion, int type, int color)
	{
		for (int i = 0; i < bullet.length; i++)
		{
			if (!bullet[i].active)
			{
				bullet[i].activate(x, y, angle, speed, motion, type, color);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	/**
	 * 敵の生成・初期化（実際は配列のインスタンスを使い回す）
	 * @param x 生成先x座標
	 * @param y 生成先y座標
	 * @return 敵のID（空きが無ければ-1）
	 */
	public static int newEnemy(float x, float y)
	{
		for (int i = 0; i < enemy.length; i++)
		{
			if ((enemy[i].active) == false)
			{
				enemy[i].activate(x, y);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	/**
	 * プレイヤー弾の生成・初期化（実際は配列のインスタンスを使い回す）
	 * @param x 生成先x座標
	 * @param y 生成先y座標
	 * @return プレイヤー弾のID（空きが無ければ-1）
	 */
	public static int newMyBullet(float x, float y)
	{
		for (int i = 0; i < mybullet.length; i++)
		{
			if ((mybullet[i].active) == false)
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

	public static int newItem(float x, float y)
	{
		for (int i = 0; i < item.length; i++)
		{
			if (!item[i].active)
			{
				item[i].activate(x, y, Random.nextInt(6));
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

	/**
	 * 2点間の距離を返す.
	 * @param o1 ゲームオブジェクト
	 * @param o2 比較先ゲームオブジェクト
	 * @return 距離
	 */
	public double getDistance(GameObject o1, GameObject o2)
	{
		// 三平方の定理
		double Xdiff = Math.abs(o1.x - o2.x);
		double Ydiff = Math.abs(o1.y - o2.y);
		return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
	}

	/**
	 * 衝突判定
	 */
	public void getColision()
	{
		// 敵の弾とプレイヤーの衝突
		if (player[0].active && !player[0].invincible)
		{
			for (int i = 0; i < bullet.length; i++)
			{
				if (bullet[i].active)
				{
					double dist = getDistance(player[0], bullet[i]);
					// グレイズ判定
					if (!bullet[i].isGrazed && dist < DIST_GRAZE)
					{
						Playdata.addGraze();
						newGraze(player[0].x, player[0].y);
						bullet[i].isGrazed = true;
					}
					// あたり判定
					else if (dist < DIST_PLAYER_TO_BULLET)
					{
						// 残機を減らす
						Playdata.addLife(-1);
						player[0].invincible = true;
						if (Playdata.isGameover)
						{
							newExplosion(player[0].x, player[0].y);
							player[0].active = false;

							// スコア保存
							Playdata.saveScore();
						}

						// 弾消滅
						bullet[i].active = false;
					}
				}
			}
		}

		//プレイヤーの弾と敵の衝突
		for (int i = 0; i < enemy.length; i++)
		{
			if (enemy[i].active)
			{
				for (int j = 0; j < mybullet.length; j++)
				{
					if (mybullet[j].active)
					{
						//あたり判定
						if (getDistance(enemy[i], mybullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							//敵の体力を減らす
							enemy[i].hit();
							//ダメージエフェクト
							newDamage(mybullet[j].x, mybullet[j].y);
							//弾消滅
							mybullet[j].active = false;
						}
					}
				}
			}
		}

		// 敵とプレイヤーの衝突
		if (player[0].active && !player[0].invincible)
		{
			for (int i = 0; i < enemy.length; i++)
			{
				if (enemy[i].active)
				{
					if (getDistance(player[0], enemy[i]) < DIST_PLAYER_TO_ENEMY)
					{
						// 残機を減らす
						Playdata.addLife(-1);
						player[0].invincible = true;
						if (Playdata.isGameover)
						{
							newExplosion(player[0].x, player[0].y);
							player[0].active = false;
						}
					}
				}
			}
		}
	}
}