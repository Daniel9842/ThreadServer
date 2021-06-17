package edu.escuelaing.arsw.threadServer;

import java.util.Date;

import edu.escuelaing.arsw.threadServer.datagramServer.DatagramTimeServer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ThreadApp.
 */
public class ThreadTest 
    extends TestCase
{
	/**
	 * this test verifies that the time that the server returns is the indicated time
	 */
    public void testDatagram() {
    	DatagramTimeServer datagramServ = new DatagramTimeServer();
    	String date = datagramServ.getDate();
    	assertEquals(new Date().toString(),date);
    }
}
