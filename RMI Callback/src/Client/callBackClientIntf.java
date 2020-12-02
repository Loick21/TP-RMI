package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface callBackClientIntf extends Remote {
	
	public void receiveNewMessage(String name,String msg) throws RemoteException;

}
