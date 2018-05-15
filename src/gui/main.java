package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
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
//Behaviors coded for usage with the robot
import behaviours.AvoidBehaviour;
import behaviours.BackBehaviour;
import behaviours.ChangeDirectionBehaviour;
import behaviours.GoForwardBehaviour;
import behaviours.QuitBehaviour;
import behaviours.RoamBehaviour;
import behaviours.StopBehaviour;
import behaviours.TurnLeftBehaviour;

public class main {

	private static EV3 BRICK = (EV3) BrickFinder.getDefault();
	private static double WHEEL_DIAMETER = 2.7;
	private static double TRACK_WIDTH = 10.5;
	private static double RANGE_LIMIT = 5.0;
	private static String lastMessage = "";
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {

			LCD.clear();
			LCD.drawString("Press to begin", 0, 0);
			Button.waitForAnyPress();
			MovePilot pilot = new MovePilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.B, Motor.D);
			EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(BRICK.getPort("S1"));
			ServerSocket ss = new ServerSocket(5969);

			LCD.clear();
			String ipAddress = "" + Inet4Address.getLocalHost();
			String[] ip = ipAddress.split("/");
			ipAddress = ip[1];
			System.out.println("Greetings");
			System.out.println("Connect to: ");
			System.out.println(ipAddress);
			Socket s = ss.accept();
			System.out.println("Client connected");
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			MessageContainer messageContainer = new MessageContainer();
			/*
			 * Behaviors: Quit, (Done SonicAvoidance (Done), Change Direction (Done), Roam
			 * (DONE) Stop (DONE) Forward (DONE) Left (DONE) Right (DONE) Back (DONE) Wait
			 * (Use Stop)
			 */
			// Behavior b1 = new MoveForward(pilot);
			// create Behavior Array
			Behavior quit = new QuitBehaviour(messageContainer);
			Behavior sonic = new AvoidBehaviour(pilot, ultraSensor, RANGE_LIMIT);
			Behavior roam = new RoamBehaviour(pilot, messageContainer);
			Behavior stop = new StopBehaviour(pilot, messageContainer);
			Behavior forward = new GoForwardBehaviour(pilot, messageContainer);
			Behavior turnLeft = new TurnLeftBehaviour(pilot, messageContainer);
			Behavior turnRight = new TurnLeftBehaviour(pilot, messageContainer);
			Behavior back = new BackBehaviour(pilot, messageContainer);
			Behavior changeDirection = new ChangeDirectionBehaviour(pilot, messageContainer);
			Behavior[] bArray = { quit, sonic, roam, stop, forward, turnLeft, turnRight, back, changeDirection };
			Arbitrator arby = new Arbitrator(bArray);
			arby.go();

			// LCD.clear();
			// LCD.drawString("Client connected", 0, 0);
			messageContainer.setMessage("Roam");
			boolean done = false;
			while (!done) {
				String message = dis.readUTF();
				if (!message.equals("")) {
					lastMessage = message;
					messageContainer.setMessage(message);
				}
				LCD.clear();
				LCD.drawString("Executing Command: " + lastMessage, 0, (LCD.SCREEN_HEIGHT/2));
				dos.writeUTF("Connection Exist");
				dos.flush();

				//// LCD.clear();
				//// LCD.drawString("EV3 terminating", 0, 1);
				// System.out.println("EV3 terminating");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
