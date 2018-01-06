package fr.eyeneed.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.RdvPatientRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.SyntheseRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Synthese;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.fichier;
import fr.eyeneed.entities.indicateurs;
import fr.eyeneed.entities.rdvPatient;


@Controller
public class RdvController {
@Autowired	
RdvPatientRepository rdvPatientRepository;
@Autowired
private UtilisateurRepository utilisateurRepository;
@Autowired
private SyntheseRepository syntheseRepository;
@Autowired
private IndicateursRepository indicateursRepository;
@Autowired
private Reponse_choisiRepository reponse_choisiRepository;
@Autowired
private JavaMailSender mailSender;


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

public void envoyerEmail(Model model, Utilisateurs user) {
	 ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml"); 
	 String recipientAddress = user.getMail();
     String subject = "Demande de rendez-vous EyeNeed";

	try {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(mimeMessage, false, "utf-8");
        fichier f=new fichier("modelMail.html");
        System.out.println(f.template("<b>Merci. Rdv envoyé</b>"));
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom("no.reply@eyeneed.fr");
        email.setReplyTo("no.reply@eyeneed.fr");
        mimeMessage.setContent(f.template("<b>Bonjour, votre identité et votre demande ont bien été enregistrées par l'application Eyeneed. Vous allez être contacté sous 7 jours par une secrétaire pour définir le rendez-vous le plus adapté à votre demande.<br/> Suivez l'avancé du projet EyeNeed sur http://projeteyeneed.strikingly.com/ </b>"),"text/html");
        mailSender.send(mimeMessage);
        model.addAttribute("utilisateur",new Utilisateurs());
   	 	model.addAttribute("mail",recipientAddress);
	} catch (MessagingException e) {
		e.printStackTrace();
	}
     
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdv")	
public String saveRdv(HttpServletRequest request,Model model){
	//trouve l'utilisateur connecté
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	return "saveRdv";
}
@RequestMapping(value="/saveRdvLunettes")	
public String saveRdvLunettes(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
//	Synthese syntheseUser = syntheseRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	Date dateDeNaissance = utilisateur.getNaissance();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE ANNEE ORDONNANCE**********/
	System.out.println("RDV LUNETTE");
	String tmp = "";
	String[] chaineRep;
	String anneeOrdonnance = "0";
	String specialiste = "";
	long tmp1;

	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		
		tmp1 = rep.getReponse().getId_reponse();
		if(tmp1 == (long) 16) {
    		anneeOrdonnance = rep.getValue();
    	}
		tmp = ""+rep.getReponse().getId_reponse();
		chaineRep = tmp.split(":");
		for(String element: chaineRep) {
			System.out.println("Element split :" + element);
			listeReponse.add(element);
		}	
    	
		System.out.println(""+rep.getReponse().getId_reponse());		 
	}
	System.out.println(anneeOrdonnance);
    System.out.println(listeReponse);
    int ordonnance = Integer.parseInt(anneeOrdonnance);
    
    Date actualDate = new Date(System.currentTimeMillis());
	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(actualDate);  
	int AnneeActuelle = cal2.get(Calendar.YEAR);
	int ordonnanceEcart = AnneeActuelle - ordonnance;
    
//    ATTENTION CODE NON TESTE : QUAND FORMULAIRE TERMINE 
	specialiste = getOrientationLunette(listeReponse,ordonnance,age,ordonnanceEcart);
//	if(!(listeReponse.contains("24")) && !(listeReponse.contains("71")) && !(listeReponse.contains("132")) && !(listeReponse.contains("151"))) {
//		if(age<42) {
//			if(ordonnance>2016) {
//				if(ordonnanceEcart<5) {
//					specialiste = "Opticien";
//				} else {
//					specialiste = "Orthoptiste";
//				}	
//			} else { 
//				if(ordonnanceEcart<3) {
//					specialiste = "Opticien";
//				} else {
//					specialiste = "Orthoptiste";
//				}				
//			}			
//			
//		} else {
//			if(age<50) {
//				if(ordonnanceEcart<3) {
//					specialiste = "Opticien";
//				} else {
//					specialiste = "Orthoptiste";
//				}
//			} else {
//				specialiste = "Ophtalmologue";
//			}
//		}
//	} else {
//		specialiste = "Ophtalmologue";
//	}
	
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);


	
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv lunettes (Ordonnance : "+(long) ordonnance+ ")", utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));
	
	/***********INDICATEUR**********/
	
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setLunettes(1);
		
		indicateursRepository.save(i);
	} else {
		i.setLunettes(i.getLunettes()+1);
		indicateursRepository.save(i);
	}
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}

