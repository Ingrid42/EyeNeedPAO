$(document).ready(function() {
	
  init_linear_chart();
  init_bar_chart();
  init_bar_chart2();
  init_Donut_Chart();
  init_BarDouble_Chart();

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


function init_linear_chart(){

	  new Chart(document.getElementById("line-chart"), {
	    type: 'line',
	    data: {
	      labels: ["Janvier","Février","Mars","Avril","Mai","Juin","Juillet"],
	      datasets: [{
	        data: [4562,4635,4572,4835,4785,4124,4265],
//	        label: "Oeil droit",
	        borderColor: "#3e95cd",
	        fill: false
	      }]
	  },
	  options: {
		  legend: {
		        display: false
		},
		  title: {
	      display: true,
	      text: 'Taux de connexion par mois'
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


function init_bar_chart() {

	new Chart(document.getElementById("bar-chart-horizontal"), {
	    type: 'horizontalBar',
	    data: {
	      labels: ["Ophtalmologue", "Orthoptiste", "Opticien"],
	      datasets: [
	        {
//	          label: "Nombre de patients orientés par spécialiste",
	          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	          data: [7452,8267,9542]
	        }
	      ]
	    },
	    options: {
	      legend: { display: false },
	      title: {
	        display: true,
	        text: 'Nombre de patients orientés par spécialistes'
	      }
	    }
	});
	
}

function init_bar_chart2() {

	new Chart(document.getElementById("bar-chart-horizontal2"), {
	    type: 'horizontalBar',
	    data: {
	      labels: ["Renouvellement lunettes", "Renouvellement lentilles", "Demande chirurgicale", "Controle de vue", "Examen Complémentaire"],
	      datasets: [
	        {
//	          label: "Nombre de patients orientés par spécialiste",
	          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	          data: [423,345,125,574,356]
	        }
	      ]
	    },
	    options: {
	      legend: { display: false },
	      title: {
	        display: true,
	        text: 'Analyse de la demande de rendez-vous'
	      }
	    }
	});
	
}


function init_Donut_Chart() {
    Morris.Donut({
        element: 'donut_chart',
        data: [{
            label: '0-16 ans',
            value: 37
        }, {
            label: '16-42 ans',
            value: 30
        }, {
            label: '42 - 49 ans',
            value: 18
        }, {
            label: '50 - 69 ans',
            value: 12
        },
        {
            label: ' > 70 ans',
            value: 3
        }],
        colors: ['rgb(233, 30, 99)', 'rgb(0, 188, 212)', 'rgb(255, 152, 0)', 'rgb(0, 150, 136)', 'rgb(96, 125, 139)'],
        formatter: function (y) {
            return y + '%'
        }
    });
}


function init_BarDouble_Chart() {
	new Chart(document.getElementById("bar-chart-grouped"), {
	    type: 'bar',
	    data: {
	      labels: ["Janvier", "Février", "Mars", "Avril","Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"],
	      datasets: [
	        {
	          label: "Diabète",
	          backgroundColor: "#3e95cd",
	          data: [235,239,244,278,278,289,290,290,302,309,325,326]
	        }, {
	          label: "Hypertension Artérielle",
	          backgroundColor: "#8e5ea2",
	          data: [560,568,574,578,580,596,600,603,604,609,624,635]
	        }
	      ]
	    },
	    options: {
	      title: {
	        display: true,
	        text: "Nombre de personnes atteintes d'une de ces pathologies" 
	      }
	    }
	});	
}
