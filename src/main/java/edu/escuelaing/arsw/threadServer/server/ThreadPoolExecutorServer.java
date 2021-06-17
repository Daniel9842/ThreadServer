package edu.escuelaing.arsw.threadServer.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class will carry the thread that creates the servers
 * @author Daniel Santiago Ducuara Ardila
 *
 */
public class ThreadPoolExecutorServer {
	/**
	 * this method creates the necessary threads for execution
	 * @param args empty parameter of main method
	 */
	public static void main(String[] args) {
		ExecutorService ThreadPool = Executors.newSingleThreadExecutor();
		int i= 0;
		while(i<100) {
			ThreadPool.execute(new HttpServerThread());
			i++;
		}
		ThreadPool.shutdown();
	}
	

}