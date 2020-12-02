package Serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Client.callBackClientIntf;

public class Serveur extends UnicastRemoteObject implements ServeurIntf {

	private static final long serialVersionUID = 1L;
	public List<String> messages;
//	public List<callBackClientIntf> CBClients;
	public HashMap<String, callBackClientIntf> CBClientHs;
	
	public Serveur() throws RemoteException, MalformedURLException, NotBoundException{
		super(0);
		messages   = new ArrayList<>();
//		CBClients  = new ArrayList<>();
		CBClientHs = new HashMap<>();
	}
	
	//Vérifie s'il ya un utilisateur avec le même nom dans la HashMap 
	@Override
	public synchronized boolean isNewUser(String name) throws RemoteException {
		if(CBClientHs.containsKey(name))return false;
		else return true;
	}
	
	//Envoie l'historique de la conversation aux autres Users
	@Override
	public List<String> oldMessages() throws RemoteException {
		List<String> msg = new ArrayList<>();
		for(String message : messages) {
			msg.add(message);
		}
		return msg;
	}
	
	//Notifie l'aarivé d'un nouveau Client aux autres utilisateurs
	@Override
	public void notifyMe(String name) throws RemoteException {
		String msg = name + " a rejoint la conversation";
		String _name = "";
		List<String>notified = new ArrayList<>();
		try {
	        for (Map.Entry mapentry : CBClientHs.entrySet()) {
	        	_name = (String)mapentry.getKey();
	        	if(!mapentry.getKey().equals(name)) {
	        		((callBackClientIntf) mapentry.getValue()).receiveNewMessage(name, msg);
	        	}
	        	notified.add((String)mapentry.getKey());
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(_name);
			CBClientHs.remove(_name);
			
	        for (Map.Entry mapentry : CBClientHs.entrySet()) {
	        	if(!mapentry.getKey().equals(name) && !notified.contains(mapentry.getKey())) {
	        		((callBackClientIntf) mapentry.getValue()).receiveNewMessage(name, msg);
	        	}
	        }
		}
		
	}
	
	//Ajoute un Client dans la Liste
	@Override
	public synchronized void addCBClient(String name, callBackClientIntf CBClient) throws RemoteException {
		CBClientHs.put(name, CBClient);
	}
	
	// Envoi de messages avec gestion de deconnexion
	@Override
	public synchronized  void sendMessage(String name,String msg) throws RemoteException {
		String _name = "";
		messages.add(name + " : " + msg);
		try {
	        for (Map.Entry mapentry : CBClientHs.entrySet()) {
        		_name = (String) mapentry.getKey();
	        	if(!mapentry.getKey().equals(name)) {
	        		((callBackClientIntf) mapentry.getValue()).receiveNewMessage(name, msg);
	        	}
	        }	
		} catch (Exception e) {	
			System.out.println(e.getMessage());
			System.out.println(_name);
			CBClientHs.remove(_name);
		}
	}
	

	// Fermé la connexion Client
	@Override
	public void close(String name) throws RemoteException {
		String msg = "a quitté la convervation.";
		sendMessage(name,msg);
		CBClientHs.remove(name);
	}
	
	
	 public static void main(String args[]) throws Exception {
	        try { 
	            LocateRegistry.createRegistry(1099); 
	        } catch (RemoteException e) {
	        }
	        Serveur chatServeur = new Serveur();
	        Naming.rebind("//localhost/RmiServer", chatServeur);
	        System.out.println("Serveur pret!");
	 }


	 /*
	  * 
	  * Utilisation de la List au lieu de la HashMap pour les opérations
	  *
	  *
	  **/

	 /*
	@Override
	public void notifyMe(String name) throws RemoteException {
		String msg = name + " a join la conversation";
		messages.add(msg);
		for (callBackClientIntf clients : CBClients) {
			clients.receiveNewMessage(name, " a join la conversation");
		}
	}
		
	@Override
	public synchronized void addCBClient(callBackClientIntf CBClient) throws RemoteException {
		CBClients.add(CBClient);
	}
		
	@Override
	public void sendMessage(String name,String msg) throws RemoteException {
		messages.add(msg);
		for (callBackClientIntf clients : CBClients) {
			clients.receiveNewMessage(name, msg);
		}
	} 
	
		@Override
	public  void sendMessage(String name,String msg) throws RemoteException {
		messages.add(name + " : " + msg);
		
	     for (Map.Entry mapentry : CBClientHs.entrySet()) {
	        if(!mapentry.getKey().equals(name)) {
	        	((callBackClientIntf) mapentry.getValue()).receiveNewMessage(name, msg);
	        }
	     }	
	} 
	
	
	*/

	 


}
