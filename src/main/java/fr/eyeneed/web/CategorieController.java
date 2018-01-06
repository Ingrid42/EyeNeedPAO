package fr.eyeneed.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eyeneed.dao.CategorieRepository;
import fr.eyeneed.dao.ModuleRepository;
import fr.eyeneed.dao.QuestionRepository;
import fr.eyeneed.dao.QuestionnaireRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Categorie;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Navigation;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Reponse;

import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Utilisateurs;

@Controller
public class CategorieController {

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
	private Reponse_choisiRepository reponse_choisiRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value = "/categorie")
	public String admin(Model model, HttpServletRequest httpServletRequest) {
		System.out.println("/*CATEGORIE CONTROLLER*/ ");
		// trouve le nom d'utilisateur
		HttpSession session = httpServletRequest.getSession();
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = securityContext.getAuthentication().getName();
		Utilisateurs user = utilisateurRepository.findByLogin(username);
		System.out.println("User logué : " + username);
//		Date dateDeNaissance = user.getNaissance();	
//		System.out.println(dateDeNaissance);
//		String tmp = "";
//		String[] chaine;
	
		
		/***********TRAITEMENT DE L AGE**********/
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(dateDeNaissance);  
//		int AnneeNaissance = cal.get(Calendar.YEAR);
//		
//		Date actualDate = new Date(System.currentTimeMillis());
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(actualDate);  
//		int AnneeActuelle = cal2.get(Calendar.YEAR);
//		
//		int age = AnneeActuelle - AnneeNaissance;
//		System.out.println(age);
		
		/***********TRAITEMENT RECUPERATION STRING INDICATOR**********/
//		List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(user);
//		ArrayList listeReponse = new ArrayList<String>();
//		
//		for (Reponse_choisi rep : list) {
//			
//			tmp = ""+rep.getReponse().getId_reponse();
//			chaine = tmp.split(":");
//			for(String element: chaine) {
//				System.out.println("Element split :" + element);
//				listeReponse.add(element);
//			}
//			
//		 
//		}
//	    System.out.println(listeReponse);		
		
	    
	    /*********** CLASSER EN CATEGORIE**********/
	    
//			if(!(listeReponse.contains("130")) && !(listeReponse.contains("144")) && !(listeReponse.contains("147"))) {
//				System.out.println("Pas d'antécédent généraux");
//				if(!(listeReponse.contains("106")) && !(listeReponse.contains("118")) && !(listeReponse.contains("112")) && !(listeReponse.contains("124"))) {
//					System.out.println("Pas d'antécédent familiaux ");
//					if(listeReponse.contains("68")) {
//						System.out.println("Pas d'antécédent chirurgicaux ");
//						if(listeReponse.contains("24")) {
//							System.out.println("Pas d'antécédent médicaux ophtalmologique ");
//							categorieRepository.save(new Categorie(1,username));
//						} else {
//							if(listeReponse.contains("39") && listeReponse.contains("41")) {
//								System.out.println("Glaucome leger");
//								categorieRepository.save(new Categorie(5,username));
//							}
//							if(listeReponse.contains("38") && listeReponse.contains("40")) {
//								System.out.println("Glaucome sévère");
//								categorieRepository.save(new Categorie(6,username));
//							}
//							if(listeReponse.contains("58")) {
//								System.out.println("DMLA legère");
//								categorieRepository.save(new Categorie(7,username));
//							}
//							if(listeReponse.contains("57")) {
//								System.out.println("DMLA sévère");
//								categorieRepository.save(new Categorie(8,username));
//							}
//						}					
//					} else {
//						if(listeReponse.contains("24")) {							
//							if(listeReponse.contains("75")) {
//								if( (listeReponse.contains("76") && !(listeReponse.contains("77")) ) ||  (listeReponse.contains("77") && !(listeReponse.contains("76")) )) {
//									System.out.println("Cataractacte 1 oeil");
//									categorieRepository.save(new Categorie(9,username));	
//								} else {
//									System.out.println("Cataractacte 2 oeil");
//									categorieRepository.save(new Categorie(10,username));
//								}
//							}							
//							if(listeReponse.contains("85")) {
//								System.out.println("Decollement de rétine");
//								categorieRepository.save(new Categorie(11,username));
//							}							
//						}						
//					}	
//				} else {
//					if(listeReponse.contains("24") && listeReponse.contains("68")) {
//						if(listeReponse.contains("118") || listeReponse.contains("106")) {
//							System.out.println("DMLA ou Glaucome dans la famille");
//							categorieRepository.save(new Categorie(2,username));
//						}
//					}
//				}
//			} else {
//				if((listeReponse.contains("24") && listeReponse.contains("68")) && !(listeReponse.contains("106")) && !(listeReponse.contains("118")) && !(listeReponse.contains("112")) && !(listeReponse.contains("124"))) {
//					if(listeReponse.contains("130") && listeReponse.contains("132") && listeReponse.contains("139")) {
//						if(listeReponse.contains("141") || listeReponse.contains("142")) {
//							System.out.println("Diabète sévère");
//							categorieRepository.save(new Categorie(3,username));
//						}
//					}
//					if(listeReponse.contains("130") && listeReponse.contains("143") && listeReponse.contains("133")) {
//						System.out.println("Diabète léger");
//						categorieRepository.save(new Categorie(4,username));
//					}
//				}
//			}		
//		
			model.addAttribute("utilisateur", user);
		
		return "accueil";	
	}

	
}
