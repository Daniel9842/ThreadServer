package edu.escuelaing.arsw.threadServer.datagramServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//Code implemented with the document shared by the teacher.
/**
 * this class implements a datagram server
 * @author Daniel Santiago Ducuara Ardila
 *
 */
public class DatagramTimeServer {

	DatagramSocket socket;
	/**
	 * this method creates the connection of the datagram to the port
	 */
	public DatagramTimeServer() {
		try {
			socket = new DatagramSocket(4445);
		} catch (SocketException ex) {
			Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	/**
	 * this method starts the server and returns the time to the client every 5 seconds
	 */
	public void startServer(){
		byte[] buf = new byte[256];
		Thread myThreadTime = new Thread();
		while (true) {
			try {
				myThreadTime.sleep(5000);
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String dString = new Date().toString();
				buf = dString.getBytes();
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);
			} catch (IOException ex) {
				Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * this method makes a call to create and start the server.
	 * @param args empty parameter of main method
	 */
	public static void main(String[] args) {
		DatagramTimeServer ds = new DatagramTimeServer();
		ds.startServer();
	}
}

