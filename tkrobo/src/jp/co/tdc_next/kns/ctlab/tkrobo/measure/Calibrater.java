package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


/**
 * キャリブレーションのクラス<br>
 * 各基準値を取得する前に「calibration」を実行してください。
 * @author higashizono
 *
 */
public class Calibrater {

	private EV3Control ev3Control;
	private Button button;

	private float blackBaseline;
	private float whiteBaseline;

	private boolean resetFlag;

	public Calibrater(EV3Control ev3Control, Button button) {

		this.ev3Control = ev3Control;
		this.button = button;
	}

	public boolean calibration() {

		resetFlag  = false;

		LCD.clear();

		LCD.drawString("Get Black...  ", 0, 4);

		blackBaseline = getBrightnessForTouchWait();
		LCD.drawString("Black:" + blackBaseline, 0, 5);


		LCD.drawString("Get White...  ", 0, 4);


		whiteBaseline = getBrightnessForTouchWait();
		LCD.drawString("White:" + whiteBaseline, 0, 6);

		LCD.drawString("Reset?", 0, 7);
		while (button.touchStatus() != TouchStatus.Released) {
			resetFlag = lejos.hardware.Button.UP.isDown();
			Delay.msDelay(10);
		}


		return resetFlag;

	}

	private float getBrightnessForTouchWait() {

		while (button.touchStatus() != TouchStatus.Released) {
			Delay.msDelay(10);
		}
		return ev3Control.getBrightness();
	}

	public float blackBaseline() {
		return blackBaseline;

	}

	public float whiteBaseline() {
		return whiteBaseline;
	}
}
