/*
 *  EV3way.java (for leJOS EV3)
 *  Created on: 2016/02/11
 *  Copyright (c) 2016 Embedded Technology Software Design Robot Contest
 */
package jp.co.tdc_next.kns.ctlab.tkrobo.device;


import jp.etrobo.ev3.balancer.Balancer;
import lejos.hardware.Battery;
import lejos.hardware.port.BasicMotorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * EV3way本体のモータとセンサーを扱うクラス。
 */
public class EV3 implements Runnable, EV3Control {
	public static final int TAIL_ANGLE_STAND_UP = 94; // 完全停止時の角度[度]
	public static final int TAIL_ANGLE_DRIVE = 3; // バランス走行時の角度[度]

	// 下記のパラメータはセンサ個体/環境に合わせてチューニングする必要があります
	private static final Port MOTORPORT_LWHEEL = MotorPort.C; // 左モータポート
	private static final Port MOTORPORT_RWHEEL = MotorPort.B; // 右モータポート
	private static final Port MOTORPORT_TAIL = MotorPort.A; // 尻尾モータポート
	private static final Port SENSORPORT_TOUCH = SensorPort.S1; // タッチセンサーポート
	private static final Port SENSORPORT_SONAR = SensorPort.S2; // 超音波センサーポート
	private static final Port SENSORPORT_COLOR = SensorPort.S3; // カラーセンサーポート
	private static final Port SENSORPORT_GYRO = SensorPort.S4; // ジャイロセンサーポート
	private static final float GYRO_OFFSET = 0.0F; // ジャイロセンサーオフセット値
	// private static final float LIGHT_WHITE = 0.4F; // 白色のカラーセンサー輝度値
	// private static final float LIGHT_BLACK = 0.0F; // 黒色のカラーセンサー輝度値
	// private static final float SONAR_ALERT_DISTANCE = 0.3F; //
	// 超音波センサーによる障害物検知距離[m]
	private static final float P_GAIN = 2.5F; // 完全停止用モータ制御比例係数
	private static final int PWM_ABS_MAX = 60; // 完全停止用モータ制御PWM絶対最大値
	// private static final float THRESHOLD = (LIGHT_WHITE + LIGHT_BLACK) /
	// 2.0F; // ライントレースの閾値

	// モータ制御用オブジェクト
	// EV3LargeRegulatedMotor では PWM 制御ができないので、TachoMotorPort を利用する
	public TachoMotorPort motorPortL; // 左モータ
	public TachoMotorPort motorPortR; // 右モータ
	public TachoMotorPort motorPortT; // 尻尾モータ

	// タッチセンサ
	private EV3TouchSensor touch;
	private SensorMode touchMode;
	private float[] sampleTouch;

	// 超音波センサ
	private EV3UltrasonicSensor sonar;
	private SampleProvider distanceMode; // 距離検出モード
	private float[] sampleDistance;

	private float sonarDistance = 0.0f;

	// カラーセンサ
	private EV3ColorSensor colorSensor;
	private SensorMode redMode; // 輝度検出モード
	private float[] sampleLight;

	// ジャイロセンサ
	private EV3GyroSensor gyro;
	private SampleProvider rate; // 角速度検出モード
	private float[] sampleGyro;

	private float forward = 0.0f;
	private float turn = 0.0f;
	private int tail = 0;

	private int driveCallCounter = 0;

	private float brightness;

	private boolean balance;

	private int leftMotorPower;
	private int rightMotorPower;

	private static EV3 ev3 = null;

	public static EV3 getInstance() {

		if (ev3 == null) {
			ev3 = new EV3();
			ev3.idling();
			ev3.reset();
		}

		return ev3;

	}

	/**
	 * コンストラクタ。
	 */
	private EV3() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[EV3]");

