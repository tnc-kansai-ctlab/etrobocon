package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Calibrater;

public class TravelTailImpl implements Travel {

	EV3 ev3 = EV3.getInstance();
	public int tail;
	//private float THRESHOLD;
	private Calibrater calibrater;

	public TravelTailImpl(Calibrater calibrater, int tail) {
		//this.THRESHOLD = (calibrater.blackBaseline() + calibrater.whiteBaseline() * 2) / 3.0F;
		this.calibrater = calibrater;
		this.tail = tail;
	}
	
	public float getBrightnessValue() {

		float temp = ev3.getBrightness();

		//LCD.drawString(Float.toString(temp), 0, 1);

		return (((temp - calibrater.blackBaseline()) / (calibrater.whiteBaseline() - calibrater.blackBaseline()))
				* 100.0f);
	}

	public void travel(WheelSpeed speed) {
//		
//		int left;
//		int right;
//		
//		if (getBrightnessValue() > 80) {
//			left = speed.getWheelSpeedScaleLeft();
//			right = speed.getWheelSpeedScaleRight();
//		}else{
//			left = speed.getWheelSpeedScaleRight();
//			right = speed.getWheelSpeedScaleLeft();
//		}
		int left = speed.getWheelSpeedScaleLeft();
		int right = speed.getWheelSpeedScaleRight();
		
		ev3.controlDirect(left, right, tail) ;
	}

}
