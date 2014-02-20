package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Tools;

/**
 * Serveur FTP assurant le connections de client et l'echange de message
 * avec celui-ci
 * @author damien
 */
public class ThreadServeur extends Thread {
	

	private int port;
	private ServerSocket serveur;
	
	/**
	 * Creation d'un serveur FTP a partir d'un port
	 * @param port le port que le serveur reservera
	 * @throws IOException
	 */
	public ThreadServeur(int port) throws IOException{
		this.port=port;
		this.serveur=new ServerSocket(this.port);
	}
	
	/**
	 * Lancement du serveur
	 */
	public void run(){
		
		Socket client = null;
				
		
			/* on attend le connexion d'un client */
			try {
				while ( (client=serveur.accept())!= null ){
					
					
					/* on demarre le protocole de requetes/reponse dans un thread */
					try{
						FtpRequest requete = new FtpRequest(client);
						requete.start();
					}catch(IOException e){
						System.out.println("impossible de demarrer le protocole de " +
								"requetes/reponses");
						Tools.send(client, "une erreur innatendue c'est produite," +
								"veuillez vous reconnecter");
						Tools.fermerConnexion(client);
					}
					
					
					/* on revient a l'attente d'un nouveau client */
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("probleme lors de l'acceptation d'un client");
			}
					
	}
		
}
