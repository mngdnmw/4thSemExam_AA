package gui;

public class MessageContainer {
	private String _message = "";
	private boolean newMessage = false;
	
	public void setMessage(String message) {
		if(!_message.equals(message)) 
		{
			_message = message;
			newMessage = true;			
		}
	}
	public String getMessage() {
		return _message;
	}
	public void setNewMessageFalse() {
		newMessage = false;
	}
	public boolean newMessageExist() {
		return newMessage;
	}
}
