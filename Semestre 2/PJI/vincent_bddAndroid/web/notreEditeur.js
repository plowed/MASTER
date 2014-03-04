if (typeof exports === 'object') {

    var joint = {
        util: require('../src/core').util,
        shapes: {},
        dia: {
            Element: require('../src/joint.dia.element').Element,
            Link: require('../src/joint.dia.link').Link
        }
    };
}

$('<div id="paper" style="border : solid 1px;" class="paper"></div><div id="contentTable" style="border:solid 1px;display:none;"></div>').appendTo($("#container-visuBDD"));

monGraph = joint.dia.Graph.extend({
	getElementByName:function(name){
		elements=this.getElements();
		nbElements=elements.length;
		for(i=0;i<nbElements;i++){
			element=elements[i];
			if(element.getName()==name){
				return element.id;
			}
		}
		return undefined;
	}
});
	

var graph = new monGraph;

var paper = new joint.dia.Paper({
    el: $('#paper'),
    width: 800,
    height: 600,
    gridSize: 1,
    model: graph,
});

MonEntity = joint.dia.Element.extend({

   markup: '<g class="rotatable"><g class="scalable"><rect class="card"/><rect class="card2"/></g><text class="nameEntity"/><text class="champsEntity"/></g>',
    
    defaults: joint.util.deepSupplement({

        type: 'MonEntity',
        size: { width: 200, height: 200 },
        attrs: {
            '.card': {
                fill: '#FFFF3C', stroke: '#000000', 'stroke-width': 2,
                width : 200, height : 100
            },
           '.card2': {
                fill: '#FFFF3C', stroke: '#000000', 'stroke-width': 2,
                width : 200, height : 100
            },

            '.nameEntity': {
		text:'Entity',
                'font-family': 'Arial', 'font-size': 12,
                ref: '.card', 'ref-x': .5, 'ref-y' : .1,
                'x-alignment': 'middle'
            },

	    '.champsEntity': {
                'font-family': 'Arial', 'font-size': 12,'line-increment':12,
                ref: '.card2', 'ref-x': .02, 'ref-y' : .1,
                'x-alignment': 'left'
            }
        },

	nomEntity:'',
    	champs:[],

    }, joint.dia.Element.prototype.defaults),

    updateEntity: function(){
	listeChamps=this.get('champs');
	nbChamps=listeChamps.length;
	listTextChamps=[];
	for(i=0;i<nbChamps;i++){
		listTextChamps[i]=listeChamps[i].nom+" : "+listeChamps[i].type;
		if(listeChamps[i].fk){
			listTextChamps[i]="<<fk>> "+listTextChamps[i];
		}
		if(listeChamps[i].pk){
			listTextChamps[i]="<<pk>> "+listTextChamps[i];
		}
	}
	textChamps=listTextChamps.join("\n");
	var nombreDeLignesEntity=this.get('nomEntity').split(String.fromCharCode(10)).length;
	var hauteurRect1=nombreDeLignesEntity*15+5;
	var hauteurRect2=listTextChamps.length*18+5;

	var maxLen=this.get('nomEntity').length;
	for(i=0;i<listTextChamps.length;i++){
		len=listTextChamps[i].length;
		if(len>maxLen){
			maxLen=len;
		}
	}
	var widthRect = maxLen*7;
	this.attr({'.nameEntity':{text : this.get('nomEntity') },
		'.champsEntity':{text : textChamps}});
	this.attr({'.champsEntity tspan:not(:first-child)':{dy : '15px' }});
	this.attr({'.card':{width:widthRect,height:hauteurRect1},'.card2':{transform:'translate(0,'+ hauteurRect1 + ')',width:widthRect,height:hauteurRect2}});
	this.resize(widthRect,hauteurRect1+hauteurRect2);
    },
    
    setName : function (nouveauNom) {
	this.set("nomEntity",nouveauNom);
    	this.updateEntity();
    },

    getName : function(){
	return this.get("nomEntity");
    },

    getListeChamps:function(){
	return this.get('champs');
    },

    addChamps : function(nameChamps,type,pk,fk){
	champ={'nom':nameChamps,'type':type,'pk':pk,'fk':fk};
	listeChamps=this.get('champs');
	listeChamps.push(champ);
	this.set('champs',listeChamps);
	this.updateEntity();
    }
});

