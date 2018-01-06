package fr.eyeneed.web;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.ModuleRepository;
import fr.eyeneed.dao.QuestionRepository;
import fr.eyeneed.dao.QuestionnaireRepository;
import fr.eyeneed.dao.RelationRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.RoleRepository;
import fr.eyeneed.dao.SyntheseRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.ModuleList;
import fr.eyeneed.entities.Navigation;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.QuestionList;
import fr.eyeneed.entities.Relation;
import fr.eyeneed.entities.RelationQR;
import fr.eyeneed.entities.Reponse;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Role;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.indicateurs;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Synthese;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import fr.eyeneed.dao.RdvPatientRepository;
import fr.eyeneed.entities.rdvPatient;

@Controller
/* controleur gérant la partie administration */
public class AdminController {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@Autowired
	private ReponseRepository reponseRepository;

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RelationRepository relationRepository;
	@Autowired
	private IndicateursRepository indicateursRepository;
	@Autowired
	private RdvPatientRepository rdvPatientRepository;
	@Autowired
	private Reponse_choisiRepository reponse_choisiRepository;
	@Autowired
	private SyntheseRepository syntheseRepository;
	
	/*@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	@Secured({"ROLE_ROOT","ADMIN"})
	@RequestMapping(value="/Connectes")	
	//fonction qui liste les utilisateur connecté
	public String listUsersConnected(Model model){
		
		List<Object> principals = sessionRegistry.getAllPrincipals();
		System.out.println("nombre de connectes" + principals.size());
		
		List<Utilisateurs> usersNamesList = new ArrayList<Utilisateurs>();
		/*
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
	}
	*/
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(768);
	}
	
	
	@RequestMapping(value="/")
	public String index(Model model){
		return "redirect:/index.html";	
	}
	
	@Secured({"ROLE_ROOT"})
	@RequestMapping(value="/init")	
	public String init(Model model){
		
		Role roleAdmin = roleRepository.save(new Role("ADMIN","FAIT DES CHOSES D'ADMIN"));
		
		List<Role> listeRole = new ArrayList<Role>();
		listeRole.add(roleAdmin);
		Utilisateurs user0 = utilisateurRepository.save(new Utilisateurs("test","test@eyeneed.fr", "NomTest", "PrenomTest",0, new Date(System.currentTimeMillis()), (long)76230, new BCryptPasswordEncoder().encode("test"), "telTest", listeRole ));
//		Utilisateurs user1 = utilisateurRepository.save(new Utilisateurs("test01","test1@eyeneed.fr", "NomTest", "PrenomTest",1, new Date(System.currentTimeMillis()), (long)76230, new BCryptPasswordEncoder().encode("test"), "telTest", listeRole));
//		Utilisateurs user2 = utilisateurRepository.save(new Utilisateurs("test02","test2@eyeneed.fr", "NomTest", "PrenomTest",1, new Date(System.currentTimeMillis()), (long)76230, new BCryptPasswordEncoder().encode("test"), "telTest", listeRole));
//		utilisateurRepository.save(new Utilisateurs("test03","test2@test.test", "NomTest", "PrenomTest",1, new Date(System.currentTimeMillis()), (long)66666, new BCryptPasswordEncoder().encode("test"), "telTest", listeRole));
		
		//***************CREER LES RELATION ************************
		Relation parent=relationRepository.save(new Relation("parent"));
		Relation enfant=relationRepository.save(new Relation("enfant"));
		
		//***************CONTEXTE GENERAL ************************
		Module mod00=moduleRepository.save(new Module("Contexte général",0)); //premier module
		Question question00=questionRepository.save(new Question("Dernier contrôle :",mod00,1));
			Reponse reponse001 = reponseRepository.save(new Reponse("Inférieur à 3 ans",1,false,question00));
			Reponse reponse002 = reponseRepository.save(new Reponse("Entre 3 et 5 ans",2,false,question00));
			Reponse reponse003 = reponseRepository.save(new Reponse("Supérieur à 5 ans",3,false,question00));	
			Reponse reponse004 = reponseRepository.save(new Reponse("J'ai oublié",4,false,question00));
		Question question01=questionRepository.save(new Question("Votre correction :",mod00,2,true));
			Reponse reponse011 = reponseRepository.save(new Reponse("Myopie",1,false,question01));
			Reponse reponse012 = reponseRepository.save(new Reponse("Astigmatie",2,false,question01));
			Reponse reponse013 = reponseRepository.save(new Reponse("Hypermétropie",3,false,question01));	
			Reponse reponse014 = reponseRepository.save(new Reponse("Presbytie",4,false,question01));	
			Reponse reponse015 = reponseRepository.save(new Reponse("Pas de correction",5,false,question01));	
			Reponse reponse016 = reponseRepository.save(new Reponse("Je ne sais pas",6,false,question01));
		Question question02=questionRepository.save(new Question("Moyen de correction :",mod00,3,true));
			Reponse reponse021 = reponseRepository.save(new Reponse("Lunettes",1,false,question02));
			Question question0211 = questionRepository.save(new Question("Quand les portez-vous ?",null,1,true));
			question0211.setContenant(reponse021);
			questionRepository.save(question0211);
				Reponse reponse02111 = reponseRepository.save(new Reponse("En permanence",1,false,question0211));
				Reponse reponse02112 = reponseRepository.save(new Reponse("Ordinateur/TV",2,false,question0211));
				Reponse reponse02113 = reponseRepository.save(new Reponse("Conduite",3,false,question0211));
				Reponse reponse02114 = reponseRepository.save(new Reponse("Lecture",4,false,question0211));
			Question question02111 = questionRepository.save(new Question("Année de délivrance de l'ordonnance :",null,1));
			question02111.setContenant(reponse021);
			questionRepository.save(question02111);	
				Reponse reponse021111 = reponseRepository.save(new Reponse("Année",1,false,question02111,"1"));	
				
			Reponse reponse022 = reponseRepository.save(new Reponse("Lentilles",2,false,question02));
			Question question0221 = questionRepository.save(new Question("Fréquence d'utilisation :",null,1));
			question0221.setContenant(reponse022);
			questionRepository.save(question0221);
				Reponse reponse02211 = reponseRepository.save(new Reponse("Quotidiennement",1,false,question0221));
				Reponse reponse02212 = reponseRepository.save(new Reponse("Occasionnellement",2,false,question0221));
			Question question0222 = questionRepository.save(new Question("Quel type de lentilles ?",null,1));
			question0222.setContenant(reponse022);
			questionRepository.save(question0222);
				Reponse reponse02221 = reponseRepository.save(new Reponse("Souples",1,false,question0222));
				Reponse reponse02222 = reponseRepository.save(new Reponse("Rigides",2,false,question0222));
			Question question02311 = questionRepository.save(new Question("Année de délivrance de l'ordonnance :",null,1));
			question02311.setContenant(reponse022);
			questionRepository.save(question02311);	
				Reponse reponse023111 = reponseRepository.save(new Reponse("Année",1,false,question02311,"1"));	
				
			Reponse reponse023 = reponseRepository.save(new Reponse("Aucun",3,false,question02));
			
					
				
//		
			
			
			
		//***************ANTÉCÉDENTS OPTHTALMOLOGIQUES ************************
		Module mod0=moduleRepository.save(new Module("Antécédents médicaux ophtalmologiques",1)); //un module test à la position 0
		Question question0=questionRepository.save(new Question("Etes-vous suivi pour un problème ophtalmologique ?",mod0,1));
		Reponse reponse01 = reponseRepository.save(new Reponse("OUI",1,false,question0));	
		Reponse reponse02 = reponseRepository.save(new Reponse("NON",2,false,question0));
			
				Question question011 = questionRepository.save(new Question("Quel(s) problème(s) ophtalmologique(s) avez-vous ?",null,1));
				question011.setContenant(reponse01);
				questionRepository.save(question011);
				Reponse reponse0111 = reponseRepository.save(new Reponse("CORNÉE",1,false,question011));
				Question question0111 = questionRepository.save(new Question("Quel problème ?",null,1, true));
				question0111.setContenant(reponse0111);
				questionRepository.save(question0111);
//				Question question0111 = questionRepository.save(new Question("Greffe",null,1));
//				question0111.setContenant(reponse0111);
//				questionRepository.save(question0111);
				Reponse reponse01111 = reponseRepository.save(new Reponse("Greffe",1,false,question0111));
//				Question question0112 = questionRepository.save(new Question("Dégénérescense",null,2));
//				question0112.setContenant(reponse0111);
//				questionRepository.save(question0112);
				Reponse reponse01121 = reponseRepository.save(new Reponse("Dégénérescense",2,false,question0111));
//				Question question0113 = questionRepository.save(new Question("Kératocône",null,3));
//				question0113.setContenant(reponse0111);
//				questionRepository.save(question0113);
				Reponse reponse01131 = reponseRepository.save(new Reponse("Kératocône",3,false,question0111));
//				Question question0114 = questionRepository.save(new Question("Cicatrice",null,4));
//				question0114.setContenant(reponse0111);
//				questionRepository.save(question0114);
				Reponse reponse01141 = reponseRepository.save(new Reponse("Cicatrice",4,false,question0111));
				
				Question question0115 = questionRepository.save(new Question("Quel œil ?",null,1,true));
				question0115.setContenant(reponse0111);
				questionRepository.save(question0115);
					Reponse reponse011511 = reponseRepository.save(new Reponse("Œil droit",1,false,question0115));
					Reponse reponse011512 = reponseRepository.save(new Reponse("Œil gauche",2,false,question0115));
					
				Question question0116 = questionRepository.save(new Question("Etes-vous régulièrement suivi pour cette pathologie ?",null,1));
				question0116.setContenant(reponse0111);
				questionRepository.save(question0116);
					Reponse reponse011611 = reponseRepository.save(new Reponse("Oui",1,false,question0116));
					Reponse reponse011612 = reponseRepository.save(new Reponse("Non",2,false,question0116));
					
					
					Reponse reponse0112 = reponseRepository.save(new Reponse("GLAUCOME",2,false,question011));
					
					Question question01120 = questionRepository.save(new Question("Etes-vous régulièrement suivi pour cette pathologie ?",null,1));
					question01120.setContenant(reponse0112);
					questionRepository.save(question01120);
						Reponse reponse011201 = reponseRepository.save(new Reponse("Oui",1,false,question01120));
						Reponse reponse011202 = reponseRepository.save(new Reponse("Non",2,false,question01120));
					
					Question question01121 = questionRepository.save(new Question("Avez-vous un traitement par collyre ?",null,1));
					question01121.setContenant(reponse0112);
					questionRepository.save(question01121);
						Reponse reponse011211 = reponseRepository.save(new Reponse("Oui",1,false,question01121));
						Reponse reponse011212 = reponseRepository.save(new Reponse("Non",2,false,question01121));							
					Question question01124 = questionRepository.save(new Question("Quand a commencé le traitement par collyre ? (Année)",null,1));
					question01124.setContenant(reponse0112);
					questionRepository.save(question01124);
						Reponse reponse011241 = reponseRepository.save(new Reponse("Année",1,false,question01124,"1"));
					Question question01122 = questionRepository.save(new Question("Avez vous été opéré pour ce glaucome ?",null,1));
					question01122.setContenant(reponse0112);
					questionRepository.save(question01122);
						Reponse reponse011221 = reponseRepository.save(new Reponse("Oui",1,false,question01122));
						Reponse reponse011222 = reponseRepository.save(new Reponse("Non",2,false,question01122));
					Question question01123 = questionRepository.save(new Question("Avez vous déjà effectué un examen complémentaire ?",null,1));
					question01123.setContenant(reponse0112);
					questionRepository.save(question01123);
						Reponse reponse011231 = reponseRepository.save(new Reponse("Oui",1,false,question01123));
							Question question01125 = questionRepository.save(new Question("Champ visuel ?",null,1));
							question01125.setContenant(reponse011231);
							questionRepository.save(question01125);
								Reponse reponse011251 = reponseRepository.save(new Reponse("Inférieur à 1 an",1,false,question01125));
								Reponse reponse011252 = reponseRepository.save(new Reponse("Entre 1 et 3 ans",2,false,question01125));
								Reponse reponse011253 = reponseRepository.save(new Reponse("Supérieur à 3 ans",3,false,question01125));
								Reponse reponse011254 = reponseRepository.save(new Reponse("Je ne sais pas",4,false,question01125));
							Question question01126 = questionRepository.save(new Question("OCT ?",null,1));
							question01126.setContenant(reponse011231);
							questionRepository.save(question01126);
								Reponse reponse011261 = reponseRepository.save(new Reponse("Inférieur à 1 an",1,false,question01126));
								Reponse reponse011262 = reponseRepository.save(new Reponse("Entre 1 et 3 ans",2,false,question01126));
								Reponse reponse011263 = reponseRepository.save(new Reponse("Supérieur à 3 ans",3,false,question01126));
								Reponse reponse011264 = reponseRepository.save(new Reponse("Je ne sais pas",4,false,question01126));
						Reponse reponse011232 = reponseRepository.save(new Reponse("Non",2,false,question01123));					
					
					
					
								
				Reponse reponse0113 = reponseRepository.save(new Reponse("DMLA",3,false,question011));
					Question question01131 = questionRepository.save(new Question("Etes-vous régulièrement suivi pour cette pathologie ?",null,1));
					question01131.setContenant(reponse0113);
					questionRepository.save(question01131);
						Reponse reponse011311 = reponseRepository.save(new Reponse("Oui",1,false,question01131));
						Reponse reponse011312 = reponseRepository.save(new Reponse("Non",2,false,question01131));
					
					Question question01132 = questionRepository.save(new Question("De quand date votre dernier OCT ?",null,2));
					question01132.setContenant(reponse0113);
					questionRepository.save(question01132);
						Reponse reponse011321 = reponseRepository.save(new Reponse("Inférieur à 1 an",1,false,question01132));
						Reponse reponse011322 = reponseRepository.save(new Reponse("Entre 1 et 3 ans",2,false,question01132));
						Reponse reponse011323 = reponseRepository.save(new Reponse("Supérieur à 3 ans",3,false,question01132));	
					Question question01133 = questionRepository.save(new Question("Avez vous déjà bénéficié d'injection oculaire ?",null,3));
					question01133.setContenant(reponse0113);
					questionRepository.save(question01133);
						Reponse reponse011331 = reponseRepository.save(new Reponse("Oui",1,false,question01133));
						Reponse reponse011332 = reponseRepository.save(new Reponse("Non",2,false,question01133));	
						
						
				Reponse reponse0114 = reponseRepository.save(new Reponse("UVÉITE",4,false,question011));
					Question question01141 = questionRepository.save(new Question("Quel type ?",null,1));
					question01141.setContenant(reponse0114);
					questionRepository.save(question01141);
						Reponse reponse011411 = reponseRepository.save(new Reponse("Unilatérale",1,false,question01141));
						Reponse reponse011412 = reponseRepository.save(new Reponse("Bilatérale",2,false,question01141));

					Question question01142 = questionRepository.save(new Question("Traitement général :",null,1,true));
					question01142.setContenant(reponse0114);
					questionRepository.save(question01142);
						Reponse reponse011421 = reponseRepository.save(new Reponse("Corticoïde",1,false,question01142));
						Reponse reponse011422 = reponseRepository.save(new Reponse("Immunosuppresseur",2,false,question01142));	
					
					Question question01143 = questionRepository.save(new Question("Etes-vous régulièrement suivi pour cette pathologie ?",null,1));
					question01143.setContenant(reponse0114);
					questionRepository.save(question01143);
						Reponse reponse011431 = reponseRepository.save(new Reponse("Oui",1,false,question01143));
						Reponse reponse011432 = reponseRepository.save(new Reponse("Non",2,false,question01143));
					
			
				Reponse reponse0115 = reponseRepository.save(new Reponse("AUTRES",5,false,question011));
				Question question01151 = questionRepository.save(new Question("Quelle pathologie ? ",null,1));
				question01151.setContenant(reponse0115);
				questionRepository.save(question01151);
					Reponse reponse0115111 = reponseRepository.save(new Reponse("Pathologie:",1,false,question01151,"1"));
					Reponse reponse0115112 = reponseRepository.save(new Reponse("Année",1,false,question01151,"1"));
				
					
	
			
	
					
					
					
					
						
			//***************ANTÉCÉDENTS CHIRURGICAUX ************************
			Module mod4=moduleRepository.save(new Module("Antécédents chirurgicaux",2));
			Question questionA=questionRepository.save(new Question("Avez vous été opéré des yeux ?",mod4,1));
			Reponse reponseA2 = reponseRepository.save(new Reponse("Oui",1,false,questionA));	
			Reponse reponseA1 = reponseRepository.save(new Reponse("Non",2,false,questionA));
				
				
					Question questionAA = questionRepository.save(new Question("Quel type d'opération ?",null,1,true));
					questionAA.setContenant(reponseA2);
					questionRepository.save(questionAA);
					
					
					Reponse reponseAA1 = reponseRepository.save(new Reponse("Chirurgie réfractive",1,false,questionAA));
						Question questionA21=questionRepository.save(new Question("Quel type ?",null,1,true));
						questionA21.setContenant(reponseAA1);
						questionRepository.save(questionA21);
							
							Reponse reponseA212 = reponseRepository.save(new Reponse("Myopie",1,false,questionA21));
							Reponse reponseA213 = reponseRepository.save(new Reponse("Hypermetropie",2,false,questionA21));
							Reponse reponseA214 = reponseRepository.save(new Reponse("Astigmatisme",3,false,questionA21));
							Reponse reponseA215 = reponseRepository.save(new Reponse("Presbytie",4,false,questionA21));
					
							
					Reponse reponseAA2 = reponseRepository.save(new Reponse("Cataracte",2,false,questionAA));
						
						Question questionA22=questionRepository.save(new Question("Quel œil ?",null,1,true));
							questionA22.setContenant(reponseAA2);
							questionRepository.save(questionA22);
								
								Reponse reponseA222 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA22));
								Reponse reponseA223 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA22));
								
						Question questionA221=questionRepository.save(new Question("Année Œil droit (aaaa) :",null,2,true));
						questionA221.setContenant(reponseAA2);
						questionRepository.save(questionA221);
							Reponse reponseA224 = reponseRepository.save(new Reponse("Année",3,false,questionA221,"1"));

						Question questionA222=questionRepository.save(new Question("Année œil gauche (aaaa) :",null,2,true));
						questionA222.setContenant(reponseAA2);
						questionRepository.save(questionA222);
							Reponse reponseA225 = reponseRepository.save(new Reponse("Année",3,false,questionA222,"1"));
							
					
					Reponse reponseAA3 = reponseRepository.save(new Reponse("Glaucome",3,false,questionAA));	
						Question questionA23=questionRepository.save(new Question("Quel œil ?",null,1,true));
							questionA23.setContenant(reponseAA3);
							questionRepository.save(questionA23);
								
								Reponse reponseA232 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA23));
								Reponse reponseA233 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA23));
								
						Question questionA231=questionRepository.save(new Question("Année œil droit (aaaa) :",null,2,true));
						questionA231.setContenant(reponseAA3);
						questionRepository.save(questionA231);
							Reponse reponseA234 = reponseRepository.save(new Reponse("Année",3,false,questionA231,"1"));

						Question questionA232=questionRepository.save(new Question("Année oeil gauche (aaaa) :",null,2,true));
						questionA232.setContenant(reponseAA3);
						questionRepository.save(questionA232);
							Reponse reponseA235 = reponseRepository.save(new Reponse("Année",3,false,questionA232,"1"));
							

					Reponse reponseAA4 = reponseRepository.save(new Reponse("Décollement de rétine",4,false,questionAA));	
						Question questionA24=questionRepository.save(new Question("Quel œil ?",null,1,true));
							questionA24.setContenant(reponseAA4);
							questionRepository.save(questionA24);
								
								Reponse reponseA242 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA24));
								Reponse reponseA243 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA24));
								
						Question questionA241=questionRepository.save(new Question("Année œil droit (aaaa) :",null,2,true));
						questionA241.setContenant(reponseAA4);
						questionRepository.save(questionA241);
							Reponse reponseA244 = reponseRepository.save(new Reponse("Année",3,false,questionA241,"1"));

						Question questionA242=questionRepository.save(new Question("Année œil gauche (aaaa) :",null,2,true));
						questionA242.setContenant(reponseAA4);
						questionRepository.save(questionA242);
							Reponse reponseA245 = reponseRepository.save(new Reponse("Année",3,false,questionA242,"1"));
							

					Reponse reponseAA5 = reponseRepository.save(new Reponse("Greffe de cornée",5,false,questionAA));	
						Question questionA25=questionRepository.save(new Question("Quel œil ?",null,1,true));
							questionA25.setContenant(reponseAA5);
							questionRepository.save(questionA25);
								
								Reponse reponseA252 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA25));
								Reponse reponseA253 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA25));
								
						Question questionA251=questionRepository.save(new Question("Année œil droit (aaaa) :",null,2,true));
						questionA251.setContenant(reponseAA5);
						questionRepository.save(questionA251);
							Reponse reponseA254 = reponseRepository.save(new Reponse("Année",3,false,questionA251,"1"));

						Question questionA252=questionRepository.save(new Question("Année oeil gauche (aaaa) :",null,2,true));
						questionA252.setContenant(reponseAA5);
						questionRepository.save(questionA252);
							Reponse reponseA255 = reponseRepository.save(new Reponse("Année",3,false,questionA252,"1"));
							
						
						
					Reponse reponseAA6 = reponseRepository.save(new Reponse("Ptérygion",6,false,questionAA));	
						Question questionA26=questionRepository.save(new Question("Quel œil ?",null,1,true));
							questionA26.setContenant(reponseAA6);
							questionRepository.save(questionA26);
								
								Reponse reponseA262 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA26));
								Reponse reponseA263 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA26));
								
						Question questionA261=questionRepository.save(new Question("Année œil droit (aaaa) :",null,2,true));
						questionA261.setContenant(reponseAA6);
						questionRepository.save(questionA261);
							Reponse reponseA264 = reponseRepository.save(new Reponse("Année",3,false,questionA261,"1"));

						Question questionA262=questionRepository.save(new Question("Année œil gauche (aaaa) :",null,2,true));
						questionA262.setContenant(reponseAA6);
						questionRepository.save(questionA262);
							Reponse reponseA265 = reponseRepository.save(new Reponse("Année",3,false,questionA262,"1"));
							

					//Reponse reponseAA7 = reponseRepository.save(new Reponse("Voies lacrymales",7,false,questionAA));
					

					Reponse reponseAA8 = reponseRepository.save(new Reponse("Strabisme",8,false,questionAA));	
					Question questionA28=questionRepository.save(new Question("Quel œil ?",null,1,true));
						questionA28.setContenant(reponseAA8);
						questionRepository.save(questionA28);
							
							Reponse reponseA282 = reponseRepository.save(new Reponse("Œil droit",1,false,questionA28));
							Reponse reponseA283 = reponseRepository.save(new Reponse("Œil gauche",2,false,questionA28));
							
					Question questionA281=questionRepository.save(new Question("Année œil droit (aaaa) :",null,2,true));
					questionA281.setContenant(reponseAA8);
					questionRepository.save(questionA281);
						Reponse reponseA284 = reponseRepository.save(new Reponse("Année",3,false,questionA281,"1"));

					Question questionA282=questionRepository.save(new Question("Année œil gauche (aaaa) :",null,2,true));
					questionA282.setContenant(reponseAA8);
					questionRepository.save(questionA282);
						Reponse reponseA285 = reponseRepository.save(new Reponse("Année",3,false,questionA282,"1"));
						
						
		//***************ANTÉCÉDENTS FAMILIAUX ************************
		Module mod1=moduleRepository.save(new Module(" Antécédents familiaux",3)); //un module test à la position 0
		Question question1=questionRepository.save(new Question("Quelqu’un de votre famille est-il sujet à une de ces pathologies ?",mod1,1,true));
			
		Reponse reponse11 = reponseRepository.save(new Reponse("Glaucome",1,false,question1));
				Question question111 = questionRepository.save(new Question("",null,1, true));
				question111.setContenant(reponse11);
				questionRepository.save(question111);	
					Reponse reponse1111 = reponseRepository.save(new Reponse("Père",1,false,question111));
					Reponse reponse1112 = reponseRepository.save(new Reponse("Mère",2,false,question111));
					Reponse reponse1113 = reponseRepository.save(new Reponse("Frère/Soeur",3,false,question111));
					Reponse reponse1114 = reponseRepository.save(new Reponse("Oncle/Tante",4,false,question111));
					Reponse reponse1115 = reponseRepository.save(new Reponse("Grands-parents",5,false,question111));

		Reponse reponse12 = reponseRepository.save(new Reponse("Décollement de rétine",2,false,question1));				
			Question question112 = questionRepository.save(new Question("",null,1, true));
			question112.setContenant(reponse12);
			questionRepository.save(question112);	
				Reponse reponse1121 = reponseRepository.save(new Reponse("Père",1,false,question112));
				Reponse reponse1122 = reponseRepository.save(new Reponse("Mère",2,false,question112));
				Reponse reponse1123 = reponseRepository.save(new Reponse("Frère/Soeur",3,false,question112));
				Reponse reponse1124 = reponseRepository.save(new Reponse("Oncle/Tante",4,false,question112));
				Reponse reponse1125 = reponseRepository.save(new Reponse("Grands-parents",5,false,question112));
			
		Reponse reponse13 = reponseRepository.save(new Reponse("DMLA",3,false,question1));
			Question question113 = questionRepository.save(new Question("",null,1, true));
			question113.setContenant(reponse13);
			questionRepository.save(question113);	
				Reponse reponse1131 = reponseRepository.save(new Reponse("Père",1,false,question113));
				Reponse reponse1132 = reponseRepository.save(new Reponse("Mère",2,false,question113));
				Reponse reponse1133 = reponseRepository.save(new Reponse("Frère/Soeur",3,false,question113));
				Reponse reponse1134 = reponseRepository.save(new Reponse("Oncle/Tante",4,false,question113));
				Reponse reponse1135 = reponseRepository.save(new Reponse("Grands-parents",5,false,question113));
					
				
			Reponse reponse14 = reponseRepository.save(new Reponse("Diabète",4,false,question1));	
				Question question114 = questionRepository.save(new Question("",null,1, true));
				question114.setContenant(reponse14);
				questionRepository.save(question114);	
					Reponse reponse1141 = reponseRepository.save(new Reponse("Père",1,false,question114));
					Reponse reponse1142 = reponseRepository.save(new Reponse("Mère",2,false,question114));
					Reponse reponse1143 = reponseRepository.save(new Reponse("Frère/Soeur",3,false,question114));
					Reponse reponse1144 = reponseRepository.save(new Reponse("Oncle/Tante",4,false,question114));
					Reponse reponse1145 = reponseRepository.save(new Reponse("Grands-parents",5,false,question114));
					
					
			
		//***************ANTÉCÉDENTS GENERAUX ************************
		Module mod2=moduleRepository.save(new Module("Antécédents généraux",4));
		Question question3=questionRepository.save(new Question("Avez-vous une de ces maladies?",mod2,1,true));
			Reponse reponse31 = reponseRepository.save(new Reponse("Diabète",1,false,question3));
				Question question311=questionRepository.save(new Question("Traitement:",null,1));
				question311.setContenant(reponse31);
				questionRepository.save(question311);
					Reponse reponse3111 = reponseRepository.save(new Reponse("Traitement par voie orale",1,false,question311));
					Reponse reponse3112 = reponseRepository.save(new Reponse("Par insuline",2,false,question311));
					Reponse reponse3113 = reponseRepository.save(new Reponse("Non traité(régime)",3,false,question311));
				
				Question question313=questionRepository.save(new Question("Ancienneté du diabète?",null,3));
				question313.setContenant(reponse31);
				questionRepository.save(question313);
					Reponse reponse3131 = reponseRepository.save(new Reponse("Inférieur à 5 ans",1,false,question313));
					Reponse reponse3132 = reponseRepository.save(new Reponse("Entre 5 et 10 ans",2,false,question313));
					Reponse reponse3133 = reponseRepository.save(new Reponse("Supérieur à 10 ans",3,false,question313));
				Question question314=questionRepository.save(new Question("Equilibre HbA1c",null,4));
				question314.setContenant(reponse31);
				questionRepository.save(question314);
					Reponse reponse3141 = reponseRepository.save(new Reponse("Inférieur à 5%",1,false,question314));
					Reponse reponse3142 = reponseRepository.save(new Reponse("Entre 5 et 7%",2,false,question314));
					Reponse reponse3143 = reponseRepository.save(new Reponse("Supérieur à 7%",3,false,question314));
					//Reponse reponse3144 = reponseRepository.save(new Reponse("Je ne sais pas",4,false,question314));
				Question question315=questionRepository.save(new Question("Laser",null,5,true));
				question315.setContenant(reponse31);
				questionRepository.save(question315);
					Reponse reponse3151 = reponseRepository.save(new Reponse("Oeil droit",1,false,question315));
					Reponse reponse3152 = reponseRepository.save(new Reponse("Oeil gauche",2,false,question315));
					Reponse reponse3153 = reponseRepository.save(new Reponse("Aucun",3,false,question315));
				Question question316=questionRepository.save(new Question("Fond d'oeil :",null,5,true));
				question316.setContenant(reponse31);
				questionRepository.save(question316);
					Reponse reponse3161 = reponseRepository.save(new Reponse("Inférieur à 1 an",1,false,question316));
					Reponse reponse3162 = reponseRepository.save(new Reponse("Entre 1 et 3 ans",2,false,question316));
					Reponse reponse3163 = reponseRepository.save(new Reponse("Supérieur à 3 ans",3,false,question316));
				
				
			Reponse reponse32 = reponseRepository.save(new Reponse("Hypertension artérielle",2,false,question3));
				Question question321=questionRepository.save(new Question("Prenez-vous un traitement ?",null,1));
				question321.setContenant(reponse32);
				questionRepository.save(question321);
					Reponse reponse3211 = reponseRepository.save(new Reponse("Oui",1,false,question321));
					Reponse reponse3212 = reponseRepository.save(new Reponse("Non",2,false,question321));

				
			Reponse reponse33 = reponseRepository.save(new Reponse("Maladie inflammatoire",3,false,question3));
				Question question331=questionRepository.save(new Question("Prenez-vous un traitement ?",null,1));
				question331.setContenant(reponse33);
				questionRepository.save(question331);
					Reponse reponse3311 = reponseRepository.save(new Reponse("Oui",1,false,question331));
					Reponse reponse3312 = reponseRepository.save(new Reponse("Non",2,false,question331));


				
				
		//*************** Habitudes visuelles ************************
		Module mod3=moduleRepository.save(new Module("Habitudes visuelles",5));
		Question question4=questionRepository.save(new Question("Évaluer votre utilisation quotidienne :",mod3,1));
			Reponse reponse41 = reponseRepository.save(new Reponse("Ordinateur",1,false,question4,"2"));
			Reponse reponse42 = reponseRepository.save(new Reponse("Conduite",2,false,question4,"2"));
			Reponse reponse43 = reponseRepository.save(new Reponse("TV",3,false,question4,"2"));
			Reponse reponse44 = reponseRepository.save(new Reponse("Jeux vidéo",4,false,question4,"2"));
			Reponse reponse45 = reponseRepository.save(new Reponse("Lecture",5,false,question4,"2"));
			Reponse reponse46 = reponseRepository.save(new Reponse("Smartphone",6,false,question4,"2"));
				
		Question question5=questionRepository.save(new Question("Quel(s) sport(s) pratiquez-vous ?",mod3,2,true));
			Reponse reponse51 = reponseRepository.save(new Reponse("Natation",1,false,question5));
			Reponse reponse52 = reponseRepository.save(new Reponse("Course à pied",2,false,question5));
			Reponse reponse53 = reponseRepository.save(new Reponse("Salle",3,false,question5));
			Reponse reponse54 = reponseRepository.save(new Reponse("Sport de combat",4,false,question5));
			Reponse reponse55 = reponseRepository.save(new Reponse("Tir",5,false,question5));
			Reponse reponse56 = reponseRepository.save(new Reponse("Sports collectifs",6,false,question5));
			Reponse reponse57 = reponseRepository.save(new Reponse("Autre",7,false,question5));
			Reponse reponse58 = reponseRepository.save(new Reponse("Aucun",8,false,question5));
				
			
		Question question6=questionRepository.save(new Question("Catégories socio-professionnelles :",mod3,3));
			Reponse reponse61 = reponseRepository.save(new Reponse("Agriculteurs exploitants",1,false,question6));
			Reponse reponse62 = reponseRepository.save(new Reponse("Artisans, commerçants, chefs d’entreprise",2,false,question6));
			Reponse reponse63 = reponseRepository.save(new Reponse("Cadres, prof. intellectuelles supérieures",3,false,question6));
			Reponse reponse64 = reponseRepository.save(new Reponse("Professions intermédiaires",4,false,question6));
			Reponse reponse65 = reponseRepository.save(new Reponse("Employés",5,false,question6));
			Reponse reponse66 = reponseRepository.save(new Reponse("Ouvriers",6,false,question6));
			Reponse reponse67 = reponseRepository.save(new Reponse("Chômeurs",7,false,question6));
			Reponse reponse68 = reponseRepository.save(new Reponse("Autre",8,false,question6));


	
		
		return "login";
	}	
	/*controleur donnant accès aux formulaires de modification des modules */
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/modules")
	public String modules(Model model,HttpServletRequest httpServletRequest){
		//trouve l'utilisateur connecté
		HttpSession session=httpServletRequest.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		
		ModuleList modules= new ModuleList(moduleRepository.findAll());
		modules.getModules().sort(null);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("ListeModules", modules);
		model.addAttribute("userName",username);
		List<Navigation> navigation= new ArrayList<Navigation>();
		navigation.add(new Navigation("/administration","Modules")); //Map de navigation
		model.addAttribute("navigation",navigation);
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs ii=new indicateurs();
		
		ii=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", ii);
		return "Form_Admin_Module";
	}
	
	/* controleur d'enregistrement des modules
	 * return sur la page modules si aucune question n'est remplie, sur la page de la question selectionnée sinon
	 */
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/saveModules")
	public String saveModules(Model model,@Valid ModuleList listeModules, BindingResult bindingResult, HttpServletRequest httpServletRequest){
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs ii=new indicateurs();
		
		ii=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", ii);
		for (Module module : listeModules.getNewModules()){
			System.out.println(module.getPosition());
			module.setId_module(null);
			module.setNom("NouveauModule");
			moduleRepository.save(module);
		}
		for (Module module : listeModules.getModules()){
			System.out.println(module.getPosition());
			moduleRepository.updatePosition(module.getId_module(),module.getPosition());
		}
		if (listeModules.getModuleSelect()!=null){
			if(listeModules.getModuleSelect().split("_")[0].equals("newModule")){
				return questions(model,listeModules.getNewModules().get(Integer.parseInt(listeModules.getModuleSelect().split("_")[1])).getId_module(),httpServletRequest);
			}else{
				return questions(model,listeModules.getModules().get(Integer.parseInt(listeModules.getModuleSelect().split("_")[1])).getId_module(),httpServletRequest);
			}
		}else{
			return modules(model, httpServletRequest);
		}
	}

	/* controleur permettant d'accèder aux formulaire de modiication des questions d'un modules */
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/questions")	
	public String questions(Model model,@RequestParam(name="mod") Long mod,HttpServletRequest httpServletRequest){
		HttpSession session=httpServletRequest.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		
		Module module=moduleRepository.getOne(mod);
		List<Question> questions=questionRepository.chercherModule(module); //Liste des question du module listé par position
		List<Question> questionsModules = new ArrayList<Question>(questions);
		questionsModules.sort(null);
		StringBuilder JsonString = new StringBuilder();
		JsonString.append("{id: \"module_").append(module.getId_module()).append("\",name:\"").append(module.getNom()).append("\",data:{},children:[");
		List<Reponse> reponses = new ArrayList<Reponse>();  //reponseRepository.chercherQuestion(question);
		for (int i = 0; i < questionsModules.size(); i++) {
			Question question = questionsModules.get(i);
			JsonString.append(QuestionToJson(question,questions,reponses));
			if(i < questionsModules.size()-1){
				JsonString.append(",");
			}
		}
		JsonString.append("]}");
		QuestionList listeQuestions = new QuestionList(questions,module,reponses);
		model.addAttribute("arbreQuestionnaire",JsonString.toString());
		model.addAttribute("ListeQuestion", listeQuestions);
		model.addAttribute("nombreQuestion", questions.size());
		model.addAttribute("module",module);
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs ii=new indicateurs();
		
		ii=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", ii);
		return "Form_Admin_Questions";
	}
	
	/*fonction permettant d'obtenir l'arborescense question/reponse d'un module sous forme d'arbre JSON */
	private String QuestionToJson(Question question, List<Question> questions,List<Reponse> reponses){
		StringBuilder JsonString = new StringBuilder();
		JsonString.append("{id:\"question_").append(question.getId_question()).append("\",name:\"").append(question.getTexte()).append("\",data:{},children:[");
		List<Reponse> reponsesQuestion=question.getReponses();
		reponsesQuestion.sort(null);
		for(int i=0; i<reponsesQuestion.size();i++){
			Reponse reponse_i = reponsesQuestion.get(i);
			reponses.add(reponsesQuestion.get(i));
			JsonString.append("{id:\"reponse_").append(reponse_i.getId_reponse()).append("\",name:\"").append(reponse_i.getTexte()).append("\",data:{},children:[");
			List<Question> sousQuestions = reponse_i.getSousQuestions();
			sousQuestions.sort(null);
			if(sousQuestions!=null){
				for(int j=0; j<sousQuestions.size();j++){
					questions.add(sousQuestions.get(j));
					JsonString.append(QuestionToJson(sousQuestions.get(j),questions,reponses));
					if(j<sousQuestions.size()-1){
						JsonString.append(",");
					}
				}
			}
			JsonString.append("]}");
			if((i<(reponsesQuestion.size()-1))){
				JsonString.append(",");
			}
		}
		JsonString.append("]}");
		return JsonString.toString();
	}
	
	
	/*fonction permettant de sauvegarder les modifications apportées aux questions/réponses d'un modules */
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/saveQuestions",method=RequestMethod.POST)	
	public String saveQuestions(Model model, @Valid QuestionList listeQuestions, BindingResult bindingResult, HttpServletRequest httpServletRequest){
		//ajouter les indicateurs
				Calendar d=Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				
				indicateurs ii=new indicateurs();
				
				ii=indicateursRepository.findOne(format1.format(d.getTime()));
				model.addAttribute("indicateur", ii);
				
		if(!bindingResult.hasErrors()){		
			Module module= listeQuestions.getModule();
			moduleRepository.updateNom(module.getId_module(),module.getNom());
			for (Reponse reponse : listeQuestions.getNewReponses()){
				reponse.setId_reponse(null);
				if(reponse.getPosition()!=0){
					reponse.setId_reponse(reponseRepository.save(reponse).getId_reponse());			
				}
			}
			for (Question question : listeQuestions.getNewQuestions()){
				question.setId_question(null);
				if(question.getPosition()!=0){
					question.setId_question(questionRepository.save(question).getId_question());
				}
			}
			for (RelationQR relationQR : listeQuestions.getRelationQRs()){
				Reponse reponse;
				Question question;
				if(relationQR.getReponse().split("_")[0].equals("newReponse")){
					reponse=listeQuestions.getNewReponses().get(Integer.parseInt(relationQR.getReponse().split("_")[1]));
				}else{
					reponse=reponseRepository.findOne((long)Integer.parseInt(relationQR.getReponse().split("_")[1]));
				}
				if(relationQR.getQuestion().split("_")[0].equals("newQuestion")){
					question=listeQuestions.getNewQuestions().get(Integer.parseInt(relationQR.getQuestion().split("_")[1]));
				}else{
					question=questionRepository.findOne((long)Integer.parseInt(relationQR.getQuestion().split("_")[1]));
				}
				if(relationQR.getRelation().equals("reponse")){
					reponseRepository.updateQuestion(reponse.getId_reponse(),question);
				}else if(relationQR.getRelation().equals("contenant")){
					questionRepository.updateReponseContenant(question.getId_question(),reponse);
				}else if(relationQR.getRelation().equals("module")){
					questionRepository.updateModule(question.getId_question(),module);
				}
			}
			for (Question question : listeQuestions.getQuestions()) {
				if(question.getPosition()==0){
					question=questionRepository.findOne(question.getId_question()); //On recharche la question à supprimer pour avoir toutes ces dépendances
					questionRepository.delete(question);
				}
				else{
					questionRepository.updateQuestion(question.getId_question(),question.getPosition(),question.getTexte());
				}
			}
			for (Reponse reponse : listeQuestions.getReponses()) {
				if(reponse.getPosition()==0){
					reponse=reponseRepository.findOne(reponse.getId_reponse());
					reponseRepository.delete(reponse);
				}
				else{
					reponseRepository.updateReponse(reponse.getId_reponse(),reponse.getPosition(),reponse.getTexte(),reponse.getIsCommentaire(),reponse.getCommentaire());
				}
			}
		}
		else{
			System.out.println(bindingResult.getAllErrors());
		}
		return this.modules(model, httpServletRequest);
	}
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_ROOT"})
		@RequestMapping(value="/visualisation")	
		public String visualisation(Model model,HttpServletRequest request){
	
			List<rdvPatient> TousLesRdvPatients= new ArrayList<rdvPatient>(rdvPatientRepository.findAll());
			System.out.print(TousLesRdvPatients);
			model.addAttribute("listeRdv", TousLesRdvPatients); 
			HttpSession session=request.getSession();
			SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			String username=securityContext.getAuthentication().getName();
			Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
			model.addAttribute("utilisateur", utilisateur);
			//ajouter les indicateurs
			Calendar d=Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			indicateurs i=new indicateurs();
			
			i=indicateursRepository.findOne(format1.format(d.getTime()));
			model.addAttribute("indicateur", i);
			model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
			return "visualisation";
		}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_ROOT","ROLE_SECRETAIRE"})
	@RequestMapping(value="/visualisationSecretaire")	
	public String visualisationSecretaire(Model model,HttpServletRequest request){

		List<rdvPatient> TousLesRdvPatients= new ArrayList<rdvPatient>(rdvPatientRepository.findAll());
		System.out.print(TousLesRdvPatients);
		model.addAttribute("listeRdv", TousLesRdvPatients); 
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs i=new indicateurs();
		
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", i);
		model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
		return "visualisationSecretaire";
	}
	
	public int calculAge(Date dateDeNaissance) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDeNaissance);  
		int AnneeNaissance = cal.get(Calendar.YEAR);
		
		Date actualDate = new Date(System.currentTimeMillis());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(actualDate);  
		int AnneeActuelle = cal2.get(Calendar.YEAR);
		
		int age = AnneeActuelle - AnneeNaissance;
		return age;
	}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_ROOT","ROLE_SECRETAIRE"})
	@RequestMapping(value="/synthese")	
	public String synthese(Model model, HttpServletRequest request){	
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		
		String anneeOrdonnance = "";
		
		List<Utilisateurs> TousLesUtilisateurs= new ArrayList<Utilisateurs>(utilisateurRepository.findAll());
//		model.addAttribute("listeRdv", TousLesRdvPatients); 
		
		for (Utilisateurs user : TousLesUtilisateurs) {
			
			List<Synthese> allSynthese= new ArrayList<Synthese>(syntheseRepository.findAll());
			List<String> listeLogin = new ArrayList<String>();
			for(Synthese syn: allSynthese) {
				listeLogin.add(syn.getLogin());
			}		
			
			if(!(listeLogin.contains( user.getLogin() ) )) {
		
//				String tmp = "";
//				long tmp1;
//				String[] chaineRep;
//	
//				List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(user);
//				ArrayList listeReponse = new ArrayList<String>();
//				
//				for (Reponse_choisi rep : list) {
//					
//					tmp = ""+rep.getReponse().getId_reponse();
//					System.out.println("Tmp : "+tmp);
//					chaineRep = tmp.split(":");
//					for(String element: chaineRep) {
//						System.out.println("Element split :" + element);
//						listeReponse.add(element);
//					}
//				}
				
				long tmp1;
				List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(user);
				ArrayList<String> listeReponse = new ArrayList<String>();
				listeReponse = getAllReponsesChoisi(user);
				
			    System.out.println(listeReponse);	
			    
//			    for (Reponse_choisi rep : list) { 			    	
//			    	tmp1 = rep.getReponse().getId_reponse();
//			    	if(tmp1 == (long) 16) {
//			    		anneeOrdonnance = anneeOrdonnance+" Lunettes : "+rep.getValue();
//			    	}
//			    	if(tmp1 == (long) 22) {
//			    		anneeOrdonnance = anneeOrdonnance+" Lentilles : "+rep.getValue();
//			    	}
//			    	
//			    }
			    
			    anneeOrdonnance = getAnneeOrdonnance(list);
			    
			    
			    // METTRE A JOUR LA TABLE SYNTHESE ICI
			    String nom = user.getNom();
				String prenom = user.getPrenom();
				String login = user.getLogin();
				Date dateDeNaissance = user.getNaissance();
				int age = calculAge(dateDeNaissance);
				// initialisation
				String anteOphtalmo = "";
				String anteChir = "";			
				String anteFamiliaux = "" ;
				String diabete = "Non" ;
				
				anteOphtalmo = getAnteOphtalmo(listeReponse,anteOphtalmo);
//				if (listeReponse.contains("24")) {
//					//oui ante ophtalmo
//					
//					//cornée
//					if(listeReponse.contains("26")) {
//						anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 26).getTexte();
//					}
//					
//					//glaucome
//					if(listeReponse.contains("35")) {
//						anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 35).getTexte();
//					}
//					
//					//DMLA
//					if(listeReponse.contains("53")) {
//						anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 53).getTexte();
//					}
//					//Uveite
//					if(listeReponse.contains("61")) {
//						anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 61).getTexte();
//					}
//					//Autre
//					if(listeReponse.contains("68")) {
//						anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 68).getTexte();
//					}
//				} else {
//					anteOphtalmo = "Non";
//				}
				
				
				anteChir = getAnteChir(listeReponse,anteChir);
//				if (listeReponse.contains("71")) {
//					//oui ante ophtalmo
//					
//					//Chirurgie réfractive
//					if(listeReponse.contains("73")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 73).getTexte();
//					}
//					
//					//Cataracte
//					if(listeReponse.contains("78")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 78).getTexte();
//					}
//					
//					//Glaucome
//					if(listeReponse.contains("83")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 83).getTexte();
//					}
//					//Décollement de rétine
//					if(listeReponse.contains("88")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 88).getTexte();
//					}
//					
//					//Greffe de cornée
//					if(listeReponse.contains("93")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 93).getTexte();
//					}
//					
//					//Ptérygion
//					if(listeReponse.contains("98")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 98).getTexte();
//					}
//					
//					//Strabisme
//					if(listeReponse.contains("103")) {
//						anteChir = anteChir+" "+reponseRepository.findById((long) 103).getTexte();
//					}
//	
//				} else {
//					anteChir = "Non";
//				}
				
				
				anteFamiliaux = getAnteFamiliaux(listeReponse,anteFamiliaux);	
//				//antécédent glaucome
//				if(listeReponse.contains("108")) {
//					anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 108).getTexte();
//				}
//				
//				//antécédent Décollement de rétine
//				if(listeReponse.contains("114")) {
//					anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 114).getTexte();
//				}
//				
//				//antécédent DMLA
//				if(listeReponse.contains("120")) {
//					anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 120).getTexte();
//				}
//				//antécédent Diabete
//				if(listeReponse.contains("126")) {
//					anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 126).getTexte();
//				}
					
					
				diabete = getDiabete(listeReponse,diabete);
//				if (listeReponse.contains("132")) {
//					diabete = reponseRepository.findById((long) 132).getTexte();
//				}
				
				syntheseRepository.save(new Synthese(prenom, nom, login, anteOphtalmo,anteChir,anteFamiliaux,diabete,age,anneeOrdonnance));		
			}
		}
		
		List<Synthese> infoSynthese = new ArrayList<Synthese>(syntheseRepository.findAll());
		System.out.print(infoSynthese);
		model.addAttribute("arraySynthese", infoSynthese);	
		
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		indicateurs i=new indicateurs();
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", i);
		model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
		
	
		return "synthese";
	}
	
	
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_ROOT"})
	@RequestMapping(value="/viewReponse")	
	public String viewReponse(Model model,HttpServletRequest request, @RequestParam("textinput") String personId){
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		
		
		System.out.println("ID is "+personId);
		Utilisateurs utilisateurRequest=utilisateurRepository.findByLogin(personId);
		
		String tmp = "";
		long tmp2;
		String[] chaineRep;
		Question questionLinkToReponse;

		List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateurRequest);
