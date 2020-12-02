package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class callBackClient extends UnicastRemoteObject implements callBackClientIntf{

	private static final long serialVersionUID = 1L;


	public callBackClient() throws RemoteException {
		super();
	}
	
	
	@Override
	public void receiveNewMessage(String name,String msg) {
		System.out.println(name + " : " + msg);
	}
	

}
