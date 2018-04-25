package gui;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Server {

	private static EV3 BRICK = (EV3) BrickFinder.getDefault();
    private static double WHEEL_DIAMETER = 2.7;
    private static double TRACK_WIDTH = 10.5;
	public static void main(String[] args)
    {
	try
	{
		
		LCD.clear();
		LCD.drawString("Press to begin", 0, 0);
		Button.waitForAnyPress();
		
		MovePilot pilot = new MovePilot(WHEEL_DIAMETER,TRACK_WIDTH,Motor.B,Motor.D);
		EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(BRICK.getPort("S1"));
		ServerSocket ss = new ServerSocket(5969);
	    
//	    LCD.clear();
//	    LCD.drawString("Waiting for client connection...", 0, 0);
	    System.out.println("Waiting for client connection...");
	    Socket s = ss.accept();
	    System.out.println("Client connected");
	    DataInputStream dis = new DataInputStream(s.getInputStream());
	    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	    MessageContainer messageContainer = new MessageContainer();
	    /* Behaviors: 
		 * Quit, 
		 * SonicAvoidance (Done), 
		 * Change Direction, 
		 * Roam
		 * Stop
		 	* Forward
		 	* Left
		 	* Right
		 	* Back
		 	* Wait
		*/
		//Behavior b1 = new MoveForward(pilot);
		// create Behavior Array
		Behavior[] bArray = {};
		Arbitrator arby = new Arbitrator(bArray);
		arby.go();
	    
	    
//	    LCD.clear();
//	    LCD.drawString("Client connected", 0, 0);
	    
	    boolean done = false;
	    while (! done)
	    {
		String message = dis.readUTF();
		LCD.drawString("Client says: " + message, 0, 2);
		System.out.println("Client says: " + message);
		dos.writeUTF("Connection Exist");
		dos.flush();
		
	
////	    LCD.clear();
////	    LCD.drawString("EV3 terminating", 0, 1);
//	    System.out.println("EV3 terminating");
	    }
	    } 
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    
    }
}