public String getOrientationLunette(ArrayList listeReponse,int ordonnance, int age, int ordonnanceEcart) {
	String specialiste = "";
	if(!(listeReponse.contains("24")) && !(listeReponse.contains("71")) && !(listeReponse.contains("132")) && !(listeReponse.contains("151"))) {
		if(age<42) {
			if(ordonnance>2016) {
				if(ordonnanceEcart<5) {
					specialiste = "Opticien";
				} else {
					specialiste = "Orthoptiste";
				}	
			} else { 
				if(ordonnanceEcart<3) {
					specialiste = "Opticien";
				} else {
					specialiste = "Orthoptiste";
				}				
			}			
			
		} else {
			if(age<50) {
				if(ordonnanceEcart<3) {
					specialiste = "Opticien";
				} else {
					specialiste = "Orthoptiste";
				}
			} else {
				specialiste = "Ophtalmologue";
			}
		}
	} else {
		specialiste = "Ophtalmologue";
	}
	return specialiste;
}


@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvLentilles")	
public String saveRdvLentilles(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	Date dateDeNaissance = utilisateur.getNaissance();
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE ANNEE ORDONNANCE**********/
	System.out.println("RDV LENTILLES");
	String tmp = "";
	String[] chaineRep;
	String anneeOrdonnance = "0";
	String specialiste = "";
	long tmp1;

	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		tmp1 = rep.getReponse().getId_reponse();
		if(tmp1 == (long) 22) {
    		anneeOrdonnance = rep.getValue();
    	}
		tmp = ""+rep.getReponse().getId_reponse();
		chaineRep = tmp.split(":");
		for(String element: chaineRep) {
			System.out.println("Element split :" + element);
			listeReponse.add(element);
		}		
		System.out.println(""+rep.getReponse().getId_reponse());
	}
	
	System.out.println(anneeOrdonnance);
    System.out.println(listeReponse);
    int ordonnance = Integer.parseInt(anneeOrdonnance);
    Date actualDate = new Date(System.currentTimeMillis());
	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(actualDate);  
	int AnneeActuelle = cal2.get(Calendar.YEAR);
	int ordonnanceEcart = AnneeActuelle - ordonnance;
    
//    ATTENTION CODE NON TESTE : QUAND FORMULAIRE TERMINE 
    
	if(!(listeReponse.contains("24")) && !(listeReponse.contains("71")) && !(listeReponse.contains("132")) && !(listeReponse.contains("151"))) {
		if(age < 50) {
			if(ordonnanceEcart < 3) {
				specialiste = "Opticien";
			} else {
				specialiste = "Orthoptiste";
			}
		} else {
			specialiste = "Ophtalmologue";
		}
	} else {
		specialiste = "Ophtalmologue";
	}
	
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);
	
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv lentilles (Ordonnance : "+(long) ordonnance+ ")",utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));
	
/***********INDICATEUR**********/
	
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setLentilles(1);
		
		indicateursRepository.save(i);
	} else {
		i.setLentilles(i.getLentilles()+1);
		indicateursRepository.save(i);
	}
	
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvPathologie")	
public String saveRdvPathologie(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	long ordonnance = 0;
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv consultation de pathologie",utilisateur.getCodePostal(),"demandé", new Date(),"Ophtalmologue",""));
	
	//ajouter les indicateurs
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setConsultations(1);
		
		indicateursRepository.save(i);
	} else {
		i.setConsultations(i.getConsultations()+1);
		indicateursRepository.save(i);
	}
	
//		envoyerEmail(model,utilisateur);
		return "saveRdv";
	}
	
@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvChirurgie")	
public String saveRdvChirurgie(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	long ordonnance = 0;
	
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv chirurgie de la vision",utilisateur.getCodePostal(),"demandé", new Date(),"Ophtalmologue",""));
	
	//ajouter les indicateurs
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setChirurgie(1);
		
		indicateursRepository.save(i);
	} else {
		i.setChirurgie(i.getChirurgie()+1);
		indicateursRepository.save(i);
	}
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvExamenOCT")	
public String saveRdvExamenOCT(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	Date dateDeNaissance = utilisateur.getNaissance();
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE LISTE REPONSES**********/
	System.out.println("RDV OCT");
	String tmp = "";
	String[] chaineRep;
	String specialiste = "";
	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		
//		tmp = ""+rep.getReponse().getId_reponse();		
//		chaineRep = tmp.split(":");
//		for(String element: chaineRep) {
//			System.out.println("Element split :" + element);
//			listeReponse.add(element);
//		}
		System.out.println(""+rep.getReponse().getId_reponse());
	}
    System.out.println(listeReponse);
    
    
	if(age < 50) {
		specialiste = "Orthoptiste";
	} else {
		specialiste = "Ophtalmologue";
	}

	
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);
	
	/***********INDICATEUR**********/
	
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs i=new indicateurs();
		
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		
		if(i == null){
			//si première visite de la journée
			i=new indicateurs(format1.format(d.getTime()));
			i.setVisites(1);
			i.setExamens(1);
			
			indicateursRepository.save(i);
		} else {
			i.setExamens(i.getExamens()+1);
			indicateursRepository.save(i);
		}
		
		long ordonnance = 0;
		rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"OCT",utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));
	
