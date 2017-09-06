package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3;

public class TravelSpinImpl implements Travel {

	EV3 ev3 = EV3.getInstance();
	public void travel(WheelSpeed speed) {
		float forward = speed.getWheelSpeedScaleLeft();
		float turn = 50.0F;
		int tail = 0;
		ev3.controlBalance(forward, turn ,tail);
	}

}
