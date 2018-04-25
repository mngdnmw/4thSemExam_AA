package behaviours;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import wk14.MessageContainer;

@SuppressWarnings("deprecation")
public class RoamBehaviour implements Behavior {
	private static int TRAVEL_SPEED = 4;
	private boolean suppressed;
	DifferentialPilot pilot;
	private MessageContainer messageContainer;
	private boolean isWaiting;
	public RoamBehaviour(DifferentialPilot pilot, MessageContainer messageContainer) {
		this.pilot = pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		this.messageContainer = messageContainer;
		suppressed = false;
		isWaiting = false;
	}

	@Override
	public boolean takeControl() {
	
		
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.forward();
		while (!suppressed)
			Thread.yield();
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
