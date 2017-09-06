package jp.co.tdc_next.kns.ctlab.tkrobo.device;

/**
 * EV3の制御と各種センサー値の取得を取りまとめたインターフェース
 *
 * @author Higashizono Syuhei
 *
 */
public interface EV3Control {

	/**
	 * 走行制御
	 *
	 * @param forward
	 *            前進・後進
	 * @param turn
	 *            旋回
	 * @param tail
	 *            尻尾の角度
	 */
	public void controlBalance(float forward, float turn, int tail);

	/**
	 * 左右のモーターを直接制御します。
	 *
	 * @param left
	 *            左モーターパワー
	 * @param right
	 *            右モーターパワー
	 * @param tail
	 *            尻尾の角度
	 */
	public void controlDirect(int left, int right, int tail);

	/**
	 * タッチセンサー押下のチェック。
	 *
	 * @return true ならタッチセンサーが押された。
	 */
	public boolean touchSensorIsPressed();

	/**
	 * 超音波センサーにより障害物との距離を取得する。
	 *
	 * @return 障害物との距離(m)。
	 */
	public float getSonarDistance();

	/**
	 * カラーセンサーから輝度値を取得する。
	 *
	 * @return 輝度値。
	 */
	public float getBrightness();

	/**
	 * ジャイロセンサーから角速度を取得する。
	 *
	 * @return 角速度。
	 */
	public float getGyroValue();

	/**
	 * 左モーターの回転数を取得する。
	 * @return 回転数
	 */
	public int getLMotorCount();

	/**
	 * 右モーターの回転数を取得する。
	 * @return 回転数
	 */
	public int getRMotorCount();
	
	public int getTailAngle();
}
