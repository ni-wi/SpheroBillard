package ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.*;



@SuppressWarnings("serial")
public class VideoCamera extends JPanel
{
    VideoCapture camera; 

    public VideoCamera(VideoCapture cam) 
    {

        camera  = cam; 

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        // TODO Auto-generated method stub

    }
    public BufferedImage Mat2BufferedImage(Mat m)
    {

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1)
        {
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

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Mat mat = new Mat();

        if( camera.read(mat))
        {

        }
        
        Mat hsv = new Mat();
        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);
        
        //H = 81,82 S = 67,84 V = 89,02
  
      Core.inRange(hsv,new Scalar(61.0 / 2.0 , 37.0 * 2.55 , 59.0 * 2.55 ), new Scalar(121.0 / 2.0, 97.0 * 2.55, 100.0 * 2.55),hsv);
      //Core.inRange(hsv,new Scalar(40 , 160 , 200 ), new Scalar(60, 200, 250),hsv);

        //System.out.println(hsv.get(hsv.rows() / 2, hsv.cols() / 2)[0] +  " " + hsv.get(hsv.rows() / 2, hsv.cols() / 2)[1] + " " + hsv.get(hsv.rows() / 2, hsv.cols() / 2)[2]);
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(50,50));
        Imgproc.dilate(hsv, hsv, element);
        Imgproc.erode(hsv, hsv, element);
        BufferedImage image = Mat2BufferedImage(hsv);

        g.drawImage(image,10,10,image.getWidth(),image.getHeight(), null);
        

    }
    public Mat turnGray( Mat img)

    {
        Mat mat1 = new Mat();
        Imgproc.cvtColor(img, mat1, Imgproc.COLOR_RGB2GRAY);
        return mat1;
    }
    public Mat threash(Mat img)
    {
        Mat threshed = new Mat();
        int SENSITIVITY_VALUE = 100;
        Imgproc.threshold(img, threshed, SENSITIVITY_VALUE,255,Imgproc.THRESH_BINARY);
        return threshed;
    }


}