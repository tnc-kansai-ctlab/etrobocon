package jp.co.tdc_next.kns.ctlab.tkrobo.drive;

public class WheelSpeed {
	public int wheelSpeedScaleLeft;
	public int wheelSpeedScaleRight;

	public WheelSpeed (int wheelSpeedScaleLeft, int wheelSpeedScaleRight){
		this.wheelSpeedScaleLeft = wheelSpeedScaleLeft;
		this.wheelSpeedScaleRight = wheelSpeedScaleRight;
	}
	public int getWheelSpeedScaleLeft() {
		return wheelSpeedScaleLeft;
	}
	public void setWheelSpeedScaleLeft(int wheelSpeedScaleLeft) {
		this.wheelSpeedScaleLeft = wheelSpeedScaleLeft;
	}
	public int getWheelSpeedScaleRight() {
		return wheelSpeedScaleRight;
	}
	public void setWheelSpeedScaleRight(int wheelSpeedScaleRight) {
		this.wheelSpeedScaleRight = wheelSpeedScaleRight;
	}

}
