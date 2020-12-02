package Client;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import Serveur.ServeurIntf;

public class Client{

	static Scanner sc = new Scanner(System.in);
	ServeurIntf Serveur; 
	private String name;
	
	public  Client(String name) throws RemoteException, MalformedURLException, NotBoundException {
		Serveur = (ServeurIntf)Naming.lookup("//localhost/RmiServer");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static void main(String args[]) throws Exception {

		System.out.println("*********** Bienvenu dans la conversation ! ************\n");
		System.out.print("Entrer votre nom : ");
		String _name = sc.nextLine();
						
		callBackClient CBClient = new callBackClient();
		
        Client client = new Client(_name);
        
        while(!client.Serveur.isNewUser(_name)) {
    		System.out.print("\n*** Un user possède déjà ce nom ****\n");
    		System.out.print("Entrer votre nom : ");
        	_name = sc.nextLine();
        }
        client.setName(_name);
        
		System.out.println("\n ** Bienvenu " + client.getName() + " ! ** \n");
		
		if(!client.Serveur.oldMessages().isEmpty()) {
			System.out.println("\n Vous avez de nouveaux messages \n");
			for(String message : client.Serveur.oldMessages()) {
				System.out.println(message);
			}
		}
		
	
	    client.Serveur.notifyMe(client.getName());
        client.Serveur.addCBClient(client.getName(),CBClient);
        
        String msg = sc.nextLine();	
        
        try {
            while(!msg.equals(null) && !msg.equals("q")) {
                if(!msg.equals(null) && !msg.equals("q") )client.Serveur.sendMessage(client.getName(),msg);
                msg = sc.nextLine();
            }
            
            client.Serveur.close(client.getName());
            sc.close();
    		System.out.println("\n*********** Au revoir ! ************\n");
		} catch (ConnectException e) {
			System.out.println("\n *** Le serveur est coupé ***\n");
            sc.close();
    		System.out.println("\n*********** Au revoir ! ************\n");
		}

    
	}

}