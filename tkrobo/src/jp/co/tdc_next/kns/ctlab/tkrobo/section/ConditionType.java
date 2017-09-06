/**
 * 
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.section;

/**
 * @author t2011002
 *
 */
public enum ConditionType {

	// 検知
	/** 黒色検知 */
	BLACK_DETECTION,
	/** 灰色検知 */
	GRAY_DETECTION,
	/** 障害物検知 */
	OBSTACLES_DETECTION,
	/** 衝突検知 */
	CONFLICT_DETECTION,
	
	// 実績
	/** 回転数 */
	DISTANCE,
	/** 経過時間(ミリ秒) */
	TIME,
	/** しっぽ角度*/
	TAIL_ANGLE,
	/** ライン未検知からの経過時間(ミリ秒) */
	WHITE_DURATION;
	
}
