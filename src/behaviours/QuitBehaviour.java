package behaviours;

import java.io.DataOutputStream;
import java.util.Random;

import gui.MessageContainer;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class QuitBehaviour  implements Behavior{
	private static int TRAVEL_SPEED = 8;
	private boolean suppressed;
	private RangeFinderAdapter rf;
	private int rangeLimit;
	private DataOutputStream dos;
	Navigator nav;
	MovePilot pilot;
	MessageContainer messageContainer;
	public QuitBehaviour(MessageContainer messageContainer) {
	this.messageContainer = messageContainer;
	}

	@Override
	public boolean takeControl() {
		if(messageContainer.getCommand() ==MessageContainer.Command.QUIT) {
			return true;
		}
		return false;
	}
	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}


