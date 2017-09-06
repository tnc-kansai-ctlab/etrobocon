package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;


public class Button {

	private boolean pressStatus;
    private EV3Control ev3Control;


	public Button(EV3Control ev3Control) {
		this.ev3Control = ev3Control;
	}

	public TouchStatus touchStatus() {


		if (ev3Control.touchSensorIsPressed()) {
			pressStatus = true; // タッチセンサーが押された
			return TouchStatus.Pressed;
		} else {
			if (pressStatus) {
				pressStatus = false; // タッチセンサーが押された後に放した
				return TouchStatus.Released;
			}
		}

		return TouchStatus.NotPressed;
	}

}
