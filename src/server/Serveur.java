package server;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Classe de cr�ation du serveur
 * @author Carine Kong, Ghislain Rodrigues, Rony Dray
 * @version 1
 */
public class Serveur {

	private VueServeur vueServ;
	private ServImpl st;
	private Registry registry = null;
	

	/**
	 * Constructeur pour cr�er le serveur avec l'annuaire
	 * @throws RemoteException
	 */
	public Serveur() throws RemoteException {

		st = new ServImpl();
		vueServ = new VueServeur(this);
		demarrer();
	}

	/**
	 * 
	 * @return le service du serveur
	 */
	public ServImpl getSt() {
		return st;
	}
	
	/**
	 * 
	 * @return la vue du serveur
	 */
	public VueServeur getVueServeur(){
		return vueServ;
	}
	
	/**
	 * Demarre le serveur en se connectant au service. 
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void demarrer(){
		vueServ.setEtat(Globals.CONNEXION_WS);
		st = new ServImpl();
		if(st.getWsClient().estAccessible()){
			vueServ.setEtat(Globals.AVAILABLE_WS);
			Serv stub;
			try {
				stub = (Serv) UnicastRemoteObject.exportObject(st, 0);
				vueServ.setEtat(Globals.CREATE_REGISTRY);
				if(registry == null){
					registry = LocateRegistry.createRegistry(Integer.parseInt(Globals.DEFAULT_PORT));
				}
				vueServ.setEtat(Globals.ABONNEMENT);
				registry.rebind("puissance4", stub);
				vueServ.setEtat(Globals.HOST_OK);
			} catch (AccessException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
				vueServ.setEtat(Globals.DUPLICATE_HOST);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		else{
			vueServ.setEtat(Globals.UNAVAILABLE_WS);
		}
	}
	
}
