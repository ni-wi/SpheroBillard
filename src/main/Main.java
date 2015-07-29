package main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import ui.UIStart;
import ui.VideoCamera;

public class Main
{

public Main()
{

    // TODO Auto-generated constructor stub
}

/**
 * @param args
 * @throws IOException
 */
public static void main(String[] args) throws IOException
{

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    //VideoCapture camera = new VideoCapture("http://192.168.0.7/image.jpg");
    VideoCapture camera = new VideoCapture(0);
    
    if (camera.isOpened()) 
    {
        System.out.println("Video is captured");
    }
    else
    {
        System.out.println("");
    }
    VideoCamera cam = new VideoCamera(camera);
    UIStart ui = new UIStart(cam);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    frame.add(cam);
    frame.setSize(800,800);  
    frame.setVisible(true);

//    Thread camThread = new Thread(new VideoCamera(camera));
//    camThread.start();
    Thread cameraThread = new Thread()
	{
		@Override
		public void run()
		{
			  try {
				while(camera.isOpened())
				    {
				        cam.repaint();
				    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	cameraThread.start();

    
}



  }