//		ArrayList listeReponse = new ArrayList<String>();
		Map<String, String> mapQuestionReponse = new LinkedHashMap<>();
		
		for (Reponse_choisi rep : list) {
			
			tmp = ""+rep.getReponse().getId_reponse();		
			chaineRep = tmp.split(":");
			for(String element: chaineRep) {
				System.out.println("Element split :" + element);
				tmp2 = Long.parseLong(element);
				questionLinkToReponse = reponseRepository.findById(tmp2).getQuestion();
//				listeReponse.add(questionLinkToReponse.getTexte()+':'+reponseRepository.findById(tmp2).getTexte());
				mapQuestionReponse.put(reponseRepository.findById(tmp2).getTexte()+'('+tmp2+')',questionLinkToReponse.getTexte());
			}		 
		}
//	    System.out.println(listeReponse);
		System.out.println("Parcours de l'objet HashMap : ");
		  Set<Entry<String, String>> setHm = mapQuestionReponse.entrySet();
		  Iterator<Entry<String, String>> it = setHm.iterator();
		  while(it.hasNext()){
		     Entry<String, String> e = it.next();
		     System.out.println(e.getKey() + " : " + e.getValue());
		  }
	    
		  model.addAttribute("mapQR", mapQuestionReponse);	
		
		
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		indicateurs i=new indicateurs();
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		model.addAttribute("indicateur", i);
		model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
		
		return "viewReponse";
	}
	
	public ArrayList<String> getAllReponsesChoisi(Utilisateurs user) {
		String tmp = "";
		long tmp1;
		String[] chaineRep;

		List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(user);
		ArrayList listeReponse = new ArrayList<String>();
		
		for (Reponse_choisi rep : list) {
			
			tmp = ""+rep.getReponse().getId_reponse();
			System.out.println("Tmp : "+tmp);
			chaineRep = tmp.split(":");
			for(String element: chaineRep) {
				System.out.println("Element split :" + element);
				listeReponse.add(element);
			}
		}
	    return listeReponse ;
	}
	
	public String getAnneeOrdonnance(List<Reponse_choisi> list) {
		long tmp1;
		String anneeOrdonnance = "";
		for (Reponse_choisi rep : list) { 			    	
	    	tmp1 = rep.getReponse().getId_reponse();
	    	if(tmp1 == (long) 16) {
	    		anneeOrdonnance = anneeOrdonnance+" Lunettes : "+rep.getValue();
	    	}
	    	if(tmp1 == (long) 22) {
	    		anneeOrdonnance = anneeOrdonnance+" Lentilles : "+rep.getValue();
	    	}
	    	
	    }
		return anneeOrdonnance;
	}
	
	public String getAnteOphtalmo(ArrayList<String> listeReponse, String anteOphtalmo) {
		if (listeReponse.contains("24")) {
			//oui ante ophtalmo
			
			//cornée
			if(listeReponse.contains("26")) {
				anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 26).getTexte();
			}
			
			//glaucome
			if(listeReponse.contains("35")) {
				anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 35).getTexte();
			}
			
			//DMLA
			if(listeReponse.contains("53")) {
				anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 53).getTexte();
			}
			//Uveite
			if(listeReponse.contains("61")) {
				anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 61).getTexte();
			}
			//Autre
			if(listeReponse.contains("68")) {
				anteOphtalmo = anteOphtalmo+" "+reponseRepository.findById((long) 68).getTexte();
			}
		} else {
			anteOphtalmo = "Non";
		}
		
		return anteOphtalmo;
	}
	
	public String getAnteChir(ArrayList<String> listeReponse, String anteChir) {
		if (listeReponse.contains("71")) {
			//oui ante ophtalmo
			
			//Chirurgie réfractive
			if(listeReponse.contains("73")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 73).getTexte();
			}
			
			//Cataracte
			if(listeReponse.contains("78")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 78).getTexte();
			}
			
			//Glaucome
			if(listeReponse.contains("83")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 83).getTexte();
			}
			//Décollement de rétine
			if(listeReponse.contains("88")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 88).getTexte();
			}
			
			//Greffe de cornée
			if(listeReponse.contains("93")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 93).getTexte();
			}
			
			//Ptérygion
			if(listeReponse.contains("98")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 98).getTexte();
			}
			
			//Strabisme
			if(listeReponse.contains("103")) {
				anteChir = anteChir+" "+reponseRepository.findById((long) 103).getTexte();
			}

		} else {
			anteChir = "Non";
		}
		return anteChir;
	}
	
	public String getAnteFamiliaux(ArrayList<String> listeReponse, String anteFamiliaux) {
		//antécédent glaucome
		if(listeReponse.contains("108")) {
			anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 108).getTexte();
		}
		
		//antécédent Décollement de rétine
		if(listeReponse.contains("114")) {
			anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 114).getTexte();
		}
		
		//antécédent DMLA
		if(listeReponse.contains("120")) {
			anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 120).getTexte();
		}
		//antécédent Diabete
		if(listeReponse.contains("126")) {
			anteFamiliaux = anteFamiliaux+" "+reponseRepository.findById((long) 126).getTexte();
		}
		return anteFamiliaux;
	}
	
	public String getDiabete(ArrayList<String> listeReponse, String diabete) { 
		if (listeReponse.contains("132")) {
			diabete = reponseRepository.findById((long) 132).getTexte();
		}
		return diabete;
	}

	
	
}