Association = joint.dia.Link.extend({
    defaults: {
        type: 'Association',
        attrs: { '.marker-target': { d: 'M 20 0 L 0 10 L 20 20 z', fill: 'white' }}
    },

    renderVertexMarkers: function() {

    },

    renderArrowheadMarkers: function() {

    }
});

function displayChamps(entityCurrent,listeChamps){
	tableAssocier=[];
	for(var i=0;i<listeChamps.length;i++){
		champs=listeChamps[i];
		if("nameChamps" in champs && "type" in champs && "pk" in champs && "fk" in champs){
			entityCurrent.addChamps(champs.nameChamps,champs.type,champs.pk,champs.fk);
			if(champs.fk && "reference" in champs && "table" in champs.reference){
				if(tableAssocier.indexOf(champs.reference.table)==-1){
					tableAssocier.push(champs.reference.table);
				}
			}
		}
	}
	return tableAssocier;
}

function displayEntity(listeTable){
	association=[];
	for(var i=0;i<listeTable.length;i++){
		table=listeTable[i];
		if("nomTable" in table){
			entityCurrent = new  MonEntity({
				position: { x:200  , y: 100 },
        			size: { width: 180, height: 50 }});
			entityCurrent.setName(table.nomTable);
			if("champs" in table){
				tableAssocier=displayChamps(entityCurrent,table.champs);
				if(tableAssocier.length > 0){
					association.push({from : table.nomTable, to : tableAssocier});
				}
			}
			graph.addCell(entityCurrent);
		}
	}
	if(association.length>0){
		displayAssociation(association);
	}
}

function displayAssociation(listeAssociation){
	nbAssociation=listeAssociation.length;
	for(iDisplayAssoc=0;iDisplayAssoc<nbAssociation;iDisplayAssoc++){
		idElementFrom=graph.getElementByName(listeAssociation[iDisplayAssoc].from);
		elementTo=listeAssociation[iDisplayAssoc].to;
		nbTo=elementTo.length;
		for(j=0;j<nbTo;j++){
			idElementTo=graph.getElementByName(elementTo[j]);
			link=new Association({ source: { id: idElementFrom }, target: { id: idElementTo }});
			graph.addCell(link);
		}
	}
}

function displayContenu(cell){
	name=cell.getName();
	listeChamps=cell.getListeChamps();
	nbChamps=listeChamps.length;
	header="";
	for(i=0;i<nbChamps;i++){
		header+="<th>"+listeChamps[i].nom+"</th>";
	}
	$("<table><thead><tr>"+header+"</tr></thead></table>").appendTo($("#contentTable"));
	$("#contentTable").show();
}

function eventOnClickEntity (evt,x,y){
	var cell=evt.model;
	if(cell.defaults.type=='MonEntity'){
		displayContenu(cell);
	}
}

paper.on('cell:pointerup', eventOnClickEntity);
	
	
		



/*cell = new uneClasse({
        position: { x:200  , y: 100 },
        size: { width: 180, height: 50 }});
cell.setName("MA CLASSE");*/

/*
paper.on('cell:pointerup', function(evt, x, y) { 
    var cell=evt.model;
    cell.attr({'.card':{fill:'#00FF00'}});
    var nouveauNom=prompt("Nouveau Nom : ");
    if (nouveauNom!=null) {
    	cell.setName(nouveauNom);
    }
});
*/
/*var divPaper=document.getElementById("paper");
divPaper.addEventListener(
	"click",
	{
		handleEvent: function (event) {
			evenement=event;
		}
	},
	false
	);*/

