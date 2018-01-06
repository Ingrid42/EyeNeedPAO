$(document).ready(function() {
  init_chart_doughnut();  
  init_linear_chart();
 

  $('.collapse-link').on('click', function() {
    var $BOX_PANEL = $(this).closest('.x_panel'),
    $ICON = $(this).find('i'),
    $BOX_CONTENT = $BOX_PANEL.find('.x_content');

    if ($BOX_PANEL.attr('style')) {
      $BOX_CONTENT.slideToggle(200, function(){
        $BOX_PANEL.removeAttr('style');
      });
    } else {
      $BOX_CONTENT.slideToggle(200);
      $BOX_PANEL.css('height', 'auto');
    }

    $ICON.toggleClass('fa-chevron-up fa-chevron-down');
  });

  $('.close-link').click(function () {
    var $BOX_PANEL = $(this).closest('.x_panel');
    console.log("test");
    $BOX_PANEL.remove();
  });


});


function init_chart_doughnut(){

  if( typeof (Chart) === 'undefined'){ return; }



  if ($('.canvasAge').length){

    var chart_doughnut_settings = {
      type: 'doughnut',
      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
      data: {
        labels: [
          "moins de 30 ans",
          "de30 à42 ans",
          "de 43 à 60 ans",
          "plus de 60 ans"
        ],
        datasets: [{
          data: donneesAge,
          backgroundColor: [
            "#FB8B24",
            "#3498DB",
            "#5F0F40",
            "#E36414"
          ],
          hoverBackgroundColor: [
            "#FB8B24",
            "#3498DB",
            "#5F0F40",
            "#E36414"
          ]
        }]
      },
      options: {
        legend: false,
        responsive: false
      }
    }

    $('.canvasAge').each(function(){

      var chart_element = $(this);
      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

    });

  }//canvasAge
  if ($('.canvasDepartements').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelDepartements,
	        datasets: [{
	          data: donneesDepartements,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasDepartements').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesDepartements.length; i++) {
	    $("#departementsLabel" + i).text("Département " +labelDepartements[i] +"");
		$("#departements" + i).text(donneesDepartements[i] +"%");
	    }
	  }//canvasDepartements
  
  //CanevasVilles
  if ($('.canvasVilles').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVilles,
	        datasets: [{
	          data: donneesVilles,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVilles').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVilles.length; i++) {
	    $("#villesLabel" + i).text(labelVilles[i] +"");
		$("#villes" + i).text(donneesVilles[i] +"%");
		
	    }
	  }//canvasVilles
  
 
//CanevasVillesLunettes
  if ($('.canvasVillesLunettes').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVillesLunettes,
	        datasets: [{
	          data: donneesVillesLunettes,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesLunettes').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    
	    for (i = 0; i < donneesVillesLunettes.length; i++) {
	    $("#villesLunettesLabel" + i).text(labelVillesLunettes[i] +"");
		$("#villesLunettes" + i).text(donneesVillesLunettes[i] +"%");
		
	    }
	  }//canvasVillesLunettes
 
//CanevasVillesConsultations
  if ($('.canvasVillesConsultations').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {   
	        labels: labelVillesConsultations,
	        datasets: [{
	          data: donneesVillesConsultations,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesConsultations').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVillesConsultations.length; i++) {
	    $("#villesConsultationsLabel" + i).text(labelVillesConsultations[i] +"");
		$("#villesConsultations" + i).text(donneesVillesConsultations[i] +"%");
		
		console.log("villesConsultations=" + donneesVillesConsultations[i]);
	    }
	  }//canvasVillesConsultations
  
  
//CanevasVillesChirurgie
  if ($('.canvasVillesChirurgie').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVillesChirurgie,
	        datasets: [{
	          data: donneesVillesChirurgie,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesChirurgie').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVillesChirurgie.length; i++) {
	    $("#villesChirurgieLabel" + i).text(labelVillesChirurgie[i] +"");
		$("#villesChirurgie" + i).text(donneesVillesChirurgie[i] +"%");
	    }
	  }//canvasVillesChirurgie
  
  
//CanevasVillesExamens
  if ($('.canvasVillesExamens').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVillesExamens,
	        datasets: [{
	          data: donneesVillesExamens,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesExamens').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVillesExamens.length; i++) {
	    $("#villesExamensLabel" + i).text(labelVillesExamens[i] +"");
		$("#villesExamens" + i).text(donneesVillesExamens[i] +"%");
	    }
	  }//canvasVillesExamens
  
  
//CanevasVillesControle
  if ($('.canvasVillesControle').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVillesControle,
	        datasets: [{
	          data: donneesVillesControle,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesControle').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVillesControle.length; i++) {
	    $("#villesControleLabel" + i).text(labelVillesControle[i] +"");
		$("#villesControle" + i).text(donneesVillesControle[i] +"%");
	    }
	  }//canvasVillesControle
  
  
//CanevasVillesFondOeil
  if ($('.canvasVillesFondOeil').length){

	    var chart_doughnut_settings = {
	      type: 'doughnut',
	      tooltipFillColor: "rgba(51, 51, 51, 0.55)",
	      data: {
	        labels: labelVillesFondOeil,
	        datasets: [{
	          data: donneesVillesFondOeil,
	          backgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ],
	          hoverBackgroundColor: [
	            "#FB8B24",
	            "#3498DB",
	            "#5F0F40",
	            "#E36414",
	            "#9A031E"
	          ]
	        }]
	      },
	      options: {
	        legend: false,
	        responsive: false
	      }
	    }

	    $('.canvasVillesFondOeil').each(function(){

	      var chart_element = $(this);
	      var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

	    });
	    for (i = 0; i < donneesVillesFondOeil.length; i++) {
	    $("#villesFondOeilLabel" + i).text(labelVillesFondOeil[i] +"");
		$("#villesFondOeil" + i).text(donneesVillesFondOeil[i] +"%");
	    }
	  }//canvasVillesFondOeil 
  
}

function init_linear_chart(){
	// "#FB8B24","#3498DB","#5F0F40","#E36414", "#9A031E
  new Chart(document.getElementById("line-chart"), {
    type: 'line',
    data: {
      labels: labelVisites,
      datasets: [{
        data: donneesVisites,
        label: "visites",
        borderColor: "#3e95cd",
        fill: false
      },
      {
          data: donneesQuestionnaire,
          label: "Questionnaires remplis",
          borderColor: "#FB8B24",
          fill: false
        },
        {
            data: donneesNouveaux,
            label: "Nouveaux utilisateurs",
            borderColor: "#3498DB",
            fill: false
          }
    ]
  },
  options: {
    title: {
      display: true,
      text: 'Nombre de visites quotidiens sur un mois'
    },
    // scales: {
    //   yAxes: [{
    //     ticks: {
    //       beginAtZero:true
    //     }
    //   }]
    // }  A AJOUTER SI ON VEUT QUE LES Y DU GRAPH COMMENCE A ZERO
  }
});
	// "#FB8B24","#3498DB","#5F0F40","#E36414", "#9A031E
  new Chart(document.getElementById("line-rdv"), {
	    type: 'line',
	    data: {
	      labels: labelVisites,
	      datasets: [{
	        data: donneesConsultations,
	        label: "consultations",
	        borderColor: "#3e95cd",
	        fill: false
	      },
	      {
	          data: donneesFondOeil,
	          label: "fondOeil",
	          borderColor: "#FB8B24",
	          fill: false
	        },
	        {
		          data: donneesLunettes,
		          label: "lunettes",
		          borderColor: "#3498DB",
		          fill: false
		        },{
		        data: donneesControle,
		          label: "controle",
		          borderColor: "#5F0F40",
		          fill: false
		        },
		        {
			        data: donneesChirurgie,
			          label: "chirurgie",
			          borderColor: "#9A031E",
			          fill: false
			        }
		        
	    ]
	  },
	 
	  options: {
	    title: {
	      display: true,
	      text: 'Nombre de rdv quotidiens sur un mois'
	    },
	    // scales: {
	    //   yAxes: [{
	    //     ticks: {
	    //       beginAtZero:true
	    //     }
	    //   }]
	    // }  A AJOUTER SI ON VEUT QUE LES Y DU GRAPH COMMENCE A ZERO
	  }
	});

}



