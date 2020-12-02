package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import Serveur.ServeurIntf;

public class Client {
	ServeurIntf Serveur;
	static Scanner sc = new Scanner(System.in);
	private String name;
	
	public Client(String name) throws MalformedURLException, RemoteException, NotBoundException {
		Serveur   = (ServeurIntf)Naming.lookup("//localhost/RmiServer");
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public static void main(String args[]) throws Exception {
		System.out.println("*********** Bienvenu dans la conversation ! ************\n");
		System.out.print("Entrer votre nom : ");
		String _name = sc.nextLine();
		System.out.println("\n ** Bienvenu " + _name  + " ! ** \n");
		
		Client chatClient = new Client(_name);       
		chatClient.Serveur.notifyMe(chatClient.getName());
		
        ListenningThread listenningThread = new ListenningThread(chatClient);
        listenningThread.start();
        
        String msg =sc.nextLine();    
        while(!msg.equals("q")) {
            if(!msg.equals("q") && !msg.equals("null"))chatClient.Serveur.setMessage(chatClient.getName() +" : "+ msg);
            msg = sc.nextLine();
        }
        
        listenningThread.interrupt();
        System.out.println("\n Aurevoir");
        sc.close();
    }

}
