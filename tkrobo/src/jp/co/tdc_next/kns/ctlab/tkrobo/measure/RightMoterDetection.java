package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class RightMoterDetection implements Detection{

	private EV3Control ev3Control;
	public RightMoterDetection(EV3Control ev3Control) {
		this.ev3Control = ev3Control;
	}

	@Override
	public boolean Notify() {
		if (ev3Control.getRMotorCount() > 10) { // 閾値は仮設定
			return true;
		} else {
			return false;
		}
	}
}

