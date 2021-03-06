package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;

public class LineChecker {

	private EV3Control ev3Control;

	public LineChecker(EV3Control ev3Control) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[LineChecker]" + "[LineChecker]");

		this.ev3Control = ev3Control;
	}

	public boolean lineCheck() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.measure]" + "[LineChecker]" + "[lineCheck]");

		boolean lineCheck;
		 float brightness = ev3Control.getBrightness();

		if (brightness == 0) {
			lineCheck = true; // ラインチェック
		} else {
			lineCheck = false;
		}

		return lineCheck;
	}

}
