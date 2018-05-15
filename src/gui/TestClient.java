package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;

import wk14.Client;

public class TestClient {

	private static DataOutputStream dos;
	public static void main(String[] args) {


	try {
		Socket socket = new Socket("192.168.43.174", 5969);
//		Socket socket = new Socket("127.0.0.1", 19231);//for mocking
		DataInputStream dis = new DataInputStream(socket.getInputStream());
	    dos = new DataOutputStream(socket.getOutputStream());
	    
		//pw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
