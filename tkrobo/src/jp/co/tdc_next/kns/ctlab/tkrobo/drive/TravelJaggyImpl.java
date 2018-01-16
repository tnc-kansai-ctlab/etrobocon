package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Calibrater;

public class TravelJaggyImpl implements Travel {

	EV3 ev3 = EV3.getInstance();
	private static final float LIGHT_WHITE = 0.50F; // 白色のカラーセンサー輝度値
	private static final float LIGHT_BLACK = 0.02F; // 黒色のカラーセンサー輝度値
	// private static final float THRESHOLD = (LIGHT_WHITE+LIGHT_BLACK)/2.0F; //
	// ライントレースの閾値

	private float THRESHOLD;

	public TravelJaggyImpl(Calibrater calibrater) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[TravelJaggyImpl]" + "[TravelJaggyImpl]");


		this.THRESHOLD = (calibrater.blackBaseline() + calibrater.whiteBaseline()) / 2.0F;

	}

	public void travel(WheelSpeed speed) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[TravelJaggyImpl]" + "[travel]");

		float forward = speed.getWheelSpeedScaleLeft();
		float turn = jaggyTravel();
		int tail = 0;
		ev3.controlBalance(forward, turn, tail);
	}

	/**
	 * ジグザグ走行制御
	 */
	public float jaggyTravel() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[TravelJaggyImpl]" + "[jaggyTravel]");

		if (ev3.getBrightness() > THRESHOLD) {
			return 50.0F; // 右旋回命令
		} else {
			return -50.0F; // 左旋回命令
		}
	}

}
