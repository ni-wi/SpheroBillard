package main;

import java.io.IOException;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

import ui.UIStart;
import ui.VideoCamera;

public class Main {

	private Main() {
	}

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// VideoCapture camera = new
		// VideoCapture("http://192.168.0.7/image.jpg");

		// TODO move camera init section in constructor of VideoCamera
		VideoCapture camera = new VideoCapture(0);

		try {
			// wait some time for setting up camera
			Thread.sleep(40);
		} catch (InterruptedException e) {
		}
		
		if (camera.isOpened()) {
			System.out.println("Video is captured");
		} else {
			System.err.println("error: cannot open camera.");
		}
		VideoCamera cam = new VideoCamera(camera);
		@SuppressWarnings("unused")
		UIStart ui = new UIStart(cam);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(cam);
		frame.setSize(800, 800);
		frame.setVisible(true);

		(new Thread(cam)).start();
//		Thread cameraThread = new Thread() {
//			@Override
//			public void run() {
//				try {
//					while (camera.isOpened()) {
//						cam.repaint();
//						Thread.sleep(10);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		};
//		cameraThread.start();

	}

}
