package Serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServeurIntf extends Remote{
	public List<String> getMessage(int indiceClient) throws RemoteException;
	public void setMessage(String msg) throws RemoteException;
	public void notifyMe(String name) throws RemoteException;
	public void stop(String name)throws RemoteException;
}