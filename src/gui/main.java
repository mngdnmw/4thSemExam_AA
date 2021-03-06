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
import behaviours.DirectionBehaviour;
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
			s.setKeepAlive(true);
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
			
			ThreadMessages(dis, dos, messageContainer);
			
			Behavior quit = new QuitBehaviour(messageContainer);
			Behavior sonic = new AvoidBehaviour(pilot, ultraSensor, RANGE_LIMIT);
			Behavior roam = new RoamBehaviour(pilot, messageContainer);
			Behavior stop = new StopBehaviour(pilot, messageContainer);
			Behavior dir = new DirectionBehaviour(pilot, messageContainer);
			Behavior changeDirection = new ChangeDirectionBehaviour(pilot, messageContainer);
			Behavior[] bArray = {stop,roam, changeDirection,sonic,dir, quit};

			Arbitrator arby = new Arbitrator(bArray);
			
			arby.go();

			// LCD.clear();
			// LCD.drawString("Client connected", 0, 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void ThreadMessages(final DataInputStream dis, final DataOutputStream dos,
			final MessageContainer mesCon) {
		new Thread() {
			public void run() {
				boolean done = false;
				while (!done) {
					try {
						String message = dis.readUTF();
						
						if (!message.equals("")) {
							lastMessage = message;
							mesCon.setMessage(message);
						}
						if (mesCon.command == MessageContainer.Command.QUIT) {
							done = true;
							break;
						}

						LCD.clear();
						LCD.drawString("Executing Command: " + lastMessage, 0, (LCD.SCREEN_HEIGHT / 2));

						dos.flush();
					} catch (IOException e) {
						System.exit(0);
					}

				}
			}
		}.start();
	}
}