		motorPortL = MOTORPORT_LWHEEL.open(TachoMotorPort.class); // 左モータ
		motorPortR = MOTORPORT_RWHEEL.open(TachoMotorPort.class); // 右モータ
		motorPortT = MOTORPORT_TAIL.open(TachoMotorPort.class); // 尻尾モータ
		motorPortL.setPWMMode(BasicMotorPort.PWM_BRAKE);
		motorPortR.setPWMMode(BasicMotorPort.PWM_BRAKE);
		motorPortT.setPWMMode(BasicMotorPort.PWM_BRAKE);

		// タッチセンサー
		touch = new EV3TouchSensor(SENSORPORT_TOUCH);
		touchMode = touch.getTouchMode();
		sampleTouch = new float[touchMode.sampleSize()];

		// 超音波センサー
		// 使用している個体では例外が発生する。差し当たり超音波センサーは利用しないので例外回避。

		try {
			sonar = new EV3UltrasonicSensor(SENSORPORT_SONAR);
			distanceMode = sonar.getDistanceMode(); // 距離検出モード
			sampleDistance = new float[distanceMode.sampleSize()];
			sonar.enable();
		} catch (IllegalArgumentException iae) {

			sonar = null;
			distanceMode = null;
			sampleDistance = null;
		}

		// カラーセンサー
		colorSensor = new EV3ColorSensor(SENSORPORT_COLOR);
		redMode = colorSensor.getRedMode(); // 輝度検出モード
		sampleLight = new float[redMode.sampleSize()];

