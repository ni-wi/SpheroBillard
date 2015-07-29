package connection;

import interfaces.RobotConnectedListener;
import se.nicklasgavelin.bluetooth.BluetoothDevice;
import se.nicklasgavelin.sphero.Robot;
import se.nicklasgavelin.sphero.command.FrontLEDCommand;
import se.nicklasgavelin.sphero.exception.InvalidRobotAddressException;
import se.nicklasgavelin.sphero.exception.RobotBluetoothException;

public class SpheroConnect implements Runnable {

	private BluetoothDevice btDev;
	private Robot robot;
	private RobotConnectedListener robotConnectListener;

	public SpheroConnect(BluetoothDevice btDev) {
		this.btDev = btDev;
	}

	public void setListener(RobotConnectedListener listener) {
		this.robotConnectListener = listener;
	}

	public Robot getRobot() {
		return this.robot;
	}

	@Override
	public void run() {
		try {
			robot = new Robot(btDev);
			// Add ourselves as listeners for the responses
			// Check if we can connect
			if (robot.connect()) {

				if (robotConnectListener != null)
					robotConnectListener.connected(robot);

				System.out.println("Connected to " + btDev.getName() + " : " + btDev.getAddress());
				robot.rgbTransition(255, 0, 0, 0, 255, 255, 50);

				// Send direct command
				robot.sendCommand(new FrontLEDCommand(1));
				// r.sendCommand(new RollCommand(90, 0.5f, false));
			}
		} catch (InvalidRobotAddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RobotBluetoothException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
