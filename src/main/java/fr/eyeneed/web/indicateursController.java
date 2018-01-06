package fr.eyeneed.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.QuestionnaireRepository;
import fr.eyeneed.dao.RdvPatientRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.indicateurs;

@Controller
public class indicateursController {
	@Autowired
	private IndicateursRepository indicateursRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private QuestionnaireRepository questionnaireRepository;
	@Autowired
	private RdvPatientRepository rdvPatientRepository;
	/*
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	@RequestMapping(value="/listConnectes")	
	//fonction qui liste les utilisateur connecté
	public String listConnected(Model model){
		List<Object> principals = sessionRegistry.getAllPrincipals();

		List<Utilisateurs> usersNamesList = new ArrayList<Utilisateurs>();

		for (Object principal: principals) {
		    if (principal instanceof User) {
		    	List<SessionInformation> activeUserSessions =sessionRegistry.getAllSessions(principal, false);
		    	if (!activeUserSessions.isEmpty()) {
		    		usersNamesList.add(utilisateurRepository.findByLogin(((User) principal).getUsername()));
		    		}
		    }
		}
		model.addAttribute("utilisateurs", usersNamesList);
	return "connected";	
	}*/
	

	@RequestMapping(value="/statistiques",method=RequestMethod.GET)	
	//fonction qui liste les utilisateur connecté
	public String statistiques(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		//trouve les indicateurs		
		List<indicateurs>  i=indicateursRepository.findAll();
		model.addAttribute("indicateurs", i);
		
		//tranche d'age
		List<Integer> trancheAge=new ArrayList<Integer>();
		
		
		trancheAge.add(utilisateurRepository.moins30ans());
		trancheAge.add(utilisateurRepository.entre30et42ans());
		trancheAge.add(utilisateurRepository.entre42et60ans());
		trancheAge.add(utilisateurRepository.plus60ans());
//		System.out.println(utilisateurRepository.moins30ans());
//		System.out.println(utilisateurRepository.entre30et42ans());
//		System.out.println(utilisateurRepository.entre42et60ans());
//		System.out.println(utilisateurRepository.plus60ans());
		model.addAttribute("trancheAge", trancheAge);
		model.addAttribute("nbreUtilisateurs", utilisateurRepository.findAll().size());
		//5 villes
		List<Object> villes= utilisateurRepository.findBestCodePostal();
		model.addAttribute("villes", villes);
		//5 départements
		List<Object> departements= utilisateurRepository.findBestDepartement();
		model.addAttribute("departements", departements);
		//durée questionnaire
		Date dureeQuestionnaire=questionnaireRepository.dureeMoyenne();
		model.addAttribute("dureeQuestionnaire", dureeQuestionnaire);
		//5 villes pour rdv
		/*System.out.println("rdv lunettes/lentilles" + rdvPatientRepository.findBestCodePostal("rdv lunettes/lentilles").size());
		System.out.println("rdv consultation de pathologie" + rdvPatientRepository.findBestCodePostal("rdv consultation de pathologie").size());
		System.out.println("rdv chirurgie de la vision" + rdvPatientRepository.findBestCodePostal("rdv chirurgie de la vision").size());
		System.out.println("rdv examen complémentaire" + rdvPatientRepository.findBestCodePostal("rdv examen complémentaire").size());
		System.out.println("rdv de contrôle" + rdvPatientRepository.findBestCodePostal("rdv de contrôle").size());
		System.out.println("rdv fond d'oeil" + rdvPatientRepository.findBestCodePostal("rdv fond d'oeil").size());*/
		
		model.addAttribute("villesLunettes", rdvPatientRepository.findBestCodePostal("rdv lunettes/lentilles"));
		model.addAttribute("villesConsultations", rdvPatientRepository.findBestCodePostal("rdv consultation de pathologie"));
		model.addAttribute("villesChirurgie", rdvPatientRepository.findBestCodePostal("rdv chirurgie de la vision"));
		model.addAttribute("villesExamens", rdvPatientRepository.findBestCodePostal("rdv examen complémentaire"));
		model.addAttribute("villesControle", rdvPatientRepository.findBestCodePostal("rdv de contrôle"));
		model.addAttribute("villesFondOeil", rdvPatientRepository.findBestCodePostal("rdv fond d'oeil"));
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs ii=new indicateurs();
		
		ii=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", ii);
	
		return "statistiques";
	}
}
