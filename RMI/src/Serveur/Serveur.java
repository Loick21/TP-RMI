package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Serveur extends UnicastRemoteObject implements ServeurIntf{
	
	private static final long serialVersionUID = 1L;
	Scanner sc = new Scanner(System.in);
	public List<String> message;
	
	public Serveur() throws RemoteException{
		super(0);
		message = new ArrayList<>();
	}
	
	@Override
	public synchronized void notifyMe(String name) {
		message.add(name + " a joint la conversation");
	}
	
	@Override
	public synchronized void stop(String name) {
		message.add(name + " a quitté la conversation");
	}
	
	@Override
	public synchronized  List<String> getMessage(int indiceClient) throws RemoteException {

		
		List<String> msg = new ArrayList<>();
		
		for (int i = indiceClient; i < message.size(); i++) {
			msg.add(message.get(i));
		}
		return msg;
	}

	@Override
	public synchronized void setMessage(String msg) throws RemoteException {
		message.add(msg);
		for (String message : message) {
			System.out.println(message);
		}
	}
	
	
	 public static void main(String args[]) throws Exception {
	        try { 
	            LocateRegistry.createRegistry(1099); 
	        } catch (RemoteException e) {}
	        Serveur chatServeur = new Serveur();
	        Naming.rebind("//localhost/RmiServer", chatServeur);
	        System.out.println("Serveur pret!");
	    }

}
