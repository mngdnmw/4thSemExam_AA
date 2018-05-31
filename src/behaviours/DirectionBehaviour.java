package behaviours;

import gui.MessageContainer;
import gui.MessageContainer.Command;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class DirectionBehaviour implements Behavior {
	private static int TRAVEL_SPEED = 4;
	private boolean suppressed;
	MovePilot pilot;
	private MessageContainer messageContainer;

	public DirectionBehaviour(MovePilot pilot, MessageContainer messageContainer) {
		this.pilot = pilot;
		this.pilot.setLinearSpeed(TRAVEL_SPEED);
		this.messageContainer = messageContainer;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {
		Command com = messageContainer.getCommand();
		if (com == MessageContainer.Command.FORWARD || com == MessageContainer.Command.RIGHT
				|| com == MessageContainer.Command.LEFT || com == MessageContainer.Command.BACK) {
			return true;
		}
		return false;
	}

	@Override
	public void action() {

		Command com = messageContainer.getCommand();
		if (com == MessageContainer.Command.FORWARD) {
			pilot.forward();
		} else if (com == MessageContainer.Command.RIGHT) {
			pilot.rotate(-TRAVEL_SPEED);
		} else if (com == MessageContainer.Command.LEFT) {
			pilot.rotate(TRAVEL_SPEED);
		} else if (com == MessageContainer.Command.BACK) {
			pilot.backward();
		}

	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
