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

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Calibrater]" + "[Calibrater]");


		this.ev3Control = ev3Control;
		this.button = button;
	}

	public boolean calibration() {

		resetFlag  = false;

		LCD.clear();

		LCD.drawString("Get Black...  ", 0, 4);

		//黒色を認識？ボタン押下待ち
		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "Please black");
		blackBaseline = getBrightnessForTouchWait();
		LCD.drawString("Black:" + blackBaseline, 0, 5);

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "black get");

		LCD.drawString("Get White...  ", 0, 4);


		//白色を認識？ボタン押下待ち
		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "Please white");
		whiteBaseline = getBrightnessForTouchWait();
		LCD.drawString("White:" + whiteBaseline, 0, 6);

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "white get");

		LCD.drawString("Reset?", 0, 7);

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "Reset");

		while (button.touchStatus() != TouchStatus.Released) {
			resetFlag = lejos.hardware.Button.UP.isDown();
			System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Button]" + "[calibration]" + "Please button");
			Delay.msDelay(10);
		}


		return resetFlag;

	}

	private float getBrightnessForTouchWait() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Calibrater]" + "[getBrightnessForTouchWait]" + "Please button");


		while (button.touchStatus() != TouchStatus.Released) {
			Delay.msDelay(10);
		}
		return ev3Control.getBrightness();
	}

	public float blackBaseline() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Calibrater]" + "[blackBaseline]");

		return blackBaseline;

	}

	public float whiteBaseline() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[Calibrater]" + "[whiteBaseline]");

		return whiteBaseline;
	}
}
