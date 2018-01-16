package jp.co.tdc_next.kns.ctlab.tkrobo.sample;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Calibrater;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class PIDDriver implements Driveable {

	float P_GAIN; // Kp 0.6～0.95(推薦値)
	float I_GAIN; // Ki 0.6～0.7(推薦値)
	float D_GAIN; // Kd 0.3～0.45(推薦値)
	float targetLight; // 目標値
	float diff[] = new float[2]; // 差分
	float integral; // 積分

	private Stopwatch stopwatch;

	private EV3Control ev3Control;
	private Calibrater calibrater;

	public PIDDriver(EV3Control ev3Control, Calibrater calibrater) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.sample]" + "[PIDDriver]" + "[PIDDriver]");


		this.ev3Control = ev3Control;
		this.calibrater = calibrater;

		this.targetLight = 60;
		this.integral = 0;
		this.diff[0] = 0;
		this.diff[1] = 0;

		// this.FREQUENCY = 0.01F; // 周期
		this.P_GAIN = 0.9F; // Kp 0.6～0.95(推薦値)
		this.I_GAIN = 0.0F; // Ki 0.6～0.7(推薦値)
		this.D_GAIN = 0.3F; // Kd 0.3～0.45(推薦値)

		stopwatch = new Stopwatch();
	}

	@Override
	public void drive(int speed, int leftMotorCount, int rightMotorCount) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.sample]" + "[PIDDriver]" + "[drive]");


		int s_r = ev3Control.getRMotorCount();
		int s_l = ev3Control.getLMotorCount();

		do {

			Delay.msDelay(4);

			ev3Control.controlBalance(speed, calcTurnValue(getBrightnessValue()), 0);

		} while (Math.abs(ev3Control.getRMotorCount() - s_r) < leftMotorCount
				&& Math.abs(ev3Control.getLMotorCount() - s_l) < rightMotorCount);

	}

	public float getBrightnessValue() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.sample]" + "[PIDDriver]" + "[getBrightnessValue]");


		float temp = ev3Control.getBrightness();

		LCD.drawString(Float.toString(temp), 0, 1);

		return (((temp - calibrater.blackBaseline()) / (calibrater.whiteBaseline() - calibrater.blackBaseline()))
				* 100.0f);
	}

	/**
	 * 現在の光センサの値により、PID制御を行い、回転量を算出します。
	 *
	 * @param nowLight
	 *            現在の光センサの値
	 * @return PID制御後の回転量
	 */
	private float calcTurnValue(float nowLight) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.sample]" + "[PIDDriver]" + "[calcTurnValue]");


		LCD.drawString(Float.toString(nowLight), 0, 2);
		float tm = stopwatch.elapsed() / 250.0f;

		float P, I, D; // P,I,Dの値
		int turn; // 回転量

		// 前回の差分を更新する。
		diff[0] = diff[1];
		// 今回の差分を算出する。
		diff[1] = nowLight - targetLight;
		// 積分を算出する。
		integral = ((diff[1] + diff[0]) / 2) * tm;// FREQUENCY;

		P = P_GAIN * diff[1]; // 比例
		I = I_GAIN * integral; // 積分
		D = D_GAIN * (diff[1] - diff[0]) / tm;// FREQUENCY; // 微分

		// 回転量を算出する。
		turn = (int) (P + I + D);

		LCD.drawString("P+I+D：" + Float.toString(turn), 0, 5);
		stopwatch.reset();
		// 回転量を返す。
		return MathLimit(turn, 100, -100);

	}

	/**
	 * 制限実施対象の値をInputし、上限値と下限値を超えているかを制限する。 上（下）限値を超えた場合、上（下）限値を返す。
	 * 上（下）限値を超えない場合、制限実施対象の値をそのまま返す。
	 *
	 * @param value
	 *            制限実施対象の値
	 * @param max
	 *            上限値
	 * @param min
	 *            下限値
	 * @return 制限実施後の値を返す。
	 */
	private float MathLimit(float value, float max, float min) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.sample]" + "[PIDDriver]" + "[MathLimit]");

		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		}
		return value;
	}

}
