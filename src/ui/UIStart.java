package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import org.opencv.core.Point;

import connection.CaliThread;
import connection.DeviceFoundListener;
import connection.ManualControlHome;
import connection.SpheroConnect;
import connection.SpheroDiscover;
import connection.SpheroHome;
import interfaces.RobotConnectedListener;
import se.nicklasgavelin.bluetooth.BluetoothDevice;
import se.nicklasgavelin.sphero.Robot;
//import se.nicklasgavelin.sphero.command.RollCommand;

public class UIStart implements RobotConnectedListener {

	private Robot robot;
	private VideoCamera cam;

	public UIStart(VideoCamera cam) {

		this.cam = cam;
		final ArrayList<BluetoothDevice> devicesFound = new ArrayList<>();
		ArrayList<SpheroConnect> pool = new ArrayList<>();
		SpheroHome home = new SpheroHome();

		Thread shitThread = new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 100; ++i) {
					System.out.println("(" + i + ") thread running");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Point p1 = cam.getCurrentPosition();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Point p2 = cam.getCurrentPosition();

				System.out.println(p1 + " " + p2);
			}
		};

		home.addSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SpheroDiscover discover = new SpheroDiscover(new DeviceFoundListener() {

					@Override
					public void deviceFound(ArrayList<BluetoothDevice> btDev) {
						DefaultListModel<String> devNames = new DefaultListModel<>();
						for (BluetoothDevice d : btDev) {
							devNames.addElement(d.getName());
							devicesFound.add(d);
						}
						home.setListContent(devNames);
					}
				});
				discover.start();
			}
		});

		home.addManualControlListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (robot != null) {
					@SuppressWarnings("unused")
					ManualControlHome manual = new ManualControlHome(robot);
				} else {
					System.err.println("error: couldn't start manual control because robot is null.");
				}
			}
		});

		home.addConnectListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				shitThread.start();
				int index = home.getSelectedDevice();
				if (index != -1) {
					SpheroConnect connect = new SpheroConnect(devicesFound.get(index));
					pool.add(connect);
					connect.setListener(UIStart.this);
					connect.start();
				}

			}
		});

		home.addDisconnectListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pool.get(0).getRobot().disconnect();
				pool.clear();
			}

		});

		home.addGameStartListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calibrateSphero();
			}
		});

		home.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

	}

	public Robot getRobot() {
		return this.robot;
	}

	public void calibrateSphero() {
		// Point oldPos = cam.getCurrentPosition();
		//// robot.roll(0.0f, 0.5f);
		// robot.sendCommand(new RollCommand(0, 0.5f, false));
		//
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// robot.sendCommand(new RollCommand(0, 0.0f, true));
		//
		// cam.updatePosition();
		// Point newPos = cam.getCurrentPosition();
		// double heading = cam.calcDifRadius(oldPos, newPos);
		// System.out.println("test");
		// System.out.println(oldPos + " " + newPos);
		// //System.out.println(heading);
		// robot.calibrate((float) heading);

		CaliThread calibrationThread = new CaliThread(cam, robot);
		calibrationThread.start();

	}

	@Override
	public void connected(Robot robot) {
		this.robot = robot;
	}
}
