package thread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Etat;
import utils.Tools;

/**
 * Cette classe s'occupe du traitement des requetes utilisateurs et des reponses
 * @author damien
 */
public class FtpRequest extends Thread{

	private Socket client;

	private ServerSocket dataServerSocket=null; //server socket pour l'envoi de fichier
	private Socket clientData=null; //socket pour l'envoi de fichier
	private int port=0; //port pour l'envoi de fichier

	private Etat etat; // assocition des valeurs de retours par etat

	private BufferedReader bufferedReader; //reader de la socket client

	private String requete; //requete recue par le client

	private String cheminCourant = Tools.path;

	private String user=null;


	/**
	 * Creer l'objet ftpRequest associe a un client
	 * @param client socket client
	 * @throws IOException
	 * 
	 */
	public FtpRequest(Socket client) throws IOException{

		this.client=client;
		this.etat=Etat.INIT;
		/* une fois connecte on lui envoi un message de bienvenue */
		this.etat.sendMessageOK(this.client);
		bufferedReader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
	}


	public void run(){

		while(client.isConnected() && !client.isClosed()){
			if((requete = Tools.lireLigne(client, bufferedReader)) != null)
				this.processRequest(requete);
			else
				break;

		}

		if(clientData!=null){

			Tools.fermerConnexion(clientData);
		}

		if(dataServerSocket!=null){
			Tools.fermerConnexion(dataServerSocket);
		}

	}


	/**
	 * effectue des traitements generaux concernant une 
	 *	requete entrante et delegue le traitement des commandes 
	 * @param requete la requete envoyee par le client
	 * 
	 */
	public void processRequest (String requete){


		String[] requeteSplit = requete.split(" ");
		boolean retour=false;

		switch(requeteSplit[0]){


		case "USER":
			retour=this.processUSER(requeteSplit[1]);
			break;

		case "PASS":
			retour=this.processPASS(requeteSplit[1]);
			break;

		case "PWD":
			retour=this.processPWD();
			break;

		case "TYPE":
			retour=this.processTYPE();
			break;

		case "PASV":
			retour=this.processPASV();
			break;

		case "LIST":
			retour=this.processLIST();
			break;

		case "CWD":
			retour=this.processCWD(requeteSplit[1]);
			break;

		case "CDUP":
			retour=this.processCDUP();
			break;

		case "RETR":
			retour=this.processRETR(requeteSplit[1]);
			break;

		case "STOR":
			retour=this.processSTOR(requeteSplit[1]);
			break;

		case "QUIT":
			retour=this.processQUIT();
			break;

		}

		if(!retour)
			Tools.sendErreurCommande(client);


	}


	/**
	 * se charge de traiter la commande USER
	 * @param parametre 
	 * @return boolean
	 */
	public boolean processUSER(String parametre){

		if(this.etat==Etat.INIT){
			this.etat=Etat.USER;

			if(!Tools.paramOK(parametre) || !Tools.isInUserList(parametre)){
				this.etat.sendMessageError(client);
				this.etat=Etat.INIT;
			}else{
				user = parametre;
				this.etat.sendMessageOK(client);
			}
			return true;
		}
		return false;
	}

	/**
	 * se charge de traiter la commande PASS 
	 * @param password le mot de passe envoye par le client
	 * @return boolean
	 */
	public boolean processPASS(String password){

		if(this.etat==Etat.USER){
			this.etat=Etat.PASS;

			if(!Tools.paramOK(password) || !Tools.isRightPassword(user, password)){
				this.etat.sendMessageError(client); 
				this.etat=Etat.USER;
			}else{
				this.etat.sendMessageOK(client);
				this.etat=Etat.IDENTIFIED;
			}
			return true;
		}
		return false;
	}

