/**
 *
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.strategy;

import jp.co.tdc_next.kns.ctlab.tkrobo.drive.Travel;
import jp.co.tdc_next.kns.ctlab.tkrobo.drive.TravelJaggyImpl;
import jp.co.tdc_next.kns.ctlab.tkrobo.drive.TravelPidImpl;
import jp.co.tdc_next.kns.ctlab.tkrobo.drive.TravelTailControlRun;
import jp.co.tdc_next.kns.ctlab.tkrobo.drive.TravelTailDownImpl;
import jp.co.tdc_next.kns.ctlab.tkrobo.drive.TravelTailImpl;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Calibrater;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Course;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Section;

/**
 * @author Takayuki
 *
 */
public class DriveStrategyImpl implements DriveStrategy {

	private Calibrater calibrater;

	private Travel travel;
	private Travel jaggy;
	private Travel tail;
	private Travel taildown;
	private Travel tailControl;

	public DriveStrategyImpl(Calibrater calibrater) {

		this.calibrater = calibrater;
		travel = new TravelPidImpl(this.calibrater);
		jaggy = new TravelJaggyImpl(this.calibrater);
		tail = new TravelTailImpl(this.calibrater, 73);
		taildown = new TravelTailDownImpl(this.calibrater);
		tailControl = new TravelTailControlRun(this.calibrater, 90);


		//sw = new Stopwatch();
	}

	@Override
	public void operate(Course cource) throws InterruptedException {

		while (true) {

			Section section = cource.DecideSpeed();


			if (section.getTravelType().equals(TravelType.END)) {
				break;
			}
			Thread.sleep(6);
			//Delay.msDelay(6);


			switch (section.getTravelType()) {
			case PID:
				travel.travel(section.getWheelspeed());
				break;
			case JAGGY:
				jaggy.travel(section.getWheelspeed());
				break;
			case TAIL:
				tail.travel(section.getWheelspeed());
				break;
			case TAILCONTROL:
				tailControl.travel(section.getWheelspeed());
				break;
			case TAILDOWN:
				taildown.travel(section.getWheelspeed());
				break;
			default:
			}
			Thread.sleep(8);
			//Delay.msDelay(8);

			//EV3.getInstance().run();

		}
	}

}
