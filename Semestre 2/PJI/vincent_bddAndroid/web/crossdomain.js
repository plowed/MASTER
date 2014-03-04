var prefix = "https://www.lifl.fr/miny/WSE/libJS/";

function addScriptTag (url) {
	var baliseScript = document.createElement("script");
	var start=true;
	for (var i=1;i<arguments.length;i+=2) {
		if (start) {start=false;url+="?";}
		else url+="&";
		url+=arguments[i]+"="+arguments[i+1];
	}
	baliseScript.setAttribute("src",url);
	document.getElementsByTagName("body")[0].appendChild(baliseScript);
	baliseScript.onload=function () {baliseScript.parentNode.removeChild(baliseScript);};
}

//var scripts=["prototype.js","crossdomain_urls.js"];
/*var scripts=["crossdomain_urls.js"];
for (var i=0;i<scripts.length;i++)
	addScriptTag(prefix+scripts[i]);*/
addScriptTag("http://localhost/PJI/crossdomain_urls.js");

/*
<a href="javascript://le code javascript ˆ exŽcuter ...

var crossWSE = document.createElement("script");crossWSE.setAttribute("src","http://localhost/WSE/libJS/crossdomain.js");document.getElementsByTagName("body")[0].appendChild(crossWSE);

wse.joinSession("first_test");

listener = {}; 
listener.newMessageReceive = function (message)  { 
alert("boom");
}
wse.addListener(listener);

wse.sendMessage({ ah : "big"});
*/
