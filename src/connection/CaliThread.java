package connection;

import org.opencv.core.Point;

import se.nicklasgavelin.sphero.Robot;
import se.nicklasgavelin.sphero.command.RollCommand;
import ui.VideoCamera;

public class CaliThread extends Thread {

	private VideoCamera cam;
	private Robot robot;

	public CaliThread(VideoCamera cam, Robot robot) {
		this.cam = cam;
		this.robot = robot;
	}

	@Override
	public void run() {
		Point oldPos = cam.getCurrentPosition();
		// robot.roll(0.0f, 0.5f);
		robot.sendCommand(new RollCommand(0, 0.5f, false));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.sendCommand(new RollCommand(0, 0.0f, true));

		cam.updatePosition();
		Point newPos = cam.getCurrentPosition();
		double heading = cam.calcDifRadius(oldPos, newPos);
		System.out.println("test");
		System.out.println(oldPos + " " + newPos);
		// System.out.println(heading);
		robot.calibrate((float) heading);

	}

}
