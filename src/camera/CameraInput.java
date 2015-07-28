package camera;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CameraInput
{
	private VideoCapture captureDevice = new VideoCapture();
	private Size stretchedSize = new Size();
	
	
	public CameraInput()
	{
		captureDevice.open(0);
		if (captureDevice.isOpened())
		{	
			System.out.println("Camera opened");
		}
	}


	public Mat getCurrentFrame() throws Exception
	{
		Mat out = new Mat();
		captureDevice.grab();
		if (captureDevice.retrieve(out))
		{	
			return out;
		} else
		{
			throw new Exception("Could not load frame");
		}
	}


	public void unload()
	{
		captureDevice.release();
	}
	
	
}
