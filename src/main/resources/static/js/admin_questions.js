//fonction permettant de chercher le parent d'une node dans un arbre JSON à partir de l'id de la node voulue
function chercherParent(arbre,idNode){
	return chercherParentRecursif(arbre,arbre,idNode);
	function chercherParentRecursif(arbre,parent,idNode){
		if(arbre.id==idNode){
			return parent;
		}
		else{
			var i=0;
			var reslt;
			while ((i< arbre.children.length)&&(typeof reslt == 'undefined')) {
				reslt = chercherParentRecursif(arbre.children[i],arbre,idNode);
				i++;
			}
			return reslt;
		}
	}
}

//fonction retournant une node contenu dans un arbre JSON à partir de son ID	
function chercherNode(arbre,idNode){
	if (arbre.id==idNode){
		return arbre;
	}else{
		var i=0;
		var reslt;
		while ((i< arbre.children.length)&&(typeof reslt == 'undefined')) {
			reslt = chercherNode(arbre.children[i],idNode);
			i++;
		}
		return reslt;
	}
}

//fonction parmettant de créer un élément input pouvant être insérer dans un questionnaire thymleaf
function createNode(id,variable,prefixe,value,type){
	var node = document.createElement("input");
		node.id= prefixe+id+"."+variable;
		node.name = prefixe+"["+id+"]."+variable;
		node.value=value;
		node.type=type;
	return node
}

var labelType, useGradients, nativeTextSupport, animate;

//function initialisant le canvas permettant d'afficher le questionnaire
(function() {
  var ua = navigator.userAgent,
      iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
      typeOfCanvas = typeof HTMLCanvasElement,
      nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
      textSupport = nativeCanvasSupport 
        && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
  //I'm setting this based on the fact that ExCanvas provides text support for IE
  //and that as of today iPhone/iPad current text support is lame
  labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
  nativeTextSupport = labelType == 'Native';
  useGradients = nativeCanvasSupport;
  animate = !(iStuff || !nativeCanvasSupport);
})();


//initilisation de texte au dessus du questionnaire
var Log = {
  elem: false,
  write: function(text){
    if (!this.elem) 
      this.elem = document.getElementById('log');
    this.elem.innerHTML = text;
    this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
  }
};

