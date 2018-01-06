/**
 * javascipt pour le formulaire d'Insciption, Login, NewPassword

 */


/********************************************
 * 											*
 * 	Fonction qui initialiez les fonctions	*
 * 											*
 ******************************************/
function visite(){
	
	$.ajax({
	      url: "majVisite",
	      type: 'GET',
	      data:  "",
	      success: function(result){
	    	  
	                 if(result){
	                	 return true;
	                 }
	                 else{
	                	return false;
	                 }
	               }
	      });
		
}
$(document).ready(function () {
	/********************************************
	 * 	Formulaire d'inscription				*
	 ******************************************/

	$("#naissance").change(valideNaissance);
	$("#passwordValidation").focusout(checkPasswordMatch);//vérifie la longueur du mot de passe
	$("#password").focusout(isOkPass); //vérifie le mot de passe
	var username = $('#login').val();
	$('#login').change(function() { //check le mot de passe dans la base de données aprés le changement du champ login
		var Url="checkUsername/" +  $('#login').val();
	   $.ajax({
	      url: Url,
	      type: 'GET',
	      data:  "",
	      success: function(result){
	    	  		 console.log(result);
	                 if(result){
	                	 
	                	 $("#btnSuivant").css("opacity",0.4);
//	                	 simpleNotify.notify('Identifiant déjà utilisé', 'danger')
	                	 hotsnackbar('hserror', 'Identifiant déjà utilisé.');
	                	 $("#labelLogin").css('color','#891F08');
	                	 $("#btnSuivant").prop("disabled",true);
	                	 une_variable_globale ="PB"
	                	 console.log(une_variable_globale);
       	 
	                 }
	                 else{
	                	 $("#labelLogin").css('color','rgba(255, 255, 255, 0.7)');
	                	 $("#btnSuivant").prop("disabled",false);
	                	 $("#btnSuivant").css("opacity",1);
	                	 une_variable_globale ="OK"
	                	 console.log(une_variable_globale);
	                	 
	                 }
	               }	
	      });
	});
//	$("#labelLogin").css('color','rgba(255, 255, 255, 0.5)');
//	 $("#btnSuivant").prop("disabled",false);
//	 $("#btnSuivant").css("opacity",1);
//	
//	 $("#btnSuivant").prop("disabled",true); //désactive le bouton du formulaire
//	 $("#btnSubmit").toggle();
	 $("#mail").change(function(){
		 validateEmail();
	 });
	 
	
	// $( "#datepicker" ).datepicker({ dateFormat: 'dd/mm/yy' });
	 //$( "#datepicker2" ).datepicker();
	 $("#proche").toggle();
	 $("#formParent").toggle();
	 
	 $("#question1_1").toggle();
	 $("#btnVous").click(function() {
		    $("#btnSubmit").toggle();
		});
	$("#btnParent").click(function() {
	    $("#proche").toggle();
	  });
	$("#btnProche").click(function() {
	    $("#formUtilisateur").toggle();
	    $("#formParent").toggle();
	    $("#btnSubmit").toggle();
	  });

	$("#reponse1_oui").click(function() {
	    $("#question1").toggle();
	    $("#question1_1").toggle();
	  });
});
	/********************************************
	 * 	Vérification du Formulaire d'inscription*
	 ********************************************/
	function valideForm(){

	//si nom prenom code postal date de naissance email téléphone isOkPass() et checkPasswordMatch()
		if($("#nom").val != "" && $("#prenom").val() !="" && $("#login").val() !="" ){
			if( isOkPass()  ){
				if(validateEmail()){
					if(valideNaissance()){
						if(isOkCodePostal()){
							if(une_variable_globale == "OK") {
														
								$('#login-form').submit();
							}													
						}
					}
				}
				
				
			}
		} else {
//       	 	simpleNotify.notify("Veuillez remplir les champs vides.", 'danger');
			
			hotsnackbar('hserror', 'Veuillez remplir les champs vides');
			$("#btnSuivant").css("opacity",0.4);
			$("#btnSuivant").prop("disabled",true);
		}
		
	} 

	
	
	
