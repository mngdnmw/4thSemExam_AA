package behaviours;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import gui.MessageContainer;

@SuppressWarnings("deprecation")
public class RoamBehaviour implements Behavior {
	private static int TRAVEL_SPEED = 10;
	private boolean suppressed;
	MovePilot pilot;
	private MessageContainer messageContainer;
	public RoamBehaviour(MovePilot pilot, MessageContainer messageContainer) {
		this.pilot = pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		this.messageContainer = messageContainer;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {

		return true;
	}

	@Override
	public void action() {

		pilot.forward();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
