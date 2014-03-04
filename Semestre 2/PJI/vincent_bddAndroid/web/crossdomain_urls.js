urls = { wse : {} };
//urls.wse.root = "https://www.lifl.fr/miny/WSE/";
urls.wse.root = "http://localhost/wse/WSE/";
urls.wse.getSessions = urls.wse.root + "getSessions.php";
urls.wse.traceSession = urls.wse.root + "traceSession.php";

addScriptTag(prefix+"wse.js");
addScriptTag(prefix+"crossdomain_wse.js");
addScriptTag(prefix+"devices.js");
