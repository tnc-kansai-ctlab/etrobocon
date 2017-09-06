package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class VibrationDetection implements Detection{

	private EV3Control ev3Control;
	public VibrationDetection(EV3Control ev3Control) {
		this.ev3Control = ev3Control;
	}

	@Override
	public boolean Notify() {
		if (ev3Control.getGyroValue() < 0) { // 閾値は仮設定
			return true;
		} else {
			return false;
		}
	}
}

