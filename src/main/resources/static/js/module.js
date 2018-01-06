//fonction parmettant de créer un élément input pouvant être insérer dans un questionnaire thymleaf
function createNode(id,variable,prefixe,value,type){
	var node = document.createElement("input");
		node.id= prefixe+id+"."+variable;
		node.name = prefixe+"["+id+"]."+variable;
		node.value=value;
		node.type=type;
	return node
}

function monterModule(idModule){
	console.log(idModule);
	console.log("#"+idModule+" .position");
	console.log($("#"+idModule+" .position").val());
	console.log(Number($("#"+idModule+" .position").val()));
	var position=Number($("#"+idModule+" .position").val());
	var listeModule=$("#listeModule").children();
	var moduleABouger = $("#"+listeModule[position-1].id);
	moduleABouger.insertBefore($("#"+listeModule[position-2].id));	
	listeModule=$("#listeModule").children();
	for(i = 0; i < listeModule.length; i++){
		var idModule=listeModule[i].id;
		$("#"+idModule+" .position").val(i+1);
	}
}

function descendreModule(idModule){
	var position=Number($("#"+idModule+" .position").val());
	var listeModule=$("#listeModule").children();
	var moduleABouger = $("#"+listeModule[position-1].id);
	moduleABouger.insertAfter($("#"+listeModule[position].id));	
	listeModule=$("#listeModule").children();
	for(i = 0; i < listeModule.length; i++){
		var idModule=listeModule[i].id;
		$("#"+idModule+" .position").val(i+1);
	}
}

function selectModuleQuestionnaire(idModule){
	$("#moduleSelect").val(idModule);
	$("#formModule").submit();
}

function nouveauModule(){
   	var id = Number(document.getElementById("nbNewModule").value);
	document.getElementById("nbNewModule").value=id+1;
	var moduleId = createNode(id,"id_module","newModules",id,"hidden");
	var modulePosition = createNode(id,"position","newModules",($("#listeModule").children().length+1),"hidden");
		modulePosition.setAttribute("class","position");
	var moduleTexte = document.createElement("p");
		moduleTexte.innerHTML="Nouveau module";
	var moduleButton = document.createElement("div");
		moduleButton.setAttribute("class","btn-group");

		// <input type="hidden" name="mod" th:value="${module.id_module}"/> 

		
		var buttonSubmit = document.createElement("button");
			buttonSubmit.setAttribute("type","button");
			buttonSubmit.onclick = function(){selectModuleQuestionnaire("newModule_"+id)};
			buttonSubmit.innerHTML = "Editer le module";


		var buttonMonter = document.createElement("button");
			buttonMonter.setAttribute("type","button");
			buttonMonter.onclick = function(){monterModule("newModule_"+id)};
			buttonMonter.innerHTML = "Monter";

		var buttonDescendre = document.createElement("button");
			buttonDescendre.setAttribute("type","button");
			buttonDescendre.onclick = function(){descendreModule("newModule_"+id)};
			buttonDescendre.innerHTML = "Descendre";
			
		var buttonSupprimmer = document.createElement("button");
			buttonSupprimmer.setAttribute("type","button");
			buttonSupprimmer.innerHTML = "Supprimer";

		
		moduleButton.appendChild(buttonSubmit);
		moduleButton.appendChild(buttonMonter);
		moduleButton.appendChild(buttonDescendre);
		moduleButton.appendChild(buttonSupprimmer);

	var div = document.createElement("div");
		div.id= "newModule_"+id;
			
	div.appendChild(moduleId);
	div.appendChild(modulePosition);
	div.appendChild(moduleTexte);
	div.appendChild(moduleButton);
	document.getElementById("listeModule").appendChild(div);

}