//		envoyerEmail(model,utilisateur);
		return "saveRdv";
}


@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvExamenCV")	
public String saveRdvExamenCV(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	Date dateDeNaissance = utilisateur.getNaissance();
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE LISTE REPONSES**********/
	System.out.println("RDV Champ visuel");
	String tmp = "";
	String[] chaineRep;
	String specialiste = "";
	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		
//		tmp = ""+rep.getReponse().getId_reponse();		
//		chaineRep = tmp.split(":");
//		for(String element: chaineRep) {
//			System.out.println("Element split :" + element);
//			listeReponse.add(element);
//		}
		System.out.println(""+rep.getReponse().getId_reponse());
	}
    System.out.println(listeReponse);
    
//    ATTENTION CODE NON TESTE : QUAND FORMULAIRE TERMINE 
    
	if(age < 50) {
		specialiste = "Orthoptiste";
	} else {
		specialiste = "Ophtalmologue";
	}

	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);
	
	/***********INDICATEUR**********/
	
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setExamens(1);
		
		indicateursRepository.save(i);
	} else {
		i.setExamens(i.getExamens()+1);
		indicateursRepository.save(i);
	}

	long ordonnance = 0;
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"Champ Visuel",utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));

//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}


@RequestMapping(value="/saveRdvExamen2")	
public String saveRdvExamen2(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	Date dateDeNaissance = utilisateur.getNaissance();
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE LISTE REPONSES**********/
	System.out.println("RDV OCT + Champ visuel");
	String tmp = "";
	String[] chaineRep;
	String specialiste = "";
	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		
//		tmp = ""+rep.getReponse().getId_reponse();		
//		chaineRep = tmp.split(":");
//		for(String element: chaineRep) {
//			System.out.println("Element split :" + element);
//			listeReponse.add(element);
//		}
		System.out.println(""+rep.getReponse().getId_reponse());
	}
    System.out.println(listeReponse);
        
	if(age < 50) {
		specialiste = "Orthoptiste";
	} else {
		specialiste = "Ophtalmologue";
	}

	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);
	
	/***********INDICATEUR**********/
	
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setExamens(1);
		
		indicateursRepository.save(i);
	} else {
		i.setExamens(i.getExamens()+1);
		indicateursRepository.save(i);
	}

	long ordonnance = 0;
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"OCT + Champ Visuel",utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));
	
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}


