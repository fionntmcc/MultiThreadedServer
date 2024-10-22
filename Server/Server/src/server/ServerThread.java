package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String msg;
	int num1, num2, result;
	
	public ServerThread(Socket myConnection)
	{
		connection = myConnection;
	}
	
	public void run()
	{
		try
		{
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			
			//Time to start communication with the client.....
			do
			{
				sendMessage("Please enter num1");
				msg = (String)in.readObject();
				num1 = Integer.parseInt(msg);
				
				sendMessage("Please enter num2");
				msg = (String)in.readObject();
				num2 = Integer.parseInt(msg);
				
				result = num1 + num2;
				
				sendMessage("Result is "+result);
				sendMessage("Enter 1 to repeat");
				
				msg = (String)in.readObject();
			
			}while(msg.equalsIgnoreCase("1"));
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			try
			{
				in.close();
				out.close();
				connection.close();
			}
			
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}	
		}
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}