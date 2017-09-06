package jp.co.tdc_next.kns.ctlab.tkrobo.device;

public interface DrivingWheel {


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


	/**
	 * 左右のモーターのパワーを設定します。
	 * @param leftMotorPower 左モーターパワー
	 * @param rightMotorPower 右モーターパワー
	 */
	public void setMotorPower(int leftMotorPower, int rightMotorPower);
}