@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvControle")	
public String saveRdvControle(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	Date dateDeNaissance = utilisateur.getNaissance();
	
	/***********TRAITEMENT DE L AGE**********/
	int age = calculAge(dateDeNaissance);
	System.out.println(age);
	
	/***********RECUPERE ANNEE ORDONNANCE**********/
	System.out.println("RDV de contrôle");
	String tmp = "";
	String[] chaineRep;
	String anneeOrdonnance = "0";
	String specialiste = "";

	
	List<Reponse_choisi> list = reponse_choisiRepository.findRepByUser(utilisateur);
	ArrayList listeReponse = new ArrayList<String>();
	
	for (Reponse_choisi rep : list) {
		
		tmp = ""+rep.getReponse().getId_reponse();		
		chaineRep = tmp.split(":");
		for(String element: chaineRep) {
			System.out.println("Element split :" + element);
			listeReponse.add(element);
		}		 
		System.out.println(""+rep.getReponse().getId_reponse());
	}
	
	System.out.println(anneeOrdonnance);
    System.out.println(listeReponse);
    int ordonnance = Integer.parseInt(anneeOrdonnance);
    
//    ATTENTION CODE NON TESTE : QUAND FORMULAIRE TERMINE 
    
	if(!(listeReponse.contains("24")) && !(listeReponse.contains("71")) && !(listeReponse.contains("132")) && !(listeReponse.contains("151"))) {
		if(age < 50) {
				specialiste = "Orthoptiste";
		} else {
			specialiste = "Ophtalmologue";
		}
	} else {
		specialiste = "Ophtalmologue";
	}
	
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String aujourdhui = dateFormat.format(actuelle);
	
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv de contrôle",utilisateur.getCodePostal(),"demandé", new Date(),specialiste,""));
	
	//ajouter les indicateurs
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setControle(1);
		
		indicateursRepository.save(i);
	} else {
		i.setControle(i.getControle()+1);
		indicateursRepository.save(i);
	}
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/saveRdvFondOeil")	
public String saveRdvFondOeil(HttpServletRequest request,Model model){
	HttpSession session=request.getSession();
	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	model.addAttribute("utilisateur", utilisateur);
	String mail = utilisateur.getMail();	
	String tel = utilisateur.getTel();
	String nom = utilisateur.getNom();
	String prenom = utilisateur.getPrenom();
	
	long ordonnance = 0;
	rdvPatientRepository.save(new rdvPatient(mail,nom,prenom,tel,"rdv fond d'oeil",utilisateur.getCodePostal(),"demandé", new Date(),"Ophtalmologue",""));
	
	//ajouter les indicateurs
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	indicateurs i=new indicateurs();
	
	i=indicateursRepository.findOne(format1.format(d.getTime()));
	
	if(i == null){
		//si première visite de la journée
		i=new indicateurs(format1.format(d.getTime()));
		i.setVisites(1);
		i.setFondOeil(1);
		
		indicateursRepository.save(i);
	} else {
		i.setFondOeil(i.getFondOeil()+1);
		indicateursRepository.save(i);
	}
	
//	envoyerEmail(model,utilisateur);
	return "saveRdv";
}

//@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
//@RequestMapping(value="/rdvMaile/{id}", method=RequestMethod.GET)
//public String rdvMaile(@PathVariable(value="id") Long id){
//	
//	Calendar d=Calendar.getInstance();
//	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
//	rdvPatient r= rdvPatientRepository.findOne(id);
//	r.setStatus("mailé");
//	r.setDate(new Date());
//	r.setCommentaire(r.getCommentaire() + "<br/>Mail envoyé le " + format1.format(d.getTime()));
//	rdvPatientRepository.save(r);
//	return "redirect:/visualisation";	
//	
//}
@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/rdvAttente/{id}", method=RequestMethod.GET)
public String rdvTelephone(@PathVariable(value="id") Long id){
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	rdvPatient r= rdvPatientRepository.findOne(id);
	r.setStatus("En attente");
	r.setDate(new Date());
	r.setCommentaire(r.getCommentaire() + "<br/>En attente depuis "  + format1.format(d.getTime()));
	rdvPatientRepository.save(r);
	return "redirect:/visualisation";	
	
}
@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/rdvTraite/{id}", method=RequestMethod.GET)
public String rdvTraite(@PathVariable(value="id") Long id){
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	rdvPatient r= rdvPatientRepository.findOne(id);
	r.setStatus("Traité");
	r.setDate(new Date());
	r.setCommentaire(r.getCommentaire() + "<br/>Rendez-vous traité le " + format1.format(d.getTime()));
	rdvPatientRepository.save(r);
	return "redirect:/visualisation";	
	
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/rdvCommentaire/{id}", method=RequestMethod.GET)
public String rdvCommentaire(@PathVariable(value="id") Long id,String date,HttpServletRequest request){
	//trouve l'utilisateur connecté
  	HttpSession session=request.getSession();
  	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
  	String username=securityContext.getAuthentication().getName();
  	Utilisateurs u=utilisateurRepository.findByLogin(username);
  	
	Calendar d=Calendar.getInstance();
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	rdvPatient r= rdvPatientRepository.findOne(id);
	r.setStatus("Date du rdv : " + date);
	rdvPatientRepository.save(r);
	if(u.isAdmin()){ return "redirect:/visualisation";	} else return "redirect:/visualisationSecretaire";
	
}

@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_SECRETAIRE"})
@RequestMapping(value="/rdvMedecin/{id}", method=RequestMethod.GET)
public String rdvMedecin(@PathVariable(value="id") Long id,String orientation, HttpServletRequest request){
	//trouve l'utilisateur connecté
  	HttpSession session=request.getSession();
  	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
  	String username=securityContext.getAuthentication().getName();
  	Utilisateurs u=utilisateurRepository.findByLogin(username);
  	
	rdvPatient r= rdvPatientRepository.findOne(id);
//	r.setStatus("Traité");
	r.setOrientation(orientation);
	rdvPatientRepository.save(r);
	if(u.isAdmin()){ return "redirect:/visualisation";	} else return "redirect:/visualisationSecretaire";
	
}



}
