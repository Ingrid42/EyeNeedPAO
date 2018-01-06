package fr.eyeneed.web;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import fr.eyeneed.dao.IUserService;
import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.LogRepository;
import fr.eyeneed.dao.ParentRepository;
import fr.eyeneed.dao.RelationRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.RoleRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Form;
import fr.eyeneed.entities.Log;
import fr.eyeneed.entities.OnRegistrationCompleteEvent;
import fr.eyeneed.entities.Parent;
import fr.eyeneed.entities.Relation;
import fr.eyeneed.entities.Role;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.VerificationToken;
import fr.eyeneed.entities.bestVilles;
import fr.eyeneed.entities.fichier;
import fr.eyeneed.entities.indicateurs;
import fr.eyeneed.dao.RdvPatientRepository;
import fr.eyeneed.entities.Categorie;
import fr.eyeneed.entities.rdvPatient;
import java.util.Map;
import java.util.Set;


@SuppressWarnings({ "deprecation", "deprecation", "deprecation", "deprecation" })
@Controller
public class UtilisateursControlleur {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RelationRepository relationRepository;
	@Autowired
	private ParentRepository parentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ReponseRepository reponseRepository;
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
    private IUserService service;
	@Autowired
	private MessageSource messages;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private RdvPatientRepository rdvPatientRepository;
	@Autowired
	private IndicateursRepository indicateursRepository;
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(768);
	}
	
	@RequestMapping(value="/login")		
	public String indexLogin(Model model){
		//outils o=new outils(1);
		//System.out.println(o.maxWidth(2));
		//System.out.println(reponseRepository.maxWidth(2));
		List<Utilisateurs>  utilisateurs=utilisateurRepository.findAll();
		model.addAttribute("ListeUtilisateurs", utilisateurs);	
		return "login";
	}
	@RequestMapping(value="/test")		
	public String test(Model model){
		/*List<Object> villes= utilisateurRepository.findBestCodePostal();
		Object[] obj=(Object[]) villes.get(0);
		System.out.println( obj[0]);
		model.addAttribute("villes", villes);*/
		System.out.println(utilisateurRepository.plus60ans());
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		//trouve l'utilisateur connecté
	  	HttpSession session=request.getSession();
	  	SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
	  	String username=securityContext.getAuthentication().getName();
	  	Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    
	    Date date=new Date();
	    //log la déconnexion
	    Log log=new Log(date,"login",utilisateur);
		logRepository.save(log);
	    return "redirect:/index.html";
	}
	
	
	@RequestMapping(value="/inscription")	
	public String inscription(Model model){
			
			model.addAttribute("utilisateur",new Utilisateurs());
			model.addAttribute("parent",new Utilisateurs());
			return "form_inscription";
	}
	
	@RequestMapping(value="/verifierToken",method=RequestMethod.GET)	
	public String verifierToken(Model model, @RequestParam("token") String token,WebRequest request){
		VerificationToken verificationToken = service.getVerificationToken(token);
		Utilisateurs user = verificationToken.getUser();
		/*Locale locale = request.getLocale();
	    
	    
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	      return "badUser";//token pas présent
	        //return "redirect:/badUser.html";//token pas présent
	    }
	     
	    Utilisateurs user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return "badUser"; //Mauvais utilisateur
	    } */
	     
	    user.setActive(true); 
	    user=utilisateurRepository.save(user);
	    
		return "login";
	}
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_ROOT","ROLE_SECRETAIRE"})
	@RequestMapping(value="/home")	
	public String home(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		
		if(utilisateur.isSecretaire()){
			
			//ajouter les indicateurs
			Calendar d=Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			indicateurs i=new indicateurs();
			
			i=indicateursRepository.findOne(format1.format(d.getTime()));
			
			if(i == null){
				//si première visite de la journée
				i=new indicateurs(format1.format(d.getTime()));
				i.setVisites(1);
				indicateursRepository.save(i);
			} else {
				//i.setVisites(i.getVisites()+1);
				indicateursRepository.save(i);
			}
			
			model.addAttribute("indicateur", i);
			model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
			
			return "accueilSecretaire";
			
		} else if (utilisateur.isAdmin()){
			
			//ajouter les indicateurs
			Calendar d=Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			
			indicateurs i=new indicateurs();
			
			i=indicateursRepository.findOne(format1.format(d.getTime()));
			
			if(i == null){
				//si première visite de la journée
				i=new indicateurs(format1.format(d.getTime()));
				i.setVisites(1);
				indicateursRepository.save(i);
			} else {
				//i.setVisites(i.getVisites()+1);
				indicateursRepository.save(i);
			}
			
			model.addAttribute("indicateur", i);
			model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
			
			return "accueilAdmin";
			
		} else return "accueil";
		
		
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER","ROLE_ROOT"})
	@RequestMapping(value="/accueil")	
	public String accueil(HttpServletRequest request,Model model){
		return home(request,model);
	}
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/profile")	
	public String profile(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		return "profile";
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/ChoixDuRdv")	
	public String ChoixDuRdv(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		return "choixPriseRdv";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/prendreUnRdv")	
	public String prendreUnRdv(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		return "prendreUnRdv";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/rdvEnLigne")	
	public String rdvEnLigne(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		return "rdvEnLigne";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/carnetVisuel")	
	public String carnetVisuel(HttpServletRequest request,Model model){
		//trouve l'utilisateur connecté
		HttpSession session=request.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur);
		return "carnetVisuel";
	}
	@RequestMapping(value="/newPassword")	
	public String newPassword(Model model){
		//formulaire pour un nouveau mot de passe
		
		model.addAttribute("utilisateur",new Utilisateurs());
		return "newPassword";
	}
	@RequestMapping(value="/changePassword")	
	public String changePassword(Model model,Utilisateurs user,final Locale locale) throws MessagingException{
		//envoyer un mot de passe générer par mail
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml"); 
		user=utilisateurRepository.findByLogin( user.getLogin());
        String recipientAddress = user.getMail();
        String subject = "Nouveau mot de passe pour le site www.eyeneed.fr";
        String newPassword=  UUID.randomUUID().toString().substring(0,8);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user=utilisateurRepository.save(user);
       
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(mimeMessage, false, "utf-8");
        fichier f=new fichier("modelMail.html");
		System.out.println(f.template("<b>Voici votre nouveau mot de passe " + newPassword +"</b>"));
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom("no.reply@eyeneed.fr");
        email.setReplyTo("no.reply@eyeneed.fr");
        mimeMessage.setContent(f.template("<b>Voici votre nouveau mot de passe " + newPassword +"</b>"),"text/html");
        mailSender.send(mimeMessage);
		model.addAttribute("utilisateur",new Utilisateurs());
		model.addAttribute("mail",recipientAddress);
		return "newPasswordConfirmation";
	
	}

	@RequestMapping(value="/saveUtilisateur",method=RequestMethod.POST)	
	public String saveUtilisateur(Model model,Utilisateurs user,WebRequest request){
		Role roleUser = roleRepository.save(new Role("USER","SIMPLE UTILISATEUR"));
		List<Role> listeRole = new ArrayList<Role>();
		listeRole.add(roleUser);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRole(listeRole);
		user.setActive(true);
		user.setAccesQuestionnaire(true);
		user=utilisateurRepository.save(user);
		//System.out.println(user.getNom());
		
		/*if(relation.isEmpty()){
		parent.setPassword(parent.getNaissance().toString());
		List<Parent> relations = null;
		Parent p= parentRepository.save(new Parent(parent,user,relationRepository.findOne(relation)));
		relations.add(p);
		parent.setParents(relations);
		parent=utilisateurRepository.save(parent);
		}*/
		
		//VALIDATION PAR TOKEN
		/*try {
	        String appUrl = request.getContextPath();
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
	          (user, request.getLocale(), appUrl));
	    } catch (Exception me) {
	       // return erreur;//A IMPLEMENTER ERREUR
	    	System.out.println("ERREUR:"+me);
	    	return "form_inscription";
	    }*/
		//ajouter les indicateurs
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs i=new indicateurs();
		
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		
		if(i == null){
			//si première visite de la journée
			i=new indicateurs(format1.format(d.getTime()));
			i.setConnectes(1);
			indicateursRepository.save(i);
		} else {
			i.setConnectes(i.getConnectes()+1);
			indicateursRepository.save(i);
		}
		
		model.addAttribute("indicateur", i);
		model.addAttribute("nbreUtilisateurs",utilisateurRepository.findAll().size());
		return "confirmationLogin";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
	@RequestMapping(value="/updateUtilisateur",method=RequestMethod.POST)	
	public String updateUtilisateur(Model model,Utilisateurs user,WebRequest request,HttpServletRequest request1){
		HttpSession session=request1.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		Utilisateurs utilisateur1=utilisateurRepository.findByLogin(username);
		model.addAttribute("utilisateur", utilisateur1);

		System.out.println(user.getNom());
	    System.out.println(user.getLogin());
	    System.out.println(user.getPassword());
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		System.out.println(user.getPassword());
		user=utilisateurRepository.save(user);
		return "accueil";
	}
	
}