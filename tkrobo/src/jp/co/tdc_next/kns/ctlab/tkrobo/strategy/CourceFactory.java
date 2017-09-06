/**
 *
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.strategy;

import java.util.ArrayList;
import java.util.List;

import jp.co.tdc_next.kns.ctlab.tkrobo.drive.WheelSpeed;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Condition;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.ConditionType;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Course;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Section;

/**
 * @author Takayuki
 *
 */
public class CourceFactory {

	public static Course create(CourceType courceType){

		List<Section> sectionList;

		switch (courceType) {
		case LEFT:
			sectionList = createLeftCource();
			break;
		case RIGHT:
			sectionList = createRightCource();
			break;
		case GATE:
			sectionList = createGateCource();
			break;
		case STAIRS:
			sectionList = createStairsCource();
			break;
		default:
			sectionList = createLeftCource();
		}

		return new Course(sectionList);
	}

	private static List<Section> createGateCource() {

		List<Section> sectionList = new ArrayList<>();

		//ゲート検知まで
//		WheelSpeed speed0 = new WheelSpeed(5, 5);
//		Condition condition0 = new Condition(ConditionType.TIME, 5000);
//		sectionList.add(new Section(speed0, TravelType.PID, condition0, null));

		WheelSpeed speed1 = new WheelSpeed(20, 20);
		Condition condition1 = new Condition(ConditionType.OBSTACLES_DETECTION, 0.1f);
		sectionList.add(new Section(speed1, TravelType.PID, condition1, null));
		//尻尾下ろし
		WheelSpeed speed2 = new WheelSpeed(-30, -30);
		Condition condition2 = new Condition(ConditionType.TIME, 500);
		sectionList.add(new Section(speed2, TravelType.TAILCONTROL, condition2, null));
		//尻尾下ろし
		WheelSpeed speed2_2 = new WheelSpeed(0, 0);
		Condition condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 80);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));

		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 78);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));
		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 75);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));
		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 73);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));

		//前進
		WheelSpeed speed3 = new WheelSpeed(20, 20);
		Condition condition3 = new Condition(ConditionType.DISTANCE, 350);
		sectionList.add(new Section(speed3, TravelType.TAIL, condition3, null));
		//後退
		WheelSpeed speed4 = new WheelSpeed(-20, -21);
		Condition condition4 = new Condition(ConditionType.DISTANCE, 480);
		sectionList.add(new Section(speed4, TravelType.TAIL, condition4, null));
//		WheelSpeed speed4 = new WheelSpeed(50, -50);
//		Condition condition4 = new Condition(ConditionType.DISTANCE, 250);
//		sectionList.add(new Section(speed4, TravelType.TAIL, condition4, null));
		//前進
//		WheelSpeed speed5 = new WheelSpeed(50, 50);
//		Condition condition5 = new Condition(ConditionType.DISTANCE, 400);
//		sectionList.add(new Section(speed5, TravelType.TAIL, condition5, null));
		//旋回
