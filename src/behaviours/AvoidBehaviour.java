package behaviours;

import java.io.DataOutputStream;
import java.util.Random;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class AvoidBehaviour implements Behavior{
	private static int TRAVEL_SPEED = 8;
	private boolean suppressed;
	private RangeFinderAdapter rf;
	private double rangeLimit;
	private DataOutputStream dos;
	Navigator nav;
	MovePilot pilot;
	public AvoidBehaviour(MovePilot pilot ,EV3UltrasonicSensor sonica, double rangeLimit) {
		this.pilot=pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		suppressed = false;
		rf = new RangeFinderAdapter(sonica.getDistanceMode());
		this.rangeLimit= rangeLimit;
		
	}

	@Override
	public boolean takeControl() {

		return(rf.getRange() < rangeLimit);
	}
	@Override
	public void action() {
		suppressed = false;
		Sound.buzz();
		pilot.travel(-20,true);
		while (!suppressed && pilot.isMoving())
			Thread.yield();
		Random rand = new Random();
		int angle = rand.nextInt(90)-90;
		pilot.rotate(angle);



	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}
