package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class LeftMoterDetection implements Detection{

	private EV3Control ev3Control;
	public LeftMoterDetection(EV3Control ev3Control) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[LeftMoterDetection]" + "[LeftMoterDetection]");

		this.ev3Control = ev3Control;
	}

	@Override
	public boolean Notify() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[LeftMoterDetection]" + "[Notify]");

		if (ev3Control.getLMotorCount() > 10) { // 閾値は仮設定
			return true;
		} else {
			return false;
		}
	}
}

