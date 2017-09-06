package jp.co.tdc_next.kns.ctlab.tkrobo.measure;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3Control;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Condition;
import lejos.utility.Stopwatch;

public class SectionRunActual {

	private EV3Control ev3Control;

	Stopwatch time = new Stopwatch();
	int initMotorCount;

	public SectionRunActual(EV3Control ev3Control) {
		this.ev3Control = ev3Control;
	}

	public boolean notify(Condition condition){
		boolean notify = false;
		switch (condition.getConditionType()){
		case DISTANCE:
			// 回転数

			int motorCount = Math.abs(ev3Control.getLMotorCount() - initMotorCount);
			if (condition.getConditionValue() <= motorCount) {
				notify = true;
			}
			break;
		case TIME:
			// 時間
			if (condition.getConditionValue() <= time.elapsed()) { // 時間の基準はチューニングする必要あり
				notify = true;
			}
			break;
		case OBSTACLES_DETECTION:
			// 未実装
			if (ev3Control.getSonarDistance() < 0.1) { // 閾値は仮設定
				return true;
			}
			break;
		case TAIL_ANGLE:
			if (condition.getConditionValue() >= ev3Control.getTailAngle()) {
				return true;
			}
			break;
		}

		return notify;
	}
	public void start(){

		time.reset();
		this.initMotorCount  = ev3Control.getLMotorCount();

	}
}

