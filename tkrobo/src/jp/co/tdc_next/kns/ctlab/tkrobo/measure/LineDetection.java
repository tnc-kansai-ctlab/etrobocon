package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class LineDetection implements Detection{

	private EV3Control ev3Control;
	public LineDetection(EV3Control ev3Control) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure." + "[LineDetection." + "[LineDetection]");

		this.ev3Control = ev3Control;
	}

	@Override
	public boolean Notify() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure." + "[LineDetection." + "[Notify]");

		if (ev3Control.getBrightness() < 0.1) { // 閾値は仮設定
			return true;
		} else {
			return false;
		}
	}
}

