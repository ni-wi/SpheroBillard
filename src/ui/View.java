package ui;

//imports
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class View extends JFrame
{/*
	 public static void main (String args[]) throws InterruptedException{
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	        JPanelOpenCV t = new JPanelOpenCV();
	        VideoCapture camera = new VideoCapture(0);

	        Mat frame = new Mat();
	        camera.read(frame); 

	        if(!camera.isOpened()){
	            System.out.println("Error");
	        }
	        else {                  
	            while(true){        

	                if (camera.read(frame)){

	                    BufferedImage image = t.MatToBufferedImage(frame);

	                    t.window(image, "Original Image", 0, 0);

	                    t.window(t.grayscale(image), "Processed Image", 40, 60);

	                    //t.window(t.loadImage("ImageName"), "Image loaded", 0, 0);

	                    break;
	                }
	            }   
	        }
	        camera.release();
	    }
*/
}
