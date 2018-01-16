/**
 *
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.section;

/**
 * @author t2011002
 *
 */
public class Condition {

	private ConditionType conditionType;
	private float conditionValue;

	public Condition(ConditionType conditionType, float conditionValue){

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Condition]" + "[Condition]");

		this.conditionType = conditionType;
		this.conditionValue = conditionValue;
	}

	public ConditionType getConditionType() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Condition]" + "[getConditionType]");

		return conditionType;
	}

	public float getConditionValue() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.section]" + "[Condition]" + "[getConditionValue]");

		return conditionValue;
	}

}

