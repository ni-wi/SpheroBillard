package connection;
import java.util.ArrayList;
import java.util.Collection;

import se.nicklasgavelin.bluetooth.Bluetooth;
import se.nicklasgavelin.bluetooth.Bluetooth.EVENT;
import se.nicklasgavelin.sphero.Robot;
//import se.nicklasgavelin.sphero.command.FrontLEDCommand;
//import se.nicklasgavelin.sphero.exception.InvalidRobotAddressException;
//import se.nicklasgavelin.sphero.exception.RobotBluetoothException;
import se.nicklasgavelin.bluetooth.BluetoothDevice;
import se.nicklasgavelin.bluetooth.BluetoothDiscoveryListener;

public class SpheroDiscover extends Thread implements BluetoothDiscoveryListener {

	DeviceFoundListener deviceFoundListener;

	public SpheroDiscover(DeviceFoundListener deviceFoundListener) {
		this.deviceFoundListener = deviceFoundListener;
	}

	@Override
	public void run() {
		Bluetooth bt = new Bluetooth(this, Bluetooth.SERIAL_COM);
		bt.discover();
	}

	@Override
	public void deviceSearchCompleted(Collection<BluetoothDevice> devices) {
		// Device search is completed
		System.out.println("Completed device discovery");

		ArrayList<BluetoothDevice> btDevs = new ArrayList<>();
		// Try and see if we can find any Spheros in the found devices
		for (BluetoothDevice d : devices) {
			// Check if the Bluetooth device is a Sphero device or not
			if (Robot.isValidDevice(d)) {
				btDevs.add(d);
			}
			
			// System.out.println( "Found robot " + d.getAddress() );
			//
			// // We got a valid device (Sphero device), connect to it and
			// // have some fun with colors.
			// try
			// {
			// // Create our robot from the Bluetooth device that we got
			// Robot r = new Robot( d );
			//
			// // Add ourselves as listeners for the responses
			// r.addListener( this );
			//
			// // Check if we can connect
			// if( r.connect() )
			// {
			// // Add robots to our connected robots list
			// robots.add( r );
			//
			// System.out.println( "Connected to " + d.getName() + " : " +
			// d.getAddress() );
			// r.rgbTransition( 255, 0, 0, 0, 255, 255, 50 );
			//
			// // Send direct command
			// r.sendCommand( new FrontLEDCommand( 1 ) );
			//// r.sendCommand(new RollCommand(90, 0.5f, false));
			// }
			// else
			// System.err.println( "Failed to connect to robot" );
			// }
			// catch( InvalidRobotAddressException ex )
			// {
			// ex.printStackTrace();
			// }
			// catch( RobotBluetoothException ex )
			// {
			// ex.printStackTrace();
			// }
			// }
		}
		deviceFoundListener.deviceFound(btDevs);
	}

	@Override
	public void deviceDiscovered(BluetoothDevice device) {
		System.out.println("Discovered device " + device.getName() + " : " + device.getAddress());
	}

	@Override
	public void deviceSearchFailed(EVENT error) {
		System.err.println("Failed with device search: " + error);
	}

	@Override
	public void deviceSearchStarted() {
		System.out.println("Search Started");
	}

}