//		WheelSpeed speed6 = new WheelSpeed(50, -50);
//		Condition condition6 = new Condition(ConditionType.DISTANCE, 250);
//		sectionList.add(new Section(speed6, TravelType.TAIL, condition6, null));
		//前進(車庫まで)
		WheelSpeed speed7 = new WheelSpeed(20, 20);
		Condition condition7 = new Condition(ConditionType.DISTANCE, 1250);
		sectionList.add(new Section(speed7, TravelType.TAIL, condition7, null));
		//ストップ
		WheelSpeed speed8 = new WheelSpeed(0, 0);
		Condition condition8 = new Condition(ConditionType.DISTANCE, 6000);
		sectionList.add(new Section(speed8, TravelType.TAIL, condition8, null));
		//ストップ
		WheelSpeed speed9 = new WheelSpeed(0, 0);
		Condition condition9 = new Condition(ConditionType.TIME, 5000);
		sectionList.add(new Section(speed9, TravelType.END, condition9, null));

		return sectionList;
	}

	private static List<Section> createStairsCource() {

		List<Section> sectionList = new ArrayList<>();

		//尻尾下ろし
		WheelSpeed speed2 = new WheelSpeed(-30, -30);
		Condition condition2 = new Condition(ConditionType.TIME, 500);
		sectionList.add(new Section(speed2, TravelType.TAILCONTROL, condition2, null));
		//尻尾下ろし
		WheelSpeed speed2_2 = new WheelSpeed(0, 0);
		Condition condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 80);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));

		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 78);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));
		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 75);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));
		condition2_2 = new Condition(ConditionType.TAIL_ANGLE, 73);
		sectionList.add(new Section(speed2_2, TravelType.TAILDOWN, condition2_2, null));

		//前進(車庫まで)
		WheelSpeed speed7 = new WheelSpeed(20, 20);
		Condition condition7 = new Condition(ConditionType.DISTANCE, 150);
		sectionList.add(new Section(speed7, TravelType.TAIL, condition7, null));
		//ストップ
		WheelSpeed speed8 = new WheelSpeed(0, 0);
		Condition condition8 = new Condition(ConditionType.DISTANCE, 6000);
		sectionList.add(new Section(speed8, TravelType.TAIL, condition8, null));
		
		return sectionList;
	}

	private static List<Section> createLeftCource() {

		List<Section> sectionList = new ArrayList<>();

		//スタート
		WheelSpeed speed0 = new WheelSpeed(10, 10);
		Condition condition0 = new Condition(ConditionType.TIME, 2000);
		sectionList.add(new Section(speed0, TravelType.PID, condition0, null));
		//スタート
		WheelSpeed speed1 = new WheelSpeed(50, 50);
		Condition condition1 = new Condition(ConditionType.DISTANCE, 200);
		sectionList.add(new Section(speed1, TravelType.PID, condition1, null));
//		//スタート
//		WheelSpeed speed1_2 = new WheelSpeed(65, 65);
//		Condition condition1_2 = new Condition(ConditionType.DISTANCE, 200);
//		sectionList.add(new Section(speed1_2, TravelType.PID, condition1_2, null));

		//ストレート
		WheelSpeed speed2 = new WheelSpeed(90, 90);
		Condition condition2 = new Condition(ConditionType.DISTANCE, 3200);
		sectionList.add(new Section(speed2, TravelType.PID, condition2, null));
		//ストレート
		WheelSpeed speed3 = new WheelSpeed(70, 70);
		Condition condition3 = new Condition(ConditionType.DISTANCE, 800);
		sectionList.add(new Section(speed3, TravelType.PID, condition3, null));
		//ストレート
		WheelSpeed speed4 = new WheelSpeed(90, 90);
		Condition condition4 = new Condition(ConditionType.DISTANCE, 1800);
		sectionList.add(new Section(speed4, TravelType.PID, condition4, null));
		//ストレート
		WheelSpeed speed5 = new WheelSpeed(75, 75);
		Condition condition5 = new Condition(ConditionType.DISTANCE, 600);
		sectionList.add(new Section(speed5, TravelType.PID, condition5, null));
		//ストレート
		WheelSpeed speed6 = new WheelSpeed(90, 90);
		Condition condition6 = new Condition(ConditionType.DISTANCE, 1900);
		sectionList.add(new Section(speed6, TravelType.PID, condition6, null));
		//ストレート
		WheelSpeed speed7 = new WheelSpeed(75, 75);
		Condition condition7 = new Condition(ConditionType.DISTANCE, 500);
		sectionList.add(new Section(speed7, TravelType.PID, condition7, null));
		//ストレート
		WheelSpeed speed7_2 = new WheelSpeed(85, 85);
		Condition condition7_2 = new Condition(ConditionType.DISTANCE, 1000);
		sectionList.add(new Section(speed7_2, TravelType.PID, condition7_2, null));
//		//ストップ
//		WheelSpeed speed8 = new WheelSpeed(0, 0);
//		Condition condition8 = new Condition(ConditionType.DISTANCE, 6000);
//		sectionList.add(new Section(speed8, TravelType.PID, condition8, null));
//		//ストップ


		sectionList.addAll(createGateCource());

		WheelSpeed speed9 = new WheelSpeed(0, 0);
		Condition condition9 = new Condition(ConditionType.TIME, 5000);
		sectionList.add(new Section(speed9, TravelType.END, condition9, null));

		return sectionList;
	}

	private static List<Section> createRightCource() {

		List<Section> sectionList = new ArrayList<>();

		//スタート
		WheelSpeed speed0 = new WheelSpeed(10, 10);
		Condition condition0 = new Condition(ConditionType.TIME, 2000);
		sectionList.add(new Section(speed0, TravelType.PID, condition0, null));
		//スタート
		WheelSpeed speed1 = new WheelSpeed(50, 50);
		Condition condition1 = new Condition(ConditionType.DISTANCE, 200);
		sectionList.add(new Section(speed1, TravelType.PID, condition1, null));

		//ストレート
		WheelSpeed speed2 = new WheelSpeed(90, 90);
		Condition condition2 = new Condition(ConditionType.DISTANCE, 3200);
		sectionList.add(new Section(speed2, TravelType.PID, condition2, null));
		//ストレート
		WheelSpeed speed3 = new WheelSpeed(75, 75);
		Condition condition3 = new Condition(ConditionType.DISTANCE, 1000);
		sectionList.add(new Section(speed3, TravelType.PID, condition3, null));
		//ストレート
		WheelSpeed speed4 = new WheelSpeed(90, 90);
		Condition condition4 = new Condition(ConditionType.DISTANCE, 2100);
		sectionList.add(new Section(speed4, TravelType.PID, condition4, null));
		//ストレート
		WheelSpeed speed5 = new WheelSpeed(40, 40);
		Condition condition5 = new Condition(ConditionType.DISTANCE, 450);
		sectionList.add(new Section(speed5, TravelType.PID, condition5, null));
		//ストレート
		WheelSpeed speed5_2 = new WheelSpeed(80, 80);
		Condition condition5_2 = new Condition(ConditionType.DISTANCE, 500);
		sectionList.add(new Section(speed5_2, TravelType.PID, condition5_2, null));
		//ストレート
		WheelSpeed speed5_3 = new WheelSpeed(40, 40);
		Condition condition5_3 = new Condition(ConditionType.DISTANCE, 550);
		sectionList.add(new Section(speed5_3, TravelType.PID, condition5_3, null));
		//ストレート
		WheelSpeed speed6 = new WheelSpeed(90, 90);
		Condition condition6 = new Condition(ConditionType.DISTANCE, 1850);
		sectionList.add(new Section(speed6, TravelType.PID, condition6, null));
		//ストレート
		WheelSpeed speed7 = new WheelSpeed(75, 75);
		Condition condition7 = new Condition(ConditionType.DISTANCE, 500);
		sectionList.add(new Section(speed7, TravelType.PID, condition7, null));
		//ストレート
		WheelSpeed speed8 = new WheelSpeed(110, 110);
		Condition condition8 = new Condition(ConditionType.DISTANCE, 1700);
		sectionList.add(new Section(speed8, TravelType.PID, condition8, null));
		//ストレート
		WheelSpeed speed9 = new WheelSpeed(25, 25);
		Condition condition9 = new Condition(ConditionType.DISTANCE, 1200);
		sectionList.add(new Section(speed9, TravelType.PID, condition9, null));
		//ストップ
//		WheelSpeed speed10 = new WheelSpeed(0, 0);
//		Condition condition10 = new Condition(ConditionType.DISTANCE, 5000);
//		sectionList.add(new Section(speed10, TravelType.PID, condition10, null));

		sectionList.addAll(createStairsCource());

		return sectionList;
	}


}
