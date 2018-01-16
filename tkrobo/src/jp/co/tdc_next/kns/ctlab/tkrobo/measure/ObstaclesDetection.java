package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class ObstaclesDetection implements Detection{

	private EV3Control ev3Control;
	public ObstaclesDetection(EV3Control ev3Control) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[ObstaclesDetection]" + "[ObstaclesDetection]");

		this.ev3Control = ev3Control;
	}

	@Override
	public boolean Notify() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[ObstaclesDetection]" + "[Notify]");

		if (ev3Control.getSonarDistance() < 0.3) { // 閾値は仮設定
			return true;
		} else {
			return false;
		}
	}
}

