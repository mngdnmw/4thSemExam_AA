package gui;

public class MessageContainer {

	/*
	 * Behaviors: Quit, SonicAvoidance (Done), Change Direction, Roam Stop Forward
	 * Left Right Back Wait
	 */
	public enum Command {
		QUIT, CHANGEDIR, ROAM, STOP, FORWARD, LEFT, RIGHT, BACK, WAIT, DO_NOTHING
	}

	public Command command;

	public void setMessage(String message) {
		switch (message) {
		case ("Quit"):
			command = Command.QUIT;
			break;
		case ("ChangeDirection"):
			command = Command.CHANGEDIR;
			break;
		case ("Roam"):
			command = Command.ROAM;
			break;
		case ("Stop"):
			command = Command.STOP;
			break;
		case ("Forward"):
			command = Command.FORWARD;
			break;
		case ("Right"):
			command = Command.RIGHT;
			break;
		case ("Left"):
			command = Command.LEFT;
			break;
		case ("Back"):
			command = Command.BACK;
			break;
		default:
			command = Command.DO_NOTHING;
			break;
		}

	}

	public Command getCommand() {
		return command;
	}



}
