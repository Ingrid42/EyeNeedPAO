/* fonction qui load les commentaire du rdv dans la base de données*/
function readRdv(id){
	
	$.ajax({
	      url: "readRdv/" + id,
	      type: 'GET',
	      data:  "",
	      success: function(result){
	    	  
	                 if(result){
	                	 $("#rdvText").html(result);
	                	 return true;
	                 }
	                 else{
	                	 $("#rdvText").html("Pas de commentaire pour ce rendez-vous");
	                	return false;
	                 }
	               }
	      });
		
}
function afficheStatus(id){
	
	$("#urlTel").prop("href","rdvTelephone/" +id);
	$("#urlMail").prop("href","rdvMaile/" +id);
	$("#urlTraite").prop("href","rdvTraite/" +id);
	
	readRdv(id);
	location.href = "#rdvStatus";
	
}

function ajouterCommentaire(id){
	$("#formCommentaire").prop("action","rdvCommentaire/" +id);
	location.href = "#ajouterCommentaire";
	
}

function ajouterSpecialiste(id){
	$("#formSpecialiste").prop("action","rdvMedecin/" +id);
	location.href = "#ajouterSpecialiste";
	
}