package jp.co.tdc_next.kns.ctlab.tkrobo.ui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jp.co.tdc_next.kns.ctlab.tkrobo.device.EV3;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Button;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.Calibrater;
import jp.co.tdc_next.kns.ctlab.tkrobo.measure.TouchStatus;
import jp.co.tdc_next.kns.ctlab.tkrobo.sample.RemoteTask;
import jp.co.tdc_next.kns.ctlab.tkrobo.section.Course;
import jp.co.tdc_next.kns.ctlab.tkrobo.strategy.CourceFactory;
import jp.co.tdc_next.kns.ctlab.tkrobo.strategy.CourceType;
import jp.co.tdc_next.kns.ctlab.tkrobo.strategy.DriveStrategy;
import jp.co.tdc_next.kns.ctlab.tkrobo.strategy.DriveStrategyImpl;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * キャリブレーションを実行し走行戦略の判定処理を呼び出す
 *
 * @author TJ
 *
 */
public class start implements Runnable {

	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> futureDrive;
	private ScheduledFuture<?> futureRemote;
	private ScheduledFuture<?> futureStart;
	private DriveStrategy driveStrategy;

	private Course cource;

	Button button;
	Calibrater calibrater;

	boolean endFlag;

	public static void main(String[] args) {

		start starti = new start();

		starti.control();

	}

	public void starter() {

		EV3 ev3 = EV3.getInstance();

		try {

			scheduler = Executors.newScheduledThreadPool(3);

			futureDrive = scheduler.scheduleAtFixedRate(ev3, 0, 4, TimeUnit.MILLISECONDS);
			ev3.controlDirect(0, 0, 0);

			driveStrategy = new DriveStrategyImpl(calibrater);

			cource = CourceFactory.create(CourceType.LEFT);

			// PIDDriver pidDriver = new PIDDriver(ev3, calibrater);

			LCD.drawString("Start Wait", 0, 3);
			while (button.touchStatus() != TouchStatus.Released) {
				Delay.msDelay(10);
			}

			ev3.reset();
			Sound.beep();

			futureRemote = scheduler.scheduleAtFixedRate(RemoteTask.getInstance(), 0, 500, TimeUnit.MILLISECONDS);

			// 尻尾を停止位置へ固定しスタート準備
			while (button.touchStatus() != TouchStatus.Released
					&& !RemoteTask.getInstance().checkRemoteCommand(RemoteTask.REMOTE_COMMAND_START)) {
				ev3.controlDirect(0, 0, 96);
				Delay.msDelay(10);
			}

			// デバッグ用
			// while (button.touchStatus() != TouchStatus.Released) {
			// ev3.controlBalance(0, 0, 0);
			// Delay.msDelay(4);
			// }

			EV3.getInstance().controlBalance(2, 2, 90);
			Delay.msDelay(100);

			futureDrive = scheduler.scheduleAtFixedRate(this, 0, 10, TimeUnit.MILLISECONDS);

			while (button.touchStatus() != TouchStatus.Released
					&& !RemoteTask.getInstance().checkRemoteCommand(RemoteTask.REMOTE_COMMAND_STOP)) {
				Delay.msDelay(250);
			}

			// pidDriver.drive(80, 13600, 13600);

			// Todo：スタート準備完了後、走行戦略の判定処理を呼び出す
			// DriveStrategy drivestrategy = new DriveStrategyImpl();
			// drivestrategy.operate();

			while (button.touchStatus() != TouchStatus.Released
					&& !RemoteTask.getInstance().checkRemoteCommand(RemoteTask.REMOTE_COMMAND_STOP)) {
				endFlag  = lejos.hardware.Button.DOWN.isDown();
				Delay.msDelay(250);
			}

		} finally {

			if (futureStart != null) {
				futureStart.cancel(true);
			}

			if (futureDrive != null) {
				futureDrive.cancel(true);
			}

			if (futureRemote != null) {
				futureRemote.cancel(true);
			}

			scheduler.shutdownNow();
		}
	}

	public void control() {

		// キャリブレーション実行
		this.button = new Button(EV3.getInstance());
		this.calibrater = new Calibrater(EV3.getInstance(), button);
		while(this.calibrater.calibration()) {

		}

		while (true) {


			this.starter();

			if (endFlag) {
				break;
			}


			LCD.clear();
		}

		RemoteTask.getInstance().close();
		EV3.getInstance().close();

	}

	@Override
	public void run() {
		try {
			driveStrategy.operate(cource);

//			while (true) {
//				EV3.getInstance().controlDirect(0, 0, 90);
//				Delay.msDelay(100);
//			}
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
