var crossWSE = document.createElement("script");
crossWSE.setAttribute("src","http://www.lifl.fr/miny/WSE/libJS/crossdomain.js");
document.getElementsByTagName("body")[0].appendChild(crossWSE);

function wseReady() {
	// Se connecte à la session Android
	
	wse.joinSession("BDDandroid2");

	// indique en haut de la page si WSE est utilisable ou non
	// si ça ne marche pas, essayez de recharger la page
	
	var baliseHTML_WSE =document.getElementById("WSE");
	baliseHTML_WSE.style.color="green";
	baliseHTML_WSE.firstChild.data="WSE READY - connected on session '"+wse.name+"'";

	// Crée un auditeur à WSE dont la méthode newMessageReceive sera invoquée
	// à chaque fois qu'un message sera envoyé sur la session Android
	
	listener = {}; 
	listener.newMessageReceive = function (message)  { 
		if("action" in message){
			switch(message.action){
				case "structureBDD":
					if("table" in message){
						displayEntity(message.table);
					}
				break;
			}
		}
	}
	wse.addListener(listener);
	
}

// Pour envoyer un message sur le bus
// il suffit d'utiliser la methode wse.sendMessage avec un message 
// Exemple :
// wse.sendMessage({ monAction : "ajouter", leType : "QQchose", params : { x :3, y : 4}});
