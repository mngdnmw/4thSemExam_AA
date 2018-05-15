package behaviours;

import gui.MessageContainer;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class TurnRightBehaviour implements Behavior {
	private static int TRAVEL_SPEED = 4;
	private boolean suppressed;
	MovePilot pilot;
	private MessageContainer messageContainer;

	public TurnRightBehaviour(MovePilot pilot, MessageContainer messageContainer) {
		this.pilot = pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		this.messageContainer = messageContainer;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {
		if (messageContainer.getCommand() == MessageContainer.Command.RIGHT) {
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.rotate(-1);
		while (!suppressed)
			Thread.yield();
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