		// ジャイロセンサー
		gyro = new EV3GyroSensor(SENSORPORT_GYRO);
		rate = gyro.getRateMode(); // 角速度検出モード
		sampleGyro = new float[rate.sampleSize()];


	}

	/**
	 * 走行関連メソッドの空回し。 Java の初期実行性能が悪く、倒立振子に十分なリアルタイム性が得られない。
	 * そのため、走行によく使うメソッドについて、HotSpot がネイティブコードに変換するまで空実行する。 HotSpot
	 * が起きるデフォルトの実行回数は 1500。
	 */
	private void idling() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[idling]" + "Start");

		for (int i = 0; i < 1500; i++) {
			motorPortL.controlMotor(0, 0);
			getBrightness();
			getSonarDistance();
			getGyroValue();
			Battery.getVoltageMilliVolt();
			Balancer.control(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8000);
		}
		Delay.msDelay(10000); // 別スレッドで HotSpot が完了するだろう時間まで待つ。

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[idling]" + "End");
	}

	/**
	 * センサー、モータ、倒立振子ライブラリのリセット。
	 */
	public void reset() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[reset]");

		gyro.reset();
		motorPortL.controlMotor(0, 0);
		motorPortR.controlMotor(0, 0);
		motorPortT.controlMotor(0, 0);
		motorPortL.resetTachoCount(); // 左モータエンコーダリセット
		motorPortR.resetTachoCount(); // 右モータエンコーダリセット
		motorPortT.resetTachoCount(); // 尻尾モータエンコーダリセット
		Balancer.init(); // 倒立振子制御初期化
	}

	public void resetGyro() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[resetGyro]");

		gyro.reset();
		motorPortL.resetTachoCount(); // 左モータエンコーダリセット
		motorPortR.resetTachoCount(); // 右モータエンコーダリセット
		Balancer.init();
	}

	/**
	 * センサー、モータの終了処理。
	 */
	public void close() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[close]");


		motorPortL.close();
		motorPortR.close();
		motorPortT.close();
		colorSensor.setFloodlight(false);
		if (sonar != null) {
			sonar.disable();
		}


	}

	/**
	 * タッチセンサー押下のチェック。
	 *
	 * @return true ならタッチセンサーが押された。
	 */
	public final boolean touchSensorIsPressed() {

//		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[touchSensorIsPressed]");

		touchMode.fetchSample(sampleTouch, 0);
		return ((int) sampleTouch[0] != 0);
	}

	/**
	 * 走行制御
	 *
	 * @param forward
	 *            前進・後進
	 * @param turn
	 *            旋回
	 * @param tail
	 *            尻尾の角度
	 */
	public void controlBalance(float forward, float turn, int tail) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[controlBalance]");


		this.forward = forward;
		this.turn = turn;
		this.tail = tail;
		this.balance = true;
	}

	/*
	 * 走行体完全停止用モータの角度制御
	 *
	 * @param angle モータ目標角度[度]
	 */
	private void controlTail(int angle) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[controlTail]" +  "angle=" + angle);
		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[controlTail]" +  "motorPortT.getTachoCount=" + motorPortT.getTachoCount());

		float pwm = (float) (angle - motorPortT.getTachoCount()) * P_GAIN; // 比例制御
		// PWM出力飽和処理
		if (pwm > PWM_ABS_MAX) {
			pwm = PWM_ABS_MAX;
		} else if (pwm < -PWM_ABS_MAX) {
			pwm = -PWM_ABS_MAX;
		}
		motorPortT.controlMotor((int) pwm, 1);
	}

	/**
	 * 超音波センサーにより障害物との距離を取得する。
	 *
	 * @return 障害物との距離(m)。
	 */
	public final float getSonarDistance() {

//		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getSonarDistance]");


		return sonarDistance;
	}

	/**
	 * カラーセンサーから輝度値を取得する。
	 *
	 * @return 輝度値。
	 */
	public final float getBrightness() {

//		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getBrightness]");

		redMode.fetchSample(sampleLight, 0);
		return sampleLight[0];
	}

	/**
	 * ジャイロセンサーから角速度を取得する。
	 *
	 * @return 角速度。
	 */
	public final float getGyroValue() {

//		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getGyroValue]");

		rate.fetchSample(sampleGyro, 0);
		// leJOS ではジャイロセンサーの角速度値が正負逆になっているので、
		// 倒立振子ライブラリの仕様に合わせる。
		return -sampleGyro[0];
	}

	@Override
	public void run() {

//		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[run]");


		if (++driveCallCounter >= 40 / 4) { // 約40msごとに障害物検知
			// 超音波センサーが使用できるとは限らない。
			if (distanceMode != null) {
				distanceMode.fetchSample(sampleDistance, 0);
				sonarDistance = sampleDistance[0];
			} else {
				sonarDistance = 0.0f;
			}
			driveCallCounter = 0;
		}

		//redMode.fetchSample(sampleLight, 0);


		if (balance) {
			float gyroNow = getGyroValue(); // ジャイロセンサー値
			int thetaL = motorPortL.getTachoCount(); // 左モータ回転角度
			int thetaR = motorPortR.getTachoCount(); // 右モータ回転角度
			int battery = Battery.getVoltageMilliVolt(); // バッテリー電圧[mV]
			Balancer.control(forward, turn, gyroNow, GYRO_OFFSET, thetaL, thetaR, battery); // 倒立振子制御
			motorPortL.controlMotor(Balancer.getPwmL(), 1); // 左モータPWM出力セット
			motorPortR.controlMotor(Balancer.getPwmR(), 1); // 右モータPWM出力セット
		} else {
			motorPortL.controlMotor(this.leftMotorPower, 1); // 左モータPWM出力セット
			motorPortR.controlMotor(this.rightMotorPower, 1); // 右モータPWM出力セット
		}

		ev3.controlTail(tail);
	}

	@Override
	public void controlDirect(int left, int right, int tail) {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[controlDirect]");


		this.leftMotorPower =left;
		this.rightMotorPower = right;

		this.tail = tail;

		balance = false;
	}

	@Override
	public int getLMotorCount() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getLMotorCount]");


		return motorPortL.getTachoCount();
	}

	@Override
	public int getRMotorCount() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getRMotorCount]");


		return motorPortR.getTachoCount();
	}

	@Override
	public int getTailAngle() {

		System.out.println("[jp.co.tdc_next.kns.ctlab.tkrobo.device]" + "[EV3]" + "[getTailAngle]");


		return motorPortT.getTachoCount();
	}
}
