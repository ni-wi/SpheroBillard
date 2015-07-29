package connection;

import java.util.ArrayList;
import se.nicklasgavelin.bluetooth.BluetoothDevice;

public interface DeviceFoundListener {
	public void deviceFound(ArrayList<BluetoothDevice> btDev);
}
