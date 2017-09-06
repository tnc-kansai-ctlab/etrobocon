/**
 *
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.strategy;

import jp.co.tdc_next.kns.ctlab.tkrobo.section.Course;

/**
 * @author Takayuki
 *
 */
public interface DriveStrategy {

	/** 指示する
	 * @throws InterruptedException */
	public void operate(Course cource) throws InterruptedException;

}