//initialisation du document, prend en entrès l'arbre JSON du questionnaire
function init(json){
    //init Spacetree
    //Create a new ST instance
    var selectedNode;

    var st = new $jit.ST({
        //id of viz container element
        injectInto: 'infovis',
        //set duration for the animation
        duration: 800,
        //set animation transition type
        transition: $jit.Trans.Quart.easeInOut,
        //set distance between node and its children
        levelDistance: 50,
        //enable panning
        Navigation: {
          enable:true,
          panning:true
        },
        //set node and edge styles
        //set overridable=true for styling inrowidual
        //nodes or edges
        Node: {
            height: 60,
            width: 120,
            type: 'rectangle',
            color: '#aaa',
            overridable: true
        },
        
        Edge: {
            type: 'bezier',
            overridable: true
        },
        
        onBeforeCompute: function(node){
        	if (typeof node !== 'undefined') {
        		Log.write("loading " + node.name);
        	}
        },
        
        onAfterCompute: function(){
            Log.write("done");
        },
        //This method is called on DOM label creation.
        //Use this method to add event handlers and styles to
        //your node.
        onCreateLabel: function(label, node){
            label.id = node.id;            
            label.innerHTML = node.name;
            label.onclick = function(){
		// variable désignant la node actuellement sélectionné par l'utilisateur
            	selectedNode=node.id; 
            	if(node._depth % 2 == 0) {
            		selectQuestion(chercherParent(st.toJSON(),selectedNode).id);
            	}
            	else{
            		selectQuestion(node.id);
            	}
            	st.onClick(node.id);
            };
            //set label styles
            var style = label.style;
            style.width = 120 + 'px';
            style.height = 36 + 'px';            
            style.cursor = 'pointer';
            style.color = '#333';
            style.fontSize = '0.8em';
            style.textAlign= 'center';
            style.paddingTop = '3px';
        },
        
        //This method is called right before plotting
        //a node. It's useful for changing an inrowidual node
        //style properties before plotting it.
        //The data properties prefixed with a dollar
        //sign will override the global node style properties.
        onBeforePlotNode: function(node){
            //add some color to the nodes in the path between the
            //root node and the selected node.
            if (node.selected) {
                node.data.$color = "#E5D9AC";
            }
            else {
                delete node.data.$color;
                    if (node._depth % 2 == 1) {
                        node.data.$color = '#9ABCD6';
                    }else{
                    	node.data.$color = '#8B8D9B';
                    }       
                }
            },
        
        //This method is called right before plotting
        //an edge. It's useful for changing an inrowidual edge
        //style properties before plotting it.
        //Edge data proprties prefixed with a dollar sign will
        //override the Edge global style properties.
        onBeforePlotLine: function(adj){
            if (adj.nodeFrom.selected && adj.nodeTo.selected) {
                adj.data.$color = "#eed";
                adj.data.$lineWidth = 3;
            }
            else {
                delete adj.data.$color;
                delete adj.data.$lineWidth;
            }
        }
    });
      
    //load json data
    st.loadJSON(json);
    //compute node positions and layout
    st.compute();
    //optional: make a translation of the tree
    st.geom.translate(new $jit.Complex(-200, 0), "current");
    //emulate a click on the root node.
    st.onClick(st.root);
    //end
    //Add event handlers to switch spacetree orientation.
    selectedNode = st.toJSON().id;

    //ajout de la fontion pour ajouter des réponses à une node
    var addReponse = document.getElementById('addReponse');
    addReponse.onclick = function() {
    	Log.write("adding reponse...");
    	var type=selectedNode.split("_")[0];
    	var node;
    	if(type=="module"){
        	Log.write("impossible d'ajouter des réponses au module...");
        	node=selectedNode;
    	}
    	else {
    		var nodeParent;
    		if(type=="question"||type=="newQuestion"){ 	
    			node=selectedNode;
    			nodeParent=chercherNode(st.toJSON(),selectedNode);
    		}
    		else{
    			nodeParent=chercherParent(st.toJSON(),selectedNode);
    			node=nodeParent.id;
    		}   
			st.addSubtree(ajouterReponseArbre(node), 'replot', {
    			hideLabels: false,
    			onComplete: function() {
    				Log.write("subtree added");
    			}
    		});
			var position = nodeParent.children.length+1;
    		newReponse(node,position);
    	}
    	attacherTexteArbre();
    	selectQuestion(node);
    };
    
    //ajout de la fontion pour ajouter des questions à une node
    var addQuestion = document.getElementById('addQuestion');
    addQuestion.onclick = function(){
    	Log.write("adding question..."); 
    	var type=selectedNode.split("_")[0];
    	var node;
    	var nodeParent;
    	if(type=="reponse"||type=="module"||type=="newReponse"){
    		node=selectedNode;
    		nodeParent=chercherNode(st.toJSON(),selectedNode); 
    	}
    	else{
			nodeParent=chercherParent(st.toJSON(),selectedNode);
    		node=nodeParent.id;
    	}
		st.addSubtree(ajouterQuestionArbre(node), 'replot', {
    		hideLabels: false,
    		onComplete: function() {
    			Log.write("subtree added");
    		}
    	});
		var position = nodeParent.children.length+1;
    	if (type=="module"){
    		newQuestion(node,null,position);
    	}else{
    		newQuestion(null,node,position);
    	}
    	attacherTexteArbre();
    };

//fonction ajoutant une node question sur l'arbre affiché à partir de l'id de la réponse parente
    function ajouterQuestionArbre(idNode){
    	var nouvelleNodeJson={
    		id : idNode,
			name:"",
			data:{},
			children:[{
				id: "newQuestion_"+Number(document.getElementById("nbNewQuestion").value),
				name: "NewQuestion",
				data:{},
				children:[]
			}]
		};
		return nouvelleNodeJson;
    };

//fonction ajoutant une node réponses sur l'arbre affiché à partir de l'id de la question parente
    function ajouterReponseArbre(idNode){
    	var nouvelleNodeJson={
    		id : idNode,
			name:"",
			data:{},
			children:[{
				id: "newReponse_"+Number(document.getElementById("nbNewReponse").value),
				name: "NewReponse",
				data:{},
				children:[]
			}]
		};
   		return nouvelleNodeJson;
    };
    
    var monter = document.getElementById('monter');
    monter.onclick = function(){
    	changerPosition(-1);
    }
    
    var descendre = document.getElementById('descendre');
    descendre.onclick = function(){
    	changerPosition(1);
    }
    
    //function permettant de changer la position d'une node, prend en entrée le décalage voulu
    function changerPosition(deplacement){
    	var position = $(".position"+selectedNode.split("_")[0]+"_"+selectedNode.split("_")[1]).val();
    	var arbre = st.toJSON();
    	var nodeParent = chercherParent(arbre,selectedNode);
    	var nodeABouger = nodeParent.children[position-1];
    	nodeParent.children.splice(position-1,1);
    	if(nodeParent.children.length>(Number(position)+Number(deplacement)-1)){
    		nodeParent.children.splice((Number(position)+Number(deplacement)-1),0,nodeABouger);
    	}else{
    		nodeParent.children[nodeParent.children.length]=nodeABouger;
    	}
    	var node;
    	for(i = 0; i < nodeParent.children.length; i++){
    		node=nodeParent.children[i].id;
    		$(".position"+node.split("_")[0]+"_"+node.split("_")[1]).val(i+1);
    	}
    	st.loadJSON(arbre);
    	st.refresh();
    }
    
    var supprimer = document.getElementById('supprimer');
    supprimer.onclick = function() {
    	Log.write("delete subtree...");
        st.removeSubtree(selectedNode,true,"animate",{
            hideLabels: false,
            onComplete: function() {
              removing = false;
              Log.write("subtree removed");
            }
        });
        $(".position"+selectedNode).val(0);
        $("#form_"+selectedNode).hide();
        
    };

    
    function attacherTexteArbre(){
	    $(".texteQuestion").each(
	    	function(){
	    		this.onchange=function(){
	    	    	var arbreJson = st.toJSON();
	    	    	modifName(arbreJson,this.getAttribute("numeroChamp"),this.value,false);
	    	    	st.loadJSON(arbreJson);
	    	        st.refresh();
	    	    }
	    	}
	    );
    };
    
    attacherTexteArbre();
    	
    function modifName(arbre,id,value,change){
    	if(arbre.id==id){
    		arbre.name=value;
    		change=true;
    	}
    	else{
    		var i=0;
    		while ((i < arbre.children.length) && (!change) ) {
    			modifName(arbre.children[i],id,value);
    			i=1+i;
    		}
    	}
    };
    //end
    
  //fonction créant une nouvelle question dans le questionnaire, prend en entrée l'id de la réponse parente ou l'id du module (mettre l'élément non choisi à null) et la position de la question
    function newQuestion(idModule,idReponse,position){
    	var id = Number(document.getElementById("nbNewQuestion").value);
    	document.getElementById("nbNewQuestion").value=id+1;
    	var idRelation = Number(document.getElementById("nbNewRelation").value);
    	document.getElementById("nbNewRelation").value=idRelation+1;
    	var row = document.createElement("div");
    	var nodeId = createNode(id,"id_question","newQuestions",id,"hidden");
    	var nodeTexte = createNode(id,"texte","newQuestions","Entrer Question","text");
    	var nodePosition = createNode(id,"position","newQuestions",position,"hidden");
    	var divId=document.createElement("div");
    		divId.appendChild(nodeId);
    	var divTexte=document.createElement("div");
    		divTexte.setAttribute("class","col-md-3");
    		divTexte.appendChild(nodeTexte);
    	var divPosition=document.createElement("div");
    		divPosition.appendChild(nodePosition);
    	row.appendChild(divId);
    	row.appendChild(divTexte);
    	row.appendChild(divPosition);
    	if(idModule!=null){
        	var nodeReponse = createNode(idRelation,"reponse","relationQRs",idModule,"hidden");
        	var nodeRelation = createNode(idRelation,"relation","relationQRs","module","hidden");
    	} else {
        	var nodeReponse = createNode(idRelation,"reponse","relationQRs",idReponse,"hidden");
        	var nodeRelation = createNode(idRelation,"relation","relationQRs","contenant","hidden");
    	}
    	
    	var nodeQuestion = createNode(idRelation,"question","relationQRs","newQuestion_"+id,"hidden");
    	var divQuestion =document.createElement("div");
    		divQuestion.appendChild(nodeQuestion);
    	row.appendChild(divQuestion);
    	var divReponse =document.createElement("div");
    		divReponse.appendChild(nodeReponse);
    	row.appendChild(divReponse);
    	var divRelation =document.createElement("div");
    		divRelation.appendChild(nodeRelation);
    	row.appendChild(divRelation);
    	
    	row.setAttribute("class","question row");
    	row.setAttribute("id","form_newQuestion_"+id);
    	nodeTexte.setAttribute("numeroChamp","newQuestion_"+id);
    	nodeTexte.setAttribute("class","texteQuestion");
		nodePosition.setAttribute("class","positionnewQuestion_"+id)

    	
    	document.getElementById("formQuestion").appendChild(row);
    }

    //fonction créant une nouvelle réponse dans le questionnaire, prend en entrée l'id de la question parente et la position de la réponse
    function newReponse(idQuestion,position){
    	var id = Number(document.getElementById("nbNewReponse").value);
    	var idRelation = Number(document.getElementById("nbNewRelation").value);
    	document.getElementById("nbNewReponse").value=id+1;
    	document.getElementById("nbNewRelation").value=idRelation+1;
    	var row = document.createElement("div");
    	var nodeId = createNode(id,"id_question","newReponses",id,"hidden");
    	var nodeTexte = createNode(id,"texte","newReponses","Entrer Reponse","text");
    	var nodePosition = createNode(id,"position","newReponses",position,"hidden");
    	var nodeType = createNode(id,"type","newReponses","0","text");
    	var nodeIsCommentaire = createNode(id,"isCommentaire","newReponses","0","text");
    	var nodeCommentaire = createNode(id,"commentaire","newReponses","0","text");
    	var divId =document.createElement("div");
    		divId.appendChild(nodeId);
    	row.appendChild(divId);
    	var divTexte =document.createElement("div");
			divTexte.setAttribute("class","col-md-3");
    		divTexte.appendChild(nodeTexte);
    	row.appendChild(divTexte);
    	var divPosition =document.createElement("div");
    		divPosition.appendChild(nodePosition);
    	row.appendChild(divPosition);	
    	var divType =document.createElement("div");
    		divType.setAttribute("class","col-md-3");
    		divType.appendChild(nodeType);
    	row.appendChild(divType);
    	var divIsCommentaire =document.createElement("div");
    		divIsCommentaire.setAttribute("class","col-md-3");
    		divIsCommentaire.appendChild(nodeIsCommentaire);
    	row.appendChild(divIsCommentaire);	
    	var divCommentaire =document.createElement("div");
    		divCommentaire.setAttribute("class","col-md-3");
    		divCommentaire.appendChild(nodeCommentaire);
    	row.appendChild(divCommentaire);
    	row.setAttribute("class","row reponse "+idQuestion+"Id_question");
    	row.id = "form_newReponse_"+id;
    	nodeTexte.setAttribute("numeroChamp","newReponse_"+id);
    	nodeTexte.setAttribute("class","texteQuestion");
		nodePosition.setAttribute("class","positionnewReponse_"+id)

    	var nodeReponse = createNode(idRelation,"reponse","relationQRs","newReponse_"+id,"hidden");
    	var nodeQuestion = createNode(idRelation,"question","relationQRs",idQuestion,"hidden");
    	var nodeRelation = createNode(idRelation,"relation","relationQRs","reponse","hidden");
    	var divQuestion =document.createElement("div");
    		divQuestion.appendChild(nodeQuestion);
    	row.appendChild(divQuestion);
    	var divReponse =document.createElement("div");
    		divReponse.appendChild(nodeReponse);
    	row.appendChild(divReponse);
    	var divRelation =document.createElement("div");
    		divRelation.appendChild(nodeRelation);
    	row.appendChild(divRelation);

    	
    	document.getElementById("formReponse").appendChild(row);
    }

}

//function permettant d'afficher et de cacher les réponses et questions du formulaire en fonction de l'identifiant d'une question
function selectQuestion(id){
	$(".question").hide();
	$(".reponse").hide();
	$("#form_"+id).show();
	$("."+id+"Id_question").show();
}