	/**
	 * se charge de traiter la commande PWD
	 *  
	 * @return boolean
	 */
	public boolean processPWD() {

		if(this.etat==Etat.IDENTIFIED){

			this.etat=Etat.PWD;
			this.etat.sendMessagePerso(client, "OK", this.cheminCourant);
			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;
	}

	/**
	 * se charge de traiter la commande TYPE
	 * @return boolean
	 */
	public boolean processTYPE() {

		if(this.etat==Etat.IDENTIFIED){

			this.etat=Etat.TYPE;
			this.etat.sendMessageOK(client);
			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;
	}

	/**
	 * se charge de traiter la commande PASV
	 * @return boolean
	 */
	public boolean processPASV() {
		@SuppressWarnings("unused")
		boolean portOk;
		portOk=false;

		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.PASV;


			/* on cherche un port pas utilise */
			for(int i=Tools.portDepart+1; i<Tools.portFin; i++){
				try {
					dataServerSocket = new ServerSocket(i);
					this.port = i;
					portOk=true;
					break;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("port "+i+" occupe");
				}
			}

			//pas trouve de port
			if(portOk=false){
				this.etat.sendMessageError(this.client);
				return true;
			}

			this.etat.sendMessageOK(this.client, this.port);

			try {
				clientData = dataServerSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.etat.sendMessageError(client);
			}

			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;
	}
	
	
	

	/**
	 * se charge de traiter la commande LIST 
	 *  
	 * @return boolean
	 */
	public boolean processLIST(){

		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.LIST;

			File base = new File(this.cheminCourant);
			File fileList[] = base.listFiles();
			String currentFile = "";

			this.etat.sendMessageOK(client);


			OutputStream output;
			DataOutputStream data;

			try {

				output = clientData.getOutputStream();
				data = new DataOutputStream(output);

				//envoie des donnees
				if(fileList.length > 0){
					//on formate les donnes
					for (int i = 0; i < fileList.length; i++){
						if(!fileList[i].isHidden()){
							//on recupere les fichiers non caches
							if (fileList[i].isFile())
							{
								currentFile = "+s" + fileList[i].length()+",m"+fileList[i].lastModified()/1000+",\011"+fileList[i].getName()+"\015\012";
							}
							else if(fileList[i].isDirectory())
							{
								currentFile = "+/,m"+fileList[i].lastModified()/1000+",\011"+fileList[i].getName()+"\015\012";
							}
							currentFile = currentFile + "\n";

							data.writeBytes(currentFile);
							data.flush();
						}				
					}			
				}

				this.etat=Etat.LIST2;

				//on libere le data socket  et le serveur socket
				clientData.close();
				Tools.fermerConnexion(dataServerSocket);


				etat.sendMessageOK(client);	
			} 
			catch (IOException e) {
				this.etat.sendMessageError(client);
			}

			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;


	}


	/**
	 * se charge de traiter la commande CWD 
	 *  
	 * @return boolean
	 */
	public boolean processCWD(String chemin){

		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.CWD;


			if(chemin.startsWith("/")){ //chemin absolu
				cheminCourant=chemin;	
			}else{	//chemin relatif
				if(cheminCourant.endsWith("/")) //on evite le double slash
					cheminCourant+=chemin;
				else
					cheminCourant+="/"+chemin;
			}

			if(cheminCourant.startsWith(Tools.path)){ //on verifie qu'on sort pas en dehors de notre system de fichier
				if(new File(cheminCourant).exists()){
					this.etat.sendMessageOK(client);
				}else{
					cheminCourant=Tools.path;
					this.etat.sendMessagePerso(client, "KO", "le fichier n'existe pas");
				}
			}else{ //le client sort du systeme de fichier => interdit
				cheminCourant=Tools.path;
				this.etat.sendMessagePerso(client, "KO", "acces non autorise");
			}

			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;


	}

	/**
	 * se charge de traiter la commande CDUP 
	 *  
	 * @return boolean
	 */
	public boolean processCDUP(){

		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.CDUP;

			if(!cheminCourant.equals(Tools.path)){
				int index=cheminCourant.lastIndexOf("/");
				cheminCourant=cheminCourant.substring(0, index);

				//on verifie qu'on a pas retire la racine
				if(cheminCourant.equals("")){
					cheminCourant=Tools.path;
				}
				
				this.etat.sendMessageOK(client);
			}else{
				this.etat.sendMessageError(client);
			}
			
			this.etat=Etat.IDENTIFIED;

		}else{

			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);

		}
		return true;


	}

	/**
	 * se charge de traiter la commande RETR 
	 * @param fichier
	 * @return boolean
	 */
	public boolean processRETR(String fichier){


		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.RETR;

			


			OutputStream output;
			DataOutputStream data;

			try {

				output = clientData.getOutputStream();
				data = new DataOutputStream(output);

				FileInputStream file;
				int i;

				if(new File(this.cheminCourant + "/" + fichier).exists()){
					this.etat.sendMessageOK(client);
					//on envoie le fichier
					file = new FileInputStream( this.cheminCourant + "/" + fichier );
					
					while( (i = file.read()) != -1){
						data.writeByte(i);
					}
	
					//on ferme le fichier, la socket et le dataserversocket
					file.close();
					clientData.close();
					Tools.fermerConnexion(dataServerSocket);
	
	
					this.etat=Etat.RETR2;
					this.etat.sendMessageOK(client);
				}else{
					this.etat.sendMessageError(client);
					clientData.close();
					Tools.fermerConnexion(dataServerSocket);
				}
					
				



			} catch (IOException e) {
				this.etat=Etat.RETR2;
				this.etat.sendMessageError(client);
			}


			this.etat=Etat.IDENTIFIED;

		}else{
			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);
		}
		return true;
	}

	/**
	 * se charge de traiter la commande STOR 
	 * @param fichier
	 * @return boolean
	 */
	public boolean processSTOR(String fichier){


		if(this.etat==Etat.IDENTIFIED){
			this.etat=Etat.STOR;

			this.etat.sendMessageOK(client);

			InputStream input;
			DataInputStream data;

			try {

				input = clientData.getInputStream();
				data = new DataInputStream(input);

				FileOutputStream file;
				int i;

				//on receptionne le fichier
				file = new FileOutputStream( this.cheminCourant + "/" + fichier );
				while( (i = data.read()) != -1){
					file.write(i);
					System.out.println("<server> on lit "+i);
				}

				//on ferme le fichier, la data socket et le data server socket
				file.close();
				clientData.close();
				Tools.fermerConnexion(dataServerSocket);


				this.etat=Etat.STOR2;
				this.etat.sendMessageOK(client);

			} catch (IOException e) {
				this.etat.sendMessageError(client);
			}

			this.etat=Etat.IDENTIFIED;

		}else{
			Etat tmp=Etat.IDENTIFIED;
			tmp.sendMessageError(client);
		}
		return true;
	}


	/**
	 * se charge de traiter la commande QUIT
	 * @throws IOException 
	 *  
	 * @return boolean
	 */
	public boolean processQUIT() {
		this.etat=Etat.QUIT;
		this.etat.sendMessageOK(client);
		Tools.fermerConnexion(client);

		if(clientData!=null){
			Tools.fermerConnexion(clientData);
		}


		if(dataServerSocket!=null){
			Tools.fermerConnexion(dataServerSocket);
		}
		return true;
	}
}
