package fr.eyeneed.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.LogRepository;
import fr.eyeneed.dao.ModuleRepository;
import fr.eyeneed.dao.QuestionRepository;
import fr.eyeneed.dao.QuestionnaireRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Form;
import fr.eyeneed.entities.Log;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Navigation;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Questionnaire;
import fr.eyeneed.entities.Reponse;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Role;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.indicateurs;


/*controleur permettant de gérer les questionnaires*/
@Controller
public class QuestionnaireController {
	
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private QuestionRepository questionRepository;	
	@Autowired
	private ReponseRepository reponseRepository;	
	@Autowired
	private Reponse_choisiRepository reponse_choisiRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private QuestionnaireRepository questionnaireRepository;
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private IndicateursRepository indicateursRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(768);
	}

	//questionnaire
	@Secured({"ROLE_USER","ROLE_ADMIN","ROLE_ROOT","ROLE_SECRETAIRE"})
	@RequestMapping(value="/questionnaire")	
	public String questionnaire(Model model,HttpServletRequest httpServletRequest){
		//trouve l'utilisateur connecté
		HttpSession session=httpServletRequest.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String login=securityContext.getAuthentication().getName();
		
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(login);
		java.util.Date date = new Date();//date d'aujourd'hui
		utilisateur.setDerniereConnexion(date);
		//log la connexion
		Log log=new Log(date,"login",utilisateur);
		logRepository.save(log);
		utilisateurRepository.save(utilisateur);
		if(utilisateur.isSecretaire()){
			return "redirect:/home";
		}
		if(utilisateur.isAccesQuestionnaire()){ //si l'utilisateur à accès au questionnaire(premier login ou rejouer le questionnaire)
			
			List<Module>  modules=moduleRepository.findAllByPosition();
			List<Reponse_choisi> reponses=new ArrayList<Reponse_choisi>();	
			int[] maxWidth;
			
			List<Question> questions=questionRepository.findAll();
			maxWidth=new int[questions.size()+1];
			int index=0;
			for (Question question : questions) {
				index= question.getId_question().intValue();
				
			if(question.getReponses().size()>0) {
				//calcule le longueur max des questions d'un module
					maxWidth[index]=reponseRepository.maxWidth(index);
					System.out.println("question numero:" + index+ " maxwidth:"+ maxWidth[index]);
				}
				 
			}
			
			Form form=new Form(reponses);
			int i = 0;
			Questionnaire questionnaire=new Questionnaire();
			questionnaire.setDuree(date.getTime());
			questionnaire.setUtilisateur(utilisateur);
			model.addAttribute("questionnaire", questionnaire);
			model.addAttribute("ListeModules", modules);
			model.addAttribute("nombreModules", modules.size());
			model.addAttribute("ListeReponses", form);
			model.addAttribute("maxWidth", maxWidth);
			model.addAttribute("utilisateur",utilisateur);
			
			return "questionnaire";
		} else {
			System.out.println("l'utilisateur "+ login+" a déjà répondu au questionnaire");
			 return "redirect:/home"; 
			
		}
	}
	


	

	
	
	//traitement du questionnaire rempli
	@Secured({"ROLE_USER","ROLE_ADMIN","ROLE_ROOT"})
	@RequestMapping(value="/saveQuestionnaire",method=RequestMethod.POST)	
	public String saveReponseChoisies(HttpServletRequest request,Model model, Form form,Questionnaire questionnaire){
		//trouve l'utilisateur connecté
		
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		Reponse_choisi rc=new Reponse_choisi();
		Reponse r=new Reponse();
		Date date= new Date();
		questionnaire.setDuree(date.getTime()-questionnaire.getDuree());
		System.out.println("durée=" + questionnaire.getDuree());
		questionnaire.setDate_validee(date);
		questionnaire.setUtilisateur(utilisateur);
		questionnaire=questionnaireRepository.save(questionnaire);
		//Enregistrement des réponses dans la table reponse_choisi
		System.out.println("l'utilisateur est " + utilisateur.getLogin());
		for(Reponse_choisi reponse: form.getReponses()){
			if(reponse.getReponse()!=null){		
				if(reponse.getReponse().getId_reponse()!=null){
					//rc.setReponseId(reponse.getReponse().getId_reponse());
					r=reponseRepository.findOne(reponse.getReponse().getId_reponse());
					System.out.println("la réponse id est " + reponse.getReponse().getId_reponse());
					rc.setValue("");
					if(reponse.getReponse().getTexte()!=null){
						rc.setValue(reponse.getReponse().getTexte());
						System.out.println("la value pour cette réponse est : " + reponse.getReponse().getTexte());
					}
					
					rc.setUtilisateur(utilisateur);
					rc.setDate_reponse(date);
					//rc.setReponse(r);
					reponse_choisiRepository.save(new Reponse_choisi(date, rc.getValue(),r, rc.getUtilisateur(),questionnaire));
				}
			}					
		}
		utilisateur.setAccesQuestionnaire(false);
		utilisateurRepository.save(utilisateur);
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs i=new indicateurs();
		
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		
		if(i == null){
			//si première visite de la journée
			i=new indicateurs(format1.format(d.getTime()));
			i.setVisites(1);
			i.setQuestionnaires(1);
			indicateursRepository.save(i);
		} else {
			i.setQuestionnaires(i.getQuestionnaires()+1);
			indicateursRepository.save(i);
		}
		return "questionnaireConfirmation";
	}
}