package Client;

import java.rmi.RemoteException;
import java.util.List;

public class ListenningThread extends Thread {
	
	Client chatClient;
	private int i;
	
	
	public ListenningThread(Client chatClient) {
		this.chatClient = chatClient; 
		this.i = 0;
	}
	
	@Override
	public void run() {
        try {
			while(!Thread.currentThread().isInterrupted()) {
				List<String> msg = chatClient.Serveur.getMessage(i);
				for (String message : msg) {
					System.out.println(message);
					i++;
				}
			}
		}catch (RemoteException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
}
