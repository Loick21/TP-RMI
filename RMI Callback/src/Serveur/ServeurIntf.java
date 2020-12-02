package Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Client.callBackClientIntf;

public interface ServeurIntf extends Remote {
	
	public boolean isNewUser(String name)throws RemoteException;
	public  List<String> oldMessages()throws RemoteException;
	public void notifyMe(String name) throws RemoteException;
	public void sendMessage(String name,String msg) throws RemoteException;
//	public void addCBClient(callBackClientIntf CBClient) throws RemoteException;
	public void addCBClient(String name,callBackClientIntf CBClient) throws RemoteException;

	public void close(String name) throws RemoteException;
}
