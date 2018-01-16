/**
 *
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.section;

import java.util.List;

import lejos.hardware.Sound;

/**
 * @author Takayuki
 *
 */
public class Course {

	private List<Section> sectionList;

	private int driveSection = 0;


	public Course(){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Course]" + "[Course]");

	}

	public Course(List<Section> sectionList){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Course]" + "[Course]");

		this.sectionList = sectionList;
		sectionList.get(driveSection).startMeasure();
	}

	/**
	 * 速度を決定する
	 * @return
	 */
	public Section DecideSpeed(){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Course]" + "[DecideSpeed]");



//		if(sectionList.get(driveSection).judgeAbnormal()){
//			updateSection();
//		}

		if(sectionList.get(driveSection).judgeEndOfSection()){
			updateSection();
		}

		return sectionList.get(driveSection);
	}

	/**
	 * 走行中か判定する
	 * @return
	 */
	public boolean isDriving(){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Course]" + "[isDriving]");


		if(sectionList.size() > driveSection){
			return true;
		}
		return false;
	}

	/**
	 * 次区間に切り替える
	 */
	private void updateSection(){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Course]" + "[updateSection]");


		driveSection++;

		Sound.beep();
		sectionList.get(driveSection).startMeasure();
	}

}
