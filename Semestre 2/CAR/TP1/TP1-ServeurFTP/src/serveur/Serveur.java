package serveur;

import java.io.IOException;
import thread.ThreadServeur;
import utils.Tools;

/**
 * Classe principale contenant le main et lancant le serveur
 * @author damien
 */
public class Serveur {

	/**
	 * Lance un serveur
	 */
	public Serveur(String path){
		Tools.path=path;

		Tools.userPass.put("toto", "toto");
		Tools.userPass.put("damien", "a");
		Tools.userPass.put("admin", "password");
		Tools.userPass.put("a", "a");

		//lance un serveur ftp sur un port entre 1025 et 1125
		for(int i=Tools.portDepart; i<Tools.portFin; i++){
			try {
				ThreadServeur serveur = new ThreadServeur(i);
				serveur.start();
				System.out.println("serveur lance sur le port "+i);
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("impossible de demarrer le serveur sur le port "+i);
			}
		}

	}

	public static void main(String agrs[]){
		new Serveur(agrs[0]);
	}



}
