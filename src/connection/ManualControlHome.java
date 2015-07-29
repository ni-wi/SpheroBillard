package connection;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import se.nicklasgavelin.sphero.Robot;
import se.nicklasgavelin.sphero.command.RollCommand;

public class ManualControlHome extends JFrame {

	private static final long serialVersionUID = -1584552668426797229L;

	// private Robot robot;

	public ManualControlHome(Robot robot) throws HeadlessException {
		// this.robot = robot;
		this.setSize(300, 300);
		this.setTitle("Manual Control");
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent k) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();
				float speed = 0.5f;
				switch (keycode) {
				case KeyEvent.VK_UP:
					robot.sendCommand(new RollCommand(0, speed, false));
					break;
				case KeyEvent.VK_DOWN:
					robot.sendCommand(new RollCommand(180, speed, false));
					break;
				case KeyEvent.VK_LEFT:
					robot.sendCommand(new RollCommand(270, speed, false));
					break;
				case KeyEvent.VK_RIGHT:
					robot.sendCommand(new RollCommand(90, speed, false));
					break;
				case KeyEvent.VK_1:
					robot.sendCommand(new RollCommand(0, 0.0f, true));
					break;
				default:
					break;
				}

			}
		});
	}

}
