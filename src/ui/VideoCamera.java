package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

@SuppressWarnings("serial")
public class VideoCamera extends JPanel implements Runnable {
	VideoCapture camera;
	Point currentSpheroPos;

	public VideoCamera(VideoCapture cam) {

		camera = cam;

	}

	public BufferedImage Mat2BufferedImage(Mat m) {

		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage img = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return img;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Mat mat = new Mat();

		if (camera.read(mat)) {
		}

		Mat hsv = new Mat();
		Mat binGelb = new Mat();
		Mat binRot = new Mat();
		Mat binRot2 = new Mat();
		Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);

		// H = 81,82 S = 67,84 V = 89,02
		// Rot : H = 10 , S = 245, V = 125
		// System.out.println(hsv.get(hsv.rows() / 2, hsv.cols() / 2)[0] + " " +
		// hsv.get(hsv.rows() / 2, hsv.cols() / 2)[1] + " " + hsv.get(hsv.rows()
		// / 2, hsv.cols() / 2)[2]);
		Core.inRange(hsv, new Scalar(61.0 / 2.0, 100, 100), new Scalar(121.0 / 2.0, 255.0, 255.0), binGelb);
		Core.inRange(hsv, new Scalar(0.0, 150.0, 150.0), new Scalar(10.0, 255.0, 255.0), binRot);
		Core.inRange(hsv, new Scalar(169.0, 150.0, 150.0), new Scalar(179.0, 255.0, 255.0), binRot2);
		Core.addWeighted(binRot, 1.0, binRot2, 1.0, 0.0, binRot);
		// Core.inRange(hsv,new Scalar(40 , 160 , 200 ), new Scalar(60, 200,
		// 250),hsv);

		mat = drawCircles(binGelb, mat, new Scalar(255, 0, 0, 255), 0);
		mat = drawCircles(binRot, mat, new Scalar(0, 255, 0, 255), 1);
		Core.line(mat, new Point(132, 54), new Point(420, 55), new Scalar(255, 49, 0, 255));
		Core.line(mat, new Point(420, 55), new Point(423, 344), new Scalar(255, 49, 0, 255));
		Core.line(mat, new Point(423, 344), new Point(129, 336), new Scalar(255, 49, 0, 255));
		BufferedImage image = Mat2BufferedImage(mat);
		g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);

	}

	public Mat turnGray(Mat img)

	{
		Mat mat1 = new Mat();
		Imgproc.cvtColor(img, mat1, Imgproc.COLOR_RGB2GRAY);
		return mat1;
	}

	public Mat threash(Mat img) {
		Mat threshed = new Mat();
		int SENSITIVITY_VALUE = 100;
		Imgproc.threshold(img, threshed, SENSITIVITY_VALUE, 255, Imgproc.THRESH_BINARY);
		return threshed;
	}

	public Mat drawCircles(Mat bin, Mat original, Scalar scal, int type) {

		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
		// Imgproc.GaussianBlur(bin, bin, new Size(5,5), 0);
		// Imgproc.morphologyEx(bin, bin, Imgproc.MORPH_OPEN, element);
		// Imgproc.morphologyEx(bin, bin, Imgproc.MORPH_CLOSE, element);

		Imgproc.erode(bin, bin, element);
		Imgproc.dilate(bin, bin, element);

		Imgproc.dilate(bin, bin, element);
		Imgproc.erode(bin, bin, element);

		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(bin, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

		ArrayList<Moments> mu = new ArrayList<Moments>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mu.add(i, Imgproc.moments(contours.get(i), false));
			Moments p = mu.get(i);

			int x = (int) (p.get_m10() / p.get_m00());
			int y = (int) (p.get_m01() / p.get_m00());

			if (type == 1) {
				currentSpheroPos = new Point(x, y);
				System.out.println(currentSpheroPos);
			}

			int radius = 0;
			boolean b = true;

			for (int j = 0; b && y + j <= bin.cols(); j++) {
				if (bin.get(y + j, x)[0] >= 1.0) {
					radius++;
				} else {
					b = false;
				}
			}

			Core.circle(original, new Point(x, y), radius, scal);

		}

		return original;

	}

	public Point getCurrentPosition() {
		this.updatePosition();
		return currentSpheroPos;
	}

	public double calcDiffAngle(Point oldPos, Point newPos) {

		return Math.atan2((newPos.y - oldPos.y), (newPos.x - oldPos.x));

	}

	@Override
	public void run() {
		while (true) {
			if (this.camera.isOpened())
				this.repaint();
			// while (this.camera.isOpened()) {
			// this.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	public void updatePosition() {
		Mat mat = new Mat();

		if (camera.read(mat)) {
		}
		
		Mat binRot = new Mat();
		Mat binRot2 = new Mat();
		Mat hsv = new Mat();
		Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);


		Core.inRange(hsv, new Scalar(0.0, 150.0, 150.0), new Scalar(10.0, 255.0, 255.0), binRot);
		Core.inRange(hsv, new Scalar(169.0, 150.0, 150.0), new Scalar(179.0, 255.0, 255.0), binRot2);
		Core.addWeighted(binRot, 1.0, binRot2, 1.0, 0.0, binRot);
		
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
		// Imgproc.GaussianBlur(bin, bin, new Size(5,5), 0);
		// Imgproc.morphologyEx(bin, bin, Imgproc.MORPH_OPEN, element);
		// Imgproc.morphologyEx(bin, bin, Imgproc.MORPH_CLOSE, element);

		Imgproc.erode(mat, mat, element);
		Imgproc.dilate(mat, mat, element);

		Imgproc.dilate(mat, mat, element);
		Imgproc.erode(mat, mat, element);

		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(mat, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

		ArrayList<Moments> mu = new ArrayList<Moments>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mu.add(i, Imgproc.moments(contours.get(i), false));
			Moments p = mu.get(i);

			int x = (int) (p.get_m10() / p.get_m00());
			int y = (int) (p.get_m01() / p.get_m00());

				currentSpheroPos = new Point(x, y);
		}
	}
}