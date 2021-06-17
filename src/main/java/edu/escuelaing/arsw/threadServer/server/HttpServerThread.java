package edu.escuelaing.arsw.threadServer.server;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


//Code implemented in class with the teacher

/**
 * this class creates a server that returns html files and images
 * @author Daniel Santiago Ducuara Ardila
 *
 */
public class HttpServerThread implements Runnable {
	/**
	 * this method makes a request to the server
	 * @param clientSocket is the connection to the server
	 * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
	 */
	public void processRequest(Socket clientSocket) throws IOException {
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String inputLine;
		String method="";
		String path="";
		String version="";
		List<String> headers = new ArrayList<String>();
		while ((inputLine = in.readLine()) != null) {
			if(method.isEmpty()) {
				String[] requestStrings = inputLine.split(" ");
				method = requestStrings[0];
				path = requestStrings[1];
				version = requestStrings[2];
				System.out.println("Request: " + method + "" + path + "" + version);
			}else {
				System.out.println("Header: " + inputLine);
				headers.add(inputLine);
			}
			
			if (!in.ready()) {
				break;
			}
		}
		String responseMsg = createTextResponse(path);
		out.println(responseMsg);
		out.close();
		in.close();
		clientSocket.close();
	}
	/**
	 * this method creates a response with the data from the web page
	 * @param path the website address
	 * @return a string with the page content
	 */
	public String createTextResponse(String path) {
		String type = "text/html";
		if(path.endsWith(".css")) {
			type = "text/css";
		}else if(path.endsWith(".js")) {
			type = "text/javascript";
		}
		Path file = Paths.get("./TestHttpServer"+path);
		Charset charset = Charset.forName("UTF-8");
		String outmsg="";
		try(BufferedReader reader = Files.newBufferedReader(file,charset)){
			String line = null;
			while((line=reader.readLine())!=null) {
				System.out.println(line);
				outmsg = outmsg + "\r\n"+ line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return"HTTP/1.1 200 OK\r\n"
                + "Content-Type: "+ type +"\r\\n"
                + "\r\n"
                + outmsg;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(35000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}
		Socket clientSocket = null;
		boolean running=true;
		while (running) {
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			try {
				processRequest(clientSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
