package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3;

public class TravelTailUpImpl implements Travel {
	EV3 ev3 = EV3.getInstance();

	public void travel(WheelSpeed speed) {
		int tail_up = 66;
		int tail_down = 90;
		for (int i = tail_up; i >= tail_down; i++) {
			ev3.controlDirect(0, 0, i) ;
		}
		ev3.controlBalance(-10, 0, 0);
	}

}
