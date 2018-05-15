package behaviours;

import gui.MessageContainer;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class GoForwardBehaviour  implements Behavior {
	private static int TRAVEL_SPEED = 4;
	private boolean suppressed;
	MovePilot pilot;
	private MessageContainer messageContainer;
	public GoForwardBehaviour(MovePilot pilot, MessageContainer messageContainer) {
		this.pilot = pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		this.messageContainer = messageContainer;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {	if(messageContainer.getCommand() == MessageContainer.Command.FORWARD) {
		return true;
		}
		return false;
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
