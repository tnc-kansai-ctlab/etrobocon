package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

public class WheelSpeed {
	public int wheelSpeedScaleLeft;
	public int wheelSpeedScaleRight;

	public WheelSpeed (int wheelSpeedScaleLeft, int wheelSpeedScaleRight){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[WheelSpeed]" + "[WheelSpeed]");

		this.wheelSpeedScaleLeft = wheelSpeedScaleLeft;
		this.wheelSpeedScaleRight = wheelSpeedScaleRight;
	}
	public int getWheelSpeedScaleLeft() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[WheelSpeed]" + "[getWheelSpeedScaleLeft]");

		return wheelSpeedScaleLeft;
	}
	public void setWheelSpeedScaleLeft(int wheelSpeedScaleLeft) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[WheelSpeed]" + "[setWheelSpeedScaleLeft]");

		this.wheelSpeedScaleLeft = wheelSpeedScaleLeft;
	}
	public int getWheelSpeedScaleRight() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[WheelSpeed]" + "[getWheelSpeedScaleRight]");

		return wheelSpeedScaleRight;
	}
	public void setWheelSpeedScaleRight(int wheelSpeedScaleRight) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.drive]" + "[WheelSpeed]" + "[setWheelSpeedScaleRight]");

		this.wheelSpeedScaleRight = wheelSpeedScaleRight;
	}

}