//		Fonction qui vérifie la date de naissance 

	function valideNaissance(){
		
		dateNaissance=$("#naissance").val(); 
		if(naissance="") {
			simpleNotify.notify("Champ obligatoire.", 'danger')
			Console.log("naissance vide");
			return false;
		}
		var date_temp = dateNaissance.split('/');
		 date_temp[1] -=1;        // On rectifie le mois !!!
		var ma_date = new Date();
	     ma_date.setFullYear(date_temp[2]);
	     ma_date.setMonth(date_temp[1]);
	     ma_date.setDate(date_temp[0]);
	     var age=new Number((new Date().getTime() - ma_date.getTime()) / 31536000000).toFixed(0);
	     console.log("age:"+age);
	     if(age>18) {
	    	 $("#btnSuivant").css("opacity",1);
	    	 $("#labelNaissance").css('color','rgba(255, 255, 255, 0.7)');
	    	 $("#btnSuivant").prop("disabled",false);
        	 return true;
	     
	     } else { 
//	    	 
//	    	 if(isNaN(age)) {
//	    		$("#labelNaissance").css('color','rgba(255, 255, 255, 0.5)');
//	 			return false;
//	 		 }
	    	 console.log("moins de 18 ans");
	    	 $("#btnSuivant").css("opacity",0.4);
//        	 simpleNotify.notify("Vous devez être majeur.", 'danger')
        	 hotsnackbar('hserror', 'Vous devez être majeur');
        	 $("#labelNaissance").css('color','#891F08');
        	 $("#btnSuivant").prop("disabled",true);
	    	 return false;
	     }
	     
	}

	 /* 	Fonction qui vérifie le format du mail	*/

	function validateEmail() {
		var email=$("#mail").val(); 
		  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

		  if(re.test(email)){
			 $("#labelMail").css('color','rgba(255, 255, 255, 0.7)');
			 $("#btnSuivant").prop("disabled",false);
         	 $("#btnSuivant").css("opacity",1);
			  
			  return true;
		  } else {
//			  if(email == "") {
//				$("#labelMail").css('color','rgba(255, 255, 255, 0.5)');
//				$("#btnSuivant").prop("disabled",false);
//	         	$("#btnSuivant").css("opacity",1);
//	         	return false; 
//			  } else {
				  console.log(email);
				  $("#btnSuivant").css("opacity",0.4);
//				  simpleNotify.notify("Email : format incorrect", 'danger')
				  hotsnackbar('hserror', 'Email : format incorrect');
				  $("#labelMail").css('color','#891F08');
				  $("#btnSuivant").prop("disabled",true);
				  return false;  
			 // }
			  
		  }
		 
		}

	 /* 	Fonction qui vérifie que les cgu sont cochés	*/

	 function valideCgu(){
		 $("#btnSuivant").prop("disabled",false); //active le bouton du formulaire
		 $("#btnSubmit").toggle(); 
		 
	 }
	 /* 	Fonction qui vérifie le code postal	*/


		function isOkCodePostal(){
			var p=$("#codePostal").val();
			
		    var anUpperCase = /[A-Z]/;
		    var aLowerCase = /[a-z]/; 
		    var aNumber = /[0-9]/;
		    var aSpecial = /[!|@|#|$|%|^|&|*|(|)|-|_]/;
		    var obj = {};
		    obj.result = true;

		    if(p.length < 5){
		        obj.result=false;
//		        $("#labelCodePostal").html("Code postal: <b>longueur au moins de 5 chiffres</b>");
		        hotsnackbar('hserror', 'Code postal : 5 chiffres');
		        $("#labelCodePostal").css('color','#891F08');
		       
		        return false;
		    } else {
		    	
		    	$("#labelCodePostal").text("Code postal");
		    	
		    	
		   

		    var numUpper = 0;
		    var numLower = 0;
		    var numNums = 0;
		    var numSpecials = 0;
		    for(var i=0; i<p.length; i++){
		        if(anUpperCase.test(p[i]))
		            numUpper++;
		        else if(aLowerCase.test(p[i]))
		            numLower++;
		        else if(aNumber.test(p[i]))
		            numNums++;
		        else if(aSpecial.test(p[i]))
		            numSpecials++;
		    }
		    /* A DECOMMENTER POURAUGMENTER LA COMPLEXITE DU MOT DE PASSE */
		    
		    //if(numUpper < 1 || numLower < 2 || numNums < 2 || numSpecials <2){
		    if( numNums < 5 ){
		        
		        $("#LabelCodePostal").text("Code postal: mauvais format");
//		    	$("#btnSuivant").css("opacity",0.4);
//	        	 simpleNotify.notify("Mauvais format pour le code postal.", 'danger')
//	        	 $("#LabelCodePostal").css('color','#891F08');
//	        	 $("#btnSuivant").prop("disabled",true);
		        
		        return false;
		    } else return true;
		   
		}
		    return true;  
		 }	    


	 /* 	Fonction qui vérifie le longeur du mdp	*/


	function isOkPass(){
		$("#btnSuivant").css("opacity",0.4);
  	 	$("#btnSuivant").prop("disabled",true);
		var p=$("#password").val(); 
	    var anUpperCase = /[A-Z]/;
	    var aLowerCase = /[a-z]/; 
	    var aNumber = /[0-9]/;
	    var aSpecial = /[!|@|#|$|%|^|&|*|(|)|-|_]/;
	    var obj = {};
	    obj.result = true;

	    if(p.length < 8){
	    	if (p == "") {
	    		$("#labelPassword").css('color','rgba(255, 255, 255, 0.5)');
				$("#btnSuivant").prop("disabled",false);
	         	$("#btnSuivant").css("opacity",1);
	         	return false;
	    	} else {
		        obj.result=false;
		        $("#btnSuivant").css("opacity",0.4);
//	       	 	simpleNotify.notify("8 caractères minimum pour le mot de passe", 'danger')
	       	 	hotsnackbar('hserror', '8 caractères minimum pour le mot de passe');
	       	 	$("#labelPassword").css('color','#891F08');
	       	 	$("#btnSuivant").prop("disabled",true);
		        
//		        $('#password').tooltip('show')
//		        obj.error="Not long enough!"
		        return false;	
	    	}
        
	    } else {
	    	
//	    	$("#labelPassword").text("Mot de passe");
	    	//$('#password').tooltip('hide')
	    	$("#labelPassword").css('color','rgba(255, 255, 255, 0.7)');
			$("#btnSuivant").prop("disabled",false);
        	$("#btnSuivant").css("opacity",1);
	    	return true;
	    }

	    var numUpper = 0;
	    var numLower = 0;
	    var numNums = 0;
	    var numSpecials = 0;
	    for(var i=0; i<p.length; i++){
	        if(anUpperCase.test(p[i]))
	            numUpper++;
	        else if(aLowerCase.test(p[i]))
	            numLower++;
	        else if(aNumber.test(p[i]))
	            numNums++;
	        else if(aSpecial.test(p[i]))
	            numSpecials++;
	    }
	    /* A DECOMMENTER POURAUGMENTER LA COMPLEXITE DU MOT DE PASSE */
	    
	    //if(numUpper < 1 || numLower < 2 || numNums < 2 || numSpecials <2){
	   /* if(numUpper < 1  || numNums < 2 || numSpecials <1){
	        obj.result=false;
	        $("#LabelPassword").text("Le mot de passe doit se composé d'une Majuscule, de deux chiffres et un cara");
	        obj.error="Wrong Format!";
	        //return obj;
	    }
	    return obj;*/
	}

	 /* 	Fonction qui valide le 2em mdp			*/

	function checkPasswordMatch() {
		$("#btnSuivant").css("opacity",0.4);
  	 	$("#btnSuivant").prop("disabled",true);
	    var password = $("#password").val();
	    var confirmPassword = $("#passwordValidation").val();7
	    if (confirmPassword == "") {
	    	$("#labelPasswordValidation").css('color','rgba(255, 255, 255, 0.5)');
	    	$("#btnSuivant").css("opacity",0.4);
	  	 	$("#btnSuivant").prop("disabled",true);
         	return false;
	    }

	    if (password != confirmPassword){
//	    	 $("#labelPasswordValidation").html("Confirmation:<b> non identique</b>");
	    	 $("#btnSuivant").css("opacity",0.4);
//	       	 simpleNotify.notify("Mot de passe non identique", 'danger')
	       	 hotsnackbar('hserror', 'Mot de passe non identique');
	       	 $("#labelPasswordValidation").css('color','#891F08');
	       	 $("#btnSuivant").prop("disabled",true);
	    	 return false;
	    }
	       
	    else{
//	    	$("#labelPasswordValidation").html("Confirmation: <b>OK</b>");
	    	$("#labelPasswordValidation").css('color','rgba(255, 255, 255, 0.7)');
			$("#btnSuivant").prop("disabled",false);
        	$("#btnSuivant").css("opacity",1);
	    	return true;
	    }
	        
	}


	/********************************************
	 * 	Formulaire de login				 		*
	 ******************************************/
	var isLogin;
	var isPassword;
	function valideLogin(){
		isLogin=null;
		isPassword=null;
		checkLoginLogin();
		
		
		
		
		//if(isLogin && isPassword)$("#formLogin").submit();
		
		}
	function setGlobal(varName, varValue) { window[varName] = varValue;}
	//$('#divMessage').toggle();
	function checkLoginLogin() { //check le mot de passe dans la base de données aprés le changement du champ login
		var Url="checkUsername/" +  $('#loginLogin').val() ;
	   $.ajax({
	      url: Url,
	      async: false,
	      cache: false,
	      type: 'GET',
	      data:  "",
	      success: function(result){
	    	  
	                 if(result){
	                	
	                	//$('#passwordLogin').prop('disabled',false);
	                	$("#labelMessage").html("");
	                	checkPasswordLogin();
	                	
	                 }
	                 else{
	                	 $("#labelMessage").html("ERREUR: <b>Login incorrect</b>");
	                	 if(!$('#divMessage').isActive()){
	                		 $('#divMessage').toggle(); 
	                	 }
	                	isLogin= false;
	                 }
	               }
	      });
	}	
function checkPasswordLogin() { //check le mot de passe dans la base de données 
		var Url="checkPassword"   ;
		var user={
				
				login:$('#loginLogin').val(),
				password:$('#passwordLogin').val()
		}
	   $.ajax({
	      url: Url,
	      async: false,
	      cache: false,
	      type: 'GET',
	      data:  user,
	      success: function(result){
	    	  
	                 if(result){
	                	
	                	 $("#labelMessage").html("");
	                	  $("#formLogin").submit();
	                 }
	                 else{
	                	 $("#labelMessage").html("login: <b>Mot de passe incorrect</b>");
	                	 if(!$('#divMessage').isActive()){
	                		 $('#divMessage').toggle(); 
	                	 }
	                	 
	                	 isPassword= false;
	                 }
	               }
	      });
	}
	
	
	
	
	/********************************************
	 * 	Formulaire newPassword				 	*
	 ******************************************/
	$('#loginNewPassword').keyup(function() { //check le mot de passe dans la base de données aprés le changement du champ login
		var Url="checkUsername/" +  $('#loginNewPassword').val() ;
	   $.ajax({
	      url: Url,
	      type: 'GET',
	      data:  "",
	      success: function(result){
	    	  
	                 if(result){
	                	loginIs=true;
	                	$('#submitNewPassword').prop('disabled',false);
	                	$("#labelNewPassword").html("Nom d'utilisateur");
	                	 
	                	console.log(" utilisateur: "+$('#loginNewPassword').val()+" existe");
	                 }
	                 else{
	                	 $("#labelNewPassword").html("ERREUR: <b>Nom d'utilisateur incorrect</b>");
	                	 
	                	 loginIs=false;
	                	 console.log(" utilisateur: "+$('#loginNewPassword').val()+" n'existe pas");
	                 }
	               }
	      });
	});	
	



/*********************************************
 * 	Vérification du Formulaire de newPassword*
 *********************************************/
function submitNewPassword(){
	
	if(loginIs ){
		$('newPassword-form').submit();
	}
	
}


