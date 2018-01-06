
function selectQuestionEffacable(id){
	console.log(" selectQuestionEffacable: "+id);

	
	if($("#labelInButton"+id).text() =="Je ne sais pas" || $("#labelInButton"+id).text() =="Aucun" || $("#labelInButton"+id).text() =="Pas de correction") {
		//décoche les autres réponses
		var Url="listReponseVoisines/" +  id;
		   $.ajax({
		      url: Url,
		      type: 'GET',
		      data:  "",
		      success: function(result){
		    	  
		                 if(result){
		                	//décoche toutes lé reponses voisines
		                	 var reponse=result.split(";");
		                	 for (var i = 0; i < reponse.length; i++) {
		                	 $("#"+reponse[i]+"_reponse").prop("checked",false);
		                	 $("#labelInButton"+reponse[i]).css("background","none");
		                	 appliqueSelect(reponse[i]);
		                	// selectQuestionEffacable(reponse[i]);
		                	 }
		                	 
		                 }
		                 else{
		                	 //fait rien
		                 }
		               }
		      });
		
		}
			
	
		
	
	if($("#labelInButton"+id).text() =="Myopie" || $("#labelInButton"+id).text() =="Hypermétropie") {
		//décoche les autres réponses
		var Url="listReponseVoisines/" +  id;
		   $.ajax({
		      url: Url,
		      type: 'GET',
		      data:  "",
		      success: function(result){
		    	  
		                 if(result){
		                	//décoche toutes lé reponses voisines
		                	 var reponse=result.split(";");
		                	 for (var i = 0; i < reponse.length; i++) {
		                		 if($("#labelInButton"+reponse[i]).text() =="Myopie" || $("#labelInButton"+reponse[i]).text() =="Hypermétropie") {
				                	 $("#"+reponse[i]+"_reponse").prop("checked",false);
				                	 $("#labelInButton"+reponse[i]).css("background","none");
				                	 appliqueSelect(reponse[i]);
				                	 //selectQuestionEffacable(reponse[i]);
		                		 }
		                	 }
		                	 
		                 }
		                 else{
		                	 //fait rien
		                 }
		               }
		      });
		
		}
	
	
	var reponse=$("#"+id+"_reponse"); //trouve l'élément à modifier
	if (reponse.prop("type")=="radio"){ 
		if(reponse.is(":checked")){
			var	groupe=reponse.prop('name');
			console.log(groupe);
			var button = $('input[name="'+groupe+'"]').map(function() {
			    										return this.id;
													   }).get();
			for (var i = 0; i < button.length; i++) {
				if(button[i]!=(id+"_reponse")){
					console.log(button[i]);
					selectQuestion(button[i].split('_')[0]);
				}
			}
		}
	}
	
	appliqueSelect(id);

}
function appliqueSelect(id){
	var reponse=$("#"+id+"_reponse"); //trouve l'élément à modifier
	
	if (reponse.prop("type")=="hidden"){
		console.log(" valeur de la réponse: "+reponse.val());
			if (reponse.val=""){
				$("#"+id+"_sousQuestion").toggle();
				$("#"+id+"_sousQuestion").css("display","none");
				$("#labelInButton"+id).css("background","none");
				reponse.prop("checked",false);
				$("#"+id+"_sousQuestion input").prop("checked",false);
				console.log("pas checked: "+id);
				return;
			}else{
				reponse.prop("checked",true);
				$("#"+id+"_sousQuestion").css("background-color","rgba(154,188,214,0.3)");
				$("#"+id+"_sousQuestion").toggle();
				$("#labelInButton"+id).css("background","#0B4468");
				console.log(" checked: "+id);
				return;
				
			}
	} else {
		
	
			if (!reponse.is(":checked")){
				$("#"+id+"_sousQuestion").toggle();
				$("#"+id+"_sousQuestion").css("display","none");
				$("#labelInButton"+id).css("background","none");
				reponse.prop("checked",false);
				$("#"+id+"_sousQuestion input").prop("checked",false);
				console.log("pas checked: "+id);
				return;
			}else{
				reponse.prop("checked",true);
				$("#"+id+"_sousQuestion").css("background-color","rgba(154,188,214,0.3)");
				$("#"+id+"_sousQuestion").toggle();
				$("#labelInButton"+id).css("background","#0B4468");
				console.log(" checked: "+id);
				return;
				
			}
	
	}
	
	
}
function selectQuestion(id){
	console.log(" selectQuestion: "+id);
	var reponse=$("#"+id+"_reponse");
	if (reponse.prop("type")=="radio"){
		//console.log(id);
		if(reponse.is(":checked")){
			var	groupe=reponse.prop('name');
			
			var button = $('input[name="'+groupe+'"]').map(function() {
			    										return this.id;
													   }).get();
			for (var i = 0; i < button.length; i++) {
				if(button[i]!=(id+"_reponse")){
					//console.log(button[i]);
					selectQuestion(button[i].split('_')[0]);
				}
			}
		}
	}
	if (!reponse.is(":checked")){
		reponse.prop("checked",false);
		console.log(" unchecked: "+id);
		$("#"+id+"_sousQuestion input").prop("checked",false);
		$("#"+id+"_sousQuestion").css("display","none");
		$("#labelInButton"+id).css("background","none");
	}else{
		reponse.prop("checked",true);
		console.log(" checked: "+id);
		//$("#"+id+"_sousQuestion input").prop("checked",true);
		$("#"+id+"_sousQuestion").css("display","initial");
		$("#labelInButton"+id).css("background","#0B4468");
	}

	
}
function selectQuestionOrpheline(id, texte){
	
	var question=$("#"+id+"_questionOrpheline");
	if (!question.is(":checked")){
		question.prop("value",texte);
		
	}else{
		question.prop("value","");
	
	}
	
	
}
/*
 * Fontion qui change la valeur d'un input chekbox
 */
function changeValue(id){	
	var reponse=$("#"+id+"_reponse");
	if (!reponse.is(":checked")){
		//reponse.prop("checked",false);
		reponse.prop("value","");
		
	}else{
		//reponse.prop("checked",true);
		reponse.prop("value",id);
	}
	
	
}
/*
 * Fontion qui change la valeur d'un input radio
 */
function changeValueRadio(idq,id){
	var question=$("#"+idq+"_reponse_radio");
	var reponse=$("#"+id+"_reponse");
	if (!question.is(":checked")){
		question.prop("checked",false);
		reponse.prop("checked",false);
		reponse.prop("value","");
		
	}else{
		question.prop("checked",true);
		reponse.prop("checked",true);
		reponse.prop("value",id);
	}
	
	
}
function changeValueHidden(id){
	var reponse=$("#"+id+"_reponse");
	if (reponse.prop("type")=="hidden"){
		reponse.prop("value",id);
	}
}

function test(id){
	
	
}
