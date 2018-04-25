
import java.io.DataOutputStream;
import java.util.Random;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

@SuppressWarnings("deprecation")
public class SonicAvoidanceBehaviour implements Behavior{
	private static int TRAVEL_SPEED = 8;
	private boolean suppressed;
	private RangeFinderAdapter rf;
	private int rangeLimit;
	private DataOutputStream dos;
	Navigator nav;
	DifferentialPilot pilot;
	public SonicAvoidanceBehaviour(DifferentialPilot pilot ,EV3UltrasonicSensor sonica, int rangeLimit, DataOutputStream dos) {
		this.pilot=pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		suppressed = false;
		rf = new RangeFinderAdapter(sonica.getDistanceMode());
		this.rangeLimit= rangeLimit;
		this.dos = dos;
		
	}

	@Override
	public boolean takeControl() {

		if(rf.getRange() < rangeLimit) {
		dos.writeUTF("wallFound");
		dos.flush();
		}
		return(rf.getRange() < rangeLimit);
	}
	@Override
	public void action() {
		suppressed = false;
		Sound.buzz();
		dos.writeUTF("wallFound");
		dos.flush();
		pilot.travel(-20,true);
		while (!suppressed && pilot.isMoving())
			Thread.yield();
		Random rand = new Random();
		int angle = rand.nextInt(90)-90;
		pilot.rotate(angle);
		while (!suppressed && pilot.isMoving())
			Thread.yield();
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}
