package edu.escuelaing.arsw.threadServer.datagramServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Code implemented with the document shared by the teacher.
/**
 * this class implements a datagram client
 * @author Daniel Santiago Ducuara Ardila
 *
 */
public class DatagramTimeClient {
	/**
	 * this method creates the connection to the indicated port, sends the output packet and waits for the server's response.
	 * @param args empty parameter of main method
	 */
	public static void main(String[] args) {
		while (true) {
			try {
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = new byte[256];
				InetAddress address = InetAddress.getByName("127.0.0.1");
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
				socket.send(packet);
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("The Date is: " + received + "from DatagramTimeServer");
			} catch (SocketException ex) {
				Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
			} catch (UnknownHostException ex) {
				Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
